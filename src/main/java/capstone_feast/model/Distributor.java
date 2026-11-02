package capstone_feast.model;

public class Distributor {
    private int idDistributor;
    private int idAdmin;
    private String namaDistributor;
    private String alamat;
    private String noTelepon;
    private String statusDistributor;

    public Distributor() {}

    public Distributor(int idDistributor, int idAdmin, String namaDistributor, String alamat, String noTelepon, String statusDistributor) {
        this.idDistributor = idDistributor;
        this.idAdmin = idAdmin;
        this.namaDistributor = namaDistributor;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.statusDistributor = statusDistributor;
    }

    public Distributor(int idDistributor, String namaDistributor, String alamat, String noTelepon, String statusDistributor) {
        this.idDistributor = idDistributor;
        this.idAdmin = 1; 
        this.namaDistributor = namaDistributor;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.statusDistributor = statusDistributor;
    }

    // Getter dan Setter
    public int getIdDistributor() { return idDistributor; }
    public void setIdDistributor(int idDistributor) { this.idDistributor = idDistributor; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public String getNamaDistributor() { return namaDistributor; }
    public void setNamaDistributor(String namaDistributor) { this.namaDistributor = namaDistributor; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getStatusDistributor() { return statusDistributor; }
    public void setStatusDistributor(String statusDistributor) { this.statusDistributor = statusDistributor; }

    @Override
    public String toString() {
        return this.namaDistributor;
    }
}