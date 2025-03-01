-- Membuat database
CREATE DATABASE IF NOT EXISTS java_ManagementPerpustakaan;
USE java_ManagementPerpustakaan;

-- Tabel admin
CREATE TABLE `admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabel anggota
CREATE TABLE `anggota` (
  `id_anggota` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(255) NOT NULL,
  `alamat` text DEFAULT NULL,
  `nomor_hp` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabel buku
CREATE TABLE `buku` (
  `id_buku` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `penulis` varchar(255) DEFAULT NULL,
  `penerbit` varchar(255) DEFAULT NULL,
  `tahun_terbit` year(4) DEFAULT NULL,
  `stok` int(11) NOT NULL,
  PRIMARY KEY (`id_buku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Trigger untuk mencegah penghapusan buku yang sedang dipinjam
DELIMITER $$
CREATE TRIGGER `cegah_hapus_buku_sedang_dipinjam` 
BEFORE DELETE ON `buku` FOR EACH ROW 
BEGIN
    IF (SELECT COUNT(*) FROM peminjaman WHERE id_buku = OLD.id_buku AND status = 'Dipinjam') > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Buku masih dipinjam, tidak dapat dihapus!';
    END IF;
END$$
DELIMITER ;

-- Trigger untuk mencegah stok negatif
DELIMITER $$
CREATE TRIGGER `cegah_stok_negatif` 
BEFORE UPDATE ON `buku` FOR EACH ROW 
BEGIN
    IF NEW.stok < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stok buku tidak boleh negatif!';
    END IF;
END$$
DELIMITER ;

-- Tabel peminjaman
CREATE TABLE `peminjaman` (
  `id_peminjaman` int(11) NOT NULL AUTO_INCREMENT,
  `id_anggota` int(11) DEFAULT NULL,
  `id_buku` int(11) DEFAULT NULL,
  `tanggal_pinjam` date NOT NULL,
  `tanggal_jatuh_tempo` date NOT NULL,
  `status` varchar(50) DEFAULT 'Dipinjam',
  PRIMARY KEY (`id_peminjaman`),
  FOREIGN KEY (`id_anggota`) REFERENCES `anggota`(`id_anggota`) ON DELETE SET NULL,
  FOREIGN KEY (`id_buku`) REFERENCES `buku`(`id_buku`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabel log transaksi
CREATE TABLE `log_transaksi` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `aksi` varchar(255) NOT NULL,
  `id_peminjaman` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_log`),
  FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman`(`id_peminjaman`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
