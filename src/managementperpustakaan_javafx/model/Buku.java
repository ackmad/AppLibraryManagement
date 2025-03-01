package managementperpustakaan_javafx.model;

import java.sql.Timestamp;

public class Buku {
    private int idBuku;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahunTerbit;
    private int stok;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public Buku(int idBuku, String judul, String penulis, String penerbit, int tahunTerbit, int stok, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getIdBuku() { return idBuku; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public String getPenerbit() { return penerbit; }
    public int getTahunTerbit() { return tahunTerbit; }
    public int getStok() { return stok; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public Timestamp getDeletedAt() { return deletedAt; }

    // Setters
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }
    public void setTahunTerbit(int tahunTerbit) { this.tahunTerbit = tahunTerbit; }
    public void setStok(int stok) { this.stok = stok; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Timestamp deletedAt) { this.deletedAt = deletedAt; }
} 