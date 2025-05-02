/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import managementperpustakaan_javafx.controller.Koneksi;

/**
 * Kelas Controller FXML
 *
 * @author ackmadelfanp
 */
public class AddBookController implements Initializable {

    @FXML private TextField idBukuField;
    @FXML private TextField judulField;
    @FXML private TextField penulisField;
    @FXML private TextField penerbitField;
    @FXML private TextField tahunTerbitField;
    @FXML private TextField stokField;
    
    /**
     * Menginisialisasi kelas controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set initial focus to ID Buku field when form opens
        Platform.runLater(() -> idBukuField.requestFocus());
    }    
    
    @FXML
    private void handleSave(ActionEvent event) {
        if (validateInputs()) {
            saveBook();
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        // Close the window
        Stage stage = (Stage) idBukuField.getScene().getWindow();
        stage.close();
    }
    
    private boolean validateInputs() {
        if (idBukuField.getText().isEmpty() || 
            judulField.getText().isEmpty() ||
            penulisField.getText().isEmpty() ||
            penerbitField.getText().isEmpty() ||
            tahunTerbitField.getText().isEmpty() ||
            stokField.getText().isEmpty()) {
            
            showAlert(Alert.AlertType.ERROR, "Error", "Semua field harus diisi!");
            return false;
        }
        
        // Validate tahun_terbit is a number
        try {
            Integer.parseInt(tahunTerbitField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Tahun terbit harus berupa angka!");
            return false;
        }
        
        // Validate stok is a number
        try {
            Integer.parseInt(stokField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Stok harus berupa angka!");
            return false;
        }
        
        return true;
    }
    
    private void saveBook() {
        String sql = "INSERT INTO buku (kode_buku, judul, penulis, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idBukuField.getText());
            pstmt.setString(2, judulField.getText());
            pstmt.setString(3, penulisField.getText());
            pstmt.setString(4, penerbitField.getText());
            pstmt.setInt(5, Integer.parseInt(tahunTerbitField.getText()));
            pstmt.setInt(6, Integer.parseInt(stokField.getText()));
            
            int affected = pstmt.executeUpdate();
            
            if (affected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Buku berhasil ditambahkan!");
                clearFields();
                // Set focus back to ID Buku field after clearing
                Platform.runLater(() -> idBukuField.requestFocus());
            }
            
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saat menyimpan buku: " + e.getMessage());
        }
    }
    
    private void clearFields() {
        idBukuField.clear();
        judulField.clear();
        penulisField.clear();
        penerbitField.clear();
        tahunTerbitField.clear();
        stokField.clear();
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
