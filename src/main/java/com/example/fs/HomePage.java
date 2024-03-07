package com.example.fs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class HomePage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        Parent root = loader.load();
        HomePageController controller = loader.getController();
        controller.initialize();
        stage.setTitle("Fauget Crochet Studio");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
