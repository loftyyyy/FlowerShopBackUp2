package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.*;

public class PostgresqlDataBase {

    String url = "jdbc:postgresql://localhost:5435/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";

    String insertQuery = "INSERT INTO users(email, username, password, balance, cashinhistory) VALUES(?, ?, ?, ?,?)";
    String retrieveQuery = "SELECT * FROM users";

    // TODO: Dependency Injection
    @FXML
    private Label missingCredentials;

    public boolean writeDatabase(String email, String username, String password){
        String lowerCaseEmail = email.toLowerCase();

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(insertQuery);
            PreparedStatement pst2 = db.prepareStatement(retrieveQuery);
            ResultSet rst = pst2.executeQuery()){


            System.out.println("Database connected!");
            boolean isDuplicate = false;
            while(rst.next()){

                if(rst.getString("email").equals(lowerCaseEmail) || rst.getString("username").equals(username)){
                    isDuplicate = true;
                    break;
                }

            }

            if(isDuplicate){
                //this.emptyCases("Email or Username Already Registered");
                System.out.println("Email or Username Already Registered!");
                return true;

            }else {
                pst.setString(1, lowerCaseEmail);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.setDouble(4,0.00);
                pst.setString(5,"<--------------------Cash In History-------------------->\n");
                pst.executeUpdate();

                System.out.println("Successfully Created User!");
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public String getCurrentUser(String username, String password){
        String email = "";

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveQuery);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){
                String retrieveUsername = rst.getString("username");
                String retievePassword = rst.getString("password");
                String retrieveEmail = rst.getString("email");

                if(retrieveUsername.equals(username) && retievePassword.equals(password)){
                    email = retrieveEmail;
                }
            }

            return email;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public boolean readDatabase(String username, String password){

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveQuery);
            ResultSet rst = pst.executeQuery()){

            boolean signInSuccess = false;
            while(rst.next()){
                //String retrieveEmail = rst.getString("email");
                String retrieveUsername = rst.getString("username");
                String retievePassword = rst.getString("password");

                if(retrieveUsername.equals(username) && retievePassword.equals(password)){
                    signInSuccess = true;
                }
            }

            return signInSuccess;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void emptyCases(String missing){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            missingCredentials.setText(missing);

            Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), el ->{
                missingCredentials.setText("");
            }));

            revertTimeline.play();
        }));

        timeline.play();
    }
}
