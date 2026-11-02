package capstone_feast.controller;

import capstone_feast.dao.DashboardAdminDao;
import capstone_feast.view.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardAdminController {
    private DashboardAdmin frame;
    private DashboardAdminDao dao;

    public DashboardAdminController(DashboardAdmin frame) {
        this.frame = frame;
        this.dao = new DashboardAdminDao();
        initController();
        loadDashboardData();
    }

    private void initController() {
        // 1. Tombol Menu Dashboard
        if (frame.getBtnDashboard() != null) {
            frame.getBtnDashboard().addActionListener(e -> {
                // Sudah berada di halaman DashboardAdmin
            });
        }

        // 2. Tombol Menu Bahan Baku
        if (frame.getBtnBahan() != null) {
            frame.getBtnBahan().addActionListener(e -> {
                new BahanBaku().setVisible(true);
                frame.dispose();
            });
        }

        // 3. Tombol Menu Produk
        if (frame.getBtnProduk() != null) {
            frame.getBtnProduk().addActionListener(e -> {
                new Produk().setVisible(true);
                frame.dispose();
            });
        }

        // 4. Tombol Menu Resep
        if (frame.getBtnResep() != null) {
            frame.getBtnResep().addActionListener(e -> {
                new Resep().setVisible(true);
                frame.dispose();
            });
        }

        // 5. Tombol Menu Distributor
        if (frame.getBtnDistributor() != null) {
            frame.getBtnDistributor().addActionListener(e -> {
                new Distributor().setVisible(true);
                frame.dispose();
            });
        }

        // 6. Tombol Menu Kelola Akun Staff
        if (frame.getBtnKelolaAkun() != null) {
            frame.getBtnKelolaAkun().addActionListener(e -> {
                new KelolaAkunStaff().setVisible(true);
                frame.dispose();
            });
        }

        // 7. Tombol Logout
        if (frame.getBtnLogout() != null) {
            frame.getBtnLogout().addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    frame, 
                    "Apakah Anda yakin ingin keluar (Logout)?", 
                    "Konfirmasi Logout", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    new Login().setVisible(true);
                    frame.dispose();
                }
            });
        }
    }

    private void loadDashboardData() {
        try {
            if (frame != null) {
                // 1. Isi pilihan ComboBox status batch
                frame.isiPilihanStatus();
                
                // 2. Pasang Event Listener berbasis Index (Anti-Mismatch String!)
                if (frame.getCmbStatus() != null) {
                    for (java.awt.event.ActionListener al : frame.getCmbStatus().getActionListeners()) {
                        frame.getCmbStatus().removeActionListener(al);
                    }
                    
                    frame.getCmbStatus().addActionListener(e -> {
                        int selectedIndex = frame.getCmbStatus().getSelectedIndex();
                        String filterKategori = "Semua";
                        
                        // Petakan berdasarkan urutan item di ComboBox
                        if (selectedIndex == 1) {
                            filterKategori = "Selesai";
                        } else if (selectedIndex == 2) {
                            filterKategori = "Gagal";
                        } else if (selectedIndex == 3) {
                            filterKategori = "Di Proses";
                        }
                        
                        loadTabelBatch(filterKategori);
                    });
                }

                // 3. Muat data tabel pertama kali
                loadTabelBatch("Semua");
                
                // 4. Terapkan pewarnaan status pada tabel
                aturWarnaTabelStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTabelBatch(String statusFilter) {
        DefaultTableModel model = (DefaultTableModel) frame.getTabelBatchTerbaru().getModel();
        model.setRowCount(0); // Kosongkan baris agar tidak duplikat

        List<Object[]> dataBatch = dao.getBatchTerbaru();

        for (Object[] row : dataBatch) {
            String statusBatch = (row[4] != null) ? row[4].toString().trim() : ""; 
            String filter = (statusFilter != null) ? statusFilter.trim() : "Semua";

            boolean isMatch = false;
            
            if (filter.equalsIgnoreCase("Semua")) {
                isMatch = true;
            } 
            else if (filter.equalsIgnoreCase("Selesai") && statusBatch.equalsIgnoreCase("Selesai")) {
                isMatch = true;
            } 
            else if (filter.equalsIgnoreCase("Gagal") && (statusBatch.equalsIgnoreCase("Gagal") || statusBatch.equalsIgnoreCase("Dibuang") || statusBatch.equalsIgnoreCase("Habis"))) {
                isMatch = true;
            } 
            else if (filter.equalsIgnoreCase("Di Proses") && (statusBatch.equalsIgnoreCase("Di Proses") || statusBatch.equalsIgnoreCase("Di proses"))) {
                isMatch = true;
            }

            if (isMatch) {
                model.addRow(row);
            }
        }
    }

    private void aturWarnaTabelStatus() {
        JTable table = frame.getTabelBatchTerbaru();
        
        if (table.getColumnCount() > 4) {
            table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    
                    if (value != null) {
                        String status = value.toString().trim();
                        
                        if (isSelected) {
                            c.setForeground(table.getSelectionForeground());
                        } else {
                            if (status.equalsIgnoreCase("Selesai")) {
                                c.setForeground(new Color(0, 153, 0)); // Hijau
                            } else if (status.equalsIgnoreCase("Di Proses")) {
                                c.setForeground(new Color(204, 153, 0)); // Kuning/Oranye gelap agar kontras
                            } else if (status.equalsIgnoreCase("Gagal")) {
                                c.setForeground(new Color(204, 0, 0)); // Merah
                            } else {
                                c.setForeground(table.getForeground());
                            }
                        }
                    }
                    return c;
                }
            });
        }
    }
}