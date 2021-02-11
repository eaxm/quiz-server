package route;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Returns the current version
 */
public class VersionRoute implements Route {

    private String version;

    public VersionRoute(String version) {
        this.version = version;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return version;
    }
}
