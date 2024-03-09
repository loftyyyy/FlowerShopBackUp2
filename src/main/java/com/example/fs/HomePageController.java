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

    // Labels
    @FXML private Label title1;
    @FXML private Label title2;
    @FXML private Label title3;
    @FXML private Label title4;

    // Panes
    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;

    // Images
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;
    @FXML private ImageView flower1;
    @FXML private ImageView flower2;
    @FXML private ImageView flower3;
    @FXML private ImageView flower4;
    @FXML private ImageView flower11;
    @FXML private ImageView flower21;
    @FXML private ImageView flower31;
    @FXML private ImageView flower41;

    // Meaning Labels
    @FXML private Label meaning1;
    @FXML private Label meaning2;
    @FXML private Label meaning3;
    @FXML private Label meaning4;

    // Flower Cards
    @FXML private Pane flowerCard1;
    @FXML private Pane flowerCard2;
    @FXML private Pane flowerCard3;
    @FXML private Pane flowerCard4;
    @FXML private Pane flowerCard11;
    @FXML private Pane flowerCard21;
    @FXML private Pane flowerCard31;
    @FXML private Pane flowerCard41;

    // Heart Icons
    @FXML private ImageView heartIcon1;
    @FXML private ImageView heartIcon2;
    @FXML private ImageView heartIcon3;
    @FXML private ImageView heartIcon4;
    @FXML private ImageView heartIcon11;
    @FXML private ImageView heartIcon21;
    @FXML private ImageView heartIcon31;
    @FXML private ImageView heartIcon41;

    // Cart Icons
    @FXML private ImageView cartIcon1;
    @FXML private ImageView cartIcon2;
    @FXML private ImageView cartIcon3;
    @FXML private ImageView cartIcon4;
    @FXML private ImageView cartIcon11;
    @FXML private ImageView cartIcon21;
    @FXML private ImageView cartIcon31;
    @FXML private ImageView cartIcon41;

    // Animation Constants
    private final Duration animationDuration = Duration.seconds(0.5);
    private final double zoomFactor = 1.2;

    // Image Resources
    private Image grayHeartImage;
    private Image redHeartImage;
    private Image grayCartImage;
    private Image blueCartImage;

    // Red/Blue Booleans for Heart and Cart Icons
    private boolean isRed1 = false;
    private boolean isRed2 = false;
    private boolean isRed3 = false;
    private boolean isRed4 = false;
    private boolean isRed11 = false;
    private boolean isRed21 = false;
    private boolean isRed31 = false;
    private boolean isRed41 = false;
    private boolean isBlue1 = false;
    private boolean isBlue2 = false;
    private boolean isBlue3 = false;
    private boolean isBlue4 = false;
    private boolean isBlue11 = false;
    private boolean isBlue21 = false;
    private boolean isBlue31 = false;
    private boolean isBlue41 = false;
    private MarketController marketController;

    public void setMarketController(MarketController marketController) {
        this.marketController = marketController;
    }

    @FXML
    public void initialize() {
        loadImages();
        setupHeartAndCartIcons();
    }

    private void loadImages() {
        grayHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/grayheart.png")));
        redHeartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/redheart.png")));
        grayCartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cartgrey.png")));
        blueCartImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cartblue.png")));
    }

    private void setupHeartAndCartIcons() {
        // Set initial images for heart and cart icons
        heartIcon1.setImage(grayHeartImage);
        heartIcon2.setImage(grayHeartImage);
        heartIcon3.setImage(grayHeartImage);
        heartIcon4.setImage(grayHeartImage);
        heartIcon11.setImage(grayHeartImage);
        heartIcon21.setImage(grayHeartImage);
        heartIcon31.setImage(grayHeartImage);
        heartIcon41.setImage(grayHeartImage);

        cartIcon1.setImage(grayCartImage);
        cartIcon2.setImage(grayCartImage);
        cartIcon3.setImage(grayCartImage);
        cartIcon4.setImage(grayCartImage);
        cartIcon11.setImage(grayCartImage);
        cartIcon21.setImage(grayCartImage);
        cartIcon31.setImage(grayCartImage);
        cartIcon41.setImage(grayCartImage);
    }

    // Mouse Entered Events
    @FXML public void pane1MouseEntered(MouseEvent event) { handlePaneMouseEntered(event, pane1); }
    @FXML public void pane2MouseEntered(MouseEvent event) { handlePaneMouseEntered(event, pane2); }
    @FXML public void pane3MouseEntered(MouseEvent event) { handlePaneMouseEntered(event, pane3); }
    @FXML public void pane4MouseEntered(MouseEvent event) { handlePaneMouseEntered(event, pane4); }
    @FXML public void flowerEnter1(MouseEvent event) { handleImageViewZoomIn(flower1); }
    @FXML public void flowerEnter2(MouseEvent event) { handleImageViewZoomIn(flower2); }
    @FXML public void flowerEnter3(MouseEvent event) { handleImageViewZoomIn(flower3); }
    @FXML public void flowerEnter4(MouseEvent event) { handleImageViewZoomIn(flower4); }

    @FXML public void flowerEnter11(MouseEvent event) { handleImageViewZoomIn(flower11); }
    @FXML public void flowerEnter21(MouseEvent event) { handleImageViewZoomIn(flower21); }
    @FXML public void flowerEnter31(MouseEvent event) { handleImageViewZoomIn(flower31); }
    @FXML public void flowerEnter41(MouseEvent event) { handleImageViewZoomIn(flower41); }

    // Mouse Exited Events
    @FXML public void pane1MouseExited(MouseEvent event) { handlePaneMouseExited(event, pane1); }
    @FXML public void pane2MouseExited(MouseEvent event) { handlePaneMouseExited(event, pane2); }
    @FXML public void pane3MouseExited(MouseEvent event) { handlePaneMouseExited(event, pane3); }
    @FXML public void pane4MouseExited(MouseEvent event) { handlePaneMouseExited(event, pane4); }
    @FXML public void flowerExit1(MouseEvent event) { handleImageViewZoomOut(flower1); }
    @FXML public void flowerExit2(MouseEvent event) { handleImageViewZoomOut(flower2); }
    @FXML public void flowerExit3(MouseEvent event) { handleImageViewZoomOut(flower3); }
    @FXML public void flowerExit4(MouseEvent event) { handleImageViewZoomOut(flower4); }
    @FXML public void flowerExit11(MouseEvent event) { handleImageViewZoomOut(flower11); }
    @FXML public void flowerExit21(MouseEvent event) { handleImageViewZoomOut(flower21); }
    @FXML public void flowerExit31(MouseEvent event) { handleImageViewZoomOut(flower31); }
    @FXML public void flowerExit41(MouseEvent event) { handleImageViewZoomOut(flower41); }

    // Flower Card Mouse Entered and Exited Events
    @FXML public void flowerCardEnter1(MouseEvent event) { handlePaneZoomIn(flowerCard1); }
    @FXML public void flowerCardExit1(MouseEvent event) { handlePaneZoomOut(flowerCard1); }
    @FXML public void flowerCardEnter2(MouseEvent event) { handlePaneZoomIn(flowerCard2); }
    @FXML public void flowerCardExit2(MouseEvent event) { handlePaneZoomOut(flowerCard2); }
    @FXML public void flowerCardEnter3(MouseEvent event) { handlePaneZoomIn(flowerCard3); }
    @FXML public void flowerCardExit3(MouseEvent event) { handlePaneZoomOut(flowerCard3); }
    @FXML public void flowerCardEnter4(MouseEvent event) { handlePaneZoomIn(flowerCard4); }
    @FXML public void flowerCardExit4(MouseEvent event) { handlePaneZoomOut(flowerCard4); }
    @FXML public void flowerCardEnter11(MouseEvent event) { handlePaneZoomIn(flowerCard11); }
    @FXML public void flowerCardExit11(MouseEvent event) { handlePaneZoomOut(flowerCard11); }

    @FXML public void flowerCardEnter21(MouseEvent event) { handlePaneZoomIn(flowerCard21); }
    @FXML public void flowerCardExit21(MouseEvent event) { handlePaneZoomOut(flowerCard21); }
    @FXML public void flowerCardEnter31(MouseEvent event) { handlePaneZoomIn(flowerCard31); }
    @FXML public void flowerCardExit31(MouseEvent event) { handlePaneZoomOut(flowerCard31); }
    @FXML public void flowerCardEnter41(MouseEvent event) { handlePaneZoomIn(flowerCard41); }
    @FXML public void flowerCardExit41(MouseEvent event) { handlePaneZoomOut(flowerCard41); }

    // Heart Icon Click Events
    @FXML void clickHeart1(MouseEvent event) { toggleHeartIcon(heartIcon1); playPumpAnimation(heartIcon1); }
    @FXML void clickHeart2(MouseEvent event) { toggleHeartIcon(heartIcon2); playPumpAnimation(heartIcon2); }
    @FXML void clickHeart3(MouseEvent event) { toggleHeartIcon(heartIcon3); playPumpAnimation(heartIcon3); }
    @FXML void clickHeart4(MouseEvent event) { toggleHeartIcon(heartIcon4); playPumpAnimation(heartIcon4); }
    @FXML void clickHeart11(MouseEvent event) { toggleHeartIcon(heartIcon11); playPumpAnimation(heartIcon11); }
    @FXML void clickHeart21(MouseEvent event) { toggleHeartIcon(heartIcon21); playPumpAnimation(heartIcon21); }
    @FXML void clickHeart31(MouseEvent event) { toggleHeartIcon(heartIcon31); playPumpAnimation(heartIcon31); }
    @FXML void clickHeart41(MouseEvent event) { toggleHeartIcon(heartIcon41); playPumpAnimation(heartIcon41); }

    // Cart Icon Click Events
    @FXML void clickCart1(MouseEvent event) { toggleCartIcon(cartIcon1); playPumpAnimation(cartIcon1); }
    @FXML void clickCart2(MouseEvent event) { toggleCartIcon(cartIcon2); playPumpAnimation(cartIcon2); }
    @FXML void clickCart3(MouseEvent event) { toggleCartIcon(cartIcon3); playPumpAnimation(cartIcon3); }
    @FXML void clickCart4(MouseEvent event) { toggleCartIcon(cartIcon4); playPumpAnimation(cartIcon4); }
    @FXML void clickCart11(MouseEvent event) { toggleCartIcon(cartIcon11); playPumpAnimation(cartIcon11); }
    @FXML void clickCart21(MouseEvent event) { toggleCartIcon(cartIcon21); playPumpAnimation(cartIcon21); }
    @FXML void clickCart31(MouseEvent event) { toggleCartIcon(cartIcon31); playPumpAnimation(cartIcon31); }
    @FXML void clickCart41(MouseEvent event) { toggleCartIcon(cartIcon41); playPumpAnimation(cartIcon41); }

    // Mouse Hover Effects on ImageViews and FlowerCards
    private void handleImageViewZoomIn(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, imageView);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();
    }

    private void handleImageViewZoomOut(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, imageView);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    private void handlePaneZoomIn(Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, pane);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();
    }

    private void handlePaneZoomOut(Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, pane);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    // Entrance Transition
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
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, image);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(animationDuration, meaning);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(animationDuration, pane);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    private void ExitTransition(ImageView image, Label meaning, Pane pane) {
        ScaleTransition scaleTransition = new ScaleTransition(animationDuration, image);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(animationDuration, meaning);
        meaningTranslate.setToY(0);

        ScaleTransition paneScale = new ScaleTransition(animationDuration, pane);
        paneScale.setToX(1);
        paneScale.setToY(1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    // Toggle Heart Icon and Cart Icon Methods
    private void toggleHeartIcon(ImageView heartIcon) {
        if (heartIcon.equals(heartIcon1)) {
            isRed1 = toggleRIconColor(heartIcon, isRed1);
        } else if (heartIcon.equals(heartIcon2)) {
            isRed2 = toggleRIconColor(heartIcon, isRed2);
        } else if (heartIcon.equals(heartIcon3)) {
            isRed3 = toggleRIconColor(heartIcon, isRed3);
        } else if (heartIcon.equals(heartIcon4)) {
            isRed4 = toggleRIconColor(heartIcon, isRed4);
        } else if (heartIcon.equals(heartIcon11)) {
            isRed11 = toggleRIconColor(heartIcon, isRed11);
        } else if (heartIcon.equals(heartIcon21)) {
            isRed21 = toggleRIconColor(heartIcon, isRed21);
        } else if (heartIcon.equals(heartIcon31)) {
            isRed31 = toggleRIconColor(heartIcon, isRed31);
        } else if (heartIcon.equals(heartIcon41)) {
            isRed41 = toggleRIconColor(heartIcon, isRed41);
        }
    }

    private boolean toggleRIconColor(ImageView icon, boolean isRed) {
        if (isRed) {
            icon.setImage(grayHeartImage);
            return false;
        } else {
            icon.setImage(redHeartImage);
            return true;
        }
    }

    private void toggleCartIcon(ImageView cartIcon) {
        if (cartIcon.equals(cartIcon1)) {
            isBlue1 = toggleBIconColor(cartIcon, isBlue1);
        } else if (cartIcon.equals(cartIcon2)) {
            isBlue2 = toggleBIconColor(cartIcon, isBlue2);
        } else if (cartIcon.equals(cartIcon3)) {
            isBlue3 = toggleBIconColor(cartIcon, isBlue3);
        } else if (cartIcon.equals(cartIcon4)) {
            isBlue4 = toggleBIconColor(cartIcon, isBlue4);
        } else if (cartIcon.equals(cartIcon11)) {
            isBlue11 = toggleBIconColor(cartIcon, isBlue11);
        } else if (cartIcon.equals(cartIcon21)) {
            isBlue21 = toggleBIconColor(cartIcon, isBlue21);
        } else if (cartIcon.equals(cartIcon31)) {
            isBlue31 = toggleBIconColor(cartIcon, isBlue31);
        } else if (cartIcon.equals(cartIcon41)) {
            isBlue41 = toggleBIconColor(cartIcon, isBlue41);
        }
    }

    private boolean toggleBIconColor(ImageView icon, boolean isBlue) {
        if (isBlue) {
            icon.setImage(grayCartImage);
            return false;
        } else {
            icon.setImage(blueCartImage);
            return true;
        }
    }


    // Heart and Cart Icon Animation
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
