package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import model.Kasus;
import dao.KasusDAO;

public class menuView extends JFrame {
    private JTable table;
    private JTextField searchField;
    private JButton btnDownloadPDF, btnTambah, btnLogout;
    private JLabel welcomeLabel, paginationLabel;
    private KasusDAO kasusDAO;
    private DefaultTableModel tableModel;
    private String username;

    // ===== KONSTRUKTOR =====
    public menuView(String username) {
        this.username = username;
        this.kasusDAO = new KasusDAO();
        
        setTitle("Sistem Kelola Kasus");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        initComponents();
        loadTableData();
        
       
    }

    // ===== INISIALISASI KOMPONEN =====
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ===== HEADER SECTION =====
        JPanel headerPanel = createHeaderPanel();

        // ===== SEARCH SECTION =====
        JPanel searchPanel = createSearchPanel();

        // ===== TABLE SECTION =====
        JPanel tablePanel = createTablePanel();

        // ===== FOOTER SECTION =====
        JPanel footerPanel = createFooterPanel();

        // Gabungkan searchPanel dan tablePanel ke dalam satu panel tengah
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        // ===== ASSEMBLE MAIN PANEL =====
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // ===== HEADER PANEL =====
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Welcome message
        welcomeLabel = new JLabel("Selamat Datang Kembali, " + username + "!");
        welcomeLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        welcomeLabel.setForeground(new Color(60, 60, 60));

