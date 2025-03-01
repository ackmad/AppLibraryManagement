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
import managementperpustakaan_javafx.model.Buku;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class UpdateBookController implements Initializable {

    @FXML
    private TextField judulField;
    @FXML
    private TextField penulisField;
    @FXML
    private TextField penerbitField;
    @FXML
    private TextField tahunField;
    @FXML
    private TextField stokField;
    @FXML
    private Button saveButton;

    private Buku book;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code if needed
    }

    public void setBook(Buku book) {
        this.book = book;
        judulField.setText(book.getJudul());
        penulisField.setText(book.getPenulis());
        penerbitField.setText(book.getPenerbit());
        tahunField.setText(String.valueOf(book.getTahunTerbit()));
        stokField.setText(String.valueOf(book.getStok()));
    }

    @FXML
    private void handleSave() {
        try (Connection conn = Koneksi.getConnection()) {
            String query = "UPDATE buku SET judul = ?, penulis = ?, penerbit = ?, tahun_terbit = ?, stok = ? WHERE id_buku = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, judulField.getText());
            pstmt.setString(2, penulisField.getText());
            pstmt.setString(3, penerbitField.getText());
            pstmt.setInt(4, Integer.parseInt(tahunField.getText()));
            pstmt.setInt(5, Integer.parseInt(stokField.getText()));
            pstmt.setInt(6, book.getIdBuku());
            pstmt.executeUpdate();

            showAlert("Sukses", "Buku berhasil diperbarui.");

            // Cek apakah saveButton dan scene tidak null
            if (saveButton.getScene() != null && saveButton.getScene().getWindow() != null) {
                System.out.println("Save Button: " + saveButton);
                System.out.println("Scene: " + saveButton.getScene());
                System.out.println("Window: " + saveButton.getScene().getWindow());
                ((Stage) saveButton.getScene().getWindow()).close(); // Close the window
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal memperbarui buku: " + e.getMessage());
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
