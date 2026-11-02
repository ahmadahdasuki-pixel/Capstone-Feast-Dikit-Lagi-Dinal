package capstone_feast.config;

public class UserSession {
    private static String namaUser;
    private static String role;

    public static void setNamaUser(String nama) {
        namaUser = nama;
    }

    public static String getNamaUser() {
        return namaUser;
    }

    public static void setRole(String r) {
        role = r;
    }

    public static String getRole() {
        return role;
    }
    
    public static void clear() {
        namaUser = null;
        role = null;
    }
}