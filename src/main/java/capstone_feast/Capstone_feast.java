package capstone_feast;

import capstone_feast.view.Login;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Capstone_feast {

    public static void main(String[] args) {
        // Mengatur tampilan (Look and Feel) agar sesuai dengan sistem operasi / Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mengatur Look and Feel: " + e.getMessage());
        }

        // Menjalankan form Login pertama kali menggunakan Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}