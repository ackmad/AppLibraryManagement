-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 02, 2025 at 07:22 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_ManagementPerpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`) VALUES
('Raga', '123');

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id_anggota` int(11) NOT NULL,
  `nomor_anggota` varchar(100) DEFAULT NULL,
  `nama` varchar(150) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `nomor_hp` varchar(150) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id_anggota`, `nomor_anggota`, `nama`, `alamat`, `email`, `nomor_hp`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, '122323', 'deni', 'kalikoa', '@gmail', '686868', '2025-02-21 03:01:25', '2025-02-21 03:01:25', NULL),
(2, '214154', 'Yanuar', 'Cirebon', 'yanuar@gmail.com', '291849', '2025-02-22 15:20:35', '2025-02-22 15:20:35', NULL),
(3, '21847', 'Raka', 'Bandung', 'Raka@yahoo.com', '91289', '2025-02-22 15:21:37', '2025-02-22 15:21:37', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id_buku` int(11) NOT NULL,
  `kode_buku` varchar(255) DEFAULT NULL,
  `judul` varchar(255) DEFAULT NULL,
  `penulis` varchar(255) DEFAULT NULL,
  `penerbit` varchar(255) DEFAULT NULL,
  `tahun_terbit` int(11) DEFAULT NULL,
  `kategori` varchar(255) DEFAULT NULL,
  `stok` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id_buku`, `kode_buku`, `judul`, `penulis`, `penerbit`, `tahun_terbit`, `kategori`, `stok`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, '123', 'Toba Lake', 'Orang', 'ppp', 1999, 'action', 13, '2025-02-08 13:42:58', '2025-02-22 15:11:13', NULL),
(2, '13', 'Raga si anak ajaib', 'Raka hP', 'Webtoon', 2003, 'action', 0, '2025-02-20 15:21:56', '2025-02-22 15:21:00', NULL),
(3, 'BK001', 'Laskar Pelangi', 'Andrea Hirata', 'Bentang Pustaka', 2005, 'Novel', 14, '2025-02-22 18:31:11', '2025-02-22 18:35:42', NULL),
(4, 'BK002', 'Bumi Manusia', 'Pramoedya Ananta Toer', 'Hasta Mitra', 1980, 'Novel Sejarah', 10, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(5, 'BK003', 'Pemrograman Java untuk Pemula', 'Eko Kurniawan', 'Informatika', 2020, 'Teknologi', 20, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(6, 'BK004', 'Filosofi Teras', 'Henry Manampiring', 'Kompas', 2018, 'Filsafat', 12, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(7, 'BK005', 'Rich Dad Poor Dad', 'Robert Kiyosaki', 'Gramedia', 2015, 'Keuangan', 8, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(8, 'BK006', 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 'Bloomsbury', 1997, 'Fantasi', 25, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(9, 'BK007', 'Atomic Habits', 'James Clear', 'Penguin Random House', 2018, 'Pengembangan Diri', 18, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(10, 'BK008', 'Sejarah Indonesia Modern', 'M.C. Ricklefs', 'Serambi', 2008, 'Sejarah', 7, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(11, 'BK009', 'Matematika Diskrit', 'Rinaldi Munir', 'Informatika', 2016, 'Matematika', 15, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL),
(12, 'BK010', 'Pulang', 'Tere Liye', 'Republika', 2015, 'Novel', 13, '2025-02-22 18:31:11', '2025-02-22 18:31:11', NULL);

--
-- Triggers `buku`
--
DELIMITER $$
CREATE TRIGGER `prevent_deleting_borrowed_books` BEFORE DELETE ON `buku` FOR EACH ROW BEGIN
    DECLARE borrowed_count INT;

    
    SELECT COUNT(*) INTO borrowed_count
    FROM peminjaman
    WHERE id_buku = OLD.id_buku
      AND status = 'dipinjam';

    
    IF borrowed_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Penghapusan ditolak: Buku ini sedang dipinjam dan tidak dapat dihapus.';
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_stok_buku` AFTER UPDATE ON `buku` FOR EACH ROW BEGIN
    
    IF NEW.stok < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stok buku tidak boleh negatif!';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `log_peminjaman`
--

