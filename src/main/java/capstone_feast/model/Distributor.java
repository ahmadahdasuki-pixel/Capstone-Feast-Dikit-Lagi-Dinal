package capstone_feast.model;

public class Distributor {
    private String idDistributor;
    private String namaDistributor;
    private String alamat;
    private String nomorTeleponDistributor;

    public Distributor(String idDistributor, String namaDistributor, String alamat, String nomorTeleponDistributor) {
        this.idDistributor = idDistributor;
        this.namaDistributor = namaDistributor;
        this.alamat = alamat;
        this.nomorTeleponDistributor = nomorTeleponDistributor;
    }

    public String getIdDistributor() { return idDistributor; }
    public void setIdDistributor(String idDistributor) { this.idDistributor = idDistributor; }

    public String getNamaDistributor() { return namaDistributor; }
    public void setNamaDistributor(String namaDistributor) { this.namaDistributor = namaDistributor; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNomorTeleponDistributor() { return nomorTeleponDistributor; }
    public void setNomorTeleponDistributor(String nomorTeleponDistributor) { this.nomorTeleponDistributor = nomorTeleponDistributor; }
}