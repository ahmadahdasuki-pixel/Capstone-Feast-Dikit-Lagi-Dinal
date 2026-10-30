package capstone_feast.model;

public class DetailResep {
    private String idDetailResep;
    private double jumlahBahan;

    public DetailResep(String idDetailResep, double jumlahBahan) {
        this.idDetailResep = idDetailResep;
        this.jumlahBahan = jumlahBahan;
    }

    public String getIdDetailResep() { return idDetailResep; }
    public void setIdDetailResep(String idDetailResep) { this.idDetailResep = idDetailResep; }

    public double getJumlahBahan() { return jumlahBahan; }
    public void setJumlahBahan(double jumlahBahan) { this.jumlahBahan = jumlahBahan; }
}