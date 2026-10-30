package capstone_feast.model;

public class Produk {
    private String idProduk;
    private String namaProduk;
    private String satuanProduk;
    private double hargaJual;
    private String statusProduk;

    public Produk(String idProduk, String namaProduk, String satuanProduk, double hargaJual, String statusProduk) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.satuanProduk = satuanProduk;
        this.hargaJual = hargaJual;
        this.statusProduk = statusProduk;
    }

    public String getIdProduk() { return idProduk; }
    public void setIdProduk(String idProduk) { this.idProduk = idProduk; }

    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }

    public String getSatuanProduk() { return satuanProduk; }
    public void setSatuanProduk(String satuanProduk) { this.satuanProduk = satuanProduk; }

    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }

    public String getStatusProduk() { return statusProduk; }
    public void setStatusProduk(String statusProduk) { this.statusProduk = statusProduk; }
}