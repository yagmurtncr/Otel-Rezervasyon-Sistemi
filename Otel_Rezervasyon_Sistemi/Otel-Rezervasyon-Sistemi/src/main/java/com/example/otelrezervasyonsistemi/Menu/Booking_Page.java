package com.example.otelrezervasyonsistemi.Menu;
import com.example.otelrezervasyonsistemi.DatabaseConnection;
import com.example.otelrezervasyonsistemi.LoggedIn;
import com.example.otelrezervasyonsistemi.UserSession;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class Booking_Page extends LoggedIn implements Initializable {

    @FXML
    private Text Entry_Date;

    @FXML
    private ChoiceBox<String> Type_Choicebox1;

    @FXML
    private Button Menu_Button;

    @FXML
    private Text Message;


    @FXML
    private Button Rezervation_Button;

    @FXML
    private Text Room_Type;

    @FXML
    private ChoiceBox<String> Type_Choicebox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            Message.setText("Database connection failed.");
            return;
        }

        try {
            // Type_Choicebox için sorguyu hazırla ve çalıştır
            String roomTypeQuery = "SELECT DISTINCT room_type FROM otel_rezervasyon.rooms";
            try (Statement stmt = connectDB.createStatement();
                 ResultSet rs = stmt.executeQuery(roomTypeQuery)) {
                ObservableList<String> roomTypes = FXCollections.observableArrayList();

                while (rs.next()) {
                    roomTypes.add(rs.getString("room_type"));
                }

                Type_Choicebox.setItems(roomTypes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.setText("An error occurred while fetching data from the database.");
        } finally {
            try {
                connectDB.close(); // Bağlantıyı kapat
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Type_Choicebox dinleyici ekle
        Type_Choicebox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    updateDateChoiceBox(newValue);
                }
            }
        });

        // Date_Choicebox başlangıçta görünmez yap
        Type_Choicebox1.setVisible(false);
        Entry_Date.setVisible(false);
    }

    private void updateDateChoiceBox(String roomType) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            Message.setText("Database connection failed.");
            return;
        }

        try {
            // Date_Choicebox için sorguyu hazırla ve çalıştır
            String dateQuery = "SELECT DISTINCT entry_date, release_date FROM otel_rezervasyon.rooms WHERE room_type = ? AND room_sitiuation IS NULL ORDER BY entry_date";
            try (PreparedStatement pstmt = connectDB.prepareStatement(dateQuery)) {
                pstmt.setString(1, roomType);
                try (ResultSet rs = pstmt.executeQuery()) {
                    ObservableList<String> dates = FXCollections.observableArrayList();

                    while (rs.next()) {
                        String entryDate = rs.getString("entry_date");
                        String releaseDate = rs.getString("release_date");
                        dates.add(entryDate + " / " + releaseDate);
                    }

                    Type_Choicebox1.setItems(dates);
                    Type_Choicebox1.setVisible(!dates.isEmpty());
                    Entry_Date.setVisible(!dates.isEmpty());// Eğer tarih varsa görünür yap
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.setText("An error occurred while fetching data from the database.");
        } finally {
            try {
                connectDB.close(); // Bağlantıyı kapat
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Make_Rez(ActionEvent event) {
        String selectedDateRange = Type_Choicebox1.getValue();
        String roomType = Type_Choicebox.getValue();

        if (selectedDateRange == null || roomType == null) {
            Message.setText("Please fill in all fields.");
            return;
        }

        // Giriş ve çıkış tarihlerini ayrıştırma
        String[] dates = selectedDateRange.split(" / ");
        String entryDate = dates[0];
        String releaseDate = dates[1];

        String updateSql = "UPDATE otel_rezervasyon.rooms SET room_sitiuation = 'full', username = ? WHERE room_sitiuation IS NULL AND entry_date = ? AND release_date = ? AND room_type = ? LIMIT 1";

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            Message.setText("Database connection failed.");
            return;
        }
        String username = UserSession.getInstance().getUsername(); // Kullanıcı adını UserSession'dan al
        try (PreparedStatement pstmt = connectDB.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username); // username
            pstmt.setString(2, entryDate); // entryDate
            pstmt.setString(3, releaseDate); // releaseDate
            pstmt.setString(4, roomType); // roomType

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    //if (generatedKeys.next()) {
                        //String roomNumber = generatedKeys.getString(1);
                    Message.setText("Rezervation successful!");
                        //Message.setText("Reservation successful! Room number: " + roomNumber);
                    //}
                    //else {
                    //    Message.setText("Reservation successful! But could not retrieve room number.");
                    //}
                }
            } else {
                Message.setText("No available rooms for the selected dates and room type.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.setText("An error occurred while making the reservation.");
        }
    }

    @FXML
    void Menu_Page(MouseEvent event) {
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



