package capstone_feast.controller;

import capstone_feast.dao.BahanBakuDao;
import capstone_feast.model.BahanBaku;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BahanBakuController {
    private capstone_feast.view.BahanBaku frame;
    private BahanBakuDao dao;

    public BahanBakuController(capstone_feast.view.BahanBaku frame) {
        this.frame = frame;
        this.dao = new BahanBakuDao();
        initController();
        loadTable();
    }

    private void initController() {
        frame.getBtnTambah().addActionListener(e -> tambahBahanDialog());
        frame.getBtnUbah().addActionListener(e -> ubahBahanDialog());
        frame.getBtnHapus().addActionListener(e -> hapusBahan());
    }

    public void loadTable() {
    List<BahanBaku> list = dao.getAllBahanBaku();
    DefaultTableModel model = (DefaultTableModel) frame.getTabelBahan().getModel();
    model.setRowCount(0); // Kosongkan tabel sebelum isi ulang

    for (BahanBaku b : list) {
        model.addRow(new Object[]{
            b.getIdBahan(),
            b.getNamaBahan(),
            b.getSatuan(),
            b.getStokSaatIni(),
            b.getStokMinimum(),
            b.getStatusBahanBaku() // <-- Tambahkan ini agar kolom status muncul di JTable
        });
    }
}

    private void tambahBahanDialog() {
        String nama = JOptionPane.showInputDialog(frame, "Masukkan Nama Bahan:");
        if (nama == null || nama.trim().isEmpty()) return;

        String satuan = JOptionPane.showInputDialog(frame, "Masukkan Satuan (Contoh: kg, liter, pcs):");
        if (satuan == null || satuan.trim().isEmpty()) return;

        try {
            double stok = Double.parseDouble(JOptionPane.showInputDialog(frame, "Masukkan Stok Saat Ini:"));
            double minStok = Double.parseDouble(JOptionPane.showInputDialog(frame, "Masukkan Stok Minimum:"));

            // idAdmin di-set 1 (atau sesuaikan dengan admin yang sedang login), status default "tersedia"
            BahanBaku baru = new BahanBaku(0, 1, nama, satuan, stok, minStok, "tersedia");
            if (dao.insert(baru)) {
                JOptionPane.showMessageDialog(frame, "Bahan baku berhasil ditambahkan!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Gagal menambahkan bahan baku.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Nilai stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahBahanDialog() {
        int selectedRow = frame.getTabelBahan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih baris data pada tabel yang ingin diubah!");
            return;
        }

        int idBahan = (int) frame.getTabelBahan().getValueAt(selectedRow, 0);
        String namaLama = (String) frame.getTabelBahan().getValueAt(selectedRow, 1);
        String satuanLama = (String) frame.getTabelBahan().getValueAt(selectedRow, 2);
        double stokLama = (double) frame.getTabelBahan().getValueAt(selectedRow, 3);
        double minStokLama = (double) frame.getTabelBahan().getValueAt(selectedRow, 4);

        String nama = JOptionPane.showInputDialog(frame, "Ubah Nama Bahan:", namaLama);
        if (nama == null) return;

        String satuan = JOptionPane.showInputDialog(frame, "Ubah Satuan:", satuanLama);
        if (satuan == null) return;

        try {
            double stok = Double.parseDouble(JOptionPane.showInputDialog(frame, "Ubah Stok Saat Ini:", stokLama));
            double minStok = Double.parseDouble(JOptionPane.showInputDialog(frame, "Ubah Stok Minimum:", minStokLama));

            BahanBaku ubah = new BahanBaku(idBahan, 1, nama, satuan, stok, minStok, "tersedia");
            if (dao.update(ubah)) {
                JOptionPane.showMessageDialog(frame, "Bahan baku berhasil diubah!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Gagal mengubah data.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Nilai stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusBahan() {
        int selectedRow = frame.getTabelBahan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Pilih baris data pada tabel yang ingin dihapus!");
            return;
        }

        int idBahan = (int) frame.getTabelBahan().getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Yakin ingin menghapus bahan baku ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(idBahan)) {
                JOptionPane.showMessageDialog(frame, "Data berhasil dihapus!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Gagal menghapus data.");
            }
        }
    }
}