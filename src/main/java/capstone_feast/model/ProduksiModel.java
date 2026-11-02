package capstone_feast.model;

public class ProduksiModel {
    private int idBatch;
    private String namaProduk;
    private String nomorBatch;
    private int jumlahProduksi;
    private String tanggalProduksi;
    private String tanggalKadaluarsa;
    private String statusBatch;

    public int getIdBatch() {
        return idBatch;
    }

    public void setIdBatch(int idBatch) {
        this.idBatch = idBatch;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNomorBatch() {
        return nomorBatch;
    }

    public void setNomorBatch(String nomorBatch) {
        this.nomorBatch = nomorBatch;
    }

    public int getJumlahProduksi() {
        return jumlahProduksi;
    }

    public void setJumlahProduksi(int jumlahProduksi) {
        this.jumlahProduksi = jumlahProduksi;
    }

    public String getTanggalProduksi() {
        return tanggalProduksi;
    }

    public void setTanggalProduksi(String tanggalProduksi) {
        this.tanggalProduksi = tanggalProduksi;
    }

    public String getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    public void setTanggalKadaluarsa(String tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    public String getStatusBatch() {
        return statusBatch;
    }

    public void setStatusBatch(String statusBatch) {
        this.statusBatch = statusBatch;
    }
}