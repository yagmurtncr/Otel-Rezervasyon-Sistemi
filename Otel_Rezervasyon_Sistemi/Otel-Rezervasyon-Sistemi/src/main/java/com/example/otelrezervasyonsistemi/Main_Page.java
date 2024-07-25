package com.example.otelrezervasyonsistemi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
// import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main_Page  {

    @FXML
    private Button About_Button;
    @FXML
    private Button Rooms_Button;
    @FXML
    private Button Services_Button;
    @FXML
    private Button Booking_Button;
    @FXML
    private Button Canceling_Button;
    @FXML
    private Button Changing_Button;

    @FXML
    void Booking_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Booking_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Booking_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void Canceling_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Canceling_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Canceling_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(true);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void Changing_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Changing_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Changing_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void Rooms_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Rooms_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rooms_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Services_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Services_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Services_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void about_page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) About_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newStage.setTitle("Hotel Rezervation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
