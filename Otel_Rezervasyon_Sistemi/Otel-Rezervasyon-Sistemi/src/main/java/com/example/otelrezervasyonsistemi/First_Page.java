package com.example.otelrezervasyonsistemi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class First_Page {

    @FXML
    private Button LogIn_Button;

    @FXML
    private Button SignUp_Button;

    @FXML
    void LogIn_Page(ActionEvent event) {
        try {
            Stage stage = (Stage) LogIn_Button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoggedIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hotel Rezervation System");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SignUp_Page(ActionEvent event) {
        try {
            Stage stage = (Stage) SignUp_Button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hotel Rezervation System");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
