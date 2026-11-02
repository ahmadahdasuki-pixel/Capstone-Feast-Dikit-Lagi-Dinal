package capstone_feast.controller;

import capstone_feast.dao.ProduksiDao;
import capstone_feast.model.ProduksiModel;
import capstone_feast.view.Produksi;
import capstone_feast.view.Login;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ProduksiController {
    private Produksi frame;
    private ProduksiDao dao;
    private int selectedIdBatch = -1; 

    public ProduksiController(Produksi frame) {
        this.frame = frame;
        this.dao = new ProduksiDao();
        initController();
        loadComboBoxProduk(); 
        loadTableProduksi();  
        setupCekKebutuhanTableRenderer(); 
    }

    private void initController() {
        if (frame.getBtnCekKebutuhan() != null) {
            frame.getBtnCekKebutuhan().addActionListener(e -> cekKebutuhanBahanBaku());
        }

        if (frame.getBtnMulaiProduksi() != null) {
            frame.getBtnMulaiProduksi().addActionListener(e -> prosesMulaiProduksi());
        }

        if (frame.getBtnReset() != null) {
            frame.getBtnReset().addActionListener(e -> resetForm());
        }
        
        if (frame.getBtnLogout() != null) {
            frame.getBtnLogout().addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    frame, 
                    "Yakin Ingin Keluar?", 
                    "Konfirmasi Logout", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    new Login().setVisible(true);
                    frame.dispose();
                }
            });
        }

        if (frame.getTabelDaftarBatch() != null) {
            frame.getTabelDaftarBatch().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = frame.getTabelDaftarBatch().getSelectedRow();
                    if (row != -1) {
                        Object val = frame.getTabelDaftarBatch().getValueAt(row, 0);
                        if (val != null) {
                            String idBatchStr = val.toString();
                            selectedIdBatch = Integer.parseInt(idBatchStr.replaceAll("[^0-9]", ""));
                        }
                    }
                }
            });
        }

        if (frame.getBtnIsiHasilProduksi() != null) {
            frame.getBtnIsiHasilProduksi().addActionListener(e -> prosesUbahStatusProduksi());
        }
    }

    private void loadComboBoxProduk() {
        JComboBox cmb = frame.getCmbProduk(); 
        if (cmb != null) {
            cmb.removeAllItems();
            
            List<ProduksiDao.ProdukItem> daftarProduk = dao.getListProdukItem();
            for (ProduksiDao.ProdukItem item : daftarProduk) {
                cmb.addItem(item);
            }
        }
    }

    private void cekKebutuhanBahanBaku() {
        Object selectedItem = frame.getCmbProduk().getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(frame, "Pilih produk terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String jumlahStr = frame.getTxtJumlahProduksi().getText().trim();
        
        if (jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Jumlah produksi wajib diisi terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jumlahProduksi;
        try {
            jumlahProduksi = Integer.parseInt(jumlahStr);
            if (jumlahProduksi <= 0) {
                JOptionPane.showMessageDialog(frame, "Jumlah produksi harus di atas 0 (> 0)!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Jumlah produksi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ProduksiDao.ProdukItem produkItem = (ProduksiDao.ProdukItem) selectedItem;
            int idProduk = produkItem.getId();

            DefaultTableModel modelBahan = (DefaultTableModel) frame.getTabelCekBahan().getModel();
            modelBahan.setRowCount(0);

            List<ProduksiDao.BahanBakuModel> listBahan = dao.getBahanBakuByProduk(idProduk);
            if (listBahan.isEmpty()) {
                JOptionPane.showMessageDialog(
                    frame, 
                    "Peringatan: Resep untuk produk \"" + produkItem.getNama() + "\" tidak dapat dimuat.\n" +
                    "Kemungkinan besar terdapat bahan baku dalam resep ini yang berstatus 'Nonaktif' di database.\n" +
                    "Silakan periksa kembali status bahan baku pada menu Bahan Baku.", 
                    "Bahan Baku Nonaktif / Resep Kosong", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            for (ProduksiDao.BahanBakuModel b : listBahan) {
                double totalKebutuhan = b.getJumlahPerUnit() * jumlahProduksi;
                String status = (b.getStokSaatIni() >= totalKebutuhan) ? "Cukup (Aman)" : "Stok Kurang (Tidak Cukup)";

                modelBahan.addRow(new Object[]{
                    b.getNamaBahan(),
                    totalKebutuhan + " " + b.getSatuan(),
                    b.getStokSaatIni() + " " + b.getSatuan(),
                    status
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Terjadi kesalahan saat memuat bahan baku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prosesMulaiProduksi() {
        Object selectedItem = frame.getCmbProduk().getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(frame, "Pilih produk terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProduksiDao.ProdukItem produkItem = (ProduksiDao.ProdukItem) selectedItem;
        
        if ("Nonaktif".equalsIgnoreCase(produkItem.getStatusBatch())) {
            JOptionPane.showMessageDialog(
                frame, 
                "Gagal: Produk \"" + produkItem.getNama() + "\" berstatus Nonaktif dan tidak dapat diproduksi!", 
                "Validasi Produk", 
                JOptionPane.ERROR_MESSAGE
            );
            return; 
        }

        int idProduk = produkItem.getId();

        String jumlahStr = frame.getTxtJumlahProduksi().getText().trim();
        String tglProduksiInput = frame.getTxtTanggalProduksi().getText().trim();
        String tglKadaluarsaInput = frame.getTxtTanggalKadaluarsa().getText().trim();

        // 1. Validasi Kosong
        if (jumlahStr.isEmpty() || tglProduksiInput.isEmpty() || tglKadaluarsaInput.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Semua kolom wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi Jumlah Produksi (Harus Angka & > 0)
        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr);
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(frame, "Jumlah produksi harus berupa angka di atas 0 (> 0)!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Jumlah produksi harus berupa angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- 3. VALIDASI MUTLAK: CEK KETERSEDIAAN STOK BAHAN BAKU ---
        List<ProduksiDao.BahanBakuModel> listBahan = dao.getBahanBakuByProduk(idProduk);
        
        if (listBahan.isEmpty()) {
            JOptionPane.showMessageDialog(
                frame, 
                "Produksi Gagal!\nResep produk tidak ditemukan atau terdapat bahan baku dalam resep yang berstatus 'Nonaktif'.", 
                "Error Resep", 
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        StringBuilder bahanKurangMsg = new StringBuilder();
        boolean stokCukup = true;

        for (ProduksiDao.BahanBakuModel bahan : listBahan) {
            double totalDibutuhkan = bahan.getJumlahPerUnit() * jumlah;
            if (bahan.getStokSaatIni() < totalDibutuhkan) {
                stokCukup = false;
                bahanKurangMsg.append("- ")
                              .append(bahan.getNamaBahan())
                              .append(" (Dibutuhkan: ").append(totalDibutuhkan)
                              .append(" ").append(bahan.getSatuan())
                              .append(", Stok Tersedia: ").append(bahan.getStokSaatIni())
                              .append(" ").append(bahan.getSatuan()).append(")\n");
            }
        }

        // Jika ada bahan yang kurang, BLOKIR TOTAL proses produksi
        if (!stokCukup) {
            JOptionPane.showMessageDialog(
                frame, 
                "Produksi TIDAK DAPAT dilanjutkan karena stok bahan baku berikut tidak mencukupi:\n\n" + 
                bahanKurangMsg.toString() + 
                "\nSilakan hubungi Admin untuk melakukan penambahan stok (restock) bahan baku terlebih dahulu!", 
                "Stok Bahan Baku Kurang", 
                JOptionPane.ERROR_MESSAGE
            );
            return; 
        }
        // -----------------------------------------------------------

        // 4. Validasi Format & Perbandingan Tanggal
        String tglProduksi;
        String tglKadaluarsa;

        try {
            String cleanTglProd = tglProduksiInput.replace("/", "-");
            String cleanTglKad = tglKadaluarsaInput.replace("/", "-");

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate parsedTglProd = LocalDate.parse(cleanTglProd, inputFormatter);
            LocalDate parsedTglKad = LocalDate.parse(cleanTglKad, inputFormatter);

            if (parsedTglKad.isBefore(parsedTglProd) || parsedTglKad.isEqual(parsedTglProd)) {
                JOptionPane.showMessageDialog(frame, "Tanggal kadaluarsa tidak boleh lebih awal atau sama dengan tanggal produksi!", "Validasi Tanggal Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            tglProduksi = parsedTglProd.format(dbFormatter);
            tglKadaluarsa = parsedTglKad.format(dbFormatter);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Format tanggal salah! Gunakan format: DD-MM-YYYY (Contoh: 20-10-2026)", "Format Tanggal Salah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idStaffDefault = 4; 

            boolean sukses = dao.insertProduksi(idProduk, idStaffDefault, tglProduksi, tglKadaluarsa, jumlah);

            if (sukses) {
                JOptionPane.showMessageDialog(frame, "Sukses: Batch produksi dimulai dengan status 'Di Proses'!");
                loadTableProduksi();
                resetForm();
            } else {
                // Output pesan deskriptif yang diperbaiki
                JOptionPane.showMessageDialog(
                    frame, 
                    "Gagal Memulai Produksi!\n\n" +
                    "Kemungkinan Penyebab:\n" +
                    "1. Terdapat bahan baku dalam resep produk ini yang berstatus 'Nonaktif'.\n" +
                    "2. Terjadi kendala saat transaksi database (stok gagal dikurangi).\n\n" +
                    "Silakan periksa kembali status bahan baku pada menu Bahan Baku.", 
                    "Error Transaksi Produksi", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Terjadi kesalahan sistem: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prosesUbahStatusProduksi() {
        if (selectedIdBatch == -1) {
            JOptionPane.showMessageDialog(frame, "Silakan klik/pilih salah satu baris data pada tabel 'Daftar Batch Produksi' terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object[] pilihanStatus = {"Selesai", "Gagal"};
        String statusPilihan = (String) JOptionPane.showInputDialog(
            frame, 
            "Pilih hasil status produksi untuk Batch ID: BAT-00" + selectedIdBatch, 
            "Ubah Status Produksi", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            pilihanStatus, 
            pilihanStatus[0]
        );

        if (statusPilihan != null) {
            boolean sukses = dao.updateStatusProduksi(selectedIdBatch, statusPilihan);
            if (sukses) {
                JOptionPane.showMessageDialog(frame, "Status produksi berhasil diubah menjadi '" + statusPilihan + "'!");
                loadTableProduksi(); 
                selectedIdBatch = -1; 
            } else {
                JOptionPane.showMessageDialog(
                    frame, 
                    "Gagal memperbarui status produksi ke database. Pastikan koneksi atau data batch valid.", 
                    "Error Database", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void loadTableProduksi() {
        List<ProduksiModel> list = dao.getAllProduksi();
        DefaultTableModel model = (DefaultTableModel) frame.getTabelDaftarBatch().getModel();
        model.setRowCount(0);

        for (ProduksiModel p : list) {
            model.addRow(new Object[]{
                "BAT-00" + p.getIdBatch(),
                p.getNamaProduk(),
                p.getJumlahProduksi(),
                p.getTanggalProduksi(),
                p.getTanggalKadaluarsa(),
                p.getStatusBatch() 
            });
        }
    }

    private void setupCekKebutuhanTableRenderer() {
        JTable tableBahan = frame.getTabelCekBahan();
        if (tableBahan != null && tableBahan.getColumnCount() > 3) {
            tableBahan.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    
                    if (value != null) {
                        String status = value.toString().toLowerCase();
                        if (isSelected) {
                            c.setForeground(table.getSelectionForeground());
                        } else {
                            if (status.contains("kurang") || status.contains("tidak")) {
                                c.setForeground(new Color(204, 0, 0)); 
                            } else if (status.contains("cukup") || status.contains("aman")) {
                                c.setForeground(new Color(0, 153, 0)); 
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

    private void resetForm() {
        frame.getTxtJumlahProduksi().setText("");
        frame.getTxtTanggalProduksi().setText("");
        frame.getTxtTanggalKadaluarsa().setText("");
        DefaultTableModel modelBahan = (DefaultTableModel) frame.getTabelCekBahan().getModel();
        modelBahan.setRowCount(0);
        selectedIdBatch = -1;
    }
}