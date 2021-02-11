package route;

import com.google.gson.Gson;
import model.json.AuthInfo;
import model.json.Session;
import model.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.naming.AuthenticationException;

/**
 * Handles login attempt of a user
 */
public class LoginRoute implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {

        String body = request.body();
        Gson gson = new Gson();
        AuthInfo authInfo = gson.fromJson(body, AuthInfo.class);
        UserService userService = new UserService();

        try {
            if (!userService.exists(authInfo.getUsername())) {
                // userService.createUser(authInfo); // TODO: Move to register route
                response.status(401);
                return "Invalid credentials";
            }

            Session session = userService.auth(authInfo);
            String jsonResponse = gson.toJson(session);
            return jsonResponse;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            response.status(401);
            return "Invalid credentials";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(401);
            return "Authentication failed";
        }


    }
}
