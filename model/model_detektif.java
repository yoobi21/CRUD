package model;

public class model_detektif {
    private String id_detektif;
    private String nama_detektif;
    private String spesialisasi;
    private String username;
    private String password;

    public model_detektif() {}

    public model_detektif(String id_detektif, String nama_detektif, String spesialisasi, String username, String password) {
        this.id_detektif = id_detektif;
        this.nama_detektif = nama_detektif;
        this.spesialisasi = spesialisasi;
        this.username = username;
        this.password = password;
    }

    public String get_id_detektif() { return id_detektif; }
    public void set_id_detektif(String id_detektif) { this.id_detektif = id_detektif; }

    public String get_nama_detektif() { return nama_detektif; }
    public void set_nama_detektif(String nama_detektif) { this.nama_detektif = nama_detektif; }

    public String get_spesialisasi() { return spesialisasi; }
    public void set_spesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }

    public String get_username() { return username; }
    public void set_username(String username) { this.username = username; }

    public String get_password() { return password; }
    public void set_password(String password) { this.password = password; }
    
}