package capstone_feast.dao;

import capstone_feast.config.DatabaseConnection;
import capstone_feast.model.BahanBaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BahanBakuDao {

    // Mengambil seluruh data bahan baku untuk tabel
    public List<BahanBaku> getAllBahanBaku() {
        List<BahanBaku> list = new ArrayList<>();
        String query = "SELECT * FROM bahan_baku ORDER BY id_bahan ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                double stokVal = 0.0;
                try {
                    stokVal = rs.getDouble("stok_saat_ini");
                } catch (SQLException e1) {
                    try {
                        stokVal = rs.getDouble("stok");
                    } catch (SQLException e2) {
                        stokVal = 0.0;
                    }
                }

                String statusVal = "Aktif";
                try {
                    statusVal = rs.getString("status_bahan_baku");
                } catch (SQLException e1) {
                    try {
                        statusVal = rs.getString("status");
                    } catch (SQLException e2) {
                        statusVal = "Aktif";
                    }
                }

                BahanBaku b = new BahanBaku(
                    rs.getInt("id_bahan"),
                    rs.getInt("id_admin"),
                    rs.getString("nama_bahan"),
                    rs.getString("satuan"),
                    stokVal,
                    rs.getDouble("stok_minimum"),
                    statusVal
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cek apakah bahan dengan nama yang sama sudah ada di database (Mencegah Duplikasi)
    public BahanBaku getBahanBakuByNama(String namaBahan) {
        String query = "SELECT * FROM bahan_baku WHERE LOWER(nama_bahan) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, namaBahan.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double stokVal = 0.0;
                try { stokVal = rs.getDouble("stok_saat_ini"); } catch (Exception e) { stokVal = rs.getDouble("stok"); }
                
                String statusVal = "Aktif";
                try { statusVal = rs.getString("status_bahan_baku"); } catch (Exception e) { statusVal = rs.getString("status"); }

                return new BahanBaku(
                    rs.getInt("id_bahan"),
                    rs.getInt("id_admin"),
                    rs.getString("nama_bahan"),
                    rs.getString("satuan"),
                    stokVal,
                    rs.getDouble("stok_minimum"),
                    statusVal
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert data bahan baku baru
    public boolean insertBahanBaku(String namaBahan, String satuan, double stokSaatIni, double stokMinimum) {
        String query = "INSERT INTO bahan_baku (id_admin, nama_bahan, satuan, stok_saat_ini, stok_minimum, status_bahan_baku) VALUES (?, ?, ?, ?, ?, 'Aktif')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, 1);
            ps.setString(2, namaBahan);
            ps.setString(3, satuan);
            ps.setDouble(4, stokSaatIni);
            ps.setDouble(5, stokMinimum);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String queryFallback = "INSERT INTO bahan_baku (id_admin, nama_bahan, satuan, stok, stok_minimum, status) VALUES (?, ?, ?, ?, ?, 'Aktif')";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps2 = conn.prepareStatement(queryFallback)) {
                ps2.setInt(1, 1);
                ps2.setString(2, namaBahan);
                ps2.setString(3, satuan);
                ps2.setDouble(4, stokSaatIni);
                ps2.setDouble(5, stokMinimum);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    // Menambah stok saja jika nama bahan sudah ada
    public boolean updateStokBahanBaku(int idBahan, double tambahanStok) {
        String query = "UPDATE bahan_baku SET stok_saat_ini = stok_saat_ini + ? WHERE id_bahan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, tambahanStok);
            ps.setInt(2, idBahan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String queryFallback = "UPDATE bahan_baku SET stok = stok + ? WHERE id_bahan = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps2 = conn.prepareStatement(queryFallback)) {
                ps2.setDouble(1, tambahanStok);
                ps2.setInt(2, idBahan);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    // Update data bahan baku secara menyeluruh
    public boolean updateBahanBaku(int idBahan, String namaBahan, String satuan, double stokSaatIni, double stokMinimum) {
        String query = "UPDATE bahan_baku SET nama_bahan = ?, satuan = ?, stok_saat_ini = ?, stok_minimum = ? WHERE id_bahan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, namaBahan);
            ps.setString(2, satuan);
            ps.setDouble(3, stokSaatIni);
            ps.setDouble(4, stokMinimum);
            ps.setInt(5, idBahan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String queryFallback = "UPDATE bahan_baku SET nama_bahan = ?, satuan = ?, stok = ?, stok_minimum = ? WHERE id_bahan = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps2 = conn.prepareStatement(queryFallback)) {
                ps2.setString(1, namaBahan);
                ps2.setString(2, satuan);
                ps2.setDouble(3, stokSaatIni);
                ps2.setDouble(4, stokMinimum);
                ps2.setInt(5, idBahan);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    // Menonaktifkan bahan baku (Hapus Bahan)
    public boolean nonaktifkanBahanBaku(int idBahan) {
        String query = "UPDATE bahan_baku SET status_bahan_baku = 'Nonaktif' WHERE id_bahan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idBahan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String queryFallback = "UPDATE bahan_baku SET status = 'Nonaktif' WHERE id_bahan = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps2 = conn.prepareStatement(queryFallback)) {
                ps2.setInt(1, idBahan);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    // Mengaktifkan kembali bahan baku yang sebelumnya nonaktif
    public boolean aktifkanBahanBaku(int idBahan) {
        String query = "UPDATE bahan_baku SET status_bahan_baku = 'Aktif' WHERE id_bahan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idBahan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String queryFallback = "UPDATE bahan_baku SET status = 'Aktif' WHERE id_bahan = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps2 = conn.prepareStatement(queryFallback)) {
                ps2.setInt(1, idBahan);
                return ps2.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }
}