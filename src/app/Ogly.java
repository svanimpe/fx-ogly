package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * This example shows how to use the MVC pattern in JavaFX using FXML and
 * Scene Builder, how to use properties and binding and how to style nodes
 * using CSS.
 */
public class Ogly extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        OglyModel model = new OglyModel();
        OglyPane root = new OglyPane(model);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ogly");
        stage.show();
    }
}
