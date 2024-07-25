/***
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

public class Services_Page {


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
***/

package com.example.otelrezervasyonsistemi.Menu;

import com.example.otelrezervasyonsistemi.FoodList;
import com.example.otelrezervasyonsistemi.FoodListItem;
import com.example.otelrezervasyonsistemi.SportServicesList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Services_Page implements Initializable {

    @FXML
    private ListView<String> breakfastListView;
    @FXML
    private ListView<String> lunchListView;
    @FXML
    private ListView<String> dinnerListView;
    @FXML
    private ListView<String> sportServicesListView; // Spor servislerini gösterecek ListView

    private FoodList foodList;
    private SportServicesList sportServicesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodList = new FoodList();
        sportServicesList = new SportServicesList();

        // Kahvaltı, öğle yemeği ve akşam yemeği için menü öğelerini göster
        showFoodMenu();

        // Spor servislerini göster
        showSportServices();
    }

    private void showFoodMenu() {
        ObservableList<String> breakfastItems = FXCollections.observableArrayList();
        ObservableList<String> lunchItems = FXCollections.observableArrayList();
        ObservableList<String> dinnerItems = FXCollections.observableArrayList();

        for (FoodListItem item : foodList.getItems()) {
            if (item.getName() == "Breakfast") {
                breakfastItems.add(item.getName());
                for (String s: item.getItems())
                    breakfastItems.add(s);
            }

            if (item.getName() == "Lunch") {
                lunchItems.add(item.getName());
                for (String s: item.getItems())
                    lunchItems.add(s);
            }

            if (item.getName() == "Dinner") {
                dinnerItems.add(item.getName());
                for (String s: item.getItems())
                    dinnerItems.add(s);
            }
        }

        breakfastListView.setItems(breakfastItems);
        lunchListView.setItems(lunchItems);
        dinnerListView.setItems(dinnerItems);
    }

    private void showSportServices() {
        LinkedList<String> services = sportServicesList.getServices();
        ObservableList<String> sportItems = FXCollections.observableArrayList(services);
        sportServicesListView.setItems(sportItems);
    }

    @FXML
    private Button Menu_Button;

    @FXML
    void Menu_Page(ActionEvent event) {
        try {
            Stage currentStage = (Stage) Menu_Button.getScene().getWindow();
            currentStage.close();

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