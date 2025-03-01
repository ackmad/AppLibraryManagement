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
    private int idAnggota;
    private String namaAnggota;
    private String judulBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalJatuhTempo;
    private LocalDate tanggalPengembalian;
    private double denda;
    private String statusPengembalian;
    private int idBuku;

    public Pengembalian(int idPeminjaman, int idAnggota, String namaAnggota, String judulBuku,
                        LocalDate tanggalPinjam, LocalDate tanggalJatuhTempo, LocalDate tanggalPengembalian,
                        double denda, String statusPengembalian, int idBuku) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPengembalian = tanggalPengembalian;
        this.denda = denda;
        this.statusPengembalian = statusPengembalian;
        this.idBuku = idBuku;
    }
    
    // Getter dan Setter
    public int getIdPengembalian() { return idPengembalian;}
    public int getIdPeminjaman() { return idPeminjaman; }
    public int getIdAnggota() { return idAnggota; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getJudulBuku() { return judulBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public LocalDate getTanggalPengembalian() { return tanggalPengembalian; }
    public double getDenda() { return denda; }
    public String getStatusPengembalian() { return statusPengembalian; }
    public void setStatusPengembalian(String statusPengembalian) { this.statusPengembalian = statusPengembalian; }
    public int getIdBuku() { return idBuku; }

    // Method to update the book stock
    public void updateBookStock(int idBuku) {
        // Implement logic to increase the stock of the book in the database
        // This would typically involve a database update query
    }

    // ...
}
