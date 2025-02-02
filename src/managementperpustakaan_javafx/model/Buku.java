package managementperpustakaan_javafx.model;

public class Buku {
    private int idBuku;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahunTerbit;
    private int stok;

    public Buku(int idBuku, String judul, String penulis, String penerbit, int tahunTerbit, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
    }

    // Getters
    public int getIdBuku() { return idBuku; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public String getPenerbit() { return penerbit; }
    public int getTahunTerbit() { return tahunTerbit; }
    public int getStok() { return stok; }

    // Setters
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }
    public void setTahunTerbit(int tahunTerbit) { this.tahunTerbit = tahunTerbit; }
    public void setStok(int stok) { this.stok = stok; }
} 