package com.example.fs;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignInController {
    @FXML
    StackPane rootPane;
    @FXML
    private TextField username_signin;

    @FXML
    private TextField password_signin;

    @FXML
    private Label missingCredentials;

    @FXML
    private Button signupBTN;


    private PostgresqlDataBase db = new PostgresqlDataBase();

    public void SignIn(Event e) throws IOException {

        if (username_signin.getText().isBlank() && password_signin.getText().isEmpty()){
            emptyCases("Missing Entire Credentials!");
        } else if(username_signin.getText().isBlank()){
            emptyCases("Missing username!");

        }else if(password_signin.getText().isEmpty()){
            emptyCases("Missing password!");
        }else{

            //TODO Compare username and password and if yes, open the Shopping Page the code below is a test case.
            
            if(db.readDatabase(username_signin.getText(), password_signin.getText())){
                //TODO Find a way to somehow make the email be accessible to every class.
                String email = db.getCurrentUser(username_signin.getText(), password_signin.getText());
                makeUserEmailText(email);
                Parent root = FXMLLoader.load(getClass().getResource("AddToCart.fxml"));
                Stage window = (Stage) signupBTN.getScene().getWindow();
                window.setScene(new Scene(root));
            }else{
                emptyCases("Incorrect username or password");
            }

        }

    }
    public void makeUserEmailText(String email){

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("userEmail.txt"))){
            writer.write(email);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void makeFadeOut(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadSignUpScene();
            }
        });
        fadeTransition.play();


    }
    public void loadSignUpScene(){
        Parent secondView;
        try {
            secondView = (StackPane) FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
            Scene newScene = new Scene(secondView);

            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SignUp() throws IOException{

//        Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
//        Stage window = (Stage) signupBTN.getScene().getWindow();
//        window.setScene(new Scene(root));
        makeFadeOut();
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