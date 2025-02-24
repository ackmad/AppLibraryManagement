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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class AddMemberController implements Initializable {

    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField nomorHpField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set initial focus to nama field when form opens
        Platform.runLater(() -> namaField.requestFocus());
    }    
    
    @FXML
    private void handleSave(ActionEvent event) {
        if (validateInputs()) {
            saveMember();
        }
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        // Close the window
        Stage stage = (Stage) namaField.getScene().getWindow();
        stage.close();
    }
    
    private boolean validateInputs() {
        if (namaField.getText().isEmpty() || 
            alamatField.getText().isEmpty() ||
            nomorHpField.getText().isEmpty()) {
            
            showAlert(Alert.AlertType.ERROR, "Error", "Semua field harus diisi!");
            return false;
        }
        
        // Validate nomor HP format
        if (!nomorHpField.getText().matches("^08\\d{9,11}$")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Format nomor HP tidak valid!\nGunakan format: 08xxxxxxxxxx");
            return false;
        }
        
        return true;
    }
    
    private void saveMember() {
        String sql = "INSERT INTO anggota (nama, alamat, nomor_hp) VALUES (?, ?, ?)";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, namaField.getText());
            pstmt.setString(2, alamatField.getText());
            pstmt.setString(3, nomorHpField.getText());
            
            int affected = pstmt.executeUpdate();
            
            if (affected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Anggota berhasil ditambahkan!");
                clearFields();
                // Set focus back to nama field after clearing
                Platform.runLater(() -> namaField.requestFocus());
            }
            
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saat menyimpan anggota: " + e.getMessage());
        }
    }
    
    private void clearFields() {
        namaField.clear();
        alamatField.clear();
        nomorHpField.clear();
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
