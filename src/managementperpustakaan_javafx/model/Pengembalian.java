package managementperpustakaan_javafx.model;

import java.time.LocalDate;

public class Pengembalian {
    private int idPengembalian;
    private int idPeminjaman;
    private LocalDate tanggalPengembalian;
    private int denda;

    public Pengembalian(int idPengembalian, int idPeminjaman, LocalDate tanggalPengembalian, int denda) {
        this.idPengembalian = idPengembalian;
        this.idPeminjaman = idPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
        this.denda = denda;
    }

    // Getters
    public int getIdPengembalian() { return idPengembalian; }
    public int getIdPeminjaman() { return idPeminjaman; }
    public LocalDate getTanggalPengembalian() { return tanggalPengembalian; }
    public int getDenda() { return denda; }

    // Setters
    public void setIdPengembalian(int idPengembalian) { this.idPengembalian = idPengembalian; }
    public void setIdPeminjaman(int idPeminjaman) { this.idPeminjaman = idPeminjaman; }
    public void setTanggalPengembalian(LocalDate tanggalPengembalian) { this.tanggalPengembalian = tanggalPengembalian; }
    public void setDenda(int denda) { this.denda = denda; }
} 