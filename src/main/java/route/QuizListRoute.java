package route;

import com.google.gson.Gson;
import model.json.Quiz;
import model.QuizService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;


/**
 * Returns all quizzes in json format
 */
public class QuizListRoute implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        QuizService quizService = new QuizService();
        List<Quiz> list = quizService.getQuizList();

        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
