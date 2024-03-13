package com.example.fs;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class SigninPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SigninPage.class.getResource("SigninPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fauget Crochet Studio");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        launch();
    }

    public void LogIn(ActionEvent actionEvent) {
    }
}