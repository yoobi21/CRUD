package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.dao_detektif;
import model.model_detektif;

public class registerMenu extends JFrame {
    private JTextField txt_id_detektif;
    private JTextField txt_alias;
    private JComboBox<String> combo_spesialisasi; // ✅ Field class
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JButton btn_generate_id;
    private JButton btn_confirm;
    private JButton btn_back;
    private JLabel lbl_id_display;

    private dao_detektif dao = new dao_detektif();

    public registerMenu() {
        setTitle("Detective Registration");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Panel utama dengan background gelap
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(20, 20, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Panel untuk konten utama (akan di-center)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(20, 20, 30));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title - ID Detektif
        JLabel lbl_title = new JLabel("ID Detektif");
        lbl_title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl_title.setForeground(new Color(220, 180, 60));
        lbl_title.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ID Display
        lbl_id_display = new JLabel("D215606");
        lbl_id_display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lbl_id_display.setForeground(Color.WHITE);
        lbl_id_display.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_id_display.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Generate ID Button
        btn_generate_id = new JButton("Generate ID");
        btn_generate_id.setMaximumSize(new Dimension(300, 35));
        btn_generate_id.setPreferredSize(new Dimension(300, 35));
        btn_generate_id.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn_generate_id.setBackground(new Color(60, 60, 80));
        btn_generate_id.setForeground(Color.LIGHT_GRAY);
        btn_generate_id.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100)));
        btn_generate_id.setFocusPainted(false);
        btn_generate_id.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_generate_id.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_generate_id.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // ✅ Tambah margin bawah

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(20, 20, 30));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Alias field
        JLabel lbl_alias = new JLabel("Alias");
        lbl_alias.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_alias.setForeground(Color.LIGHT_GRAY);
        lbl_alias.setAlignmentX(Component.CENTER_ALIGNMENT);

        txt_alias = new JTextField();
        txt_alias.setMaximumSize(new Dimension(300, 35));
        txt_alias.setPreferredSize(new Dimension(300, 35));
        txt_alias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_alias.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txt_alias.setBackground(new Color(40, 40, 50));
        txt_alias.setForeground(Color.WHITE);
        txt_alias.setCaretColor(Color.WHITE);
        txt_alias.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_alias.setHorizontalAlignment(JTextField.CENTER);

        // Spesialisasi field
        JLabel lbl_spesialisasi = new JLabel("Spesialisasi");
        lbl_spesialisasi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_spesialisasi.setForeground(Color.LIGHT_GRAY);
        lbl_spesialisasi.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_spesialisasi.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Buat dropdown (JComboBox) untuk spesialisasi - ✅ GUNAKAN FIELD CLASS
        String[] spesialisasiOptions = {
                "Pilih Spesialisasi",
                "Forensik Digital",
                "Investigasi Finansial",
                "Pengintaian",
                "Analisis Intelijen",
                "Kriminalistik"
        };

        combo_spesialisasi = new JComboBox<>(spesialisasiOptions); // ✅ Tidak ada deklarasi ulang
        combo_spesialisasi.setMaximumSize(new Dimension(300, 35));
        combo_spesialisasi.setPreferredSize(new Dimension(300, 35));
        combo_spesialisasi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo_spesialisasi.setBackground(new Color(40, 40, 50));
        combo_spesialisasi.setForeground(Color.WHITE);
        combo_spesialisasi.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        combo_spesialisasi.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Styling untuk dropdown agar lebih gelap
        combo_spesialisasi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(new Color(40, 40, 50));
                c.setForeground(Color.WHITE);
                if (isSelected) {
                    c.setBackground(new Color(60, 60, 80));
                }
                return c;
            }
        });

        // Username field
        JLabel lbl_username = new JLabel("Username");
        lbl_username.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_username.setForeground(Color.LIGHT_GRAY);
        lbl_username.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl_username.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        txt_username = new JTextField();
        txt_username.setMaximumSize(new Dimension(300, 35));
        txt_username.setPreferredSize(new Dimension(300, 35));
        txt_username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
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
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txt_password.setBackground(new Color(40, 40, 50));
        txt_password.setForeground(Color.WHITE);
        txt_password.setCaretColor(Color.WHITE);
        txt_password.setAlignmentX(Component.CENTER_ALIGNMENT);
        txt_password.setHorizontalAlignment(JTextField.CENTER);

        // Button panel untuk Confirm dan Back
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(20, 20, 30));
        buttonPanel.setMaximumSize(new Dimension(320, 50));

        // Confirm Button
        btn_confirm = new JButton("Confirm");
        btn_confirm.setPreferredSize(new Dimension(140, 40));
        btn_confirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn_confirm.setBackground(new Color(220, 180, 60));
        btn_confirm.setForeground(Color.BLACK);
        btn_confirm.setFocusPainted(false);
        btn_confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Back Button
        btn_back = new JButton("Back");
        btn_back.setPreferredSize(new Dimension(140, 40));
        btn_back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn_back.setBackground(new Color(60, 60, 80));
        btn_back.setForeground(Color.LIGHT_GRAY);
        btn_back.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100)));
        btn_back.setFocusPainted(false);
        btn_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tambahkan button ke panel
        buttonPanel.add(btn_confirm);
        buttonPanel.add(btn_back);

        // ✅ PERBAIKI: Tambahkan combo_spesialisasi ke form panel (sebelumnya lupa)
        formPanel.add(lbl_alias);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txt_alias);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(lbl_spesialisasi);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(combo_spesialisasi); // ✅ INI YANG DITAMBAHKAN
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(lbl_username);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txt_username);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(lbl_password);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txt_password);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        formPanel.add(buttonPanel);

        // Tambahkan semua komponen ke center panel
        centerPanel.add(lbl_title);
        centerPanel.add(lbl_id_display);
        centerPanel.add(btn_generate_id);
        centerPanel.add(formPanel);

        // Tambahkan center panel ke main panel (di tengah)
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Tambahkan main panel ke frame
        add(mainPanel);

        // Generate initial ID
        txt_id_detektif = new JTextField(generate_id());
        lbl_id_display.setText(txt_id_detektif.getText());

        // === Button actions ===
        btn_generate_id.addActionListener(e -> {
            txt_id_detektif.setText(generate_id());
            lbl_id_display.setText(txt_id_detektif.getText());
        });

        btn_confirm.addActionListener(e -> register_action());
        btn_back.addActionListener(e -> back_to_login());

        // Hover effects
        addButtonHoverEffects();
    }

    private String generate_id() {
        long time = System.currentTimeMillis() % 1000000L;
        return "D" + String.format("%06d", time);
    }

    private void register_action() {
        String id_detektif = txt_id_detektif.getText().trim();
        String alias = txt_alias.getText().trim();
        String spesialisasi = (String) combo_spesialisasi.getSelectedItem(); // ✅ Sekarang bisa diakses
        String username = txt_username.getText().trim();
        String password = new String(txt_password.getPassword()).trim();

        // Validasi spesialisasi
        if (spesialisasi.equals("Pilih Spesialisasi")) {
            JOptionPane.showMessageDialog(this, "Pilih spesialisasi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (alias.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dao.is_username_exist(username)) {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model_detektif d = new model_detektif(id_detektif, alias, spesialisasi, username, password);
        try {
            dao.insert(d);
            JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.", "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            back_to_login();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal registrasi: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void back_to_login() {
        new loginView().setVisible(true);
        dispose();
    }

    // === HOVER EFFECTS ===
    private void addButtonHoverEffects() {
        btn_generate_id.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_generate_id.setBackground(new Color(80, 80, 100));
                btn_generate_id.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent evt) {
                btn_generate_id.setBackground(new Color(60, 60, 80));
                btn_generate_id.setForeground(Color.LIGHT_GRAY);
            }
        });

        btn_confirm.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_confirm.setBackground(new Color(240, 200, 80));
            }

            public void mouseExited(MouseEvent evt) {
                btn_confirm.setBackground(new Color(220, 180, 60));
            }
        });

        btn_back.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn_back.setBackground(new Color(80, 80, 100));
                btn_back.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent evt) {
                btn_back.setBackground(new Color(60, 60, 80));
                btn_back.setForeground(Color.LIGHT_GRAY);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new registerMenu().setVisible(true));
    }
}