package com.example.fs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Flower;

import java.io.IOException;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;


    private Flower flower;

    private MarketListener marketListener;

    public void clickEvent(MouseEvent mouseEvent){
        marketListener.onClickListener(flower);
    }

    public void setData(Flower flower, MarketListener marketListener){

        this.flower = flower;
        this.marketListener = marketListener;
        nameLabel.setText(flower.getName());
        priceLabel.setText("₱" + flower.getPrice());
        Image image = new Image(getClass().getResourceAsStream(flower.getImgSrc()));
        img.setImage(image);



    }



}
