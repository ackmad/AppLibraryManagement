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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import managementperpustakaan_javafx.model.Anggota;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class UpdateMemberController implements Initializable {

    @FXML
    private TextField namaField;
    @FXML
    private TextField alamatField;
    @FXML
    private TextField nomorHpField;
    @FXML
    private Button saveButton;

    private Anggota member;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code if needed
    }

    public void setMember(Anggota member) {
        this.member = member;
        namaField.setText(member.getNama());
        alamatField.setText(member.getAlamat());
        nomorHpField.setText(member.getNomorHp());
    }

    @FXML
    private void handleSave() {
        try (Connection conn = Koneksi.getConnection()) {
            String query = "UPDATE anggota SET nama = ?, alamat = ?, nomor_hp = ? WHERE id_anggota = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, namaField.getText());
            pstmt.setString(2, alamatField.getText());
            pstmt.setString(3, nomorHpField.getText());
            pstmt.setInt(4, member.getIdAnggota());
            pstmt.executeUpdate();

            showAlert("Sukses", "Anggota berhasil diperbarui.");
            ((Stage) saveButton.getScene().getWindow()).close(); // Close the window
        } catch (SQLException e) {
            showAlert("Error", "Gagal memperbarui anggota: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) saveButton.getScene().getWindow()).close(); // Close the window
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
