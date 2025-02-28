/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package managementperpustakaan_javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import managementperpustakaan_javafx.model.Pengembalian;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author ackmadelfanp
 */
public class PengembalianController implements Initializable {

    @FXML
    private TableView<Pengembalian> returnTable; // Tipe data yang sesuai
    @FXML
    private Button kembalikanBtn;

    private ObservableList<Pengembalian> returnData; // Data untuk tabel

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inisialisasi tabel dan data
        initializeTable();
        loadReturnData();
    }

    private void initializeTable() {
        // Mengatur kolom tabel
        TableColumn<Pengembalian, Integer> idPengembalianColumn = new TableColumn<>("ID Pengembalian");
        idPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("idPengembalian"));

        TableColumn<Pengembalian, Integer> idPeminjamanColumn = new TableColumn<>("ID Peminjaman");
        idPeminjamanColumn.setCellValueFactory(new PropertyValueFactory<>("idPeminjaman"));

        TableColumn<Pengembalian, Integer> idAnggotaColumn = new TableColumn<>("ID Anggota");
        idAnggotaColumn.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));

        TableColumn<Pengembalian, Integer> idBukuColumn = new TableColumn<>("ID Buku");
        idBukuColumn.setCellValueFactory(new PropertyValueFactory<>("idBuku"));

        TableColumn<Pengembalian, String> tanggalPinjamColumn = new TableColumn<>("Tanggal Pinjam");
        tanggalPinjamColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPinjam"));

        TableColumn<Pengembalian, String> tanggalJatuhTempoColumn = new TableColumn<>("Tanggal Jatuh Tempo");
        tanggalJatuhTempoColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalJatuhTempo"));

        TableColumn<Pengembalian, String> tanggalPengembalianColumn = new TableColumn<>("Tanggal Pengembalian");
        tanggalPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalPengembalian"));

        TableColumn<Pengembalian, Double> dendaColumn = new TableColumn<>("Denda");
        dendaColumn.setCellValueFactory(new PropertyValueFactory<>("denda"));

        TableColumn<Pengembalian, String> statusPengembalianColumn = new TableColumn<>("Status Pengembalian");
        statusPengembalianColumn.setCellValueFactory(new PropertyValueFactory<>("statusPengembalian"));

        // Menambahkan kolom ke tabel
        returnTable.getColumns().addAll(idPengembalianColumn, idPeminjamanColumn, idAnggotaColumn, idBukuColumn,
                tanggalPinjamColumn, tanggalJatuhTempoColumn, tanggalPengembalianColumn, dendaColumn, statusPengembalianColumn);
    }

    private void loadReturnData() {
        // Mengisi data ke dalam tabel (contoh data)
        returnData = FXCollections.observableArrayList(
            new Pengembalian(1, 101, 201, 301, "2023-01-01", "2023-01-10", null, 0, "Belum Kembali"),
            new Pengembalian(2, 102, 202, 302, "2023-01-02", "2023-01-11", null, 0, "Belum Kembali")
            // Tambahkan data lainnya sesuai kebutuhan
        );

        returnTable.setItems(returnData);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        // Aktifkan tombol Kembalikan jika baris tabel dipilih
        kembalikanBtn.setDisable(returnTable.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void handleKembalikan() {
        // Ambil data dari baris yang dipilih
        Pengembalian selectedReturn = returnTable.getSelectionModel().getSelectedItem();
        if (selectedReturn != null) {
            // Logika untuk mengembalikan buku
            // Misalnya, memperbarui status pengembalian dan stok buku
            selectedReturn.setStatusPengembalian("Sudah Kembali");
            // Update stok buku di database (logika database tidak ditampilkan di sini)

            // Refresh tabel
            returnTable.refresh();
            kembalikanBtn.setDisable(true); // Nonaktifkan tombol setelah pengembalian
        }
    }
}
