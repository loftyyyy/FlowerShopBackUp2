package com.example.fs;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.*;
import org.apache.commons.validator.routines.*;


import java.io.IOException;
import java.util.regex.Pattern;

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

    public void signUp(ActionEvent e) throws IOException {
        String email = email_signup.getText();
        String username = username_signup.getText();
        String password = password_signup.getText();

        if(email.isBlank() || username.isBlank() || password.isBlank()){
           emailValidation.setText("Missing Field!");

        }else if(EmailValidation(email)){
            if(db.writeDatabase(email,username,password)){
                emailValidation.setText("Email Already Used!");
                //clearText();
            }else{
                emailValidation.setText("Sign Up Success! Press Sign In to continue.");
                //clearText();
            }
            //emailValidation.setText("Email Recognized!");
        }else{
            emailValidation.setText("Invalid Email!");
        }

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