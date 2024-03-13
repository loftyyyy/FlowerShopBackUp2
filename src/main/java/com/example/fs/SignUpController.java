package com.example.fs;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.*;
import org.apache.commons.validator.routines.*;


import java.io.IOException;

public class SignUpController {

    @FXML
    private StackPane rootPane;
    @FXML
    private TextField email_signup;

    @FXML
    private TextField username_signup;

    @FXML
    private TextField password_signup;

    @FXML
    private Label emailValidation;

    @FXML
    private Button signinBTN;
    private PostgresqlDataBase db = new PostgresqlDataBase();

    private String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    @FXML
    private Button signupBTN;

    public void signUp(ActionEvent e) throws IOException {
        String email = email_signup.getText();
        String username = username_signup.getText();
        String password = password_signup.getText();

        if(email.isBlank() || username.isBlank() || password.isBlank()){
           emptyCases("Please fill out all the required fields");

        }else if(EmailValidation(email)){
            if(db.writeDatabase(email,username,password)){
                //emptyCases().setText("Email Already Used!");
                emptyCases("Email Already Used!");

                //clearText();
            }else{
                emailValidation.setTextFill(Color.GREEN);
                emptyCases("Sign Up Success! Press Sign In to continue.");
                //clearText();
            }
            //emailValidation.setText("Email Recognized!");
        }else{
            emailValidation.setText("Invalid Email!");
        }

    }

    public void emptyCases(String missing){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            emailValidation.setText(missing);

            Timeline revertTimeline = new Timeline(new KeyFrame(Duration.seconds(3), el ->{
                emailValidation.setText("");
            }));

            revertTimeline.play();
        }));

        timeline.play();
    }
    public void signIn() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("SigninPage.fxml"));
//        Stage window = (Stage) signinBTN.getScene().getWindow();
//        window.setScene(new Scene(root));
        makeFadeOut();

    }

    public void makeFadeOut(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadSignInScene();
            }
        });
        fadeTransition.play();


    }
    public void loadSignInScene(){
        Parent secondView;
        try {
            secondView = (StackPane) FXMLLoader.load(getClass().getResource("SigninPage.fxml"));
            Scene newScene = new Scene(secondView);

            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(newScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






    // This method checks if the email Address is valid
//    private boolean patternMatches(String emailAddress, String regexPattern) {
//        return Pattern.compile(regexPattern)
//                .matcher(emailAddress)
//                .matches();
//    }

    private boolean EmailValidation(String email){
        EmailValidator validator = EmailValidator.getInstance();
        DomainValidator domainValidator = DomainValidator.getInstance();

        String emailDomain = DomainExtractor(email);

        if(validator.isValid(email)){
            if(domainValidator.isValid(emailDomain)){
                System.out.println("Valid Domain! " + emailDomain);
                return true;
            }else{
                System.out.println("Invalid Domain!");
            }
        }
        return false;
    }

    //Extracts and Validates Domain
    private String DomainExtractor(String email){
        return StringUtils.substringAfter(email,"@");


    }

    private void clearText(){
        email_signup.setText("");
        username_signup.setText("");
        password_signup.setText("");
    }

    @FXML
    private void clickSignIn (MouseEvent event) {
        playPumpAnimation(signupBTN);
    }
    @FXML
    private void clickSignUp (MouseEvent event) {
        playPumpAnimation(signinBTN);
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




    // Checks if domain is valid
//    protected boolean isValidDomain(String domain) {
//        boolean symbolic = false;
//        Perl5Util ipAddressMatcher = new Perl5Util();
//        if (ipAddressMatcher.match(IP_DOMAIN_PATTERN, domain)) {
//            if (!isValidIpAddress(ipAddressMatcher)) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            // Domain is symbolic name
//            Perl5Util domainMatcher = new Perl5Util();
//            symbolic = domainMatcher.match(DOMAIN_PATTERN, domain);
//        }
//        if (symbolic) {
//            if (!isValidSymbolicDomain(domain)) {
//                return false;
//            }
//        } else {
//            return false;
//        }
//        return true;
//    }
}
