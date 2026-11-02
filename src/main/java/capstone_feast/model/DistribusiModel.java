package capstone_feast.model;

public class DistribusiModel {
    private int idDistribusi;
    private String tglPengiriman;
    private String namaProdukBatch; 
    private int jumlah;
    private String namaDistributor;
    private String status;

    public DistribusiModel(int idDistribusi, String tglPengiriman, String namaProdukBatch, int jumlah, String namaDistributor, String status) {
        this.idDistribusi = idDistribusi;
        this.tglPengiriman = tglPengiriman;
        this.namaProdukBatch = namaProdukBatch;
        this.jumlah = jumlah;
        this.namaDistributor = namaDistributor;
        this.status = status;
    }

    // Getter
    public int getIdDistribusi() { return idDistribusi; }
    public String getTglPengiriman() { return tglPengiriman; }
    public String getNamaProdukBatch() { return namaProdukBatch; }
    public int getJumlah() { return jumlah; }
    public String getNamaDistributor() { return namaDistributor; }
    public String getStatus() { return status; }
}