package managementperpustakaan_javafx.model;

import java.time.LocalDate;

public class Peminjaman {
    
    private int id_peminjaman;
    private String nama;
    private String judul;
    private LocalDate tanggal_pinjam;

       private int idPeminjaman;
    private int idAnggota;
    private int idBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalJatuhTempo;
    private String status;
    
    // Additional fields for UI display
    private String namaAnggota;
    private String judulBuku;

    public Peminjaman(int idPeminjaman, int idAnggota, int idBuku, LocalDate tanggalPinjam, 
                      LocalDate tanggalJatuhTempo, String status) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.status = status;
    }

    public Peminjaman(int id_peminjaman, String nama, String judul, LocalDate tanggal_pinjam) {
        this.id_peminjaman = id_peminjaman;
        this.nama = nama;
        this.judul = judul;
        this.tanggal_pinjam = tanggal_pinjam;
    }

    // Getters
    public int getIdPeminjaman() { return idPeminjaman; }
    public int getIdAnggota() { return idAnggota; }
    public int getIdBuku() { return idBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public String getStatus() { return status; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getJudulBuku() { return judulBuku; }

    // Setters
    public void setIdPeminjaman(int idPeminjaman) { this.idPeminjaman = idPeminjaman; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }
    public void setTanggalPinjam(LocalDate tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }
    public void setTanggalJatuhTempo(LocalDate tanggalJatuhTempo) { this.tanggalJatuhTempo = tanggalJatuhTempo; }
    public void setStatus(String status) { this.status = status; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }

} 