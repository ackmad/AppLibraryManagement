/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managementperpustakaan_javafx.model;

import java.time.LocalDate;

/**
 * Kelas Pengembalian
 * 
 * @author ackmadelfanp
 */
public class Pengembalian {
    private int idPengembalian;
    private int idPeminjaman;
    private LocalDate tanggalKembaliAktual;
    private double denda;
    private String statusDenda;
    
    // Additional fields for UI display
    private String namaAnggota;
    private String judulBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private String statusPengembalian;
    private int idAnggota;
    private int idBuku;

    public Pengembalian(int idPeminjaman, int idAnggota, String namaAnggota, String judulBuku,
                        LocalDate tanggalPinjam, LocalDate tanggalKembali, LocalDate tanggalKembaliAktual,
                        double denda, String statusDenda, int idBuku) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
        this.tanggalKembaliAktual = tanggalKembaliAktual;
        this.denda = denda;
        this.statusDenda = statusDenda;
        this.idBuku = idBuku;
    }
    
    // Getters
    public int getIdPengembalian() { return idPengembalian; }
    public int getIdPeminjaman() { return idPeminjaman; }
    public LocalDate getTanggalKembaliAktual() { return tanggalKembaliAktual; }
    public double getDenda() { return denda; }
    public String getStatusDenda() { return statusDenda; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getJudulBuku() { return judulBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }
    public String getStatusPengembalian() { return statusPengembalian; }
    public int getIdAnggota() { return idAnggota; }
    public int getIdBuku() { return idBuku; }

    // Setters
    public void setIdPengembalian(int idPengembalian) { this.idPengembalian = idPengembalian; }
    public void setIdPeminjaman(int idPeminjaman) { this.idPeminjaman = idPeminjaman; }
    public void setTanggalKembaliAktual(LocalDate tanggalKembaliAktual) { this.tanggalKembaliAktual = tanggalKembaliAktual; }
    public void setDenda(double denda) { this.denda = denda; }
    public void setStatusDenda(String statusDenda) { this.statusDenda = statusDenda; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }
    public void setTanggalPinjam(LocalDate tanggalPinjam) { this.tanggalPinjam = tanggalPinjam; }
    public void setTanggalKembali(LocalDate tanggalKembali) { this.tanggalKembali = tanggalKembali; }
    public void setStatusPengembalian(String statusPengembalian) { this.statusPengembalian = statusPengembalian; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }

    // Method to update the book stock
    public void updateBookStock(int idBuku) {
        // Implement logic to increase the stock of the book in the database
        // This would typically involve a database update query
    }
}
