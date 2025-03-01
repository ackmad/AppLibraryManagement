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
import managementperpustakaan_javafx.model.Anggota;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class ManageMembersController implements Initializable {

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
    private TableView<Anggota> memberTable;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button exportButton;

    @FXML 
    private Button manageReturnBtn;

    @FXML private TableColumn<Anggota, Integer> idColumn;
    @FXML private TableColumn<Anggota, String> namaColumn;
    @FXML private TableColumn<Anggota, String> alamatColumn;
    @FXML private TableColumn<Anggota, String> nomorHpColumn;
    
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField nomorHpField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        loadMembers();
        
        // Add search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadMembers(newValue);
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/ManageBooks.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        alamatColumn.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        nomorHpColumn.setCellValueFactory(new PropertyValueFactory<>("nomorHp"));
    }

    private void loadMembers() {
        loadMembers("");
    }

    private void loadMembers(String search) {
        ObservableList<Anggota> members = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM anggota WHERE nama LIKE ? OR alamat LIKE ? OR nomor_hp LIKE ?";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String searchTerm = "%" + search + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, searchTerm);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                members.add(new Anggota(
                    rs.getInt("id_anggota"),
                    rs.getString("nama"),
                    rs.getString("alamat"),
                    rs.getString("nomor_hp")
                ));
            }
            
            memberTable.setItems(members);
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data anggota: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddMember() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managementperpustakaan_javafx/AddMember.fxml"));
            Parent root = loader.load();
            
            // Get the controller of AddMember
            AddMemberController addMemberController = loader.getController();
            
            // Set the current controller to be notified when a member is added
            addMemberController.setManageMembersController(this);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to refresh the member table
    public void refreshMemberTable() {
        loadMembers();
    }

    @FXML
    private void handleDeleteMember() {
        Anggota selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if (selectedMember == null) {
            showAlert("Error", "Pilih anggota yang akan dihapus!");
            return;
        }
        
        String query = "DELETE FROM anggota WHERE id_anggota = ?";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, selectedMember.getIdAnggota());
            pstmt.executeUpdate();
            
            loadMembers();
            showAlert("Sukses", "Anggota berhasil dihapus!");
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal menghapus anggota: " + e.getMessage());
        }
    }

    private void clearFields() {
        namaField.clear();
        alamatField.clear();
        nomorHpField.clear();
    }

    private void showAlert(String title, String content) {
        Alert.AlertType type = title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
