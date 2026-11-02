package capstone_feast.dao;

import capstone_feast.config.DatabaseConnection;
import capstone_feast.model.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private Connection connection;

    public StaffDao() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Staff> getStaffList(String keyword) {
        List<Staff> list = new ArrayList<>();
        String query = "SELECT p.id_pengguna AS id_staff, p.nama_pengguna, p.username, p.password, p.status_akun, p.nomor_telepon, s.shift " +
                       "FROM pengguna p JOIN staff s ON p.id_pengguna = s.id_pengguna " +
                       "WHERE p.nama_pengguna LIKE ? OR p.username LIKE ? OR p.nomor_telepon LIKE ? ORDER BY p.id_pengguna ASC";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff s = new Staff(
                        rs.getInt("id_staff"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("status_akun"),
                        rs.getString("nomor_telepon"),
                        rs.getString("shift")
                    );
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Staff staff) {
        String queryPengguna = "INSERT INTO pengguna (nama_pengguna, username, password, status_akun, nomor_telepon) VALUES (?, ?, ?, ?, ?)";
        String queryStaff = "INSERT INTO staff (id_pengguna, shift) VALUES (?, ?)";
        
        try {
            connection.setAutoCommit(false);
            
            try (PreparedStatement ps1 = connection.prepareStatement(queryPengguna, Statement.RETURN_GENERATED_KEYS)) {
                String nama = (staff.getNamaPengguna() != null && !staff.getNamaPengguna().isEmpty()) ? staff.getNamaPengguna() : staff.getNamaPengguna();
                
                ps1.setString(1, nama);
                ps1.setString(2, staff.getUsername());
                ps1.setString(3, staff.getPassword());
                ps1.setString(4, staff.getStatusAkun() != null ? staff.getStatusAkun() : "Aktif");
                ps1.setString(5, staff.getNomorTelepon() != null ? staff.getNomorTelepon() : "");
                ps1.executeUpdate();
                
                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPenggunaBaru = rs.getInt(1);
                        try (PreparedStatement ps2 = connection.prepareStatement(queryStaff)) {
                            ps2.setInt(1, idPenggunaBaru);
                            ps2.setString(2, staff.getShift() != null ? staff.getShift() : "Pagi");
                            ps2.executeUpdate();
                        }
                    }
                }
            }
            
            connection.commit();
            return true;
            
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.err.println("Duplicate entry error (Username sudah ada): " + e.getMessage());
            return false; 
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean insert(Staff staff, String password) {
        staff.setPassword(password);
        return insert(staff);
    }

    public boolean update(Staff staff) {
        String queryPengguna = "UPDATE pengguna SET nama_pengguna = ?, username = ?, password = ?, status_akun = ?, nomor_telepon = ? WHERE id_pengguna = ?";
        String queryStaff = "UPDATE staff SET shift = ? WHERE id_pengguna = ?";
        
        try {
            connection.setAutoCommit(false);
            
            try (PreparedStatement ps1 = connection.prepareStatement(queryPengguna)) {
                ps1.setString(1, staff.getNamaPengguna());
                ps1.setString(2, staff.getUsername());
                ps1.setString(3, staff.getPassword());
                ps1.setString(4, staff.getStatusAkun());
                ps1.setString(5, staff.getNomorTelepon());
                ps1.setInt(6, staff.getId());
                ps1.executeUpdate();
            }
            
            try (PreparedStatement ps2 = connection.prepareStatement(queryStaff)) {
                ps2.setString(1, staff.getShift());
                ps2.setInt(2, staff.getId());
                ps2.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public boolean delete(int idStaff) {
        String query = "UPDATE pengguna SET status_akun = 'Nonaktif' WHERE id_pengguna = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idStaff);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean aktifkanStaff(int idStaff) {
        String query = "UPDATE pengguna SET status_akun = 'Aktif' WHERE id_pengguna = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idStaff);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}