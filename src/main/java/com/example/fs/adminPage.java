package com.example.fs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class adminPage extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SignupPage.class.getResource("adminPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fauget Crochet Studio");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}
