package capstone_feast.model;

public class Distribusi {
    private String idDistribusi;
    private String tanggalDistribusi;
    private String statusDistribusi;
    private String keterangan;

    public Distribusi(String idDistribusi, String tanggalDistribusi, String statusDistribusi, String keterangan) {
        this.idDistribusi = idDistribusi;
        this.tanggalDistribusi = tanggalDistribusi;
        this.statusDistribusi = statusDistribusi;
        this.keterangan = keterangan;
    }

    public String getIdDistribusi() { return idDistribusi; }
    public void setIdDistribusi(String idDistribusi) { this.idDistribusi = idDistribusi; }

    public String getTanggalDistribusi() { return tanggalDistribusi; }
    public void setTanggalDistribusi(String tanggalDistribusi) { this.tanggalDistribusi = tanggalDistribusi; }

    public String getStatusDistribusi() { return statusDistribusi; }
    public void setStatusDistribusi(String statusDistribusi) { this.statusDistribusi = statusDistribusi; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}