package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Checkout;
import model.Flower;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {


    @FXML
    private GridPane grid;

    @FXML
    private Label grandTotal;

    @FXML
    private Text subTotal;

    String url = "jdbc:postgresql://localhost:5435/postgres";

    String dbUsername = "postgres";
    String dbPassword = "admin";
    MarketController marketController;

    private List<Checkout> checkouts = new ArrayList<>();

    private String retrieveCheckoutQuerty = "SELECT * FROM checkout";


    public void setMarketController(MarketController marketController) {
        this.marketController = marketController;
//        this.mainPageController.hidePopups();
//        aboutUsPage__gojoImage.setOpacity(0);
//        aboutUsPage__founderBigTitle1.setOpacity(0);
//        aboutUsPage__founderBigTitle2.setOpacity(0);
//        aboutUsPage__iconicContainer.setOpacity(0);
    }
    private ArrayList<Checkout> getCheckout(){

        List<Checkout> checkouts = new ArrayList<>();
        Checkout checkout;
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveCheckoutQuerty);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){

                String name = rst.getString("name");
                String flowerImage = rst.getString("flowerimage");
                int quantity = rst.getInt("quantity");
                double totalPrice = rst.getDouble("totalprice");
                double pricePerQuantity = rst.getDouble("priceperquantity");

                checkout = new Checkout();
                checkout.setFlowerName(name);
                checkout.setFlowerImage(flowerImage);
                checkout.setQuantity(quantity);
                checkout.setTotalPrice(totalPrice);
                checkout.setPricePerQuantity(pricePerQuantity);
                checkouts.add(checkout);

            }
            return (ArrayList<Checkout>) checkouts;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void calculateCheckOut(){
        double totalPrice = 0;
        double grandPrice = -125.00;
        for(Checkout checkout: checkouts){
            totalPrice += checkout.getTotalPrice();
        }
        BigDecimal bdtp = new BigDecimal(totalPrice);
        BigDecimal bdgp = new BigDecimal(grandPrice + totalPrice);
        bdtp = bdtp.setScale(2, RoundingMode.HALF_UP);
        bdgp = bdgp.setScale(2, RoundingMode.HALF_UP);

        String formattedTotalPrice = bdtp.toPlainString();
        String formattedGrandprice = bdgp.toPlainString();

        subTotal.setText(formattedTotalPrice);
        grandTotal.setText(formattedGrandprice);

    }
    private void embedCheckout(){

        int column = 0;
        int row = 1;
        try {
            for (Checkout checkout : checkouts) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("checkoutItem.fxml"));
                AnchorPane pane = fxmlLoader.load();

                CheckoutController checkoutController = fxmlLoader.getController();
                checkoutController.setData(checkout);

                if (column == 0) {
                    row++;
                }

                grid.add(pane, column, row);
                //grid.setVgap(100);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(pane, new Insets(10,0,10,0));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private Button PlaceOrderButton;

    private void playPumpAnimation(Button button) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(button.scaleXProperty(), 1), new KeyValue(button.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(button.scaleXProperty(), 1.2), new KeyValue(button.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(button.scaleXProperty(), 1), new KeyValue(button.scaleYProperty(), 1))
        );
        timeline.play();
    }

    @FXML
    void placeOrderClick(MouseEvent event) {
        playPumpAnimation(PlaceOrderButton);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkouts.addAll(getCheckout());
        embedCheckout();
        calculateCheckOut();



    }
}