        // Kasus title
        JLabel kasusLabel = new JLabel("Kasus");
        kasusLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
        kasusLabel.setForeground(new Color(80, 80, 80));
        kasusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Logout button
        btnLogout = new JButton("Keluar");
        btnLogout.setPreferredSize(new Dimension(80, 35));
        btnLogout.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> logout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);

        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(kasusLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    // ===== SEARCH PANEL =====
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Search field
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)));
        searchField.setText("Cari kasus...");
        searchField.setForeground(Color.GRAY);

        // Placeholder behavior
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Cari kasus...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Cari kasus...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Search button
        JButton btnSearch = new JButton("Cari");
        btnSearch.setPreferredSize(new Dimension(80, 40));
        btnSearch.addActionListener(e -> searchKasus());

        // Tambah button
        btnTambah = new JButton("+ Tambah Kasus");
        btnTambah.setPreferredSize(new Dimension(140, 40));
        btnTambah.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        btnTambah.setBackground(new Color(40, 167, 69));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.addActionListener(e -> showAddForm());

        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnTambah);

        return searchPanel;
    }

    // ===== TABLE PANEL =====
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // Column names
        String[] columnNames = {
                "NO", "ID KASUS", "NAMA KASUS", "PENYIDIK", "KLASIFIKASI KASUS", "STATUS KASUS", "AKSI"
        };

        // Table model
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Hanya kolom AKSI yang editable
            }
        };

        // Create table
        table = new JTable(tableModel);
        table.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        table.setRowHeight(45);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(220, 240, 255));

        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        header.setBackground(new Color(248, 248, 248));
        header.setForeground(new Color(80, 80, 80));
        header.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        header.setReorderingAllowed(false);

        // Set column renderers and widths
        setupTableColumns();

        // Add mouse listener for action buttons
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (col == 6 && row >= 0) { // AKSI column
                    showActionMenu(row, e.getX(), e.getY());
                }
            }
        });

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // ===== SETUP TABLE COLUMNS =====
    private void setupTableColumns() {
        // Center renderer for NO and AKSI columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Left renderer for other columns
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        // Set column renderers and widths
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(0).setMaxWidth(80);

        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);

        table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        table.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setPreferredWidth(140);

        table.getColumnModel().getColumn(6).setCellRenderer(new ActionCellRenderer());
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setMaxWidth(120);
    }

    // ===== FOOTER PANEL =====
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Pagination info
        paginationLabel = new JLabel("Menampilkan 0 dari 0");
        paginationLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        paginationLabel.setForeground(new Color(120, 120, 120));

        // Download PDF button
        btnDownloadPDF = new JButton("Download PDF");
        btnDownloadPDF.setPreferredSize(new Dimension(120, 35));
        btnDownloadPDF.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        btnDownloadPDF.setBackground(new Color(70, 130, 180));
        btnDownloadPDF.setForeground(Color.WHITE);
        btnDownloadPDF.setFocusPainted(false);
        btnDownloadPDF.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnDownloadPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDownloadPDF.addActionListener(e -> downloadPDF());

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnDownloadPDF);

        footerPanel.add(paginationLabel, BorderLayout.WEST);
        footerPanel.add(buttonPanel, BorderLayout.EAST);

        return footerPanel;
    }

    // ===== LOAD DATA KE TABLE =====
    private void loadTableData() {
        List<Kasus> kasusList = kasusDAO.getAllKasus();
        updateTable(kasusList);
    }

    // ===== UPDATE TABLE DENGAN DATA =====
    private void updateTable(List<Kasus> kasusList) {
        tableModel.setRowCount(0);

        int no = 1;
        for (Kasus kasus : kasusList) {
            tableModel.addRow(new Object[] {
                    no++,
                    kasus.getid_kasus(),
                    kasus.getnama_kasus(),
                    kasus.getPenyidik(),
                    kasus.getklasifikasi_kasus(),
                    kasus.getStatusDisplay(),

            });
        }

        paginationLabel.setText("Menampilkan " + kasusList.size() + " data");
    }

    // ===== FUNGSI PENCARIAN =====
    private void searchKasus() {
        String keyword = searchField.getText();
        if (keyword.equals("Cari kasus...") || keyword.trim().isEmpty()) {
            loadTableData();
        } else {
            List<Kasus> kasusList = kasusDAO.searchKasus(keyword.trim());
            updateTable(kasusList);
        }
    }

    // ===== TAMPILKAN FORM TAMBAH KASUS =====
    private void showAddForm() {
        KasusForm form = new KasusForm(this, "Tambah Kasus Baru");
        form.setVisible(true);

        if (form.isSaved()) {
            Kasus newKasus = form.getKasus();
            if (kasusDAO.addKasus(newKasus)) {
                JOptionPane.showMessageDialog(this, "Kasus berhasil ditambahkan!");
                loadTableData();
                // Download PDF otomatis setelah menambah kasus
                
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan kasus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== TAMPILKAN FORM EDIT KASUS =====
    private void showEditForm(int row) {
        String noIdKasus = table.getValueAt(row, 1).toString();
        List<Kasus> kasusList = kasusDAO.searchKasus(noIdKasus);

        if (!kasusList.isEmpty()) {
            Kasus kasus = kasusList.get(0);
            KasusForm form = new KasusForm(this, "Edit Kasus", kasus);
            form.setVisible(true);

            if (form.isSaved()) {
                Kasus updatedKasus = form.getKasus();
                if (kasusDAO.updateKasus(updatedKasus)) {
                    JOptionPane.showMessageDialog(this, "Kasus berhasil diupdate!");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate kasus!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // ===== HAPUS KASUS =====
    private void deleteKasus(int row) {
        String noIdKasus = table.getValueAt(row, 1).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus kasus: " + noIdKasus + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (kasusDAO.deleteKasus(noIdKasus)) {
                JOptionPane.showMessageDialog(this, "Kasus berhasil dihapus!");
                loadTableData();
                // Download PDF otomatis setelah menghapus kasus
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus kasus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== TAMPILKAN MENU AKSI =====
    private void showActionMenu(int row, int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit");
        editItem.addActionListener(e -> showEditForm(row));

        JMenuItem deleteItem = new JMenuItem("Hapus");
        deleteItem.addActionListener(e -> deleteKasus(row));

        JMenuItem viewItem = new JMenuItem("Lihat Detail");
        viewItem.addActionListener(e -> viewDetails(row));

        popupMenu.add(viewItem);
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        popupMenu.show(table, x, y);
    }

    // ===== LIHAT DETAIL KASUS =====
    private void viewDetails(int row) {
        String noIdKasus = table.getValueAt(row, 1).toString();
        String namaKasus = table.getValueAt(row, 2).toString();
        String penyidik = table.getValueAt(row, 3).toString();
        String klasifikasi = table.getValueAt(row, 4).toString();
        String status = table.getValueAt(row, 5).toString();

        String message = String.format(
                "<html><b>Detail Kasus:</b><br><br>" +
                        "No ID Kasus: %s<br>" +
                        "Nama Kasus: %s<br>" +
                        "Penyidik: %s<br>" +
                        "Klasifikasi Kasus: %s<br>" +
                        "Status Kasus: %s</html>",
                noIdKasus, namaKasus, penyidik, klasifikasi, status);

        JOptionPane.showMessageDialog(this, message, "Detail Kasus", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===== FUNGSI LOGOUT =====
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new loginView().setVisible(true); 
        }
    }

    // ===== PDF DOWNLOAD OTOMATIS =====
 

    // ===== PDF DOWNLOAD MANUAL =====
    private void downloadPDF() {
        // Download PDF dengan konfirmasi (manual)
        downloadPDF(true);
    }

    // ===== FUNGSI UTAMA DOWNLOAD PDF =====
    private void downloadPDF(boolean showDialog) {
        try {
            // Create documents folder if not exists
            File folder = new File("documents");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Generate filename with timestamp
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String fileName = "documents/Laporan_Kasus_" + timestamp + ".pdf";
            
            // Create PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // === TITLE ===
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph("LAPORAN DATA KASUS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // === INFO HEADER ===
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
            
            String currentDate = new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new java.util.Date());
            Paragraph date = new Paragraph("Dibuat pada: " + currentDate, infoFont);
            date.setAlignment(Element.ALIGN_LEFT);
            date.setSpacingAfter(5);
            document.add(date);

            Paragraph userInfo = new Paragraph("User: " + username, infoFont);
            userInfo.setAlignment(Element.ALIGN_LEFT);
            userInfo.setSpacingAfter(15);
            document.add(userInfo);

            // === CREATE TABLE ===
            PdfPTable pdfTable = new PdfPTable(6); // 6 columns without AKSI
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);
            pdfTable.setSpacingAfter(10f);

            // Column headers
            String[] headers = {"NO", "ID KASUS", "NAMA KASUS", "PENYIDIK", "KLASIFIKASI KASUS", "STATUS KASUS"};
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new BaseColor(70, 130, 180));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                pdfTable.addCell(cell);
            }

            // Table data
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.BLACK);
            List<Kasus> kasusList = kasusDAO.getAllKasus();
            
            int no = 1;
            for (Kasus kasus : kasusList) {
                // NO
                PdfPCell noCell = new PdfPCell(new Phrase(String.valueOf(no++), cellFont));
                noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(noCell);
                
                // ID KASUS
                pdfTable.addCell(new PdfPCell(new Phrase(kasus.getid_kasus(), cellFont)));
                
                // NAMA KASUS
                pdfTable.addCell(new PdfPCell(new Phrase(kasus.getnama_kasus(), cellFont)));
                
                // PENYIDIK
                pdfTable.addCell(new PdfPCell(new Phrase(kasus.getPenyidik(), cellFont)));
                
                // KLASIFIKASI
                pdfTable.addCell(new PdfPCell(new Phrase(kasus.getklasifikasi_kasus(), cellFont)));
                
                // STATUS
                PdfPCell statusCell = new PdfPCell(new Phrase(kasus.getStatusDisplay(), cellFont));
                statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(statusCell);
            }

            document.add(pdfTable);

            // === SUMMARY ===
            Paragraph summary = new Paragraph("Total Kasus: " + kasusList.size(), infoFont);
            summary.setAlignment(Element.ALIGN_RIGHT);
            summary.setSpacingAfter(10);
            document.add(summary);

            document.close();

            // === NOTIFICATION ===
            if (showDialog) {
                JOptionPane.showMessageDialog(this, 
                    "PDF berhasil didownload!\nLokasi: " + new File(fileName).getAbsolutePath(), 
                    "Download Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("PDF otomatis berhasil dibuat: " + new File(fileName).getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (showDialog) {
                JOptionPane.showMessageDialog(this, 
                    "Error membuat PDF: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== CUSTOM CELL RENDERER UNTUK KOLOM AKSI =====
    private class ActionCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 14));
            panel.setBackground(Color.WHITE);   

            // Dropdown icon
            JLabel dropdown = new JLabel("Modify");
            dropdown.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
            dropdown.setForeground(new Color(70, 130, 180));
            dropdown.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // View icon
            JLabel view = new JLabel("");
            view.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
            view.setForeground(new Color(70, 130, 180));
            view.setCursor(new Cursor(Cursor.HAND_CURSOR));

            panel.add(dropdown);
            panel.add(view);

            if (isSelected) {
                panel.setBackground(new Color(220, 240, 255));
            }

            return panel;
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new menuView("Admin").setVisible(true);
        });
    }
}