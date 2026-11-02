package capstone_feast.model;

public class Staff extends Pengguna {

    private String shift;
    private String password;
    private String role;

    public Staff() {
        super(0, "", "", "", ""); 
        this.password = "";
        this.shift = "Pagi";
        this.role = "Staff";
    }

    public Staff(int id, String namaPengguna, String username, String password, String statusAkun, String nomorTelepon, String shift) {
        super(id, namaPengguna, username, statusAkun, nomorTelepon); 
        this.password = password;
        this.shift = shift;
    }

    public Staff(int id, String namaPengguna, String username, String statusAkun, String nomorTelepon, String shift) {
        super(id, namaPengguna, username, statusAkun, nomorTelepon); 
        this.shift = shift;
        this.password = "";
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShiftKerja() {
        return shift;
    }

    public void setShiftKerja(String shiftKerja) {
        this.shift = shiftKerja;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getRole() {
        return role != null ? role : "Staff";
    }
}