package capstone_feast.model;

public class ProduksiBatch {
    private String idBatch;
    private String tanggalProduksi;
    private String tanggalKadaluarsa;
    private int jumlahProduksi;
    private int sisaStokBatch;
    private String statusBatch;

    public ProduksiBatch(String idBatch, String tanggalProduksi, String tanggalKadaluarsa, int jumlahProduksi, int sisaStokBatch, String statusBatch) {
        this.idBatch = idBatch;
        this.tanggalProduksi = tanggalProduksi;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.jumlahProduksi = jumlahProduksi;
        this.sisaStokBatch = sisaStokBatch;
        this.statusBatch = statusBatch;
    }

    public String getIdBatch() { return idBatch; }
    public void setIdBatch(String idBatch) { this.idBatch = idBatch; }

    public String getTanggalProduksi() { return tanggalProduksi; }
    public void setTanggalProduksi(String tanggalProduksi) { this.tanggalProduksi = tanggalProduksi; }

    public String getTanggalKadaluarsa() { return tanggalKadaluarsa; }
    public void setTanggalKadaluarsa(String tanggalKadaluarsa) { this.tanggalKadaluarsa = tanggalKadaluarsa; }

    public int getJumlahProduksi() { return jumlahProduksi; }
    public void setJumlahProduksi(int jumlahProduksi) { this.jumlahProduksi = jumlahProduksi; }

    public int getSisaStokBatch() { return sisaStokBatch; }
    public void setSisaStokBatch(int sisaStokBatch) { this.sisaStokBatch = sisaStokBatch; }

    public String getStatusBatch() { return statusBatch; }
    public void setStatusBatch(String statusBatch) { this.statusBatch = statusBatch; }
}