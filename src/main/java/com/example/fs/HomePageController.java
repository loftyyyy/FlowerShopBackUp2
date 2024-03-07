package com.example.fs;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class HomePageController {

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private ImageView image1;

    @FXML
    private Label meaning1;

    @FXML
    private Label title1;

    @FXML
    private ImageView image2;

    @FXML
    private Label meaning2;

    @FXML
    private Label title2;

    @FXML
    private ImageView image3;

    @FXML
    private Label meaning3;

    @FXML
    private Label title3;

    @FXML
    private ImageView image4;

    @FXML
    private Label meaning4;

    @FXML
    public void pane1MouseEntered(MouseEvent mouseEvent) {
        handlePaneMouseEntered(mouseEvent, pane1);
    }

    @FXML
    public void pane1MouseExited(MouseEvent mouseEvent) {
        handlePaneMouseExited(mouseEvent, pane1);
    }

    @FXML
    public void pane2MouseEntered(MouseEvent mouseEvent) {
        handlePaneMouseEntered(mouseEvent, pane2);
    }

    @FXML
    public void pane2MouseExited(MouseEvent mouseEvent) {
        handlePaneMouseExited(mouseEvent, pane2);
    }

    @FXML
    public void pane3MouseEntered(MouseEvent mouseEvent) {
        handlePaneMouseEntered(mouseEvent, pane3);
    }

    @FXML
    public void pane3MouseExited(MouseEvent mouseEvent) {
        handlePaneMouseExited(mouseEvent, pane3);
    }

    @FXML
    public void pane4MouseEntered(MouseEvent mouseEvent) {
        handlePaneMouseEntered(mouseEvent, pane4);
    }

    @FXML
    public void pane4MouseExited(MouseEvent mouseEvent) {
        handlePaneMouseExited(mouseEvent, pane4);
    }


    private void handlePaneMouseEntered(MouseEvent event, Pane pane) {
        ImageView image = getImageForPane(pane);
        Label meaning = getMeaningForPane(pane);
        EntranceTransition(image, meaning, pane);
    }

    private void handlePaneMouseExited(MouseEvent event, Pane pane) {
        ImageView image = getImageForPane(pane);
        Label meaning = getMeaningForPane(pane);
        ExitTransition(image, meaning, pane);
    }

    private ImageView getImageForPane(Pane pane) {
        if (pane.equals(pane1)) return image1;
        if (pane.equals(pane2)) return image2;
        if (pane.equals(pane3)) return image3;
        if (pane.equals(pane4)) return image4;
        return null;
    }

    private Label getMeaningForPane(Pane pane) {
        if (pane.equals(pane1)) return meaning1;
        if (pane.equals(pane2)) return meaning2;
        if (pane.equals(pane3)) return meaning3;
        if (pane.equals(pane4)) return meaning4;
        return null;
    }

    private void EntranceTransition(ImageView image, Label meaning, Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image);
        double zoomFactor = 1.2;
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    private void ExitTransition(ImageView image, Label meaning, Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning);
        meaningTranslate.setToY(0);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane);
        paneScale.setToX(1);
        paneScale.setToY(1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    @FXML
    private ImageView heartIcon1;

    @FXML
    private ImageView heartIcon2;

    @FXML
    private ImageView heartIcon3;

    @FXML
    private ImageView heartIcon4;

    private Image grayHeartImage;
    private Image redHeartImage;
    private boolean isRed1 = false;
    private boolean isRed2 = false;
    private boolean isRed3 = false;
    private boolean isRed4 = false;

    @FXML
    void clickHeart1(MouseEvent event) {
        toggleHeartIcon(heartIcon1);
        playPumpAnimation(heartIcon1);
    }

    @FXML
    void clickHeart2(MouseEvent event) {
        toggleHeartIcon(heartIcon2);
        playPumpAnimation(heartIcon2);
    }

    @FXML
    void clickHeart3(MouseEvent event) {
        toggleHeartIcon(heartIcon3);
        playPumpAnimation(heartIcon3);
    }

    @FXML
    void clickHeart4(MouseEvent event) {
        toggleHeartIcon(heartIcon4);
        playPumpAnimation(heartIcon4);
    }

    private void toggleHeartIcon(ImageView heartIcon) {
        if (heartIcon.equals(heartIcon1)) {
            if (isRed1) {
                heartIcon.setImage(grayHeartImage);
                isRed1 = false;
            } else {
                heartIcon.setImage(redHeartImage);
                isRed1 = true;
            }
        } else if (heartIcon.equals(heartIcon2)) {
            if (isRed2) {
                heartIcon.setImage(grayHeartImage);
                isRed2 = false;
            } else {
                heartIcon.setImage(redHeartImage);
                isRed2 = true;
            }
        } else if (heartIcon.equals(heartIcon3)) {
            if (isRed3) {
                heartIcon.setImage(grayHeartImage);
                isRed3 = false;
            } else {
                heartIcon.setImage(redHeartImage);
                isRed3 = true;
            }
        } else if (heartIcon.equals(heartIcon4)) {
            if (isRed4) {
                heartIcon.setImage(grayHeartImage);
                isRed4 = false;
            } else {
                heartIcon.setImage(redHeartImage);
                isRed4 = true;
            }
        }
    }

    private void playPumpAnimation(ImageView heartIcon) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(heartIcon.scaleXProperty(), 1), new KeyValue(heartIcon.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(heartIcon.scaleXProperty(), 1.2), new KeyValue(heartIcon.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(heartIcon.scaleXProperty(), 1), new KeyValue(heartIcon.scaleYProperty(), 1))
        );
        timeline.play();
    }

    @FXML
    public void initialize() {
        // Load images
        grayHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/grayheart.png")));
        redHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/redheart.png")));

        // Set initial image
        heartIcon1.setImage(grayHeartImage);
        heartIcon2.setImage(grayHeartImage);
        heartIcon3.setImage(grayHeartImage);
        heartIcon4.setImage(grayHeartImage);
    }
}
