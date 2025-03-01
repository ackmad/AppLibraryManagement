package managementperpustakaan_javafx.model;

public class Admin {
    private int idAdmin;
    private String username;
    private String name;
    private String password;

    public Admin(int idAdmin, String username, String name, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    // Getter
    public int getIdAdmin() { return idAdmin; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    // Setter
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
} 