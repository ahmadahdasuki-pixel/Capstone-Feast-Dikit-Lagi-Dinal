package capstone_feast.controller;

import capstone_feast.view.Admin;
import capstone_feast.view.Login;
import javax.swing.JOptionPane;

public class AdminController {
    private Admin frame;

    public AdminController(Admin frame) {
        this.frame = frame;
        initController();
    }

    // Menghubungkan event handler untuk setiap tombol pada Sidebar form Admin
    private void initController() {
        // Tombol Dashboard
        frame.getBtnDashboard().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Anda sudah berada di halaman Dashboard.");
        });

        // Tombol Bahan Baku (Menu Data Master)
        frame.getBtnBahan().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Membuka Form Pengelolaan Bahan Baku...");
            // TODO: Tambahkan instansiasi form Bahan Baku di sini jika sudah dibuat
        });

        // Tombol Produk (Menu Data Master)
        frame.getBtnProduk().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Membuka Form Pengelolaan Produk...");
            // TODO: Tambahkan instansiasi form Produk di sini jika sudah dibuat
        });

        // Tombol Resep (Menu Data Master)
        frame.getBtnResep().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Membuka Form Pengelolaan Resep...");
            // TODO: Tambahkan instansiasi form Resep di sini jika sudah dibuat
        });

        // Tombol Distributor (Menu Data Master)
        frame.getBtnDistributor().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Membuka Form Pengelolaan Distributor...");
            // TODO: Tambahkan instansiasi form Distributor di sini jika sudah dibuat
        });

        // Tombol Kelola Akun Staff
        frame.getBtnKelolaAkun().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Membuka Form Kelola Akun Staff...");
            // TODO: Tambahkan instansiasi form Kelola Akun Staff di sini jika sudah dibuat
        });

        // Tombol Logout (Sesuai alur flowchart: Konfirmasi -> Kembali ke Login)
        frame.getBtnLogout().addActionListener(e -> prosesLogout());
    }

    // Logika Konfirmasi Logout
    public void prosesLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            frame, 
            "Yakin Ingin Keluar?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose(); // Menutup jendela form Admin
            new Login().setVisible(true); // Membuka kembali halaman Login utama
        }
    }
}
