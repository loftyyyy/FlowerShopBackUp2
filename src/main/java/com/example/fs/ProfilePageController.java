package com.example.fs;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {


    public StackPane profilePictureContainer;
    @FXML
    private ImageView profilePicture;

    @FXML
    private ComboBox<String> paymentMethods;

    @FXML
    private TextField cashInAmount;

    @FXML
    private TextArea cashInHistory;

    @FXML
    private Label currentBalance;

    @FXML
    private Label userEmail;
    @FXML
    private Label nullCashIn;
    @FXML
    void addProfilePicture(MouseEvent event) {
        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");

        // Set filter for image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Load the selected image file into the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            profilePicture.setImage(image);

            // Set circular clip to ImageView
            Circle clip = new Circle(profilePicture.getFitWidth() / 2, profilePicture.getFitHeight() / 2, profilePicture.getFitWidth() / 2);
            profilePicture.setClip(clip);
        }
    }

    String retrieveUsers = "SELECT * FROM users";

    String insertQuery = "INSERT INTO users(email, username, password, balance) VALUES(?, ?, ?, ?)";

    String url = "jdbc:postgresql://localhost:5435/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";
    public void setUserBalanceInDataBase() throws SQLException {
        if(cashInAmount.getText().isEmpty()){
            nullCashIn.setText("Amount Must Not Be Empty!");

        }else{
            nullCashIn.setText("");

            double amountCashedIn = Double.parseDouble(cashInAmount.getText());
            double dbUserBalance = getUserBalance();
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String today = currentDate.format(formatter);
            String email = getCurrentUser();
            String paymentMethod = paymentMethods.getValue();
            String stringAmountCashedIn = cashInAmount.getText();


            try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
                PreparedStatement pst = db.prepareStatement("UPDATE users SET balance = ? WHERE email = ?");) {


                double newBalance = dbUserBalance + amountCashedIn;
                pst.setDouble(1,newBalance);
                pst.setString(2,email);
                pst.execute();

            }
            try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
                PreparedStatement pst = db.prepareStatement("UPDATE users SET cashinhistory = concat(cashinhistory, ?)  WHERE email = ?");) {

                String historyAppend = "\n\n "+ "\t\t" + today +" \n\n" + "\tPayment Method: " + paymentMethod + "\n\n" + "\tCashed In Amount: ₱" + stringAmountCashedIn  ;
                pst.setString(1,historyAppend);
                pst.setString(2,email);
                pst.execute();

            }

            cashInAmount.setText("");
            setUserBalance();
            setCashHistory();
        }


    }

    public void setUserBalance() throws SQLException{
        currentBalance.setText("₱" + String.valueOf(getUserBalance()));
        userEmail.setText(getCurrentUser());

    }
    public void setCashHistory() throws SQLException{
        cashInHistory.setText(getCashHistory());
    }

    public String getCashHistory() throws SQLException{
        String history = "";
        String email = getCurrentUser();

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveUsers);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){
                String userEmail = rst.getString("email");
                String userHistory = rst.getString("cashinhistory");

                if(userEmail.equals(email)){
                    history = userHistory;
                }

            }

        }


        return history;

    }

    public double getUserBalance() throws SQLException{

        String email = getCurrentUser();
        double balance = 0.0;
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveUsers);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){

                String userEmail = rst.getString("email");
                double userBalance = rst.getDouble("balance");

                if(userEmail.equals(email)){
                    balance += userBalance;
                }
            }
        }

        return balance;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUserBalance();
            setCashHistory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String[] quantity = {"Credit Card", "Debit Card", "GCash", "PlayMoney"};
        paymentMethods.getItems().addAll(quantity);
        paymentMethods.getSelectionModel().select(0);

    }

}
