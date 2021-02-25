package filter;

import model.UserService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;


/**
 * Checks if session sent by an user is valid
 */
public class SessionFilter implements Filter {

    private UserService userService;

    public SessionFilter() {
        userService = new UserService();
    }

    @Override
    public void handle(Request request, Response response) throws Exception {

        if (request.headers().contains("session")) {
            String session = request.headers("session");
            System.out.println("Session: " + session);

            if (userService.isUserSessionValid(session, request.ip())) {
                return;
            } else {
                System.out.println("user session is not valid");
                Spark.halt();
            }

        } else {
            System.out.println("Session header does not exist");
            Spark.halt();
        }


    }
}
