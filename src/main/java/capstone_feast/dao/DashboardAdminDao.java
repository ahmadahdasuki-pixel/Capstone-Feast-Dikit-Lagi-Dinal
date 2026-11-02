package capstone_feast.dao;

import capstone_feast.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardAdminDao {
    private Connection connection;

    public DashboardAdminDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    // 1. Statistik Bahan Baku secara Real-Time
    public Map<String, Integer> getStatistikBahanBaku() {
        Map<String, Integer> stats = new HashMap<>();
        int total = 0, tersedia = 0, minimum = 0, nonaktif = 0;
        
        String query = "SELECT * FROM bahan_baku";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Mendapatkan daftar nama kolom dari database secara otomatis
            java.sql.ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            String colStatus = "";
            String colStok = "";
            String colMin = "";

            // Deteksi otomatis nama kolom yang mirip di database Anda
            for (int i = 1; i <= columnCount; i++) {
                String colName = metaData.getColumnName(i).toLowerCase();
                if (colName.contains("status")) {
                    colStatus = colName;
                } else if (colName.contains("stok") && !colName.contains("min")) {
                    colStok = colName;
                } else if (colName.contains("min") || colName.contains("batas")) {
                    colMin = colName;
                }
            }

            while (rs.next()) {
                total++; // Menghitung total seluruh baris data bahan baku secara akurat (menjadi 15)
                
                String status = "tersedia";
                if (!colStatus.isEmpty()) {
                    try { status = rs.getString(colStatus); } catch (Exception e) {}
                }

                double stok = 0;
                if (!colStok.isEmpty()) {
                    try { stok = rs.getDouble(colStok); } catch (Exception e) {}
                }

                double min = 0;
                if (!colMin.isEmpty()) {
                    try { min = rs.getDouble(colMin); } catch (Exception e) {}
                }

                // Evaluasi status dan stok minimum
                if (status != null && (status.equalsIgnoreCase("aktif") || status.equalsIgnoreCase("tersedia"))) {
                    if (min > 0 && stok <= min) {
                        minimum++;
                    } else {
                        tersedia++;
                    }
                } else {
                    nonaktif++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Jika karena suatu hal status tidak terbaca tapi total ada, masukkan ke tersedia agar tidak 0
        if (tersedia == 0 && minimum == 0 && nonaktif == 0 && total > 0) {
            tersedia = total;
        }

        stats.put("total", total);
        stats.put("tersedia", tersedia);
        stats.put("minimum", minimum);
        stats.put("nonaktif", nonaktif);
        return stats;
    }

    // 2. Statistik Produk secara Real-Time
    public Map<String, Integer> getStatistikProduk() {
        Map<String, Integer> stats = new HashMap<>();
        int total = 0, aktif = 0, nonaktif = 0;

        String query = "SELECT status_produk FROM produk";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                total++;
                String status = rs.getString("status_produk");
                if (status != null && status.equalsIgnoreCase("aktif")) {
                    aktif++;
                } else {
                    nonaktif++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stats.put("total", total);
        stats.put("aktif", aktif);
        stats.put("nonaktif", nonaktif);
        return stats;
    }

    // 3. Statistik Produksi Batch secara Real-Time
    public Map<String, Integer> getStatistikBatch() {
        Map<String, Integer> stats = new HashMap<>();
        int total = 0, selesai = 0, gagal = 0;

        String query = "SELECT status_batch FROM produksi_batch";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                total++;
                String status = rs.getString("status_batch");
                if (status != null && (status.equalsIgnoreCase("selesai") || status.equalsIgnoreCase("aktif"))) {
                    selesai++;
                } else {
                    gagal++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stats.put("total", total);
        stats.put("selesai", selesai);
        stats.put("gagal", gagal);
        return stats;
    }

    // 4. Statistik Distribusi secara Real-Time
    public Map<String, Integer> getStatistikDistribusi() {
        Map<String, Integer> stats = new HashMap<>();
        int total = 0, selesai = 0, proses = 0;

        String query = "SELECT status_distribusi FROM distribusi";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                total++;
                String status = rs.getString("status_distribusi");
                if (status != null && status.equalsIgnoreCase("selesai")) {
                    selesai++;
                } else {
                    proses++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stats.put("total", total);
        stats.put("selesai", selesai);
        stats.put("proses", proses);
        return stats;
    }

    // 5. Data untuk Tabel "Batch Terbaru"
    public List<Object[]> getBatchTerbaru() {
        List<Object[]> list = new ArrayList<>();
        // Menghapus LIMIT 10 agar semua data (termasuk id_batch 27 & 29) ikut termuat
        String query = "SELECT p.nama_produk, pb.id_batch, pb.jumlah_produksi, pb.tanggal_kadaluarsa, pb.status_batch " +
                       "FROM produksi_batch pb " +
                       "JOIN produk p ON pb.id_produk = p.id_produk " +
                       "ORDER BY pb.id_batch DESC"; 
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String namaProduk = rs.getString("nama_produk");
                String batchId = "BAT-" + rs.getInt("id_batch");
                int jumlah = rs.getInt("jumlah_produksi");
                String kadaluarsa = rs.getString("tanggal_kadaluarsa");
                String status = rs.getString("status_batch");

                list.add(new Object[]{namaProduk, batchId, jumlah, kadaluarsa, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}