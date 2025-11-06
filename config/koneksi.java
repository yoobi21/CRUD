package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    
    private static Connection conn;
    
    public static Connection getConnection() {
        try {
            // Cek jika koneksi null, closed, atau tidak valid
            if (conn == null || conn.isClosed() || !conn.isValid(2)) {
                String url = "jdbc:mysql://localhost:3306/local";
                String user = "root";
                String pass = "";
                
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Koneksi Berhasil!");
            }
        } catch (Exception e) {
            System.out.println("❌ Koneksi Gagal!");
            e.printStackTrace();
            conn = null; // Reset connection
        }
        
        return conn;
    }
    
    // Method untuk menutup koneksi secara manual
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Koneksi ditutup!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}