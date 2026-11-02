package capstone_feast.model;

public class PemantauanModel {
    // Model untuk Tabel Stok Bahan Baku
    private String namaBahan;
    private String satuanBahan;
    private double stokSaatIni;
    private double stokMinimum;
    private String statusBahan;

    // Model untuk Tabel Stok Batch Produksi
    private int idBatch;
    private String namaProduk;
    private String tglProduksi;
    private String tglKadaluarsa;
    private double sisaStokBatch;
    private String statusBatch;

    // Konstruktor untuk Bahan Baku
    public PemantauanModel(String namaBahan, String satuanBahan, double stokSaatIni, double stokMinimum, String statusBahan) {
        this.namaBahan = namaBahan;
        this.satuanBahan = satuanBahan;
        this.stokSaatIni = stokSaatIni;
        this.stokMinimum = stokMinimum;
        this.statusBahan = statusBahan;
    }

    // Konstruktor untuk Batch Produksi
    public PemantauanModel(int idBatch, String namaProduk, String tglProduksi, String tglKadaluarsa, double sisaStokBatch, String statusBatch) {
        this.idBatch = idBatch;
        this.namaProduk = namaProduk;
        this.tglProduksi = tglProduksi;
        this.tglKadaluarsa = tglKadaluarsa;
        this.sisaStokBatch = sisaStokBatch;
        this.statusBatch = statusBatch;
    }

    // Getter & Setter
    public String getNamaBahan() { return namaBahan; }
    public String getSatuanBahan() { return satuanBahan; }
    public double getStokSaatIni() { return stokSaatIni; }
    public double getStokMinimum() { return stokMinimum; }
    public String getStatusBahan() { return statusBahan; }

    public int getIdBatch() { return idBatch; }
    public String getNamaProduk() { return namaProduk; }
    public String getTglProduksi() { return tglProduksi; }
    public String getTglKadaluarsa() { return tglKadaluarsa; }
    public double getSisaStokBatch() { return sisaStokBatch; }
    public String getStatusBatch() { return statusBatch; }
}