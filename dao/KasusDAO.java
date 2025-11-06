package dao;

import config.koneksi;
import model.Kasus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KasusDAO {

    // ✅ CREATE
    public boolean addKasus(Kasus kasus) {
        String sql = "INSERT INTO kasus (id_kasus, nama_kasus, penyidik, klasifikasi_kasus, status_kasus) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                 
            pstmt.setString(1, kasus.getid_kasus());
            pstmt.setString(2, kasus.getnama_kasus());
            pstmt.setString(3, kasus.getPenyidik());
            pstmt.setString(4, kasus.getklasifikasi_kasus());
            pstmt.setString(5, kasus.getstatus_kasus().getDisplayName());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ READ ALL - FIXED
    public List<Kasus> getAllKasus() {
        List<Kasus> kasusList = new ArrayList<>();
        // HAPUS ORDER BY id KARENA KOLOM ID TIDAK ADA
        String sql = "SELECT * FROM kasus";

        try (Connection conn = koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Kasus kasus = new Kasus();
                kasus.setid_kasus(rs.getString("id_kasus"));
                kasus.setnama_kasus(rs.getString("nama_kasus"));
                kasus.setPenyidik(rs.getString("penyidik"));
                kasus.setklasifikasi_kasus(rs.getString("klasifikasi_kasus"));
                
                // FIX: Konversi string ke enum status
                String statusDb = rs.getString("status_kasus");
                kasus.setStatus_kasus(convertToStatusEnum(statusDb));
                
                kasusList.add(kasus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kasusList;
    }

    // ✅ UPDATE
    public boolean updateKasus(Kasus kasus) {
        String sql = "UPDATE kasus SET nama_kasus = ?, penyidik = ?, klasifikasi_kasus = ?, status_kasus = ? WHERE id_kasus = ?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kasus.getnama_kasus());
            pstmt.setString(2, kasus.getPenyidik());
            pstmt.setString(3, kasus.getklasifikasi_kasus());
            pstmt.setString(4, kasus.getstatus_kasus().getDisplayName());
            pstmt.setString(5, kasus.getid_kasus()); // WHERE condition

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ DELETE - FIXED
    public boolean deleteKasus(String id_kasus) {
        String sql = "DELETE FROM kasus WHERE id_kasus = ?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_kasus);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ SEARCH - FIXED
    public List<Kasus> searchKasus(String keyword) {
        List<Kasus> kasusList = new ArrayList<>();
        String sql = "SELECT * FROM kasus WHERE nama_kasus LIKE ? OR id_kasus LIKE ? OR penyidik LIKE ?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kasus kasus = new Kasus();
                kasus.setid_kasus(rs.getString("id_kasus"));
                kasus.setnama_kasus(rs.getString("nama_kasus"));
                kasus.setPenyidik(rs.getString("penyidik"));
                kasus.setklasifikasi_kasus(rs.getString("klasifikasi_kasus"));
                
                String statusDb = rs.getString("status_kasus");
                kasus.setStatus_kasus(convertToStatusEnum(statusDb));
                
                kasusList.add(kasus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kasusList;
    }

    // ✅ GET BY ID_KASUS - FIXED
    public Kasus getKasusById(String id_kasus) {
        Kasus kasus = null;
        String sql = "SELECT * FROM kasus WHERE id_kasus = ?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_kasus);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                kasus = new Kasus();
                kasus.setid_kasus(rs.getString("id_kasus"));
                kasus.setnama_kasus(rs.getString("nama_kasus"));
                kasus.setPenyidik(rs.getString("penyidik"));
                kasus.setklasifikasi_kasus(rs.getString("klasifikasi_kasus"));
                
                String statusDb = rs.getString("status_kasus");
                kasus.setStatus_kasus(convertToStatusEnum(statusDb));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kasus;
    }

    // METHOD BARU: Untuk generate ID berikutnya
    public String getNextKasusId() {
        String lastId = getLastKasusId();
        
        if (lastId == null) {
            return "KAS001"; // ID pertama
        }
        
        try {
            // Extract angka dari ID terakhir (contoh: "KAS003" -> 3)
            int lastNumber = Integer.parseInt(lastId.substring(3));
            int nextNumber = lastNumber + 1;
            return String.format("KAS%03d", nextNumber); // Format: KAS004, KAS005, dst
        } catch (Exception e) {
            e.printStackTrace();
            return "KAS001"; // Fallback
        }
    }

    // Method untuk mendapatkan ID terakhir
    public String getLastKasusId() {
        String lastId = null;
        String query = "SELECT id_kasus FROM kasus ORDER BY id_kasus DESC LIMIT 1";
        
        try (Connection conn = koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                lastId = rs.getString("id_kasus");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lastId;
    }

    // HELPER METHOD: Konversi String ke Enum
    private Kasus.status_kasus convertToStatusEnum(String statusStr) {
        if (statusStr == null) return Kasus.status_kasus.AKTIF;
        
        switch (statusStr) {
            case "Aktif": return Kasus.status_kasus.AKTIF;
            case "Ditutup": return Kasus.status_kasus.DITUTUP;
            case "Dalam Penyelidikan": return Kasus.status_kasus.DALAM_PENYELIDIKAN;
            case "Selesai": return Kasus.status_kasus.SELESAI;
            default: return Kasus.status_kasus.AKTIF;
        }
    }
}