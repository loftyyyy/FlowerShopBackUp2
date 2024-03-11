package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Checkout;
import model.Flower;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {


    @FXML
    private Button PlaceOrderButton;

    @FXML
    private TextField address;

    @FXML
    private TextField contact;

    @FXML
    private TextField country;

    @FXML
    private TextField firstName;

    @FXML
    private Label grandTotal;

    @FXML
    private GridPane grid;

    @FXML
    private TextField lastName;

    @FXML
    private TextField postal;

    @FXML
    private Text subTotal;

    @FXML
    private TextField town;
    String url = "jdbc:postgresql://localhost:5435/postgres";

    String dbUsername = "postgres";
    String dbPassword = "admin";
    MarketController marketController;


    private List<Checkout> checkouts = new ArrayList<>();

    private String retrieveCheckoutQuerty = "SELECT * FROM checkout";
    private String retrieveUsers = "SELECT * FROM users";

    private double totalPrice = 0;
    private double grandPrice = -125.00;

    public void setMarketController(MarketController marketController) {
        this.marketController = marketController;
//        this.mainPageController.hidePopups();
//        aboutUsPage__gojoImage.setOpacity(0);
//        aboutUsPage__founderBigTitle1.setOpacity(0);
//        aboutUsPage__founderBigTitle2.setOpacity(0);
//        aboutUsPage__iconicContainer.setOpacity(0);
    }

    public void placeOrder() throws SQLException{
        missingField();

    }

    public void missingField() throws SQLException{

        if(address.getText().isEmpty() || contact.getText().isEmpty() || town.getText().isEmpty() || country.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty() || postal.getText().isEmpty() ){
            missingFieldDialog();
        }else{

            orderPlaced();
        }
    }

    public void outOfBalance() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Insufficient Balance!");
        alert.setHeaderText(null);
        alert.setContentText("Insufficient Balance! Unfortunately, your current balance of ₱"+ getUserBalance() +" is not enough to cover the total order amount of ₱" + grandPrice );
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    public void successfulPurchase(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Insufficient Balance!");
        alert.setHeaderText(null);
        alert.setContentText("Order confirmation: Your order #"+ getOrderId() + " for a total of ₱" + grandPrice+" has been placed successfully.");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    public void missingFieldDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missing Fields");
        alert.setHeaderText(null);
        alert.setContentText("There are some missing fields in the customer's information. Please fill everything out.");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }
    public void orderPlaced() throws SQLException {

        if(getUserBalance() >= grandPrice){
            successfulPurchase();
            deductUserBalance();
            clearCart();
        }else{
            outOfBalance();
        }

    }

    public void clearCart() throws SQLException{

        String user = getCurrentUser();

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement("DELETE FROM checkout WHERE email = ? ");) {
            pst.setString(1,user);
            pst.execute();
        }
//        try {
//            checkouts.addAll(setCheckout());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        embedCheckout();
//        calculateCheckOut();
    }

    public void deductUserBalance() throws SQLException{
        String user = getCurrentUser();
        double userBalance = getUserBalance();
        double userDeductedBalance = userBalance - grandPrice;

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement("UPDATE users SET balance = ? WHERE email = ? ");) {

            pst.setDouble(1,userDeductedBalance);
            pst.setString(2,user);
            pst.execute();
        }

    }

    public String getOrderId(){
        int length = 12;
        if (length <= 0) {
            throw new IllegalArgumentException("Order number length must be positive.");
        }

        StringBuilder orderNumber = new StringBuilder();

        // Generate random characters (alphanumeric + hyphen)
        for (int i = 0; i < length; i++) {
            int randomInt = (int) (Math.random() * 62);
            char randomChar;
            if (randomInt < 10) {
                randomChar = (char) ('0' + randomInt); // Generate digit (0-9)
            } else if (randomInt < 36) {
                randomChar = (char) ('A' + randomInt - 10); // Generate uppercase letter (A-Z)
            } else {
                randomChar = (char) ('a' + randomInt - 36); // Generate lowercase letter (a-z)
            }
            orderNumber.append(randomChar);
        }

        return orderNumber.toString();
    }
    public String getCurrentUser() throws SQLException {

        String email = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("userEmail.txt"))) {
            email = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return email;


    }
    public double getUserBalance() throws SQLException{

        double userBalance = 0.0;

        String userEmail = getCurrentUser();


        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveUsers);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){
                String email = rst.getString("email");
                double balance = rst.getDouble("balance");

                if(email.equals(userEmail)){
                    userBalance += balance;
                }
            }

        }
        return userBalance;
    }
    private ArrayList<Checkout> setCheckout() throws SQLException {

        String email = getCurrentUser();
        List<Checkout> checkouts = new ArrayList<>();
        Checkout checkout;
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement("SELECT * FROM checkout WHERE email=?");){
            pst.setString(1,email);
            ResultSet rst = pst.executeQuery();
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
        for(Checkout checkout: checkouts){
            totalPrice += checkout.getTotalPrice();
        }
        grandPrice = grandPrice + totalPrice;
        BigDecimal bdtp = new BigDecimal(totalPrice);
        BigDecimal bdgp = new BigDecimal(grandPrice);
        bdtp = bdtp.setScale(2, RoundingMode.HALF_UP);
        bdgp = bdgp.setScale(2, RoundingMode.HALF_UP);

        String formattedTotalPrice = bdtp.toPlainString();
        String formattedGrandprice = bdgp.toPlainString();

        subTotal.setText("₱" + formattedTotalPrice);
        grandTotal.setText("₱" + formattedGrandprice);

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
        try {
            checkouts.addAll(setCheckout());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        embedCheckout();
        calculateCheckOut();



    }
}
