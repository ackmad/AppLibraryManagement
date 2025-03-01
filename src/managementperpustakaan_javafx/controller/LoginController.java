/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import managementperpustakaan_javafx.model.Admin;

/**
 * Kelas Controller FXML
 *
 * @author ackmadelfanp
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label statusLabel;
    
    private Admin loggedInAdmin;
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username dan password harus diisi!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        if (validateLogin(username, password)) {
            try {
                // Load Dashboard FXML
                Parent dashboardParent = FXMLLoader.load(getClass().getResource("/managementperpustakaan_javafx/Dashboard.fxml"));
                Scene dashboardScene = new Scene(dashboardParent);
                
                // Get stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                
                // Set new scene
                window.setScene(dashboardScene);
                window.setTitle("Sistem Manajemen Perpustakaan - Dashboard");
                window.show();
                
            } catch (Exception e) {
                statusLabel.setText("Error saat memuat dashboard!");
                statusLabel.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Username atau password salah!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
    
    private boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Store logged in admin information
                    loggedInAdmin = new Admin(
                        rs.getInt("id_admin"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("password")
                    );
                    return true;
                }
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error saat memeriksa database!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Reset status label saat aplikasi dimulai
        statusLabel.setText("");
        
        // Tambahkan event listener untuk enter key pada password field
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                loginButton.fire();
            }
        });
    }
    
    // Method untuk mendapatkan admin yang sedang login
    public Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }
}
