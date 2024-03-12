package com.example.fs;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Flower;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class MarketController implements Initializable {
    @FXML
    private VBox chosenFlowerCard;

    @FXML
    private Label flowerNameLabel;

    @FXML
    private Label flowerPriceLabel;

    @FXML
    private ImageView flowerImg;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button addToCart;

    @FXML
    private Button searchBTN;

    @FXML
    private Pane headerPane;

    @FXML
    private AnchorPane marketAnchorPane;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private HBox hBoxFlowers;

    @FXML
    private HBox hboxCount;

    @FXML
    private AnchorPane centerAnchorPane;

    @FXML
    private Pane userAccountPane;

    @FXML
    private Label itemCount;

    @FXML
    private ScrollPane userAccountScrollPane;

    @FXML
    private Button cashInButton;

    @FXML
    private Label currentBalance;

    @FXML
    private Button logOutButton;

    @FXML
    private Label userEmail;


    private MarketListener marketListener;
    private Image image;

    private Stage stage;
    private Scene scene;
    private List<Flower> flowers = new ArrayList<>();



    String url = "jdbc:postgresql://localhost:5435/postgres";
    String dbUsername = "postgres";
    String dbPassword = "admin";

    String retrieveProductsQuery = "SELECT * FROM products";

    String retrieveCheckoutQuery = "SELECT * FROM checkout";

    String insertQuery = "INSERT INTO products(name,image,price,color,id) VALUES(?, ?, ?, ?, ?)";

    String insertCheckOutQuery = "INSERT INTO checkout(name, quantity, pricePerQuantity,totalPrice,flowerImage, email) VALUES(?,?,?,?,?,?)";

    String retrieveUsers = "SELECT * FROM users";
    String retrieveQuery = "SELECT * FROM products";
    private MarketController marketController;

    Timer timer = new Timer();
    Runnable embedFlowersTask = () -> {
        String searchName = searchField.getText().toLowerCase();
        embedMatchingFlowers(searchName);
    };

    public MarketController getMarketController(){
        return marketController;
    }

//    public void setMarketController(MarketController marketController) {
//        this.marketController = marketController;
////        this.mainPageController.hidePopups();
////        aboutUsPage__gojoImage.setOpacity(0);
////        aboutUsPage__founderBigTitle1.setOpacity(0);
////        aboutUsPage__founderBigTitle2.setOpacity(0);
////        aboutUsPage__iconicContainer.setOpacity(0);
//    }

    public void setLogOutButton() throws Exception{
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();

        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SigninPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setTitle("Fauget Crochet Studio");
        stage1.setResizable(false);
        stage1.setScene(scene);
        stage1.show();

        //Parent root = FXMLLoader.load(getClass().getResource("SigninPage.fxml"));

    }
    public void setHomePage(){

        //replaceMarketContent(getClass().getResource("HomePage.fxml"), HomePageController.class);
        FXLoader object = new FXLoader();
        AnchorPane newPane = object.getAnchorPane("HomePage.fxml");
        mainScrollPane.setContent(newPane);
    }
    public void setAboutUsPage(){
        replaceMarketContent(getClass().getResource("AboutUsPage.fxml"), AboutUsController.class);

    }
    public void setContactUsPage(){
        //replaceMarketContent();

    }
    public void setShopNowPage() throws IOException {
        FXLoader object = new FXLoader();
        AnchorPane newPane = object.getAnchorPane("AddToCart.fxml");
        rootPane.getChildren().clear();
        rootPane.getChildren().setAll(newPane);
        //returnMarketContent(getClass().getResource("ShopNowPage.fxml"));

//        replaceMarketContent(getClass().getResource("ShopNowPage.fxml"), MarketController.class);


    }
    public void setCheckoutPage(){
        replaceMarketContent(getClass().getResource("PlaceOrder.fxml"),PlaceOrderController.class);
    }

    public void setCashInPage(){

        FXLoader object = new FXLoader();
        AnchorPane newPane = object.getAnchorPane("ProfilePage.fxml");
        mainScrollPane.setContent(newPane);
    }


    public void userAccountPane() throws SQLException {
        System.out.println("Called");
        String email = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("userEmail.txt"))) {
            email = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        System.out.println(email);

        userEmail.setText(email);
        currentBalance.setText("₱" + String.valueOf(getCurrentBalance(email)));

        if(userAccountScrollPane.isVisible()){
            userAccountScrollPane.setVisible(false);

        }else{
            userAccountScrollPane.setVisible(true);
        }


    }

    public double getCurrentBalance(String userEmail) throws SQLException {
        double userBalance = 0.0;

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

    //TODO: Make the HomePage be the one to show first.
    public void returnMarketContent(URL fxmlResource) throws IOException {
        FXMLLoader loader = new FXMLLoader(fxmlResource);
        AnchorPane returnOriginalContent = loader.load();
        mainScrollPane.setContent(returnOriginalContent);

    }

    public void replaceMarketContent(URL fxmlResource, Class<?> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlResource);
            AnchorPane newContent = loader.load();
            Object controllerInstance = loader.getController();
            Method setMainPageControllerMethod = controllerClass.getMethod("setMarketController", MarketController.class);
            setMainPageControllerMethod.invoke(controllerInstance, this);
            mainScrollPane.setContent(newContent);


        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception if loading FXML fails
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void shoppingCart(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlaceOrder.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void getSearch() {
        searchField.setOnKeyReleased(event -> {
            timer.cancel();
            timer.purge();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(embedFlowersTask);
                }
            }, 500);
        });
    }
    public void searchFlowers(String searchName) throws IOException {

        ArrayList<Flower> matchingFlowers = new ArrayList<>();

        System.out.println(searchName);
        int column = 0;
        int row = 1;

        System.out.println("I've beend called 99");
        // Filter the flowers based on the search name

        for (Flower matchingFlower : matchingFlowers) {
            System.out.println(matchingFlower);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("itemv2.fxml"));
            Parent anchorPaneRoot;
            anchorPaneRoot = loader.load();

            AnchorPane anchorPane = (AnchorPane) anchorPaneRoot;
            grid.getChildren().add(anchorPane);

            System.out.println("Added");


        }
    }

    private String getImageURL(String flowerName){

        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveProductsQuery);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){
                String name = rst.getString("name");
                if(flowerName.equals(name)){
                    String imageLink = rst.getString("image");
                    return imageLink;

                }




            }
            return "NotFound";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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

    public void getCheckOutItemsAmount() throws SQLException {
        String email = getCurrentUser();
        int itemCounter = 0;
        try (Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
             PreparedStatement pst = db.prepareStatement("SELECT * FROM checkout WHERE email=?");) {
            pst.setString(1, email);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                itemCounter += 1;
            }
            itemCount.setText(String.valueOf(itemCounter));
        }
    }
    public void checkOutBtn() throws IOException, SQLException {


        String name = flowerNameLabel.getText();
        String imagePath = getImageURL(name);
        int quantity = comboBox.getValue();
        double pricePerQuantity = Double.parseDouble(flowerPriceLabel.getText().split("₱")[1]);
        double totalPrice = Math.round((pricePerQuantity * quantity) * 100.0)/100.0;

        try (Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
             PreparedStatement pst = db.prepareStatement(insertCheckOutQuery);) {

            pst.setString(1,name);
            pst.setInt(2,quantity);
            pst.setDouble(3,pricePerQuantity);
            pst.setDouble(4,totalPrice);
            pst.setString(5,imagePath);
            pst.setString(6,getCurrentUser());
            pst.executeUpdate();

            getCheckOutItemsAmount();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    private void addFlowers() {
        System.out.println("called me!");
        //List<Products> products = new ArrayList<>();
        try (Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
             PreparedStatement pst = db.prepareStatement(insertQuery);) {

            pst.setString(1,"Anthurium");
            pst.setDouble(3,500.0);
            pst.setString(2,"/images/anthurium.png");
            pst.setString(4,"B35759");
            pst.setInt(5,1);
            pst.executeUpdate();

            pst.setString(1,"Carnation");
            pst.setDouble(3,600.0);
            pst.setString(2,"/images/carnation.png");
            pst.setString(4,"A18DAB");
            pst.setInt(5,2);
            pst.executeUpdate();

            pst.setString(1,"Chamomile");
            pst.setDouble(3,300.0);
            pst.setString(2,"/images/chamomile.png");
            pst.setString(4,"BEB4BC");
            pst.setInt(5,3);
            pst.executeUpdate();

            pst.setString(1,"Crocus");
            pst.setDouble(3,1200.0);
            pst.setString(2,"/images/crocus.png");
            pst.setString(4,"724A6C");
            pst.setInt(5,4);
            pst.executeUpdate();

            pst.setString(1,"Dahlia");
            pst.setDouble(3,1200.0);
            pst.setString(2,"/images/dahlia.png");
            pst.setString(4,"B2AB6B");
            pst.setInt(5,5);
            pst.executeUpdate();

            pst.setString(1,"Eternal");
            pst.setDouble(3,600.0);
            pst.setString(2,"/images/eternal.png");
            pst.setString(4,"F19FB9");
            pst.setInt(5,6);
            pst.executeUpdate();

            pst.setString(1,"Gerbera");
            pst.setDouble(3,700.0);
            pst.setString(2,"/images/gerbera.png");
            pst.setString(4,"BC664F");
            pst.setInt(5,7);
            pst.executeUpdate();

            pst.setString(1,"Hydrangea");
            pst.setDouble(3,400.0);
            pst.setString(2,"/images/hydrangea.png");
            pst.setString(4,"9D6D8A");
            pst.setInt(5,8);
            pst.executeUpdate();

            pst.setString(1,"Lavender");
            pst.setDouble(3,650.0);
            pst.setString(2,"/images/lavender.png");
            pst.setString(4,"9B4777");
            pst.setInt(5,9);
            pst.executeUpdate();

            pst.setString(1,"Lily");
            pst.setDouble(3,400.0);
            pst.setString(2,"/images/lily.png");
            pst.setString(4,"BD747D");
            pst.setInt(5,10);
            pst.executeUpdate();

            pst.setString(1,"Memory Bloom");
            pst.setDouble(3,600.0);
            pst.setString(2,"/images/memorybloom.png");
            pst.setString(4,"3E4B7C");
            pst.setInt(5,11);
            pst.executeUpdate();

            pst.setString(1,"Orchid");
            pst.setDouble(3,400.0);
            pst.setString(2,"/images/orchid.png");
            pst.setString(4,"AF7233");
            pst.setInt(5,12);
            pst.executeUpdate();

            pst.setString(1,"Peony");
            pst.setDouble(3,400.0);
            pst.setString(2,"/images/peony.png");
            pst.setString(4,"8A7A60");
            pst.setInt(5,13);
            pst.executeUpdate();

            pst.setString(1,"Tulips");
            pst.setDouble(3,900.0);
            pst.setString(2,"/images/pinktulips.png");
            pst.setString(4,"A76255");
            pst.setInt(5,14);
            pst.executeUpdate();

            pst.setString(1,"Rose");
            pst.setDouble(3,1000.0);
            pst.setString(2,"/images/rosecrochet.png");
            pst.setString(4,"C8A2A7");
            pst.setInt(5,15);
            pst.executeUpdate();

            pst.setString(1,"Sundrop");
            pst.setDouble(3,1000.0);
            pst.setString(2,"/images/sundrop.png");
            pst.setString(4,"FFC2D1");
            pst.setInt(5,16);
            pst.executeUpdate();

            pst.setString(1,"Sunflower");
            pst.setDouble(3,1200.0);
            pst.setString(2,"/images/sunflower.png");
            pst.setString(4,"CCC0A8");
            pst.setInt(5,17);
            pst.executeUpdate();

            pst.setString(1,"Furand");
            pst.setDouble(3,1000.0);
            pst.setString(2,"/images/furand.png");
            pst.setString(4,"845539");
            pst.setInt(5,18);
            pst.executeUpdate();

            pst.setString(1,"Strawberry");
            pst.setDouble(3,500.0);
            pst.setString(2,"/images/strawberry.png");
            pst.setString(4,"92353D");
            pst.setInt(5,19);
            pst.executeUpdate();

            pst.setString(1,"Dandelion");
            pst.setDouble(3,300.0);
            pst.setString(2,"/images/dandelion.png");
            pst.setString(4,"978E89");
            pst.setInt(5,20);
            pst.executeUpdate();

            System.out.println("SuccessjA");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<Flower> getFlowers(){

        List<Flower> flowers = new ArrayList<>();
        Flower flower;
        try(Connection db = DriverManager.getConnection(this.url, this.dbUsername, this.dbPassword);
            PreparedStatement pst = db.prepareStatement(retrieveProductsQuery);
            ResultSet rst = pst.executeQuery()) {

            while(rst.next()){

                String name = rst.getString("name");
                double price = rst.getDouble("price");
                String imgSrc = rst.getString("image");
                String color = rst.getString("color");


                flower = new Flower();
                flower.setName(name);
                flower.setPrice(price);
                flower.setImgSrc(imgSrc);
                flower.setColor(color);
                flowers.add(flower);

            }
            return (ArrayList<Flower>) flowers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private List<Flower> getData(){
        List<Flower> flowers = new ArrayList<>();
        Flower flower;

        flower = new Flower();
        flower.setName("Blue Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/bluebouquet.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Green Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/greenflowers.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Green Bouquet 2");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/greenflowers2.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Lavender Bouqet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/lavenderflower.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Orange Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/orangeflowers.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Pink and Violet Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/pinkandvioletbouquet.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Pink Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/pinkboquet2.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Pink Bouquet 2");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/pinkflowers.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Sunflower Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/sunflowers.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        flower = new Flower();
        flower.setName("Tulip Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/tulipboquet.png");
        flower.setColor("FFCAD4");
        flower = new Flower();

        flower.setName("White and Pink Bouquet");
        flower.setPrice(20.99);
        flower.setImgSrc("/images/whiteandpinkflower.png");
        flower.setColor("FFCAD4");
        flowers.add(flower);

        return flowers;
    }

    private void setChosenFruit(Flower flower) {
        flowerNameLabel.setText(flower.getName());
        flowerPriceLabel.setText("₱" + flower.getPrice());
        image = new Image(getClass().getResourceAsStream(flower.getImgSrc()));
        flowerImg.setImage(image);
        chosenFlowerCard.setStyle("-fx-background-color: #" + flower.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
        comboBox.setValue(1);
    }

    public void embedMatchingFlowers(String searchName){
        ArrayList<Flower> matchingFlowers = new ArrayList<>();

        int column = 0;
        int row = 1;

        grid.getChildren().clear();

        try {

            for (Flower flower : flowers) {
                if (flower.getName().toLowerCase().contains(searchName.toLowerCase())) {
                    //System.out.println(flower);
                    matchingFlowers.add(flower);
                }
            }
            for (Flower matchingFlower : matchingFlowers) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemv2.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(matchingFlower, marketListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(pane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(pane, new Insets(10));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void embedFlowers() throws IOException {

        int column = 0;
        int row = 1;

        int count = 0;
        for (Flower flower : flowers) {
            count+=1;

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemv2.fxml"));
            AnchorPane pane = fxmlLoader.load();

            ItemController itemController = fxmlLoader.getController();
            itemController.setData(flower, marketListener);

            if (column == 3) {
                column = 0;
                row++;
            }
            grid.add(pane, column++, row);
            //set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);
            GridPane.setMargin(pane, new Insets(10));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCheckOutItemsAmount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //itemCount = new Label();
        //headerPane.getChildren().add(itemCount);kk
        System.out.println("Hi");
        //addFlowers();
        flowers.addAll(getFlowers());
        if (flowers.size() > 0) {
            setChosenFruit(flowers.get(1));
            marketListener = new MarketListener() {
                @Override
                public void onClickListener(Flower flower) {
                    setChosenFruit(flower);
                }
            };
        }
        if(grid.getChildren().isEmpty()){
            try {
                embedFlowers();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Integer[] quantity = {1,2,3,4,5,6,7,8,9,10};

        comboBox.getItems().addAll(quantity);
        comboBox.getSelectionModel().select(0);

    }
    @FXML
   private void clickCashIn (MouseEvent event) {
        playPumpAnimation(cashInButton);
    }

    @FXML
    private void clickLogOut (MouseEvent event) {
        playPumpAnimation(logOutButton);
    }

    private void playPumpAnimation(Button icon) {
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


//    public void cartPage(Stage stage) throws IOException {
//
//        Parent root = FXMLLoader.load(getClass().getResource("AddToCart.fxml"));
//        stage.setTitle("Flower Market");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
}
