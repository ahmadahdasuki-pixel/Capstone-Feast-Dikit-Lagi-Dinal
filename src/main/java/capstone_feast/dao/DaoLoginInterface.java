package capstone_feast.dao;

import capstone_feast.model.Pengguna;

public interface DaoLoginInterface {
    Pengguna login(String username, String password);
}