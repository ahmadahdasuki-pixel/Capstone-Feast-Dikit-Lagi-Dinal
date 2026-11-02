package capstone_feast.model;

public class DetailResep {
    private int idDetailResep;
    private int idProduk;
    private int idBahan;
    private String namaBahan; 
    private String satuan;    
    private double jumlahBahan;

    public DetailResep() {}

    public DetailResep(int idDetailResep, int idProduk, int idBahan, String namaBahan, String satuan, double jumlahBahan) {
        this.idDetailResep = idDetailResep;
        this.idProduk = idProduk;
        this.idBahan = idBahan;
        this.namaBahan = namaBahan;
        this.satuan = satuan;
        this.jumlahBahan = jumlahBahan;
    }

    // Getter dan Setter
    public int getIdDetailResep() { return idDetailResep; }
    public void setIdDetailResep(int idDetailResep) { this.idDetailResep = idDetailResep; }

    public int getIdProduk() { return idProduk; }
    public void setIdProduk(int idProduk) { this.idProduk = idProduk; }

    public int getIdBahan() { return idBahan; }
    public void setIdBahan(int idBahan) { this.idBahan = idBahan; }

    public String getNamaBahan() { return namaBahan; }
    public void setNamaBahan(String namaBahan) { this.namaBahan = namaBahan; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    public double getJumlahBahan() { return jumlahBahan; }
    public void setJumlahBahan(double jumlahBahan) { this.jumlahBahan = jumlahBahan; }
}