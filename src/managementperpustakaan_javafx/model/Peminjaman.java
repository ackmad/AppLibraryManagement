package managementperpustakaan_javafx.model;

import java.time.LocalDate;

public class Peminjaman {
    private int idPeminjaman;
    private int idAnggota;
    private int idBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String status;
    
    // Additional fields for UI display
    private String namaAnggota;
    private String judulBuku;

    public Peminjaman(int idPeminjaman, int idAnggota, int idBuku, LocalDate tanggalPinjam, 
                      LocalDate tanggalKembali, String status) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.status = status;
    }

    // Getters
    public int getIdPeminjaman() { return idPeminjaman; }
    public int getIdAnggota() { return idAnggota; }
    public int getIdBuku() { return idBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public String getStatus() { return status; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getJudulBuku() { return judulBuku; }

    // Setters
    public void setIdPeminjaman(int idPeminjaman) { this.idPeminjaman = idPeminjaman; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }
    public void setTanggalPinjam(LocalDate tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }
    public void setTanggalKembali(LocalDate tanggalKembali) { this.tanggalKembali = tanggalKembali; }
    public void setStatus(String status) { this.status = status; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }
}