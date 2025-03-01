/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.stream.Stream;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Kelas Controller FXML
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

    @FXML 
    private Button manageReturnBtn;

    @FXML private TableColumn<Buku, Integer> idColumn;
    @FXML private TableColumn<Buku, String> judulColumn;
    @FXML private TableColumn<Buku, String> penulisColumn;
    @FXML private TableColumn<Buku, String> penerbitColumn;
    @FXML private TableColumn<Buku, Integer> tahunColumn;
    @FXML private TableColumn<Buku, Integer> stokColumn;

    @FXML
    private ComboBox<String> searchCriteria;

    /**
     * Menginisialisasi kelas controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        setupSearchCriteria();
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
        try {
            // Load the AddBook.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managementperpustakaan_javafx/AddBook.fxml"));
            Parent root = loader.load();
            
            // Create new stage for add book window
            Stage addBookStage = new Stage();
            addBookStage.setTitle("Tambah Buku Baru");
            addBookStage.setScene(new Scene(root));
            
            // Get the controller
            AddBookController controller = loader.getController();
            
            // Optional: Add a callback when book is added successfully
            // This will refresh the book table after adding a new book
            addBookStage.setOnHidden(e -> {
                loadBooks(); // Refresh the table
            });
            
            // Show the window
            addBookStage.show();
            
        } catch (IOException e) {
            showAlert("Error", "Gagal membuka form tambah buku: " + e.getMessage());
        }
    }
    
    // Method untuk menangani penghapusan buku
    @FXML
    private void handleDeleteBook(ActionEvent event) {
        // Implementasi logika penghapusan buku
    }
    
    // Method untuk menangani export ke PDF
    @FXML
    private void handleExportToPDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")
        );
        
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                
                // Add title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                Paragraph title = new Paragraph("Books Report", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n"));
                
                // Create table
                PdfPTable table = new PdfPTable(6); // 6 columns
                table.setWidthPercentage(100);
                
                // Add header cells
                String[] headers = {"ID", "Title", "Author", "Publisher", "Year", "Stock"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(5);
                    table.addCell(cell);
                }
                
                // Add data rows
                for (Buku book : bookTable.getItems()) {
                    table.addCell(String.valueOf(book.getIdBuku()));
                    table.addCell(book.getJudul());
                    table.addCell(book.getPenulis());
                    table.addCell(book.getPenerbit());
                    table.addCell(String.valueOf(book.getTahunTerbit()));
                    table.addCell(String.valueOf(book.getStok()));
                }
                
                document.add(table);
                document.close();
                
                showSuccessAlert("Export Successful", "Data has been exported to PDF successfully!");
            } catch (Exception e) {
                showAlert("Export Error", "Failed to export data: " + e.getMessage());
            }
        }
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        judulColumn.setCellValueFactory(new PropertyValueFactory<>("judul"));
        penulisColumn.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        penerbitColumn.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        tahunColumn.setCellValueFactory(new PropertyValueFactory<>("tahunTerbit"));
        stokColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
    }

    private void setupSearchCriteria() {
        searchCriteria.getItems().addAll(
            "All",
            "ID",
            "Title",
            "Author",
            "Publisher",
            "Year"
        );
        searchCriteria.setValue("All"); // Set default value
    }

    private void loadBooks() {
        loadBooks("");
    }

    private void loadBooks(String search) {
        ObservableList<Buku> books = FXCollections.observableArrayList();
        
        String searchBy = searchCriteria.getValue();
        String query = buildSearchQuery(searchBy);
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String searchTerm = "%" + search + "%";
            if (searchBy.equals("All")) {
                pstmt.setString(1, searchTerm);
                pstmt.setString(2, searchTerm);
                pstmt.setString(3, searchTerm);
            } else {
                pstmt.setString(1, searchTerm);
            }
            
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
            showAlert("Error", "Failed to load books: " + e.getMessage());
        }
    }

    private String buildSearchQuery(String searchBy) {
        switch (searchBy) {
            case "ID":
                return "SELECT * FROM buku WHERE CAST(id_buku AS CHAR) LIKE ?";
            case "Title":
                return "SELECT * FROM buku WHERE judul LIKE ?";
            case "Author":
                return "SELECT * FROM buku WHERE penulis LIKE ?";
            case "Publisher":
                return "SELECT * FROM buku WHERE penerbit LIKE ?";
            case "Year":
                return "SELECT * FROM buku WHERE CAST(tahun_terbit AS CHAR) LIKE ?";
            default:
                return "SELECT * FROM buku WHERE judul LIKE ? OR penulis LIKE ? OR penerbit LIKE ?";
        }
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
}
