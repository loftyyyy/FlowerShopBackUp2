package com.example.fs;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    private MarketController marketController;

    public void setMarketController(MarketController marketController) {
        this.marketController = marketController;
    }

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
    private ImageView flower1;
    @FXML
    private ImageView flower2;
    @FXML
    private ImageView flower3;
    @FXML
    private ImageView flower4;

    @FXML
    private Pane flowerCard1;
    @FXML
    private Pane flowerCard2;
    @FXML
    private Pane flowerCard3;
    @FXML
    private Pane flowerCard4;

    @FXML
    public void flowerEnter1(MouseEvent event) {
        handleImageViewZoomIn(flower1);
    }

    @FXML
    public void flowerExit1(MouseEvent event) {
        handleImageViewZoomOut(flower1);
    }

    @FXML
    public void flowerEnter2(MouseEvent event) {
        handleImageViewZoomIn(flower2);
    }

    @FXML
    public void flowerExit2(MouseEvent event) {

        handleImageViewZoomOut(flower2);
    }

    @FXML
    public void flowerEnter3(MouseEvent event) {

        handleImageViewZoomIn(flower3);
    }

    @FXML
    public void flowerExit3(MouseEvent event) {

        handleImageViewZoomOut(flower3);
    }

    @FXML
    public void flowerEnter4(MouseEvent event) {

        handleImageViewZoomIn(flower4);
    }

    @FXML
    public void flowerExit4(MouseEvent event) {

        handleImageViewZoomOut(flower4);
    }

    @FXML
    public void flowerCardEnter1(MouseEvent event) {
        handlePaneZoomIn(flowerCard1);
    }

    @FXML
    public void flowerCardExit1(MouseEvent event) {
        handlePaneZoomOut(flowerCard1);
    }
    @FXML
    public void flowerCardEnter2(MouseEvent event) {

        handlePaneZoomIn(flowerCard2);
    }

    @FXML
    public void flowerCardExit2(MouseEvent event) {

        handlePaneZoomOut(flowerCard2);
    }

    @FXML
    public void flowerCardEnter3(MouseEvent event) {

        handlePaneZoomIn(flowerCard3);
    }

    @FXML
    public void flowerCardExit3(MouseEvent event) {

        handlePaneZoomOut(flowerCard3);
    }

    @FXML
    public void flowerCardEnter4(MouseEvent event) {

        handlePaneZoomIn(flowerCard4);
    }

    @FXML
    public void flowerCardExit4(MouseEvent event) {

        handlePaneZoomOut(flowerCard4);
    }

    private void handleImageViewZoomIn(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), imageView);
        double zoomFactor = 1.2;
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();
    }

    private void handleImageViewZoomOut(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), imageView);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    private void handlePaneZoomIn(Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), pane);
        double zoomFactor = 1.2;
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();
    }

    private void handlePaneZoomOut(Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), pane);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
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
    private ImageView cartIcon1;
    @FXML
    private ImageView cartIcon2;
    @FXML
    private ImageView cartIcon3;
    @FXML
    private ImageView cartIcon4;

    private Image grayCartImage;
    private Image blueCartImage;
    private boolean isBlue1 = false;
    private boolean isBlue2 = false;
    private boolean isBlue3 = false;
    private boolean isBlue4 = false;

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

    @FXML
    void clickCart1(MouseEvent event) {
        toggleCartIcon(cartIcon1);
        playPumpAnimation(cartIcon1);
    }

    @FXML
    void clickCart2(MouseEvent event) {
        toggleCartIcon(cartIcon2);
        playPumpAnimation(cartIcon2);
    }

    @FXML
    void clickCart3(MouseEvent event) {
        toggleCartIcon(cartIcon3);
        playPumpAnimation(cartIcon3);
    }

    @FXML
    void clickCart4(MouseEvent event) {
        toggleCartIcon(cartIcon4);
        playPumpAnimation(cartIcon4);
    }

    private void toggleCartIcon(ImageView cartIcon) {
        if (cartIcon.equals(cartIcon1)) {
            if (isBlue1) {
                cartIcon.setImage(grayCartImage);
                isBlue1 = false;
            } else {
                cartIcon.setImage(blueCartImage);
                isBlue1 = true;
            }
        } else if (cartIcon.equals(cartIcon2)) {
            if (isBlue2) {
                cartIcon.setImage(grayCartImage);
                isBlue2 = false;
            } else {
                cartIcon.setImage(blueCartImage);
                isBlue2 = true;
            }
        } else if (cartIcon.equals(cartIcon3)) {
            if (isBlue3) {
                cartIcon.setImage(grayCartImage);
                isBlue3 = false;
            } else {
                cartIcon.setImage(blueCartImage);
                isBlue3 = true;
            }
        } else if (cartIcon.equals(cartIcon4)) {
            if (isBlue4) {
                cartIcon.setImage(grayCartImage);
                isBlue4 = false;
            } else {
                cartIcon.setImage(blueCartImage);
                isBlue4 = true;
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
        grayHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/grayheart.png")));
        redHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/redheart.png")));
        grayCartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cartgrey.png")));
        blueCartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cartblue.png")));

        heartIcon1.setImage(grayHeartImage);
        heartIcon2.setImage(grayHeartImage);
        heartIcon3.setImage(grayHeartImage);
        heartIcon4.setImage(grayHeartImage);
        cartIcon1.setImage(grayCartImage);
        cartIcon2.setImage(grayCartImage);
        cartIcon3.setImage(grayCartImage);
        cartIcon4.setImage(grayCartImage);

    }

}
