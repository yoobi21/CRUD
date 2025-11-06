package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.dao_detektif;
import javafx.stage.Stage;
import model.model_detektif;

public class loginView extends JFrame {
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JButton btn_login, btn_register;
    private JLabel lbl_title, lbl_subtitle, lbl_or;

    private dao_detektif dao = new dao_detektif();

    public loginView() {
        setTitle("Detective Portal");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel utama dengan background gelap dan layout center
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(20, 20, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
        // Panel untuk konten utama (akan di-center)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 30));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Title - Welcome Back, Chief
        lbl_title = new JLabel("Welcome Back, Chief");
        lbl_title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl_title.setForeground(new Color(220, 180, 60));
        lbl_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Subtitle
        lbl_subtitle = new JLabel("Login untuk mengungkap fakta yang tersembunyi");
        lbl_subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_subtitle.setForeground(Color.LIGHT_GRAY);
        lbl_subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(20, 20, 30));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username field
        JLabel lbl_username = new JLabel("Username");
        lbl_username.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_username.setForeground(Color.LIGHT_GRAY);
        lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txt_username = new JTextField();
        txt_username.setMaximumSize(new Dimension(300, 35));
        txt_username.setPreferredSize(new Dimension(300, 35));
        txt_username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_username.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txt_username.setBackground(new Color(40, 40, 50));
        txt_username.setForeground(Color.WHITE);
        txt_username.setCaretColor(Color.WHITE);
        txt_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_username.setHorizontalAlignment(JTextField.CENTER);
        
        // Password field
        JLabel lbl_password = new JLabel("Password");
        lbl_password.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_password.setForeground(Color.LIGHT_GRAY);
        lbl_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_password.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        txt_password = new JPasswordField();
        txt_password.setMaximumSize(new Dimension(300, 35));
        txt_password.setPreferredSize(new Dimension(300, 35));
        txt_password.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_password.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txt_password.setBackground(new Color(40, 40, 50));
        txt_password.setForeground(Color.WHITE);
        txt_password.setCaretColor(Color.WHITE);
        txt_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_password.setHorizontalAlignment(JTextField.CENTER);
        
        // Login button
        btn_login = new JButton("Login");
        btn_login.setMaximumSize(new Dimension(300, 40));
        btn_login.setPreferredSize(new Dimension(300, 40));
        btn_login.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn_login.setBackground(new Color(220, 180, 60));
        btn_login.setForeground(Color.BLACK);
        btn_login.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        btn_login.setFocusPainted(false);
        btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_login.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // "Or" label
        lbl_or = new JLabel("Or");
        lbl_or.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_or.setForeground(Color.GRAY);
        lbl_or.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_or.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Register button
        btn_register = new JButton("Register");
        btn_register.setMaximumSize(new Dimension(300, 35));
        btn_register.setPreferredSize(new Dimension(300, 35));
        btn_register.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn_register.setBackground(new Color(60, 60, 80));
        btn_register.setForeground(Color.LIGHT_GRAY);
        btn_register.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100)));
        btn_register.setFocusPainted(false);
        btn_register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_register.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Tambahkan komponen ke form panel dengan center alignment
        formPanel.add(lbl_username);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txt_username);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(lbl_password);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txt_password);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(btn_login);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(lbl_or);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(btn_register);
        
        // Tambahkan semua komponen ke center panel
        centerPanel.add(lbl_title);
        centerPanel.add(lbl_subtitle);
        centerPanel.add(formPanel);
        
        // Tambahkan center panel ke main panel (di tengah)
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Tambahkan main panel ke frame
        add(mainPanel);
        
        // === Event listeners ===
        btn_login.addActionListener(e -> login_action());
        btn_register.addActionListener(e -> register_action());
        
        // Enter key support
        txt_password.addActionListener(e -> login_action());
        
        // Hover effects
        addButtonHoverEffects();
    }

    // === LOGIN ACTION ===
    private void login_action() {
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showErrorDialog("Isi username dan password!", "Field Kosong");
            return;
        }

        if (dao.check_login(username, password)) {
            JOptionPane.showMessageDialog(this, 
                "Login berhasil, selamat datang " + username + "!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            new menuView(username).setVisible(true); 
            dispose();
        } else {
            showErrorDialog("Username atau password salah!", "Login Gagal");
        }
    }

    // === REGISTER ACTION ===
    private void register_action() {
        new registerMenu().setVisible(true);
        dispose();
    }
    
    // === HOVER EFFECTS ===
    private void addButtonHoverEffects() {
        btn_login.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_login.setBackground(new Color(240, 200, 80));
            }
            public void mouseExited(MouseEvent evt) {
                btn_login.setBackground(new Color(220, 180, 60));
            }
        });
        
        btn_register.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_register.setBackground(new Color(80, 80, 100));
                btn_register.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent evt) {
                btn_register.setBackground(new Color(60, 60, 80));
                btn_register.setForeground(Color.LIGHT_GRAY);
            }
        });
    }
    
    // === CUSTOM ERROR DIALOG ===
    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        // Set look and feel untuk tampilan yang lebih modern
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new loginView().setVisible(true));
    }

    public void start(Stage stage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}