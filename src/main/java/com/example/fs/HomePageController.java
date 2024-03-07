package com.example.fs;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class HomePageController {


    public Pane pane1;
    public Pane pane2;
    public Pane pane3;
    public Pane pane4;



    @FXML
    private AnchorPane pane1Set1;

    @FXML
    private AnchorPane pane2Set1;

    @FXML
    private AnchorPane pane3Set1;

    @FXML
    private AnchorPane pane4Set1;

    @FXML
    private AnchorPane pane1Set2;

    @FXML
    private AnchorPane pane2Set2;

    @FXML
    private AnchorPane pane3Set2;

    @FXML
    private AnchorPane pane4Set2;

    @FXML
    private AnchorPane pane1Set3;

    @FXML
    private AnchorPane pane2Set3;

    @FXML
    private AnchorPane pane3Set3;

    @FXML
    private AnchorPane pane4Set3;


    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML
    private Label meaning1;

    @FXML
    private Label meaning2;

    @FXML
    private Label meaning3;

    @FXML
    private Label meaning4;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private Label itemCount;

    private int itemCounter = 0;
    private int currentIndexSet1 = 1;
    private int currentIndexSet2 = 1;
    private int currentIndexSet3 = 1;
    private final Duration transitionDuration = Duration.seconds(0.5);

    private final double zoomFactor = 1.2;

    private final int currentIndex = 1;


    @FXML
    void backSet1(ActionEvent event) {
        if (currentIndexSet1 > 1) {
            switchPanes(currentIndexSet1, currentIndexSet1 - 1, "set1");
            currentIndexSet1--;
        }
    }

    @FXML
    void nextSet1(ActionEvent event) {
        if (currentIndexSet1 < 4) {
            switchPanes(currentIndexSet1, currentIndexSet1 + 1, "set1");
            currentIndexSet1++;
        }
    }

    @FXML
    void backSet2(ActionEvent event) {
        if (currentIndexSet2 > 1) {
            switchPanes(currentIndexSet2, currentIndexSet2 - 1, "set2");
            currentIndexSet2--;
        }
    }

    @FXML
    void nextSet2(ActionEvent event) {
        if (currentIndexSet2 < 4) {
            switchPanes(currentIndexSet2, currentIndexSet2 + 1, "set2");
            currentIndexSet2++;
        }
    }

    @FXML
    void backSet3(ActionEvent event) {
        if (currentIndexSet3 > 1) {
            switchPanes(currentIndexSet3, currentIndexSet3 - 1, "set3");
            currentIndexSet3--;
        }
    }

    @FXML
    void nextSet3(ActionEvent event) {
        if (currentIndexSet3 < 4) {
            switchPanes(currentIndexSet3, currentIndexSet3 + 1, "set3");
            currentIndexSet3++;
        }
    }

    private void switchPanes(int currentIndex, int nextIndex, String set) {
        AnchorPane currentPane = getPaneByIndex(currentIndex, set);
        AnchorPane nextPane = getPaneByIndex(nextIndex, set);

        assert currentPane != null;
        assert nextPane != null;

        double currentToX = currentIndex < nextIndex ? -currentPane.getWidth() : currentPane.getWidth();
        double nextFromX = currentIndex < nextIndex ? nextPane.getWidth() : -nextPane.getWidth();

        TranslateTransition currentTransition = new TranslateTransition(transitionDuration, currentPane);
        currentTransition.setToX(currentToX);
        currentTransition.setOnFinished(e -> currentPane.setVisible(false));
        currentTransition.play();

        nextPane.setVisible(true);
        TranslateTransition nextTransition = new TranslateTransition(transitionDuration, nextPane);
        nextTransition.setFromX(nextFromX);
        nextTransition.setToX(0);
        nextTransition.play();
    }

    private AnchorPane getPaneByIndex(int index, String set) {
        AnchorPane pane = null;
        if (set.equals("set1")) {
            switch (index) {
                case 1 -> pane = pane4Set1;
                case 2 -> pane = pane3Set1;
                case 3 -> pane = pane2Set1;
                case 4 -> pane = pane1Set1;
            }
        } else if (set.equals("set2")) {
            switch (index) {
                case 1 -> pane = pane4Set2;
                case 2 -> pane = pane3Set2;
                case 3 -> pane = pane2Set2;
                case 4 -> pane = pane1Set2;
            }
        } else if (set.equals("set3")) {
            switch (index) {
                case 1 -> pane = pane4Set3;
                case 2 -> pane = pane3Set3;
                case 3 -> pane = pane2Set3;
                case 4 -> pane = pane1Set3;
            }
        }
        return pane;
    }

    public void addToCart(ActionEvent actionEvent) {
        itemCounter += 1;
        itemCount.setText(String.valueOf(itemCounter));
    }

    public void setMarketController(MarketController marketController) {
    }

    @FXML
    private Button enterShopButton;

    @FXML
    public void initialize() {
        String all = Objects.requireNonNull(getClass().getResource("All.css")).toExternalForm();
        Scene scene = enterShopButton.getScene();
        if (scene != null) {
            scene.getStylesheets().add(all);
        }
    }

    @FXML
    public void pane1MouseEntered(MouseEvent mouseEvent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image1);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning1);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane1);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    @FXML
    public void pane1MouseExited(MouseEvent mouseEvent) {
        // Call reset zoom animation for image1
        transition(image1, meaning1, pane1);
    }

    private void transition(ImageView image1, Label meaning1, Pane pane1) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image1);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning1);
        meaningTranslate.setToY(0);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane1);
        paneScale.setToX(1);
        paneScale.setToY(1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }


    @FXML
    public void pane2MouseEntered(MouseEvent mouseEvent) {
        // Call zoom in animation for image2
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image2);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning2);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane2);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    @FXML
    public void pane2MouseExited(MouseEvent mouseEvent) {
        // Call reset zoom animation for image2
        transition(image2, meaning2, pane2);
    }


    @FXML
    public void pane3MouseEntered(MouseEvent mouseEvent) {
        // Call zoom in animation for image3
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image3);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning3);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane3);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    @FXML
    public void pane3MouseExited(MouseEvent mouseEvent) {
        // Call reset zoom animation for image3
        transition(image3, meaning3, pane3);
    }


    @FXML
    public void pane4MouseEntered(MouseEvent mouseEvent) {
        // Call zoom in animation for image4
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), image4);
        scaleTransition.setToX(zoomFactor);
        scaleTransition.setToY(zoomFactor);
        scaleTransition.play();

        TranslateTransition meaningTranslate = new TranslateTransition(Duration.seconds(0.5), meaning4);
        meaningTranslate.setToY(20);

        ScaleTransition paneScale = new ScaleTransition(Duration.seconds(0.5), pane4);
        paneScale.setToX(1.1);
        paneScale.setToY(1.1);

        ParallelTransition parallelTransition = new ParallelTransition(meaningTranslate, paneScale);
        parallelTransition.play();
    }

    @FXML
    public void pane4MouseExited(MouseEvent mouseEvent) {
        // Call reset zoom animation for image4
        transition(image4, meaning4, pane4);
    }


}