CREATE TABLE `log_peminjaman` (
  `id_log` int(11) NOT NULL,
  `id_anggota` int(11) DEFAULT NULL,
  `id_buku` int(11) DEFAULT NULL,
  `tanggal_pinjam` date DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `log_peminjaman`
--

INSERT INTO `log_peminjaman` (`id_log`, `id_anggota`, `id_buku`, `tanggal_pinjam`, `status`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 2, '2025-02-04', 'dipinjam', '2025-02-21 03:25:15', '2025-02-21 03:25:15', NULL),
(2, 1, 1, '2008-01-02', 'Dipinjam', '2025-02-22 15:11:13', '2025-02-22 15:11:13', NULL),
(3, 1, 2, '2008-01-15', 'Dipinjam', '2025-02-22 15:14:07', '2025-02-22 15:14:07', NULL),
(4, 2, 2, '2025-02-01', 'Dipinjam', '2025-02-22 15:21:00', '2025-02-22 15:21:00', NULL),
(5, 2, 3, '2008-01-02', 'Dipinjam', '2025-02-22 18:35:42', '2025-02-22 18:35:42', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` int(11) NOT NULL,
  `id_anggota` int(11) DEFAULT NULL,
  `id_buku` int(11) DEFAULT NULL,
  `tanggal_pinjam` date DEFAULT NULL,
  `tanggal_jatuh_tempo` date DEFAULT NULL,
  `status` varchar(150) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `id_anggota`, `id_buku`, `tanggal_pinjam`, `tanggal_jatuh_tempo`, `status`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, '2008-01-02', '2008-01-03', 'Dipinjam', '2025-02-22 15:11:13', '2025-02-22 15:11:13', NULL),
(2, 1, 2, '2008-01-15', '2008-01-30', 'Dipinjam', '2025-02-22 15:14:07', '2025-02-22 15:14:07', NULL),
(3, 2, 2, '2025-02-01', '2025-02-07', 'Dipinjam', '2025-02-22 15:21:00', '2025-02-22 15:21:00', NULL),
(5, 2, 3, '2008-01-02', '2008-01-03', 'Dipinjam', '2025-02-22 18:35:42', '2025-02-22 18:35:42', NULL);

--
-- Triggers `peminjaman`
--
DELIMITER $$
CREATE TRIGGER `kurangi_stok_buku` AFTER INSERT ON `peminjaman` FOR EACH ROW BEGIN
    UPDATE buku
    SET stok = stok - 1
    WHERE id_buku = NEW.id_buku;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `log_borrowing_transaction` AFTER INSERT ON `peminjaman` FOR EACH ROW BEGIN
    
    INSERT INTO log_peminjaman (id_anggota, id_buku, tanggal_pinjam, status)
    VALUES (NEW.id_anggota, NEW.id_buku, NEW.tanggal_pinjam, NEW.status);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `prevent_duplicate_borrowing` BEFORE INSERT ON `peminjaman` FOR EACH ROW BEGIN
    DECLARE existing_borrowing_count INT;

    
    SELECT COUNT(*) INTO existing_borrowing_count
    FROM peminjaman
    WHERE id_anggota = NEW.id_anggota
      AND id_buku = NEW.id_buku
      AND status = 'dipinjam';

    
    IF existing_borrowing_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Peminjaman ditolak: Anggota ini sudah meminjam buku ini dan masih dalam status "Dipinjam".';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `pengembalian`
--

CREATE TABLE `pengembalian` (
  `id_pengembalian` int(11) NOT NULL,
  `id_peminjaman` int(11) DEFAULT NULL,
  `tanggal_kembali_aktual` date DEFAULT NULL,
  `denda` int(11) DEFAULT NULL,
  `status_denda` varchar(150) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Triggers `pengembalian`
--
DELIMITER $$
CREATE TRIGGER `calculate_late_fee` BEFORE INSERT ON `pengembalian` FOR EACH ROW BEGIN
    DECLARE late_days INT;
    DECLARE daily_fee INT DEFAULT 5000; 
    DECLARE total_fee INT;

    
    SELECT DATEDIFF(NEW.tanggal_kembali_aktual, p.tanggal_kembali) INTO late_days
    FROM peminjaman p
    WHERE p.id_peminjaman = NEW.id_peminjaman;

    
    IF late_days > 0 THEN
        SET total_fee = late_days * daily_fee;
        SET NEW.denda = total_fee; 
        SET NEW.status_denda = 'belum lunas'; 
    ELSE
        SET NEW.denda = 0; 
        SET NEW.status_denda = 'lunas'; 
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `handle_book_return` AFTER INSERT ON `pengembalian` FOR EACH ROW BEGIN
    
    UPDATE peminjaman
    SET status = 'Dikembalikan'
    WHERE id_peminjaman = NEW.id_peminjaman;

    
    UPDATE buku
    SET stok = stok + 1
    WHERE id_buku = (SELECT id_buku FROM peminjaman WHERE id_peminjaman = NEW.id_peminjaman);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `prevent_return_of_unborrowed_books` BEFORE INSERT ON `pengembalian` FOR EACH ROW BEGIN
    DECLARE book_borrowed INT;

    
    SELECT COUNT(*) INTO book_borrowed
    FROM peminjaman
    WHERE id_peminjaman = NEW.id_peminjaman;

    
    IF book_borrowed = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Pengembalian ditolak: Buku ini tidak pernah dipinjam.';
    END IF;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id_anggota`),
  ADD KEY `idx_nomor_anggota` (`nomor_anggota`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`),
  ADD KEY `idx_kode_buku` (`kode_buku`);

--
-- Indexes for table `log_peminjaman`
--
ALTER TABLE `log_peminjaman`
  ADD PRIMARY KEY (`id_log`),
  ADD KEY `id_buku` (`id_buku`),
  ADD KEY `id_anggota` (`id_anggota`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_anggota` (`id_anggota`),
  ADD KEY `id_buku` (`id_buku`);

--
-- Indexes for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD PRIMARY KEY (`id_pengembalian`),
  ADD KEY `id_peminjaman` (`id_peminjaman`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anggota`
--
ALTER TABLE `anggota`
  MODIFY `id_anggota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `buku`
--
ALTER TABLE `buku`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `log_peminjaman`
--
ALTER TABLE `log_peminjaman`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id_peminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pengembalian`
--
ALTER TABLE `pengembalian`
  MODIFY `id_pengembalian` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_peminjaman`
--
ALTER TABLE `log_peminjaman`
  ADD CONSTRAINT `log_peminjaman_ibfk_1` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id_buku`),
  ADD CONSTRAINT `log_peminjaman_ibfk_2` FOREIGN KEY (`id_anggota`) REFERENCES `anggota` (`id_anggota`);

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `anggota` (`id_anggota`),
  ADD CONSTRAINT `peminjaman_ibfk_2` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id_buku`);

--
-- Constraints for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD CONSTRAINT `pengembalian_ibfk_1` FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman` (`id_peminjaman`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
