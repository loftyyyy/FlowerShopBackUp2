package com.example.fs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Checkout;

public class CheckoutController {

    @FXML
    private ImageView flowerImg;

    @FXML
    private Label flowerName;

    @FXML
    private Label pricePerQuantityLabel;

    @FXML
    private Label quantityLabel;


    @FXML
    private Label totalPriceLabel;
    private Checkout checkout;

    public void setData(Checkout checkout){

        this.checkout = checkout;
        flowerName.setText(checkout.getFlowerName());
        pricePerQuantityLabel.setText("₱" + String.valueOf(checkout.getPricePerQuantity()));
        quantityLabel.setText(String.valueOf(checkout.getQuantity()));
        totalPriceLabel.setText("₱" + String.valueOf(checkout.getTotalPrice()));
        Image image = new Image(getClass().getResourceAsStream(checkout.getFlowerImage()));
        flowerImg.setImage(image);
    }


}