package model;

import model.json.Quiz;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles quiz interactions with the database
 */
public class QuizService {

    /**
     * Returns all quizzes from the database
     *
     * @return quiz list
     * @throws SQLException
     */
    public List<Quiz> getQuizList() throws SQLException {
        DBConnection dbc = DBConnection.getInstance();
        Connection conn = DriverManager.getConnection(dbc.getURL(), dbc.getProperties());
        String query = "SELECT quiz_name FROM quiz;";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        List<Quiz> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Quiz(rs.getString("quiz_name")));
        }
        conn.close();

        return list;
    }
}
