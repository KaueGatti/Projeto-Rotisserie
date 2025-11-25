package my.company.projetorotisseriejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {


        try {
            Parent root = loadFXML("Inicio.fxml");
            scene = new Scene(root);

            scene.getStylesheets().addAll(
                    getClass().getResource("/styles/main.css").toExternalForm(),
                    getClass().getResource("/styles/colors.css").toExternalForm(),
                    getClass().getResource("/styles/buttons.css").toExternalForm()
            );

            stage.setScene(scene);

            stage.setMaximized(true);

            stage.show();

        } catch (Exception e) {
            System.out.println("Erro start: " + e);
            e.printStackTrace();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}