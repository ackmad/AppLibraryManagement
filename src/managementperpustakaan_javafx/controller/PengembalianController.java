/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import managementperpustakaan_javafx.controller.Koneksi;
import managementperpustakaan_javafx.model.Pengembalian;
import java.io.IOException;
import javafx.collections.transformation.FilteredList;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class PengembalianController implements Initializable {

    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Pengembalian> returnTable;
    
    @FXML
    private Button returnButton;

   
   
    @FXML
    private TableColumn<Pengembalian, Integer> idPeminjamanColumn;
    @FXML
    private TableColumn<Pengembalian, Integer> idAnggotaColumn;
    @FXML
    private TableColumn<Pengembalian, String> namaAnggotaColumn;
    @FXML
    private TableColumn<Pengembalian, String> judulBukuColumn;
    @FXML
    private TableColumn<Pengembalian, Date> tanggalPinjamColumn;
    @FXML
    private TableColumn<Pengembalian, Date> tanggalJatuhTempoColumn;
    @FXML
    private TableColumn<Pengembalian, Date> tanggalPengembalianColumn;
    @FXML
    private TableColumn<Pengembalian, Double> dendaColumn;
    @FXML
    private TableColumn<Pengembalian, String> statusPengembalianColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupReturnTable();
        loadReturnData();
        returnTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            returnButton.setDisable(newSelection == null); // Enable button if a row is selected
        });
    }    
    
    private void setupReturnTable() {
        
        idPeminjamanColumn.setCellValueFactory(new PropertyValueFactory<>("idPeminjaman"));
        idAnggotaColumn.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        namaAnggotaColumn.setCellValueFactory(new PropertyValueFactory<>("namaAnggota"));
        judulBukuColumn.setCellValueFactory(new PropertyValueFactory<>("judulBuku"));
        tanggalPinjamColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPinjam"));
        tanggalJatuhTempoColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalJatuhTempo"));
        tanggalPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPengembalian"));
        dendaColumn.setCellValueFactory(new PropertyValueFactory<>("denda"));
        statusPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("statusPengembalian"));
    }

private void loadReturnData() {
    String query = "SELECT p.id_peminjaman, p.id_anggota, a.nama AS nama_anggota, " +
                   "b.id_buku, b.judul AS judul_buku, p.tanggal_pinjam, p.tanggal_jatuh_tempo, " +
                   "p.tanggal_pengembalian, p.status AS status_pengembalian, " +
                   "CASE WHEN p.tanggal_pengembalian IS NOT NULL AND p.tanggal_pengembalian > p.tanggal_jatuh_tempo " +
                   "THEN DATEDIFF(p.tanggal_pengembalian, p.tanggal_jatuh_tempo) * 1000 ELSE 0 END AS denda " +
                   "FROM peminjaman p " +
                   "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                   "JOIN buku b ON p.id_buku = b.id_buku";

    try (Connection conn = Koneksi.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        ObservableList<Pengembalian> returnList = FXCollections.observableArrayList();
        while (rs.next()) {
            returnList.add(new Pengembalian(
                rs.getInt("id_peminjaman"),
                rs.getInt("id_anggota"),
                rs.getString("nama_anggota"),
                rs.getString("judul_buku"),
                rs.getDate("tanggal_pinjam") != null ? rs.getDate("tanggal_pinjam").toLocalDate() : null,
                rs.getDate("tanggal_jatuh_tempo") != null ? rs.getDate("tanggal_jatuh_tempo").toLocalDate() : null,
                rs.getDate("tanggal_pengembalian") != null ? rs.getDate("tanggal_pengembalian").toLocalDate() : null,
                rs.getDouble("denda"),
                rs.getString("status_pengembalian"),
                rs.getInt("id_buku")
            ));
        }
        returnTable.setItems(returnList);

    } catch (SQLException e) {
        showAlert("Error", "Gagal memuat data pengembalian: " + e.getMessage());
    }
}

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            loadReturnData(); // Jika kosong, kembali ke data awal
            return;
        }

        FilteredList<Pengembalian> filteredList = new FilteredList<>(returnTable.getItems(), pengembalian -> 
            pengembalian.getNamaAnggota().toLowerCase().contains(searchText) || 
            pengembalian.getJudulBuku().toLowerCase().contains(searchText)
        );

        returnTable.setItems(filteredList);
    }

    @FXML
    private void handleReturn() {
        Pengembalian selectedPengembalian = returnTable.getSelectionModel().getSelectedItem();
        if (selectedPengembalian != null) {
            if ("Dikembalikan".equals(selectedPengembalian.getStatusPengembalian())) {
                showAlert("Info", "Buku ini sudah dikembalikan.");
                return;
            }

            // Set status pengembalian
            selectedPengembalian.setStatusPengembalian("Dikembalikan");

            // Update stok buku
            selectedPengembalian.updateBookStock(selectedPengembalian.getIdBuku()); 

            // Update database
            try (Connection conn = Koneksi.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE peminjaman SET status = ?, tanggal_pengembalian = CURDATE() WHERE id_peminjaman = ?")) {
                pstmt.setString(1, selectedPengembalian.getStatusPengembalian());
                pstmt.setInt(2, selectedPengembalian.getIdPeminjaman());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                showAlert("Error", "Gagal mengupdate status pengembalian: " + e.getMessage());
            }

            loadReturnData(); // Refresh data tabel
        }
    }

    @FXML
    private void handleDashboardClick(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/ManageBooks.fxml", "Manage Books");
    }

    @FXML
    private void handleManageMembers(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/ManageMembers.fxml", "Manage Members");
    }

    @FXML
    private void handleManageBorrowing(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Peminjaman.fxml", "Manage Borrowing");
    }

    @FXML
    private void handleManageReturn(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Pengembalian.fxml", "Manage Return");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Login.fxml", "Exit");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Library Management System - " + title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load scene: " + e.getMessage());
        }
    }
}
