/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managementperpustakaan_javafx.model;

import java.time.LocalDate;

/**
 *
 * @author ackmadelfanp
 */
public class Pengembalian {
    private int idPengembalian;
    private int idPeminjaman;
    private int idAnggota;
    private int idBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalJatuhTempo;
    private LocalDate tanggalPengembalian;
    private double denda;
    private String statusPengembalian;

    // Constructor, getters, and setters
    public Pengembalian(int idPengembalian, int idPeminjaman, int idAnggota, int idBuku, LocalDate tanggalPinjam, LocalDate tanggalJatuhTempo, LocalDate tanggalPengembalian, double denda, String statusPengembalian) {
        this.idPengembalian = idPengembalian;
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalPengembalian = tanggalPengembalian;
        this.denda = denda;
        this.statusPengembalian = statusPengembalian;
    }

    // Getters and Setters
    // ...
}
