package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AdminPageController {

    @FXML
    private TextField hexCodeTF;
    @FXML
    private Pane colorpane;
    @FXML
    private Button addproduct;
    private ImageView clickAddProduct;
    private Color color;

    @FXML
    public void initialize() {
        hexCodeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Color color = Color.web(newValue);
                colorpane.setStyle("-fx-background-color: " + toRGBCode(color) + ";");
            } catch (IllegalArgumentException e) {
                // Handle invalid hex code
            }
        });
    }

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

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
