package com.example.fs;

import javafx.application.Platform;
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

import java.io.*;

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

        File imageFile = new File(getImagePath(flower.getImgSrc()));

        try {
            // Create a FileInputStream with the File object
            FileInputStream inputStream = new FileInputStream(imageFile);

            // Now you can use the inputStream to read from the file
            // For example, you can read bytes from the file using read() method

            // Close the stream when done

            this.flower = flower;
            this.marketListener = marketListener;
            nameLabel.setText(flower.getName());
            priceLabel.setText("₱" + flower.getPrice());
            Image image = new Image(inputStream);
            img.setImage(image);
            inputStream.close();
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }



    }

    public String getImagePath(String fileName){

        String imageDir = "C:\\Users\\Sean Rommel E\\Desktop\\FS\\src\\main\\resources";
        String relativeDir = fileName;
        String absolutePath = "";

        // Create a File object for the image directory
        File directory = new File(imageDir);

        // Check if the directory exists
        if (directory.exists()) {
            // Create a File object for the absolute directory
            File absoluteDir = new File(directory, relativeDir);

            // Get the absolute path of the directory
            absolutePath = absoluteDir.getAbsolutePath();

            // Print the absolute directory path
            System.out.println("Absolute Directory Path: " + absolutePath);
        } else {
            System.out.println("Directory does not exist.");
        }
        return absolutePath;
    }



}
