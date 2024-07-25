package com.example.otelrezervasyonsistemi.Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class About_Page {

    @FXML
    private Button Menu_Button;


    @FXML
    void Menu_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Menu_Button.getScene().getWindow();
            currentStage.close(); // Önceki sahneyi kapat

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/otelrezervasyonsistemi/Main_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
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
