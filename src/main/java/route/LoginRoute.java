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

    private UserService userService;

    public LoginRoute(){
        userService = new UserService();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String body = request.body();
        Gson gson = new Gson();
        AuthInfo authInfo = gson.fromJson(body, AuthInfo.class);

        try {
            if (!userService.exists(authInfo.getUsername())) {
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
