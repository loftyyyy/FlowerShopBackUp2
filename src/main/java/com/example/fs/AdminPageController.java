package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AdminPageController {

    @FXML
    private Button addproduct;
    private ImageView clickAddProduct;


    public void clickAddProduct(MouseEvent mouseEvent) {
        playPumpAnimation(clickAddProduct);
    }

    private void playPumpAnimation(ImageView icon) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(icon.scaleXProperty(), 1)),
                new KeyFrame(Duration.ZERO, new KeyValue(icon.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.15), new KeyValue(icon.scaleXProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.15), new KeyValue(icon.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(icon.scaleXProperty(), 1)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(icon.scaleYProperty(), 1))
        );
        timeline.play();
    }
}
