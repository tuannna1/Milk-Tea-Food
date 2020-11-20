/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Display;

import Other.DateHelper;
import Other.DialogHelper;
import Other.LoaiSanPham;
import Other.LoaiSanPhamDao;
import Other.SanPham;
import Other.SanPhamDao;
import Other.ShareHelper;
import Other.MyCombobox;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quynh GaGa
 */
public class QLSanPham extends javax.swing.JFrame {

    int index = 0;
    SanPhamDao dao = new SanPhamDao();
    String imageName = null;
    LoaiSanPhamDao spdao = new LoaiSanPhamDao();
    private int i;

    /**
     * Creates new form QLSanPham
     */
    public QLSanPham() {
        ImageIcon img = new ImageIcon("src//Image//iced-tea.png");
        this.setIconImage(img.getImage());
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
        txtNguoiTao.setText(ShareHelper.USER.getHoTen());
        txtNguoiTaoSP.setText(ShareHelper.USER.getHoTen());

        load();
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tableSP.getModel();
        model.setRowCount(0);

        try {
            List<SanPham> list = dao.select();
            for (SanPham cd : list) {
                Object[] row = {
                    cd.getLoaiSP(),
                    cd.getTenSP(),
                    cd.getSoLuong(),
                    cd.getNguoiTao(),
                    cd.getMoTa()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public boolean Check() {
        //String strMaLSP = txtMaLSP.getText();
        String strTenLSP = txtTenLoaiSP.getText();
        String strSL = txtSL.getText();

        if (txtTenLoaiSP.getText().equals("") || txtSL.getText().equals("")
                || txtGhiChu.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save");
            return false;
        }

        int sl = Integer.parseInt(strSL);
        if (sl < 0) {
            JOptionPane.showMessageDialog(this, "Số lượng > 0!");
            return false;
        }

        List<SanPham> list = dao.select();
        for (SanPham nv : list) {

            if (strTenLSP.equals(nv.getTenSP())) {
                JOptionPane.showMessageDialog(this, "Không nhập trùng Tên Loại Sản Phẩm !");
                txtTenLoaiSP.requestFocus();
                return false;
            }
        }

        return true;
    }

    ;

    void insert() {
        SanPham model = getModel();
        try {
            dao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        SanPham model = getModel();
        try {
            dao.update(model);
            this.load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn có muốn xóa hay không?")) {
            String macd = txtMaLSP.getText();
            try {
                dao.delete(macd);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!" + e);
            }
        }
    }

    void clear() {
        this.setModel(new SanPham());
    }

    void edit() {
        try {
            String macd = (String) tableSP.getValueAt(this.index, 0);
            SanPham model = dao.findById(macd);
            if (model != null) {
                this.setModel(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(SanPham model) {
        txtMaLSP.setText(model.getLoaiSP());
        txtTenLoaiSP.setText(model.getTenSP());
        txtSL.setText(String.valueOf(model.getSoLuong()));
        txtNguoiTao.setText(ShareHelper.USER.getHoTen());
        txtGhiChu.setText(model.getMoTa());
    }

    SanPham getModel() {
        SanPham model = new SanPham();
        model.setLoaiSP(txtMaLSP.getText());
        model.setTenSP(txtTenLoaiSP.getText());
        model.setSoLuong(Integer.valueOf(txtSL.getText()));
        model.setNguoiTao(txtNguoiTao.getText());
        model.setMoTa(txtGhiChu.getText());
        return model;
    }

    public void showDetail(int i) {
        List<SanPham> listSP = dao.select();
        SanPham sp = listSP.get(i);
        txtMaLSP.setText(sp.getLoaiSP());
        txtTenLoaiSP.setText(sp.getTenSP());
        txtSL.setText(String.valueOf(sp.getSoLuong()));
        txtNguoiTao.setText(sp.getNguoiTao());
        txtGhiChu.setText(sp.getMoTa());

    }

    //bảng Chi Tiết Sản Phẩm
    void loadSP() {
        DefaultTableModel model = (DefaultTableModel) tableCTSP.getModel();
        model.setRowCount(0);
        try {
            List<LoaiSanPham> list = spdao.select();
            for (LoaiSanPham kh : list) {
                Object[] row = {
                    kh.getMaSP(),
                    kh.getTenLSP(),
                    kh.getTenSP(),
                    kh.getGia(),
                    kh.getKichThuoc(),
                    kh.getMaNV(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!" + e);
        }

    }

    public boolean CheckCTSP() {

        String strTenSP = txtTenSP.getText();
        String strGia = txtGia.getText();

        if (txtTenSP.getText().equals("") || txtGia.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Hãy nhập đủ dữ liệu sau đó ấn Save");
            return false;
        }

        List<LoaiSanPham> list = spdao.select();
        for (LoaiSanPham nv : list) {

        }

        return true;
    }

    ;

    void insertSP() {
        LoaiSanPham model = getModelSP();
        try {
            spdao.insertSP(model);
            this.loadSP();
            this.clearSP();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (HeadlessException e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    void updateSP() {
        LoaiSanPham model = getModelSP();
        try {
            spdao.updateSP(model);
            this.loadSP();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }
    }

    void deleteSP() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
            String MaSP = txtMa.getText();
            try {
                spdao.deleteSP(MaSP);
                this.loadSP();
                this.clearSP();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!" + e);
            }
        }
    }

    void clearSP() {
        this.setModelSP(new LoaiSanPham());
    }

    void editSP() {
        try {
            Integer makh = (Integer) tableCTSP.getValueAt(this.index, 0);
            LoaiSanPham model = spdao.findByIdSP(makh);
            if (model != null) {
                this.setModelSP(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModelSP(LoaiSanPham model) {
//        cboLoaiSP.setSelectedIndex(0);
        index = -1;
        txtMa.setText(model.getMaSP());
        txtTenSP.setText(model.getTenSP());
        txtGia.setText(String.valueOf(model.getGia()));
        cbbKichThuoc.setSelectedIndex(0);
        txtNguoiTaoSP.setText(ShareHelper.USER.getHoTen());

    }

    LoaiSanPham getModelSP() {
        LoaiSanPham model = new LoaiSanPham();
        SanPham sanPham = (SanPham) cboLoaiSP.getSelectedItem();
        model.setMaSP(txtMa.getText());
        model.setTenLSP(sanPham.getTenSP());
        model.setTenSP(txtTenSP.getText());
        model.setGia(Double.valueOf(txtGia.getText()));
        model.setKichThuoc(String.valueOf(cbbKichThuoc.getSelectedItem()));
        model.setMaNV(txtNguoiTao.getText());

        return model;
    }

    void selectComboBox() {
        SanPham sanPham = (SanPham) cboLoaiSP.getSelectedItem();
    }

    void fillComboBoxSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiSP.getModel();
        model.removeAllElements();

        try {
            List<SanPham> list = dao.select();
            for (SanPham cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void show(int i) {
        try {
            List<LoaiSanPham> listLSP = spdao.select();
            LoaiSanPham lsp = listLSP.get(i);

            String tenLSP = (String) tableCTSP.getValueAt(i, 1);
            System.out.println(tenLSP);

            cboLoaiSP.getModel().setSelectedItem(tenLSP);

            txtMa.setText(lsp.getMaSP());
            txtTenSP.setText(lsp.getTenSP());
            txtGia.setText(String.valueOf(lsp.getGia()));
            cbbKichThuoc.setSelectedItem(String.valueOf(lsp.getKichThuoc()));
            txtNguoiTaoSP.setText(lsp.getMaNV());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSP = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnThemMoiSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnNhapMoiSP = new javax.swing.JButton();
        txtSL = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        txtTenLoaiSP = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableCTSP = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnThemMoiCTSP = new javax.swing.JButton();
        btnXoaCTSP = new javax.swing.JButton();
        btnSuaCTSP = new javax.swing.JButton();
        btnNhapMoiCTSP = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNguoiTaoSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        cbbKichThuoc = new javax.swing.JComboBox<>();
        cboLoaiSP = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ SẢN PHẨM");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tabs.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("MÃ LOẠI SẢN PHẨM");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("LOẠI SẢN PHẨM");

        txtMaLSP.setEditable(false);

        tableSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÃ LOẠI SẢN PHẨM", "TÊN LOẠI SẢN PHẨM", "SỐ LƯỢNG", "NGƯỜI TẠO", "GHI CHÚ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableSP);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("SỐ LƯỢNG");

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemMoiSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemMoiSP.setForeground(new java.awt.Color(0, 102, 255));
        btnThemMoiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add (1).png"))); // NOI18N
        btnThemMoiSP.setText("THÊM MỚI");
        btnThemMoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiSPActionPerformed(evt);
            }
        });

        btnXoaSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaSP.setForeground(new java.awt.Color(0, 102, 255));
        btnXoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/eraser.png"))); // NOI18N
        btnXoaSP.setText("XÓA");
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        btnSuaSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaSP.setForeground(new java.awt.Color(0, 102, 255));
        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/document.png"))); // NOI18N
        btnSuaSP.setText("SỬA");
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        btnNhapMoiSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNhapMoiSP.setForeground(new java.awt.Color(0, 102, 255));
        btnNhapMoiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnNhapMoiSP.setText("NHẬP MỚI");
        btnNhapMoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnThemMoiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNhapMoiSP)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaSP)
                            .addComponent(btnNhapMoiSP))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnXoaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemMoiSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("GHI CHÚ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("NGƯỜI TẠO");

        txtNguoiTao.setEditable(false);
        txtNguoiTao.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtNguoiTao.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(39, 39, 39)
                        .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(35, 35, 35)
                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(117, 117, 117))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("LOẠI SẢN PHẨM", jPanel7);

        tableCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ SẢN PHẨM", "LOẠI SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ", "KÍCH THƯỚC", "NGƯỜI TẠO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCTSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableCTSP);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("LOẠI SẢN PHẨM");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("TÊN SẢN PHẨM");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("GIÁ");

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThemMoiCTSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemMoiCTSP.setForeground(new java.awt.Color(0, 102, 255));
        btnThemMoiCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add (1).png"))); // NOI18N
        btnThemMoiCTSP.setText("THÊM MỚI");
        btnThemMoiCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiCTSPActionPerformed(evt);
            }
        });

        btnXoaCTSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaCTSP.setForeground(new java.awt.Color(0, 102, 255));
        btnXoaCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/eraser.png"))); // NOI18N
        btnXoaCTSP.setText("XÓA");
        btnXoaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTSPActionPerformed(evt);
            }
        });

        btnSuaCTSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaCTSP.setForeground(new java.awt.Color(0, 102, 255));
        btnSuaCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/document.png"))); // NOI18N
        btnSuaCTSP.setText("SỬA");
        btnSuaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTSPActionPerformed(evt);
            }
        });

        btnNhapMoiCTSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNhapMoiCTSP.setForeground(new java.awt.Color(0, 102, 255));
        btnNhapMoiCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnNhapMoiCTSP.setText("NHẬP MỚI");
        btnNhapMoiCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiCTSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnThemMoiCTSP)
                .addGap(18, 18, 18)
                .addComponent(btnSuaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNhapMoiCTSP)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnThemMoiCTSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addComponent(btnNhapMoiCTSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaCTSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaCTSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("KÍCH THƯỚC");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("NGƯỜI TẠO");

        txtNguoiTaoSP.setEditable(false);
        txtNguoiTaoSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtNguoiTaoSP.setForeground(new java.awt.Color(255, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("MÃ  LOẠI");

        txtMa.setEditable(false);

        cbbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lớn", "Vừa", "Nhỏ" }));

        cboLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                            .addComponent(cboLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(135, 135, 135)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNguoiTaoSP, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(cbbKichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(136, 136, 136))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(cbbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtNguoiTaoSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("CHI TIẾT SẢN PHẨM", jPanel4);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 153));
        jLabel8.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(tabs)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(478, 478, 478)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.loadSP();
        this.load();
    }//GEN-LAST:event_formWindowOpened

    private void btnThemMoiCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiCTSPActionPerformed
        // TODO add your handling code here:
        if (CheckCTSP()) {
            this.insertSP();
        }

    }//GEN-LAST:event_btnThemMoiCTSPActionPerformed

    private void btnSuaCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTSPActionPerformed
        // TODO add your handling code here:
        this.updateSP();
    }//GEN-LAST:event_btnSuaCTSPActionPerformed

    private void btnXoaCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTSPActionPerformed
        // TODO add your handling code here:
        this.deleteSP();
    }//GEN-LAST:event_btnXoaCTSPActionPerformed

    private void btnNhapMoiCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiCTSPActionPerformed
        // TODO add your handling code here:
        this.clearSP();
    }//GEN-LAST:event_btnNhapMoiCTSPActionPerformed

    private void tableSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSPMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.index = tableSP.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
                this.showDetail(index);
                //tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tableSPMouseClicked

    private void btnThemMoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiSPActionPerformed
        // TODO add your handling code here:
        if (Check()) {
            this.insert();
            this.load();
        }
    }//GEN-LAST:event_btnThemMoiSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnNhapMoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiSPActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnNhapMoiSPActionPerformed

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        // TODO add your handling code here:
        this.loadSP();
        this.fillComboBoxSP();
    }//GEN-LAST:event_tabsMouseClicked

    private void tableCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCTSPMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.index = tableCTSP.getSelectedRow();
            if (this.index >= 0) {
                show(index);
                tabs.setSelectedIndex(1);
            }
        }
    }//GEN-LAST:event_tableCTSPMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLSanPham().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNhapMoiCTSP;
    private javax.swing.JButton btnNhapMoiSP;
    private javax.swing.JButton btnSuaCTSP;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemMoiCTSP;
    private javax.swing.JButton btnThemMoiSP;
    private javax.swing.JButton btnXoaCTSP;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JComboBox<String> cbbKichThuoc;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableCTSP;
    private javax.swing.JTable tableSP;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtNguoiTaoSP;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTenLoaiSP;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
