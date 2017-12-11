
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Guillaume on 16/10/2017.
 */
public class RunPartie extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();

        primaryStage.setScene(view.getSceneMenu());
        primaryStage.show();
    }

    public static void newGame(Stage primaryStage, int nbJoueur){
        Model model = new Model(nbJoueur);
        View view = new View(model);
        Controller controller=new Controller(view,model);

        primaryStage.setScene(view.getSceneJeu());
        primaryStage.show();
    }


}
