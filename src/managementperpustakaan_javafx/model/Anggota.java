package managementperpustakaan_javafx.model;

public class Anggota {
    private int idAnggota;
    private String nama;
    private String alamat;
    private String nomorHp;

    public Anggota(int idAnggota, String nama, String alamat, String nomorHp) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.alamat = alamat;
        this.nomorHp = nomorHp;
    }

    // Getters
    public int getIdAnggota() { return idAnggota; }
    public String getNama() { return nama; }
    public String getAlamat() { return alamat; }
    public String getNomorHp() { return nomorHp; }

    // Setters
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public void setNama(String nama) { this.nama = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNomorHp(String nomorHp) { this.nomorHp = nomorHp; }
} 