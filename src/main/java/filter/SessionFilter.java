package filter;

import model.UserService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.sql.SQLDataException;

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
            try {
                // TODO: Check for expiration
                long userId = userService.getUserIdBySession(session);
                System.out.printf("userId %d made a %s request at %s%n", userId, request.requestMethod(), request.pathInfo()); // Temporary logging
            } catch (SQLDataException e) {
                System.out.println("Invalid session");
                Spark.halt();
            }

        } else {
            System.out.println("Session header does not exist");
            Spark.halt();
        }


    }
}
