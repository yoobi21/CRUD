package dao;

import model.Kasus;
import java.sql.*;
import java.util.*;
import config.koneksi;
import model.model_detektif;

public class dao_detektif {

    public void insert(model_detektif d) {
    String sql = "INSERT INTO detektif (id_detektif, nama_detektif, spesialisasi, username, password) VALUES (?, ?, ?, ?, ?)";
    
    System.out.println("🔍 DEBUG: Attempting to insert data...");
    System.out.println("🔍 DEBUG: ID: " + d.get_id_detektif());
    System.out.println("🔍 DEBUG: Nama: " + d.get_nama_detektif());
    System.out.println("🔍 DEBUG: Spesialisasi: " + d.get_spesialisasi());
    System.out.println("🔍 DEBUG: Username: " + d.get_username());
    System.out.println("🔍 DEBUG: Password: " + d.get_password());
    
    try (Connection conn = koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        System.out.println("🔍 DEBUG: Connection established: " + (conn != null));
        
        ps.setString(1, d.get_id_detektif());
        ps.setString(2, d.get_nama_detektif());
        ps.setString(3, d.get_spesialisasi());
        ps.setString(4, d.get_username());
        ps.setString(5, d.get_password());
        
        int rowsAffected = ps.executeUpdate();
        System.out.println("🔍 DEBUG: Rows affected: " + rowsAffected);
        
        if (rowsAffected > 0) {
            System.out.println("✅ Data detektif berhasil ditambahkan!");
        } else {
            System.out.println("❌ No rows affected - insert failed!");
        }
        
    } catch (Exception e) {
        System.out.println("❌ ERROR during insert:");
        e.printStackTrace();
    }
}
    public List<model_detektif> get_all() {
        List<model_detektif> list = new ArrayList<>();
        String sql = "SELECT * FROM detektif";
        try (Connection conn = koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model_detektif d = new model_detektif();
                d.set_id_detektif(rs.getString("id_detektif"));
                d.set_nama_detektif(rs.getString("nama_detektif"));
                d.set_spesialisasi(rs.getString("spesialisasi"));
                d.set_username(rs.getString("username"));
                d.set_password(rs.getString("password"));
                list.add(d);
            }
            

        } catch (Exception e) {
            

            e.printStackTrace();
        }
        return list;
    }

    public model_detektif get_by_id(String id_detektif) {
        model_detektif d = null;
        String sql = "SELECT * FROM detektif WHERE id_detektif = ?";
        try (Connection conn = koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id_detektif);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                d = new model_detektif();
                d.set_id_detektif(rs.getString("id_detektif"));
                d.set_nama_detektif(rs.getString("nama_detektif"));
                d.set_spesialisasi(rs.getString("spesialisasi"));
                d.set_username(rs.getString("username"));
                d.set_password(rs.getString("password"));
            }

            

        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return d;
    }

    public void update(model_detektif d) {
        String sql = "UPDATE detektif SET nama_detektif=?, spesialisasi=?, username=?, password=? WHERE id_detektif=?";
        try (Connection conn = koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.get_nama_detektif());
            ps.setString(2, d.get_spesialisasi());
            ps.setString(3, d.get_username());
            ps.setString(4, d.get_password());
            ps.setString(5, d.get_id_detektif());
            ps.executeUpdate();

            System.out.println("✅ Data detektif berhasil diupdate!");

            

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public void delete(String id_detektif) {
        String sql = "DELETE FROM detektif WHERE id_detektif=?";
        try (Connection conn = koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id_detektif);
            ps.executeUpdate();
            System.out.println("🗑️ Data detektif berhasil dihapus!");
            

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public boolean check_login(String username, String password) {
        String sql = "SELECT * FROM detektif WHERE username = ? AND password = ?";
        try (Connection conn = koneksi.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            

            return rs.next(); // Jika ada data, berarti login berhasil

        } catch (Exception e) {
            
            e.printStackTrace();

            return false;
        }
    }

    public boolean is_username_exist(String username) {
    String sql = "SELECT * FROM detektif WHERE username = ?";
    System.out.println("🔍 DEBUG: Checking username: " + username);
    
    try (Connection conn = koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        
        boolean exists = rs.next();
        System.out.println("🔍 DEBUG: Username exists: " + exists);
        
        return exists;
        
    } catch (Exception e) {
        System.out.println("❌ ERROR during username check:");
        e.printStackTrace();
        return false;
    }
}


}