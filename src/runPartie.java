
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class runPartie extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        View view = new View(model);
        Controller controller=new Controller(view,model);

        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }
}
