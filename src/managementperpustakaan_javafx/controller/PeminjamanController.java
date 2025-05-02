/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import managementperpustakaan_javafx.controller.Koneksi;
import managementperpustakaan_javafx.model.Peminjaman;

/**
 * Kelas Controller FXML
 *
 * @author ackmadelfanp
 */
public class PeminjamanController implements Initializable {

    @FXML
    private Button dashboardBtn;
    
    @FXML
    private Button manageBookBtn;
    
    @FXML
    private Button manageMemberBtn;
    
    @FXML
    private Button manageBorrowBtn;
    
    @FXML
    private Button logoutBtn;
    
    @FXML
    private Button manageReturnBtn;
    
    @FXML
    private ComboBox<String> memberComboBox;
    
    @FXML
    private ComboBox<String> bookComboBox;
    
    @FXML
    private DatePicker borrowDatePicker;
    
    @FXML
    private DatePicker returnDatePicker;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Peminjaman> borrowingTable;
    
    @FXML
    private TableColumn<Peminjaman, Integer> idColumn;
    
    @FXML
    private TableColumn<Peminjaman, String> memberColumn;
    
    @FXML
    private TableColumn<Peminjaman, String> bookColumn;
    
    @FXML
    private TableColumn<Peminjaman, LocalDate> borrowDateColumn;
    
    @FXML
    private TableColumn<Peminjaman, LocalDate> returnDateColumn;
    
    @FXML
    private TableColumn<Peminjaman, String> statusColumn;

    /**
     * Menginisialisasi kelas controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadComboBoxes();
        loadBorrowings();
        
        // Set default dates
        borrowDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(7));
    }
    
    private void setupTable() {
        memberColumn.setCellValueFactory(new PropertyValueFactory<>("namaAnggota"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("judulBuku"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPinjam"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalKembali"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void loadComboBoxes() {
        try (Connection conn = Koneksi.getConnection()) {
            // Load members
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT nama FROM anggota")) {
                while (rs.next()) {
                    memberComboBox.getItems().add(rs.getString("nama"));
                }
            }
            
            // Load books
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT judul FROM buku WHERE stok > 0")) {
                while (rs.next()) {
                    bookComboBox.getItems().add(rs.getString("judul"));
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data combobox: " + e.getMessage());
        }
    }
    
    private void loadBorrowings() {
        ObservableList<Peminjaman> borrowings = FXCollections.observableArrayList();
        
        String query = "SELECT peminjaman.id_peminjaman, peminjaman.id_anggota, peminjaman.id_buku, " +
                      "peminjaman.tanggal_pinjam, peminjaman.tanggal_kembali, peminjaman.status, " +
                      "anggota.nama as nama_anggota, buku.judul as judul_buku " +
                      "FROM peminjaman " +
                      "JOIN anggota ON peminjaman.id_anggota = anggota.id_anggota " +
                      "JOIN buku ON peminjaman.id_buku = buku.id_buku " +
                      "ORDER BY peminjaman.tanggal_pinjam DESC";
        
        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Peminjaman p = new Peminjaman(
                    rs.getInt("id_peminjaman"),
                    rs.getInt("id_anggota"),
                    rs.getInt("id_buku"),
                    rs.getDate("tanggal_pinjam").toLocalDate(),
                    rs.getDate("tanggal_kembali").toLocalDate(),
                    rs.getString("status")
                );
                p.setNamaAnggota(rs.getString("nama_anggota"));
                p.setJudulBuku(rs.getString("judul_buku"));
                borrowings.add(p);
            }
            
            borrowingTable.setItems(borrowings);
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data peminjaman: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleBorrow() {
        String memberName = memberComboBox.getValue();
        String bookTitle = bookComboBox.getValue();
        LocalDate borrowDate = borrowDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (memberName == null || bookTitle == null || borrowDate == null || returnDate == null) {
            showAlert("Error", "Semua field harus diisi!");
            return;
        }
        
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Get member ID and book ID
                int memberId = getMemberId(conn, memberName);
                int bookId = getBookId(conn, bookTitle);
                
                // Insert peminjaman
                String insertQuery = "INSERT INTO peminjaman (id_anggota, id_buku, tanggal_pinjam, tanggal_kembali, status) " +
                                   "VALUES (?, ?, ?, ?, 'Dipinjam')";
                
                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setInt(1, memberId);
                    pstmt.setInt(2, bookId);
                    pstmt.setDate(3, Date.valueOf(borrowDate));
                    pstmt.setDate(4, Date.valueOf(returnDate));
                    pstmt.executeUpdate();
                }
                                
                conn.commit();
                loadBorrowings();
                clearFields();
                showAlert("Sukses", "Peminjaman berhasil dicatat!");
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal mencatat peminjaman: " + e.getMessage());
        }
    }
    
    private int getMemberId(Connection conn, String memberName) throws SQLException {
        String query = "SELECT id_anggota FROM anggota WHERE nama = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memberName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_anggota");
            }
            throw new SQLException("Anggota tidak ditemukan");
        }
    }
    
    private int getBookId(Connection conn, String bookTitle) throws SQLException {
        String query = "SELECT id_buku FROM buku WHERE judul = ? AND stok > 0";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, bookTitle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_buku");
            }
            throw new SQLException("Buku tidak tersedia");
        }
    }
    
    private void clearFields() {
        memberComboBox.setValue(null);
        bookComboBox.setValue(null);
        borrowDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(7));
    }
    
    private void showAlert(String title, String content) {
        Alert.AlertType type = title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void handleDashboardClick(ActionEvent event) throws IOException {
        loadPage(event, "/managementperpustakaan_javafx/Dashboard.fxml");
    }
    
    @FXML
    private void handleManageBooks(ActionEvent event) throws IOException {
        loadPage(event, "/managementperpustakaan_javafx/ManageBooks.fxml");
    }
    
    @FXML
    private void handleManageMembers(ActionEvent event) throws IOException {
        loadPage(event, "/managementperpustakaan_javafx/ManageMembers.fxml");
    }
    
    @FXML
    private void handleManageBorrowing(ActionEvent event) throws IOException {
        loadPage(event, "/managementperpustakaan_javafx/Peminjaman.fxml");
    }
    
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        loadPage(event, "/managementperpustakaan_javafx/Login.fxml");
    }
    
    @FXML
    private void handleManageReturn(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Pengembalian.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadPage(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
