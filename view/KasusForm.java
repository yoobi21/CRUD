package view;

import model.Kasus;
import dao.KasusDAO; // IMPORT DAO
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KasusForm extends JDialog {
    private JTextField txtIdKasus, txtNamaKasus, txtPenyidik, txtKlasifikasi;
    private JComboBox<String> comboStatus;
    private JButton btnSimpan, btnBatal;
    private boolean saved = false;
    private Kasus kasus;
    private boolean isEditMode;
    private String originalId; // Untuk menyimpan ID asli saat edit
    
    // Constructor untuk mode ADD
    public KasusForm(Frame parent, String title) {
        super(parent, title, true);
        this.kasus = new Kasus();
        this.isEditMode = false;
        
        initComponents();
        setupFormMode();
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }
    
    // Constructor untuk mode EDIT
    public KasusForm(Frame parent, String title, Kasus kasusToEdit) {
        super(parent, title, true);
        this.kasus = kasusToEdit;
        this.isEditMode = true;
        this.originalId = kasusToEdit.getid_kasus(); // Simpan ID asli
        
        initComponents();
        setupFormMode();
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
    
    // METHOD BARU: Setup mode form (tambah/edit)
    private void setupFormMode() {
        if (isEditMode) {
            // MODE EDIT - Isi data yang akan diedit
            setupEditMode();
        } else {
            // MODE TAMBAH - Generate ID baru
            setupAddMode();
        }
    }
    
    // METHOD BARU: Setup mode tambah
    private void setupAddMode() {
        // Generate ID otomatis untuk tambah data
        generateAutoId();
        txtIdKasus.setEditable(false);
        
        // Set judul window
        setTitle("Tambah Kasus Baru");
        
        // Clear fields lainnya
        clearFormFields();
    }
    
    // METHOD BARU: Setup mode edit
    private void setupEditMode() {
        // Isi form dengan data yang akan diedit
        fillFormData();
        txtIdKasus.setEditable(false); // ID tidak bisa diubah saat edit
        
        // Set judul window
        setTitle("Edit Kasus - " + originalId);
    }
    
    // METHOD BARU: Generate ID otomatis
    private void generateAutoId() {
        if (!isEditMode) { // Hanya generate jika mode tambah
            try {
                KasusDAO kasusDAO = new KasusDAO();
                String nextId = kasusDAO.getNextKasusId(); // AMBIL DARI DAO
                txtIdKasus.setText(nextId);
                kasus.setid_kasus(nextId);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error generating ID: " + e.getMessage());
                // Fallback
                txtIdKasus.setText("KAS001");
                kasus.setid_kasus("KAS001");
            }
        }
    }
    
    // METHOD BARU: Clear form fields
    private void clearFormFields() {
        txtNamaKasus.setText("");
        txtPenyidik.setText("");
        txtKlasifikasi.setText("");
        comboStatus.setSelectedIndex(0);
        // ID tidak di-clear karena sudah di-generate
    }
    
    private void fillFormData() {
        txtIdKasus.setText(kasus.getid_kasus());
        txtNamaKasus.setText(kasus.getnama_kasus());
        txtPenyidik.setText(kasus.getPenyidik());
        txtKlasifikasi.setText(kasus.getklasifikasi_kasus());
        
        // Convert enum status to string for combobox
        String statusString = convertStatusToString(kasus.getstatus_kasus());
        comboStatus.setSelectedItem(statusString);
    }
    
    // METHOD BARU: Convert enum status to string
    private String convertStatusToString(Kasus.status_kasus status) {
        switch (status) {
            case AKTIF: return "Aktif";
            case DITUTUP: return "Ditutup";
            case DALAM_PENYELIDIKAN: return "Dalam Penyelidikan";
            case SELESAI: return "Selesai";
            default: return "Aktif";
        }
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
        
        // Untuk mode edit, pastikan ID tetap menggunakan original
        if (isEditMode) {
            kasus.setid_kasus(originalId);
        } else {
            kasus.setid_kasus(txtIdKasus.getText().trim());
        }
        
        kasus.setnama_kasus(txtNamaKasus.getText().trim());
        kasus.setPenyidik(txtPenyidik.getText().trim());
        kasus.setklasifikasi_kasus(txtKlasifikasi.getText().trim());
        
        // Set status kasus
        String selectedStatus = comboStatus.getSelectedItem().toString();
        kasus.setStatus_kasus(convertStringToStatus(selectedStatus));
        
        saved = true;
        dispose();
    }
    
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
    
    // METHOD BARU: Untuk mengetahui mode form
    public boolean isEditMode() {
        return isEditMode;
    }
}