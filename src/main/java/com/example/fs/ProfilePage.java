package com.example.fs;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProfilePage extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
        stage.setTitle("Fauget Crochet Studio");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {


        launch();
    }

    public void LogIn(ActionEvent actionEvent) {

    }
}
