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
import java.time.LocalDate;
import java.sql.Date;

/**
 * Kelas Controller FXML
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
     * Menginisialisasi kelas controller.
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
        tanggalJatuhTempoColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalKembali"));
        tanggalPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalKembaliAktual"));
        dendaColumn.setCellValueFactory(new PropertyValueFactory<>("denda"));
        statusPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("statusDenda"));
    }

    private void loadReturnData() {
        String query = "SELECT p.id_peminjaman, p.id_anggota, p.id_buku, " +
                      "p.tanggal_pinjam, p.tanggal_kembali, p.status, " +
                      "a.nama AS nama_anggota, " +
                      "b.judul AS judul_buku " +
                      "FROM peminjaman p " +
                      "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                      "JOIN buku b ON p.id_buku = b.id_buku " +
                      "WHERE p.status = 'Dipinjam' " +
                      "ORDER BY p.tanggal_pinjam DESC";

        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ObservableList<Pengembalian> returnList = FXCollections.observableArrayList();
            while (rs.next()) {
                // Hitung denda
                LocalDate tanggalKembali = rs.getDate("tanggal_kembali").toLocalDate();
                LocalDate today = LocalDate.now();
                long selisihHari = 0;
                if (today.isAfter(tanggalKembali)) {
                    selisihHari = java.time.temporal.ChronoUnit.DAYS.between(tanggalKembali, today);
                }
                double denda = selisihHari * 5000; // Denda 5000 per hari

                returnList.add(new Pengembalian(
                    rs.getInt("id_peminjaman"),
                    rs.getInt("id_anggota"),
                    rs.getString("nama_anggota"),
                    rs.getString("judul_buku"),
                    rs.getDate("tanggal_pinjam").toLocalDate(),
                    rs.getDate("tanggal_kembali").toLocalDate(),
                    null, // tanggal_kembali_aktual belum ada
                    denda,
                    "Belum Lunas",
                    rs.getInt("id_buku")
                ));
            }
            returnTable.setItems(returnList);

        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data peminjaman: " + e.getMessage());
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
            try (Connection conn = Koneksi.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    LocalDate today = LocalDate.now();
                    
                    // Insert into pengembalian table
                    String insertQuery = "INSERT INTO pengembalian (id_peminjaman, tanggal_kembali_aktual, denda, status_denda) " +
                                      "VALUES (?, ?, ?, ?)";
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                        pstmt.setInt(1, selectedPengembalian.getIdPeminjaman());
                        pstmt.setDate(2, Date.valueOf(today));
                        pstmt.setDouble(3, selectedPengembalian.getDenda());
                        pstmt.setString(4, selectedPengembalian.getDenda() > 0 ? "Belum Lunas" : "Lunas");
                        pstmt.executeUpdate();
                    }

                    // Update peminjaman status
                    String updateQuery = "UPDATE peminjaman SET status = 'Dikembalikan' WHERE id_peminjaman = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                        pstmt.setInt(1, selectedPengembalian.getIdPeminjaman());
                        pstmt.executeUpdate();
                    }

                    conn.commit();
                    String message = "Buku berhasil dikembalikan!";
                    if (selectedPengembalian.getDenda() > 0) {
                        message += "\nDenda yang harus dibayar: Rp " + String.format("%,.0f", selectedPengembalian.getDenda());
                    }
                    showSuccessAlert("Sukses", message);
                    loadReturnData();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                showAlert("Error", "Gagal mengembalikan buku: " + e.getMessage());
            }
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

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

    @FXML
    private void handleRowClick() {
        Pengembalian selectedPengembalian = returnTable.getSelectionModel().getSelectedItem();
        if (selectedPengembalian != null) {
            handleReturn();
        }
    }
}
