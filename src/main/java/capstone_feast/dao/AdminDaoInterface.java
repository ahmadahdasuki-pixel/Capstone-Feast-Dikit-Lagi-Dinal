package capstone_feast.dao;

import capstone_feast.model.Pengguna;
import java.util.List;

public interface AdminDaoInterface {
    List<Pengguna> getAllPengguna();
    boolean tambahPengguna(Pengguna pengguna);
    boolean updatePengguna(Pengguna pengguna);
    boolean hapusPengguna(int idPengguna);
}