package managementperpustakaan_javafx.model;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Pengembalian {
    private final IntegerProperty idPengembalian;
    private final IntegerProperty idPeminjaman;
    private final StringProperty namaPeminjam;
    private final StringProperty judulBuku;
    private final ObjectProperty<LocalDate> tanggalPeminjaman;
    private final ObjectProperty<LocalDate> tanggalPengembalian;
    private final IntegerProperty denda;
    private final IntegerProperty idBuku;

    public Pengembalian(int idPengembalian, int idPeminjaman, String namaPeminjam, 
                       String judulBuku, LocalDate tanggalPeminjaman, 
                       LocalDate tanggalPengembalian, int denda, int idBuku) {
        this.idPengembalian = new SimpleIntegerProperty(idPengembalian);
        this.idPeminjaman = new SimpleIntegerProperty(idPeminjaman);
        this.namaPeminjam = new SimpleStringProperty(namaPeminjam);
        this.judulBuku = new SimpleStringProperty(judulBuku);
        this.tanggalPeminjaman = new SimpleObjectProperty<>(tanggalPeminjaman);
        this.tanggalPengembalian = new SimpleObjectProperty<>(tanggalPengembalian);
        this.denda = new SimpleIntegerProperty(denda);
        this.idBuku = new SimpleIntegerProperty(idBuku);
    }

    // Getters for properties
    public IntegerProperty idPengembalianProperty() { return idPengembalian; }
    public IntegerProperty idPeminjamanProperty() { return idPeminjaman; }
    public StringProperty namaPeminjamProperty() { return namaPeminjam; }
    public StringProperty judulBukuProperty() { return judulBuku; }
    public ObjectProperty<LocalDate> tanggalPeminjamanProperty() { return tanggalPeminjaman; }
    public ObjectProperty<LocalDate> tanggalPengembalianProperty() { return tanggalPengembalian; }
    public IntegerProperty dendaProperty() { return denda; }
    public IntegerProperty idBukuProperty() { return idBuku; }

    // Getters for values
    public int getIdPengembalian() { return idPengembalian.get(); }
    public int getIdPeminjaman() { return idPeminjaman.get(); }
    public String getNamaPeminjam() { return namaPeminjam.get(); }
    public String getJudulBuku() { return judulBuku.get(); }
    public LocalDate getTanggalPeminjaman() { return tanggalPeminjaman.get(); }
    public LocalDate getTanggalPengembalian() { return tanggalPengembalian.get(); }
    public int getDenda() { return denda.get(); }
    public int getIdBuku() { return idBuku.get(); }

    // Setters
    public void setIdPengembalian(int value) { idPengembalian.set(value); }
    public void setIdPeminjaman(int value) { idPeminjaman.set(value); }
    public void setNamaPeminjam(String value) { namaPeminjam.set(value); }
    public void setJudulBuku(String value) { judulBuku.set(value); }
    public void setTanggalPeminjaman(LocalDate value) { tanggalPeminjaman.set(value); }
    public void setTanggalPengembalian(LocalDate value) { tanggalPengembalian.set(value); }
    public void setDenda(int value) { denda.set(value); }
    public void setIdBuku(int value) { idBuku.set(value); }
} 