/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import managementperpustakaan_javafx.controller.Koneksi;
import managementperpustakaan_javafx.model.Buku;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class ManageBooksController implements Initializable {

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
    private TextField searchField;
    
    @FXML
    private TableView<Buku> bookTable;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button exportButton;

    @FXML private TableColumn<Buku, Integer> idColumn;
    @FXML private TableColumn<Buku, String> judulColumn;
    @FXML private TableColumn<Buku, String> penulisColumn;
    @FXML private TableColumn<Buku, String> penerbitColumn;
    @FXML private TableColumn<Buku, Integer> tahunColumn;
    @FXML private TableColumn<Buku, Integer> stokColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadBooks();
        
        // Add search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadBooks(newValue);
        });
    }    
    
    @FXML
    private void handleDashboardClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Dashboard.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleManageBooks(ActionEvent event) {
        // Already on Manage Books page, no need to navigate
    }
    
    @FXML
    private void handleManageMembers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/ManageMembers.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleManageBorrowing(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Peminjaman.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Login.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method untuk menangani pencarian buku
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        // Implementasi logika pencarian
    }
    
    // Method untuk menangani penambahan buku
    @FXML
    private void handleAddBook(ActionEvent event) {
        // Implementasi logika penambahan buku
    }
    
    // Method untuk menangani penghapusan buku
    @FXML
    private void handleDeleteBook(ActionEvent event) {
        // Implementasi logika penghapusan buku
    }
    
    // Method untuk menangani export ke Excel
    @FXML
    private void handleExportToExcel(ActionEvent event) {
        // Implementasi logika export ke Excel
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        judulColumn.setCellValueFactory(new PropertyValueFactory<>("judul"));
        penulisColumn.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        penerbitColumn.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        tahunColumn.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        stokColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
    }

    private void loadBooks() {
        loadBooks("");
    }

    private void loadBooks(String search) {
        ObservableList<Buku> books = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM buku WHERE judul LIKE ? OR penulis LIKE ? OR penerbit LIKE ?";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String searchTerm = "%" + search + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, searchTerm);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                books.add(new Buku(
                    rs.getInt("id_buku"),
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("penerbit"),
                    rs.getInt("tahun_terbit"),
                    rs.getInt("stok")
                ));
            }
            
            bookTable.setItems(books);
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data buku: " + e.getMessage());
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
