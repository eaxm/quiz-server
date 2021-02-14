package route;

import com.google.gson.Gson;
import model.UserService;
import model.json.AuthInfo;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Registers an user
 */
public class RegisterRoute implements Route {

    private UserService userService;

    public RegisterRoute(){
        userService = new UserService();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String body = request.body();
        Gson gson = new Gson();
        AuthInfo authInfo = gson.fromJson(body, AuthInfo.class);

        if(userService.exists(authInfo.getUsername())){

            // TODO
            response.status(409);
            return "user already exists";
        }

        userService.createUser(authInfo);
        response.status(200);
        return "user created";
    }
}
