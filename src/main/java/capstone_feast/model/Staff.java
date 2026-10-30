
package capstone_feast.model;

public class Staff extends Pengguna {

    private String shift;

    public Staff(int id, String namaPengguna,
                 String username, String statusAkun,
                 String nomorTelepon, String shift) {

        super(id, namaPengguna, username,
              statusAkun, nomorTelepon);

        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getRole() {
        return "Staff";
    }
}
