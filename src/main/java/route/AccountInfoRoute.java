package route;

import com.google.gson.Gson;
import model.UserService;
import model.json.AccountInfo;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Timestamp;

public class AccountInfoRoute implements Route {

    private UserService userService;


    public AccountInfoRoute(){
        this.userService = new UserService();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String session = request.headers("session");
        long userId = userService.getUserIdBySession(session);
        String username = userService.getUsernameByUserId(userId);
        Timestamp registerDate = userService.getRegisterDateByUserId(userId);

        AccountInfo accountInfo = new AccountInfo(username, registerDate);
        Gson gson = new Gson();

        return gson.toJson(accountInfo);
    }
}
