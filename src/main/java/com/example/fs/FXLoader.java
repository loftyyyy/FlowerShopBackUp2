package com.example.fs;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public class FXLoader {

    private AnchorPane pane;
    public AnchorPane getAnchorPane(String URL){

        try{
            URL fileUrl = getClass().getResource(URL);

            pane = new FXMLLoader().load(fileUrl);


        }catch(Exception e){
            e.printStackTrace();
        }

        return pane;

    }
}
