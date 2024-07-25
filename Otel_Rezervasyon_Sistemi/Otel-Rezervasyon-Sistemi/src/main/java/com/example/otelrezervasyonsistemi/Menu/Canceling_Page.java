package com.example.otelrezervasyonsistemi.Menu;

import com.example.otelrezervasyonsistemi.DatabaseConnection;
import com.example.otelrezervasyonsistemi.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Canceling_Page extends Reservation implements Initializable {

    @FXML
    private Button Menu_Button;

    @FXML
    private TableColumn<Reservation, String> colEntryDate1;

    @FXML
    private TableColumn<Reservation, String> colReleaseDate1;

    @FXML
    private TableColumn<Reservation, Integer> colRoomNumber1;

    @FXML
    private TableColumn<Reservation, String> colRoomType1;

    @FXML
    private Text message;

    @FXML
    private TableView<Reservation> reservationTable1;

    // No-argument constructor
    public Canceling_Page() {
        super("", "", "", 0); // Call to super with default values
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEntryDate1.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        colReleaseDate1.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colRoomType1.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colRoomNumber1.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        loadReservations();
    }

    private void loadReservations() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }

        String username = UserSession.getInstance().getUsername();
        String reservationsQuery = "SELECT entry_date, release_date, room_type, room_number FROM otel_rezervasyon.rooms WHERE username = ?";

        try (PreparedStatement pstmt = connectDB.prepareStatement(reservationsQuery)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                ObservableList<Reservation> reservations = FXCollections.observableArrayList();

                while (rs.next()) {
                    String entryDate = rs.getString("entry_date");
                    String releaseDate = rs.getString("release_date");
                    String roomType = rs.getString("room_type");
                    int roomNumber = rs.getInt("room_number");
                    reservations.add(new Reservation(entryDate, releaseDate, roomType, roomNumber));
                }

                reservationTable1.setItems(reservations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("An error occurred while fetching reservations from the database.");
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Cancel(MouseEvent event) {
        Reservation selectedReservation = reservationTable1.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            message.setText("Please select a reservation to cancel.");
            return;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }

        String deleteSql = "UPDATE otel_rezervasyon.rooms SET username = NULL, room_sitiuation = NULL WHERE username = ? AND entry_date = ? AND release_date = ? AND room_type = ? AND room_number = ?";

        try {
            connectDB.setAutoCommit(false); // Transaction start

            // Deleting selected reservation
            try (PreparedStatement pstmtDelete = connectDB.prepareStatement(deleteSql)) {
                pstmtDelete.setString(1, UserSession.getInstance().getUsername());
                pstmtDelete.setString(2, selectedReservation.getEntryDate());
                pstmtDelete.setString(3, selectedReservation.getReleaseDate());
                pstmtDelete.setString(4, selectedReservation.getRoomType());
                pstmtDelete.setInt(5, selectedReservation.getRoomNumber());
                int affectedRows = pstmtDelete.executeUpdate();

                if (affectedRows > 0) {
                    message.setText("Reservation canceled successfully!");
                    loadReservations(); // Refresh the table view
                } else {
                    message.setText("Failed to cancel the reservation.");
                }
            }

            connectDB.commit(); // Transaction commit
        } catch (SQLException e) {
            try {
                connectDB.rollback(); // Transaction rollback in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            message.setText("An error occurred while canceling the reservation.");
        } finally {
            try {
                connectDB.setAutoCommit(true); // Resetting auto-commit to true
                connectDB.close();
            } catch (SQLException e) {
                System.out.println(e + "hata bulundu!");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Menu_Page(MouseEvent event) {
        try {
            Stage currentStage = (Stage) Menu_Button.getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/otelrezervasyonsistemi/Main_Page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            newStage.setTitle("Hotel Reservation System");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.setFullScreen(false);
            newStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
