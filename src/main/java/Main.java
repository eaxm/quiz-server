import route.LoginRoute;
import route.RegisterRoute;
import route.QuizListRoute;
import route.VersionRoute;
import spark.Spark;

/**
 * Main class
 */
public class Main {

    private final static int PORT = 8000;
    private final static String VERSION = "1.0";

    public static void main(String[] args) {

        // TODO: create database tables

        Spark.port(PORT);

        Spark.get("/version", new VersionRoute(VERSION));
        Spark.post("/login", new LoginRoute());
        Spark.post("/register", new RegisterRoute());


        Spark.path("/app", () -> {
            // TODO: session filter

            Spark.get("/list", new QuizListRoute());
        });

        System.out.printf("Quiz server version %s listening on port %d%n", VERSION, PORT);

    }
}
