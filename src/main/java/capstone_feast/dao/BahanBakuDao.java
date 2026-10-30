package capstone_feast.dao;

import capstone_feast.config.DatabaseConnection;
import capstone_feast.model.BahanBaku;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BahanBakuDao {
    private Connection connection;

    public BahanBakuDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<BahanBaku> getAllBahanBaku() {
        List<BahanBaku> list = new ArrayList<>();
        String query = "SELECT * FROM bahan_baku";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                BahanBaku item = new BahanBaku(
                    rs.getInt("id_bahan"),
                    rs.getInt("id_admin"),
                    rs.getString("nama_bahan"),
                    rs.getString("satuan"),
                    rs.getDouble("stok_saat_ini"),
                    rs.getDouble("stok_minimum"),
                    rs.getString("status_bahan_baku")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(BahanBaku bahan) {
        String query = "INSERT INTO bahan_baku (id_admin, nama_bahan, satuan, stok_saat_ini, stok_minimum, status_bahan_baku) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, bahan.getIdAdmin());
            ps.setString(2, bahan.getNamaBahan());
            ps.setString(3, bahan.getSatuan());
            ps.setDouble(4, bahan.getStokSaatIni());
            ps.setDouble(5, bahan.getStokMinimum());
            ps.setString(6, bahan.getStatusBahanBaku());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(BahanBaku bahan) {
        String query = "UPDATE bahan_baku SET nama_bahan = ?, satuan = ?, stok_saat_ini = ?, stok_minimum = ?, status_bahan_baku = ? WHERE id_bahan = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, bahan.getNamaBahan());
            ps.setString(2, bahan.getSatuan());
            ps.setDouble(3, bahan.getStokSaatIni());
            ps.setDouble(4, bahan.getStokMinimum());
            ps.setString(5, bahan.getStatusBahanBaku());
            ps.setInt(6, bahan.getIdBahan());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idBahan) {
        String query = "DELETE FROM bahan_baku WHERE id_bahan = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBahan);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}