public class Rumahsakit {

    private int id ;
    private String nama= null;
    private String penyakit = null;

    public Rumahsakit(int id, String nama, String penyakit) {
        this.id = id;
        this.nama = nama;
        this.penyakit = penyakit;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPenyakit() {
        return penyakit;
    }
}