package com.example.fs;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;

public class HomePageController {

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
    private ScrollPane mainScrollPane;

    @FXML
    private Label itemCount;

    private int itemCounter = 0;
    private int currentIndexSet1 = 1;
    private int currentIndexSet2 = 1;
    private int currentIndexSet3 = 1;
    private final Duration transitionDuration = Duration.seconds(0.5);
    private MarketController marketController;

    public void setMarketController(MarketController marketController) {
        this.marketController = marketController;
//        this.mainPageController.hidePopups();
//        aboutUsPage__gojoImage.setOpacity(0);
//        aboutUsPage__founderBigTitle1.setOpacity(0);
//        aboutUsPage__founderBigTitle2.setOpacity(0);
//        aboutUsPage__iconicContainer.setOpacity(0);
    }

    public void goToShopNowPage() throws IOException {
        marketController.shopNowPage();
    }


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

    public void initialize() {
    }
}
