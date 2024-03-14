package com.example.fs;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class AdminPageController {

    @FXML
    private TextField hexCodeTF;
    @FXML
    private Pane colorpane;
    @FXML
    private Button addproduct;
    private ImageView clickAddProduct;

    @FXML
    private Label imageLabel;
    @FXML
    private ImageView productView;

    @FXML
    private Label addProductField;

    @FXML
    private Label backgroundColorField;

    @FXML
    private TextField productName;

    @FXML
    private Label productNameField;

    @FXML
    private TextField productPrice;


    @FXML
    private Label setPriceField;

    @FXML
    private ImageView logOutLogo;


    private Color color;
    private Stage stage;

    private String filePathInImagesDir = "";

    String url = "jdbc:postgresql://localhost:5435/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";
    String insertQuery = "INSERT INTO products(name,image,price,color,id) VALUES(?, ?, ?, ?, ?)";

    @FXML
    public void initialize() {
        hexCodeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Color color = Color.web(newValue);
                colorpane.setStyle("-fx-background-color: " + toRGBCode(color) + ";");
            } catch (IllegalArgumentException e) {
                // Handle invalid hex code
            }
        });
    }

    public void showImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a product");
        File initialDirectory = new File("C:\\");
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            File destinationDirectory = new File("C:\\Users\\Sean Rommel E\\Desktop\\FS\\src\\main\\resources\\images");
            File destinationFile = new File(destinationDirectory, selectedFile.getName());

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File saved to: " + destinationFile.getAbsolutePath());

                // Get the name of the file
                String fileName = selectedFile.getName();

                // Extract the name and extension
                int dotIndex = fileName.lastIndexOf(".");
                String name = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
                String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);

                // Print the name and extension
                System.out.println("File Name: " + name);
                System.out.println("File Extension: " + extension);
                filePathInImagesDir = "/images/" + name.replace(" ", "-") + "." + extension;
                String filePath = destinationFile.toURI().toString();


                Image productImage = new Image(filePath);

                productView.setImage(productImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Error, no image");
        }
    }

    public void clickAddProduct(MouseEvent mouseEvent) throws SQLException, FileSystemException {
        //playPumpAnimation(clickAddProduct);
        if (checkFields()) {

            if (!isValidPrice()) {

                System.out.println("Yes false not a valid number format");
                productNameField.setTextFill(Color.RED);

                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
                    productNameField.setText("Please input a valid number format for the price");

                    Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(3), el -> {
                        productNameField.setText("");
                    }));

                    revertTimeline.play();
                }));

                timeline2.play();
            } else {
                checkImageView();
            }

        }

    }

//    public void refreshImageFolder() throws FileSystemException, org.apache.commons.vfs2.FileSystemException {
//        FileSystemManager fsManager = VFS.getManager();
//
//        // Get file object for your image folder
//        FileObject imageFolder = fsManager.resolveFile("C:\\Users\\Sean Rommel E\\Desktop\\FS\\src\\main\\resources\\images");
//
//        // Refresh the folder to ensure updated information
//        imageFolder.refresh();
//
//        // Now you can access the folder contents with confidence
//        FileObject[] images = imageFolder.getChildren();
//        for (FileObject image : images) {
//            System.out.println("Image: " + image.getName().getBaseName());
//        }
//    }

    public void checkImageView() throws SQLException {
        if (filePathInImagesDir.isEmpty()) {

            imageLabel.setTextFill(Color.RED);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
                imageLabel.setText("Please add an image");

                Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(3), el -> {
                    imageLabel.setText("");
                }));

                revertTimeline.play();
            }));

            timeline.play();
        } else {

            insertProductToDatabase();
            successfulProductAddition();


        }

    }
    public void successfulProductAddition(){

        addProductField.setTextFill(Color.GREEN);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            addProductField.setText("Successfully added a new product");

            Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(3), el -> {
                addProductField.setText("");
            }));

            revertTimeline.play();
        }));

        timeline.play();
        productName.setText("");
        productPrice.setText("");
        hexCodeTF.setText("");
        productView.setImage(null);
        colorpane.setStyle("-fx-background-color: " + "#e1e0dc" + ";");
    }

    public boolean isValidPrice() {
        try {
            Double.parseDouble(productPrice.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public void insertProductToDatabase() throws SQLException {
        try (Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
             PreparedStatement pst = db.prepareStatement(insertQuery);) {

            pst.setString(1, productName.getText());
            pst.setString(2, filePathInImagesDir);
            pst.setDouble(3, Double.parseDouble(productPrice.getText()) );
            pst.setString(4,String.format(toRGBCode(Color.valueOf(hexCodeTF.getText()))).substring(1));
            pst.setInt(5, (getLastProductId() + 1));
            pst.executeUpdate();
            System.out.println("Successfully added a new product!");
        }
    }

    public void setLogOutButton() throws Exception{
        Stage stage = (Stage) logOutLogo.getScene().getWindow();
        stage.close();

//        Stage stage1 = new Stage();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SigninPage.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage1.setTitle("Fauget Crochet Studio");
//        stage1.setResizable(false);
//        stage1.setScene(scene);
//        stage1.show();

        //Parent root = FXMLLoader.load(getClass().getResource("SigninPage.fxml"));

    }
    public int getLastProductId() {
        int lastProductId = 0;
        String getLastRowQuery = "SELECT * FROM products ORDER BY id DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getLastRowQuery)) {

            if (resultSet.next()) {
                // Process the last row data
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Retrieve other column values as needed
                System.out.println("Last row: ID=" + id + ", Name=" + name);
                lastProductId = id;
            } else {
                System.out.println("No rows found in the table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastProductId;
    }
    public boolean checkFields(){
        boolean pass = false;

        boolean productNameIsEmpty = productName.getText().isEmpty();
        boolean priceFieldIsEmpty = productPrice.getText().isEmpty();
        boolean backgroundColorIsEmpty = hexCodeTF.getText().isEmpty();

        if(priceFieldIsEmpty || productNameIsEmpty || backgroundColorIsEmpty){

            productNameField.setTextFill(Color.RED);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
                productNameField.setText("Please fill out all the required fields");

                Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(3), el ->{
                    productNameField.setText("");
                }));

                revertTimeline.play();
            }));

            timeline.play();
        }else{
            pass = true;
        }




        return pass;
    }

    private void playPumpAnimation(ImageView icon) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(icon.scaleXProperty(), 1)),
                new KeyFrame(Duration.ZERO, new KeyValue(icon.scaleYProperty(), 1)),
                new KeyFrame(Duration.seconds(0.15), new KeyValue(icon.scaleXProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.15), new KeyValue(icon.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(icon.scaleXProperty(), 1)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(icon.scaleYProperty(), 1))
        );
        timeline.play();
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
