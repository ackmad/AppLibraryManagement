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
import java.time.LocalDateTime;
import managementperpustakaan_javafx.controller.Koneksi;

public class DashboardController implements Initializable {

    @FXML private Label labelJumlahBuku;
    @FXML private Label labelAnggotaAktif;
    @FXML private Label labelBukuDipinjam;
    @FXML private Label labelTotalDenda;
    @FXML private Button manageReturnBtn;
    
    @FXML private TableView<RecentActivity> recentActivitiesTable;
    @FXML private TableColumn<RecentActivity, String> dateColumn;
    @FXML private TableColumn<RecentActivity, String> activityColumn;
    @FXML private TableColumn<RecentActivity, String> userColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateDashboardData();
        setupRecentActivitiesTable();
        loadRecentActivities();
    }

    private void updateDashboardData() {
        try (Connection conn = Koneksi.getConnection()) {
            // Menghitung jumlah buku
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(stok) as total_buku FROM buku")) {
                if (rs.next()) {
                    labelJumlahBuku.setText(String.valueOf(rs.getInt("total_buku")));
                }
            }

            // Menghitung jumlah anggota
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total_anggota FROM anggota")) {
                if (rs.next()) {
                    labelAnggotaAktif.setText(String.valueOf(rs.getInt("total_anggota")));
                }
            }

            // Menghitung buku yang sedang dipinjam
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total_dipinjam FROM peminjaman WHERE status = 'Dipinjam'")) {
                if (rs.next()) {
                    labelBukuDipinjam.setText(String.valueOf(rs.getInt("total_dipinjam")));
                }
            }

            // Menghitung total denda
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT SUM(denda) as total_denda FROM pengembalian")) {
                if (rs.next()) {
                    int totalDenda = rs.getInt("total_denda");
                    labelTotalDenda.setText(String.format("Rp %,d", totalDenda));
                }
            }

        } catch (SQLException e) {
            showAlert("Error", "Gagal mengambil data dashboard: " + e.getMessage());
        }
    }

    private void setupRecentActivitiesTable() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
    }

    private void loadRecentActivities() {
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT lt.timestamp, lt.aksi, a.nama " +
                "FROM log_transaksi lt " +
                "JOIN peminjaman p ON lt.id_peminjaman = p.id_peminjaman " +
                "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                "ORDER BY lt.timestamp DESC LIMIT 10")) {

            ObservableList<RecentActivity> activities = FXCollections.observableArrayList();
            
            while (rs.next()) {
                activities.add(new RecentActivity(
                    rs.getTimestamp("timestamp").toString(),
                    rs.getString("aksi"),
                    rs.getString("nama")
                ));
            }
            
            recentActivitiesTable.setItems(activities);

        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat aktivitas terbaru: " + e.getMessage());
        }
    }

    @FXML
    private void handleDashboardClick(ActionEvent event) {
        // Refresh dashboard data
        updateDashboardData();
        loadRecentActivities();
    }

    @FXML
    private void handleManageBooks(ActionEvent event) {
        try {
            loadScene(event, "/managementperpustakaan_javafx/ManageBooks.fxml", "Manage Books");
        } catch (Exception e) {
            showAlert("Error", "Failed to load Manage Books page: " + e.getMessage());
        }
    }

    @FXML
    private void handleManageMembers(ActionEvent event) {
        try {
            loadScene(event, "/managementperpustakaan_javafx/ManageMembers.fxml", "Manage Members");
        } catch (Exception e) {
            showAlert("Error", "Failed to load Manage Members page: " + e.getMessage());
        }
    }

    @FXML
    private void handleManageBorrowing(ActionEvent event) {
        try {
            loadScene(event, "/managementperpustakaan_javafx/Peminjaman.fxml", "Manage Borrowing");
        } catch (Exception e) {
            showAlert("Error", "Failed to load Manage Borrowing page: " + e.getMessage());
        }
    }

    @FXML
    private void handleManageReturn(ActionEvent event) {
        try {
            loadScene(event, "/managementperpustakaan_javafx/Peminjaman.fxml", "Manage Borrowing");
        } catch (Exception e) {
            showAlert("Error", "Failed to load Manage Borrowing page: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            loadScene(event, "/managementperpustakaan_javafx/Login.fxml", "Login");
        } catch (Exception e) {
            showAlert("Error", "Failed to logout: " + e.getMessage());
        }
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Library Management System - " + title);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Inner class for Recent Activities
    public static class RecentActivity {
        private String date;
        private String activity;
        private String user;

        public RecentActivity(String date, String activity, String user) {
            this.date = date;
            this.activity = activity;
            this.user = user;
        }

        public String getDate() { return date; }
        public String getActivity() { return activity; }
        public String getUser() { return user; }
    }
}
