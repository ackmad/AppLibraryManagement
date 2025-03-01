package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import managementperpustakaan_javafx.model.Peminjaman;

public class DashboardController implements Initializable {

    // Label untuk jumlah buku
    @FXML private Label labelJumlahBuku;
    // Label untuk anggota aktif
    @FXML private Label labelAnggotaAktif;
    // Label untuk buku yang dipinjam
    @FXML private Label labelBukuDipinjam;
    // Label untuk total denda
    @FXML private Label labelTotalDenda;
    @FXML private Button manageReturnBtn;
    @FXML private TableView<String> anggotaTableView;
    @FXML private TableColumn<String, String> namaColumn;
    
    @FXML private TableView<Peminjaman> recentActivitiesTable;
    @FXML private TableColumn<Peminjaman, Integer> idColumn;
    @FXML private TableColumn<Peminjaman, String> namaColumnPeminjaman;
    @FXML private TableColumn<Peminjaman, String> bukuColumn;
    @FXML private TableColumn<Peminjaman, String> tanggalColumn;

    /**
     * Menginisialisasi kelas controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateDashboardData();
        loadAnggotaList();
    }

    private void updateDashboardData() {
        try (Connection conn = Koneksi.getConnection()) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(stok) as total_buku FROM buku")) {
                if (rs.next()) {
                    labelJumlahBuku.setText(String.valueOf(rs.getInt("total_buku")));
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total_anggota FROM anggota")) {
                if (rs.next()) {
                    labelAnggotaAktif.setText(String.valueOf(rs.getInt("total_anggota")));
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total_dipinjam FROM peminjaman WHERE status = 'Dipinjam'")) {
                if (rs.next()) {
                    labelBukuDipinjam.setText(String.valueOf(rs.getInt("total_dipinjam")));
                }
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(denda) as total_denda FROM pengembalian")) {
                if (rs.next()) {
                    labelTotalDenda.setText(String.format("Rp %,d", rs.getInt("total_denda")));
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal mengambil data dashboard: " + e.getMessage());
        }
    }

    private void loadAnggotaList() {
        ObservableList<String> anggotaList = FXCollections.observableArrayList();

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT nama FROM anggota");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                anggotaList.add(rs.getString("nama"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        anggotaTableView.setItems(anggotaList);
        namaColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    }

    @FXML
    private void handleDashboardClick(ActionEvent event) {
        updateDashboardData();
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/ManageBooks.fxml", "Managemen Buku");
    }

    @FXML
    private void handleManageMembers(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/ManageMembers.fxml", "Managemen Anggota");
    }

    @FXML
    private void handleManageBorrowing(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Peminjaman.fxml", "Pinjaman");
    }

    @FXML
    private void handleManageReturn(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Pengembalian.fxml", "Pengembalian");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        loadScene(event, "/managementperpustakaan_javafx/Login.fxml", "Login");
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
            showAlert("Error", "Failed to load " + title + " page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}