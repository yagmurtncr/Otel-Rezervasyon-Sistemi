package com.example.otelrezervasyonsistemi.Menu;

import com.example.otelrezervasyonsistemi.DatabaseConnection;
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

public class Changing_Page extends Reservation implements Initializable {

    @FXML
    private Text Entry_Date;

    @FXML
    private Text Room_Type;

    @FXML
    private Text message;

    @FXML
    private Button Menu_Button;


    @FXML
    private ChoiceBox<String> Type_Choicebox;

    @FXML
    private ChoiceBox<String> Type_Choicebox1;
    @FXML
    private TableView<Reservation> reservationTable1;
    @FXML
    private TableColumn<Reservation, String> colEntryDate1;

    @FXML
    private TableColumn<Reservation, String> colReleaseDate1;

    @FXML
    private TableColumn<Reservation, Integer> colRoomNumber1;

    @FXML
    private TableColumn<Reservation, String> colRoomType1;

    // No-argument constructor
    public Changing_Page() {
        super("", "", "", 0); // Call to super with default values
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Diğer başlangıç kodları...

        colEntryDate1.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        colReleaseDate1.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        colRoomType1.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colRoomNumber1.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        loadReservations();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }

        try {
            // RoomType_Choicebox için sorgu
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
            message.setText("An error occurred while fetching data from the database.");
        } finally {
            try {
                connectDB.close(); // Bağlantıyı kapat
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // RoomType_Choicebox'a dinleyici ekle
        Type_Choicebox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    updateDateChoiceBox(newValue);
                }
            }
        });

        // Tarih ve Entry_Date başta gizli
        Type_Choicebox1.setVisible(false);
        Entry_Date.setVisible(false);

    }


    private void updateDateChoiceBox(String roomType) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }

        try {
            // Query for Date_Choicebox
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
                    Entry_Date.setVisible(!dates.isEmpty()); // Show if there are dates
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("An error occurred while fetching data from the database.");
        } finally {
            try {
                connectDB.close(); // Close the connection
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadReservations() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }

        String username = UserSession.getInstance().getUsername(); // Kullanıcı adını UserSession'dan al
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
    void updateReservation(ActionEvent event) {
        Reservation selectedReservation = reservationTable1.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            message.setText("Please select a reservation to update.");
            return;
        }

        String selectedDateRange = Type_Choicebox1.getValue();
        String roomType = Type_Choicebox.getValue();

        if (selectedDateRange == null || roomType == null) {
            message.setText("Please fill in all fields.");
            return;
        }

        // Giriş ve çıkış tarihlerini ayır
        String[] dates = selectedDateRange.split(" / ");
        String entryDate = dates[0];
        String releaseDate = dates[1];

        String updateSql = "UPDATE otel_rezervasyon.rooms SET room_sitiuation = 'full', username = ? WHERE room_sitiuation IS NULL AND entry_date = ? AND release_date = ? AND room_type = ? LIMIT 1";
        String deleteSql = "UPDATE otel_rezervasyon.rooms SET username = NULL, room_sitiuation = NULL WHERE username = ? AND entry_date = ? AND release_date = ? AND room_type = ?";

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (connectDB == null) {
            message.setText("Database connection failed.");
            return;
        }
        String username = UserSession.getInstance().getUsername(); // Kullanıcı adını UserSession'dan al

        try {
            connectDB.setAutoCommit(false); // Transaction start

            // Mevcut rezervasyonu sil
            try (PreparedStatement pstmtDelete = connectDB.prepareStatement(deleteSql)) {
                pstmtDelete.setString(1, username);
                pstmtDelete.setString(2, selectedReservation.getEntryDate());
                pstmtDelete.setString(3, selectedReservation.getReleaseDate());
                pstmtDelete.setString(4, selectedReservation.getRoomType());
                pstmtDelete.executeUpdate();
            }

            // Yeni rezervasyon ile güncelle
            try (PreparedStatement pstmtUpdate = connectDB.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmtUpdate.setString(1, username); // username
                pstmtUpdate.setString(2, entryDate); // entryDate
                pstmtUpdate.setString(3, releaseDate); // releaseDate
                pstmtUpdate.setString(4, roomType); // roomType

                int affectedRows = pstmtUpdate.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmtUpdate.getGeneratedKeys()) {
                        message.setText("Reservation successful!");
                    }
                } else {
                    message.setText("No available rooms for the selected dates and room type.");
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
            message.setText("An error occurred while making the reservation.");
        } finally {
            try {
                connectDB.setAutoCommit(true); // Resetting auto-commit to true
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
