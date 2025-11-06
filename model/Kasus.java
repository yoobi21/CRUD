package model;

import java.util.List;

public class Kasus {
    private String id_kasus;
    private String nama_kasus;
    private String penyidik;
    private String klasifikasi_kasus;
    private status_kasus status_kasus;

    // Enum untuk Status Kasus
    public enum status_kasus {
        AKTIF("Aktif"),
        DITUTUP("Ditutup"),
        DALAM_PENYELIDIKAN("Dalam Penyelidikan"),
        SELESAI("Selesai");
        
        private final String displayName;
        
        status_kasus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    public Kasus() {}
    
    public Kasus(String id_kasus, String nama_kasus, String penyidik, String klasifikasi_kasus, status_kasus status_kasus) {
        this.id_kasus = id_kasus;
        this.nama_kasus = nama_kasus;
        this.penyidik = penyidik;
        this.klasifikasi_kasus = klasifikasi_kasus;
        this.status_kasus = status_kasus;
    }
    
    // Getters and Setters - FIXED
    public String getid_kasus() { return id_kasus; }
    public void setid_kasus(String id_kasus) { this.id_kasus = id_kasus; }
    
    public String getnama_kasus() { return nama_kasus; }
    public void setnama_kasus(String nama_kasus) { this.nama_kasus = nama_kasus; }
    
    public String getPenyidik() { return penyidik; }
    public void setPenyidik(String penyidik) { this.penyidik = penyidik; }
    
    public String getklasifikasi_kasus() { return klasifikasi_kasus; }
    public void setklasifikasi_kasus(String klasifikasi_kasus) { this.klasifikasi_kasus = klasifikasi_kasus; }
    
    public status_kasus getstatus_kasus() { return status_kasus; }
    public void setStatus_kasus(status_kasus status_kasus) { this.status_kasus = status_kasus; }
    
    // Helper method untuk mendapatkan display name status
    public String getStatusDisplay() {
        return status_kasus != null ? status_kasus.getDisplayName() : "";
    }

    // HAPUS METHOD-METHOD YANG TIDAK PERLU
    public int getId() { return 0; }
    public void setId(int id) { }
}