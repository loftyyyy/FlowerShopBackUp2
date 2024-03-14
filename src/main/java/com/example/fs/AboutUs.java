package com.example.fs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutUs extends Application {
    private Object marketController;

    @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutUsPage.fxml"));
            Parent root = loader.load();
            AboutUsController controller = loader.getController();
            controller.initialize();

            stage.setTitle("Fauget Crochet Studio");
            stage.setScene(new Scene(root));
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }
