/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import managementperpustakaan_javafx.model.Pengembalian;
import managementperpustakaan_javafx.controller.Koneksi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class PengembalianController implements Initializable {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private TableView<Pengembalian> borrowedBooksTable;
    @FXML private TableColumn<Pengembalian, Integer> memberIdColumn;
    @FXML private TableColumn<Pengembalian, String> memberNameColumn;
    @FXML private TableColumn<Pengembalian, String> bookTitleColumn;
    @FXML private TableColumn<Pengembalian, LocalDate> borrowDateColumn;
    @FXML private TableColumn<Pengembalian, LocalDate> dueDateColumn;
    @FXML private TableColumn<Pengembalian, Integer> fineColumn;
    @FXML private Button returnButton;
    @FXML private Button dashboardBtn;
    @FXML private Button manageBookBtn;
    @FXML private Button manageMemberBtn;
    @FXML private Button manageBorrowBtn;
    @FXML private Button logoutBtn;
    @FXML private Button manageReturnBtn;

    private Connection connection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = Koneksi.getConnection();

        // Initialize table columns
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("idPeminjaman"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("namaPeminjam"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("judulBuku"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPeminjaman"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPengembalian"));
        fineColumn.setCellValueFactory(new PropertyValueFactory<>("denda"));

        // Format the fine column to show currency
        fineColumn.setCellFactory(column -> new TableCell<Pengembalian, Integer>() {
            @Override
            protected void updateItem(Integer denda, boolean empty) {
                super.updateItem(denda, empty);
                if (empty || denda == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp %,d", denda));
                }
            }
        });

        // Enable return button only when a row is selected
        borrowedBooksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            returnButton.setDisable(newSelection == null);
        });

        loadBorrowedBooks();
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadBorrowedBooks();
            return;
        }

        try {
            String query = "SELECT p.*, pm.nama_peminjam, b.judul " +
                          "FROM pengembalian p " +
                          "JOIN peminjaman pm ON p.id_peminjaman = pm.id " +
                          "JOIN buku b ON pm.id_buku = b.id " +
                          "WHERE pm.nama_peminjam LIKE ? OR b.judul LIKE ?";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            
            ResultSet rs = stmt.executeQuery();
            borrowedBooksTable.getItems().clear();
            while (rs.next()) {
                borrowedBooksTable.getItems().add(createPengembalianFromResultSet(rs));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal mencari data");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturnBook() {
        Pengembalian selectedBook = borrowedBooksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) return;

        try {
            // Calculate fine if any
            LocalDate returnDate = LocalDate.now();
            LocalDate dueDate = selectedBook.getTanggalPengembalian();
            double fine = 0.0;
            
            if (returnDate.isAfter(dueDate)) {
                long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                fine = daysLate * 5000; // Rp 5000 per day
            }

            // Update the borrowing record
            String updateQuery = "UPDATE pengembalian SET tanggal_pengembalian = ?, denda = ? WHERE id_pengembalian = ?";
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setDouble(2, fine);
            stmt.setInt(3, selectedBook.getIdPengembalian());
            stmt.executeUpdate();

            // Update book stock
            String updateBookQuery = "UPDATE buku SET stock = stock + 1 WHERE id = ?";
            PreparedStatement bookStmt = connection.prepareStatement(updateBookQuery);
            bookStmt.setInt(1, selectedBook.getIdBuku());
            bookStmt.executeUpdate();

            // Show success message with fine information
            String message = "Buku berhasil dikembalikan.";
            if (fine > 0) {
                message += "\nDenda keterlambatan: Rp " + String.format("%,.0f", fine);
            }
            showAlert(Alert.AlertType.INFORMATION, "Sukses", message);

            // Refresh the table
            loadBorrowedBooks();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal mengembalikan buku");
            e.printStackTrace();
        }
    }

    private void loadBorrowedBooks() {
        try {
            String query = "SELECT p.id_pengembalian, p.id_peminjaman, p.tanggal_pengembalian, p.denda, " +
                          "pm.nama_peminjam, pm.tanggal_peminjaman, pm.id_buku, " +
                          "b.judul " +
                          "FROM pengembalian p " +
                          "JOIN peminjaman pm ON p.id_peminjaman = pm.id " +
                          "JOIN buku b ON pm.id_buku = b.id";
            
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            borrowedBooksTable.getItems().clear();
            while (rs.next()) {
                borrowedBooksTable.getItems().add(createPengembalianFromResultSet(rs));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat data");
            e.printStackTrace();
        }
    }

    private Pengembalian createPengembalianFromResultSet(ResultSet rs) throws SQLException {
        return new Pengembalian(
            rs.getInt("id_pengembalian"),
            rs.getInt("id_peminjaman"),
            rs.getString("nama_peminjam"),
            rs.getString("judul"),
            rs.getDate("tanggal_peminjaman").toLocalDate(),
            rs.getDate("tanggal_pengembalian").toLocalDate(),
            rs.getInt("denda"),
            rs.getInt("id_buku")
        );
    }

    // Navigation methods
    @FXML
    private void handleDashboardClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Dashboard.fxml"));
            Scene scene = dashboardBtn.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageBooks() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/ManageBooks.fxml"));
            Scene scene = manageBookBtn.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageMembers() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/ManageMembers.fxml"));
            Scene scene = manageMemberBtn.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageBorrowing() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Peminjaman.fxml"));
            Scene scene = manageBorrowBtn.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Login.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleManageReturn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Pengembalian.fxml"));
            Stage stage = (Stage) manageReturnBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
