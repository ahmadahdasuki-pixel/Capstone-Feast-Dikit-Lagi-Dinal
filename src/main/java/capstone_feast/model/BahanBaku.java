package capstone_feast.model;

public class BahanBaku {
    private int idBahan;
    private int idAdmin;
    private String namaBahan;
    private String satuan;
    private double stokSaatIni;
    private double stokMinimum;
    private String statusBahanBaku;

    // Constructor lengkap
    public BahanBaku(int idBahan, int idAdmin, String namaBahan, String satuan, double stokSaatIni, double stokMinimum, String statusBahanBaku) {
        this.idBahan = idBahan;
        this.idAdmin = idAdmin;
        this.namaBahan = namaBahan;
        this.satuan = satuan;
        this.stokSaatIni = stokSaatIni;
        this.stokMinimum = stokMinimum;
        this.statusBahanBaku = statusBahanBaku;
    }

    // Getter dan Setter
    public int getIdBahan() { return idBahan; }
    public void setIdBahan(int idBahan) { this.idBahan = idBahan; }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public String getNamaBahan() { return namaBahan; }
    public void setNamaBahan(String namaBahan) { this.namaBahan = namaBahan; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    public double getStokSaatIni() { return stokSaatIni; }
    public void setStokSaatIni(double stokSaatIni) { this.stokSaatIni = stokSaatIni; }

    public double getStokMinimum() { return stokMinimum; }
    public void setStokMinimum(double stokMinimum) { this.stokMinimum = stokMinimum; }

    public String getStatusBahanBaku() { return statusBahanBaku; }
    public void setStatusBahanBaku(String statusBahanBaku) { this.statusBahanBaku = statusBahanBaku; }
}