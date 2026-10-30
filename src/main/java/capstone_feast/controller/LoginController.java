package capstone_feast.controller;

import capstone_feast.dao.LoginDao;
import capstone_feast.model.Pengguna;
import capstone_feast.view.Admin;
import capstone_feast.view.Login;
import capstone_feast.view.Staff;
import javax.swing.JOptionPane;

public class LoginController {
    private Login frame;
    private LoginDao loginDao;

    public LoginController(Login frame) {
        this.frame = frame;
        this.loginDao = new LoginDao();
        initController();
    }

    private void initController() {
        frame.getBtnLogin().addActionListener(e -> prosesLogin());
    }

    public void prosesLogin() {
        String username = frame.getTxtUsername().getText().trim();
        String password = new String(frame.getTxtPassword().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi ke database melalui DAO
        Pengguna user = loginDao.login(username, password);

        if (user != null) {
            // Memanggil method getRole() secara polimorfisme dari subclass Admin/Staff
            String role = user.getRole(); 
            
            JOptionPane.showMessageDialog(frame, "Login Berhasil sebagai " + role + "!");

            // Pengecekan perpindahan halaman berdasarkan role polimorfik
            if ("Admin".equalsIgnoreCase(role)) {
                new Admin().setVisible(true); // Membuka form Admin
            } else if ("Staff".equalsIgnoreCase(role)) {
                new Staff().setVisible(true); // Membuka form Staff
            } else {
                JOptionPane.showMessageDialog(frame, "Role pengguna tidak dikenal!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Menutup jendela login
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Login Gagal! Periksa kembali username, password, atau status akun Anda.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}