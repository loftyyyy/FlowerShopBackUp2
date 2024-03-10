package com.example.fs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {


    public StackPane profilePictureContainer;
    @FXML
    private ImageView profilePicture;

    @FXML
    private ComboBox<String> paymentMethods;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        String[] quantity = {"Credit Card", "Debit Card", "GCash", "PlayMoney"};
        paymentMethods.getItems().addAll(quantity);
        paymentMethods.getSelectionModel().select(0);

    }

}
