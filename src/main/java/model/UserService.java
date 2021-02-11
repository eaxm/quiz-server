package model;

import model.json.AuthInfo;
import model.json.Session;
import util.DBConnection;
import util.HashTool;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Base64;
import java.util.Date;


/**
 * Handles user interactions with the database
 */
public class UserService {

    /**
     * Checks if a given username exists
     *
     * @param username
     * @return true if username exists
     * @throws SQLException
     */
    public boolean exists(String username) throws SQLException {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = DriverManager.getConnection(dbc.getURL(), dbc.getProperties());

        String query = "SELECT * FROM account WHERE username = ?;";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();


        boolean userExists = rs.next();
        conn.close();

        return userExists;
    }

    /**
     * Checks if user credentials are valid and returns a session
     *
     * @param authInfo
     * @return session
     * @throws SQLException
     * @throws AuthenticationException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public Session auth(AuthInfo authInfo) throws SQLException, AuthenticationException, InvalidKeySpecException, NoSuchAlgorithmException {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = DriverManager.getConnection(dbc.getURL(), dbc.getProperties());
        String authQuery = "SELECT * FROM account WHERE username = ?;";

        PreparedStatement ps = conn.prepareStatement(authQuery);
        ps.setString(1, authInfo.getUsername());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String salt = rs.getString("salt");
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            String hashedPassword = rs.getString("password");
            hashedPassword = new String(Base64.getDecoder().decode(hashedPassword));

            String inputPasswordHashed = new String(HashTool.hashPassword(authInfo.getPassword(), saltBytes));

            if (hashedPassword.equals(inputPasswordHashed)) {
                Session session = new Session("test-session"); // TODO: Create session
                conn.close();
                return session;
            }

        }
        conn.close();
        throw new AuthenticationException("Invalid credentials");

    }

    /**
     * Creates a new user
     *
     * @param authInfo
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public void createUser(AuthInfo authInfo) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = DriverManager.getConnection(dbc.getURL(), dbc.getProperties());

        byte[] salt = HashTool.generateSalt();
        String saltStr = Base64.getEncoder().encodeToString(salt);
        byte[] hashedPassword = HashTool.hashPassword(authInfo.getPassword(), salt);
        String hashedPasswordStr = Base64.getEncoder().encodeToString(hashedPassword);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO account(username, password, register_date, salt) VALUES(?, ? ,?, ?)");
        ps.setString(1, authInfo.getUsername());
        ps.setString(2, hashedPasswordStr);
        ps.setTimestamp(3, new Timestamp(new Date().getTime()));
        ps.setString(4, saltStr);

        ps.executeUpdate();
        conn.close();
    }
}