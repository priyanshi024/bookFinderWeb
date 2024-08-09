package ca.georgiancollege.assignment2gc2506;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookFinderApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ensure the path to the FXML file is correct
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ca/georgiancollege/assignment2gc2506/book-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Book Finder");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
