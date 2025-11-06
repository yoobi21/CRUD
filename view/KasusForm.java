package view;

import model.Kasus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KasusForm extends JDialog {
    private JTextField txtIdKasus, txtNamaKasus, txtPenyidik, txtKlasifikasi;
    private JComboBox<String> comboStatus;
    private JButton btnSimpan, btnBatal;
    private boolean saved = false;
    private Kasus kasus;
    
    public KasusForm(Frame parent, String title) {
    super(parent, title, true);
    this.kasus = new Kasus();

   

    initComponents();
    
    
    txtIdKasus.setEditable(false); // Biar gak bisa diedit
    
    setSize(400, 300);
    setLocationRelativeTo(parent);
}
    
    public KasusForm(Frame parent, String title, Kasus kasus) {
        super(parent, title, true);
        this.kasus = kasus;
        initComponents();
        fillFormData();
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setResizable(false);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Labels and Fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Kasus *"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtIdKasus = new JTextField(20);
        formPanel.add(txtIdKasus, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Nama Kasus *"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtNamaKasus = new JTextField(20);
        formPanel.add(txtNamaKasus, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Penyidik *"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtPenyidik = new JTextField(20);
        formPanel.add(txtPenyidik, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Klasifikasi Kasus"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.weightx = 1.0;
        txtKlasifikasi = new JTextField(20);
        formPanel.add(txtKlasifikasi, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Status Kasus *"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.weightx = 1.0;
        String[] statusOptions = {"Aktif", "Ditutup", "Dalam Penyelidikan", "Selesai"};
        comboStatus = new JComboBox<>(statusOptions);
        formPanel.add(comboStatus, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        btnBatal = new JButton("Batal");
        btnBatal.addActionListener(e -> dispose());
        
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBackground(new Color(70, 130, 180));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.addActionListener(e -> saveKasus());
        
        buttonPanel.add(btnBatal);
        buttonPanel.add(btnSimpan);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(btnSimpan);
    }
    
    private void fillFormData() {
        txtIdKasus.setText(kasus.getid_kasus());
        txtNamaKasus.setText(kasus.getnama_kasus());
        txtPenyidik.setText(kasus.getPenyidik());
        txtKlasifikasi.setText(kasus.getklasifikasi_kasus());
        comboStatus.setSelectedItem(kasus.getstatus_kasus());
    }
    
    private void saveKasus() {
    if (txtIdKasus.getText().trim().isEmpty() || 
        txtNamaKasus.getText().trim().isEmpty() ||
        txtPenyidik.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "ID Kasus, Nama Kasus, dan Penyidik harus diisi!", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    kasus.setid_kasus(txtIdKasus.getText().trim());
    kasus.setnama_kasus(txtNamaKasus.getText().trim());
    kasus.setPenyidik(txtPenyidik.getText().trim());
    kasus.setklasifikasi_kasus(txtKlasifikasi.getText().trim());
    
    // PERBAIKAN: Set status kasus yang benar
    String selectedStatus = comboStatus.getSelectedItem().toString();
    kasus.setStatus_kasus(convertStringToStatus(selectedStatus));
    
    saved = true;
    dispose();
}

// Tambahkan method konversi
private Kasus.status_kasus convertStringToStatus(String statusStr) {
    switch (statusStr) {
        case "Aktif": return Kasus.status_kasus.AKTIF;
        case "Ditutup": return Kasus.status_kasus.DITUTUP;
        case "Dalam Penyelidikan": return Kasus.status_kasus.DALAM_PENYELIDIKAN;
        case "Selesai": return Kasus.status_kasus.SELESAI;
        default: return Kasus.status_kasus.AKTIF;
    }
}
    
    public boolean isSaved() {
        return saved;
    }
    
    public Kasus getKasus() {
        return kasus;
    }
}