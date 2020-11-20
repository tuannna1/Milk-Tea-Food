/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Display;

import javax.swing.ImageIcon;
import Other.DAOHoaDon;
import Other.DialogHelper;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import Other.HoaDon;
import Other.ChiTietHoaDon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import Other.DateHelper;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import bll.ChuyenDoi;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import Display.CTHoaDon;

/**
 *
 * @author trinhtrinh
 */
public class ThongKe extends javax.swing.JFrame {
 public static int tabIndex;
    DAOHoaDon hd = new DAOHoaDon();
    int index = 0;
    String dburl = "jdbc:sqlserver://localhost;databaseName=Milk_Tea&FoodS;user=java3;password=java";

    /**
     * Creates new form ThongKe
     */
    public ThongKe() {

        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tabs.setSelectedIndex(tabIndex);
        if(tabIndex==0) tabs.setSelectedIndex(tabIndex);
        if(tabIndex==1) tabs.setSelectedIndex(tabIndex);
        setLocationRelativeTo(this);
        load();
        load2();
//       fillComboBoxHD();
        fillComboBox();
        cboMaDonHang.setSelectedIndex(-1);
        cboNgayThangNam.setSelectedIndex(-1);
        cboNamNgayThang2.setSelectedIndex(-1);
        lblTong.setText(ChuyenDoi.DinhDangTien(sum()) + "VND");
        String date = sdf.format(dcChooseDay.getDate());

    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat mth = new SimpleDateFormat("M", Locale.getDefault());
    SimpleDateFormat yr = new SimpleDateFormat("yyyy", Locale.getDefault());
    Statement st;

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblTKHD.getModel();
        model.setRowCount(0);

        List<HoaDon> list = hd.select();

        try {

            for (HoaDon cd : list) {
                Object[] row = {
                    cd.getMaHD(),
                    cd.getSoHoaDon(),
                    ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                    cd.getMaNV(),
                    cd.getMaKH(),
                    cd.getTongTien(),
                    cd.getGhiChu()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    void load2() {
        DefaultTableModel model2 = (DefaultTableModel) tblTKDT.getModel();
        model2.setRowCount(0);

        List<HoaDon> list2 = hd.select();

        try {

            for (HoaDon hd : list2) {
                Object[] row = {
                    hd.getMaHD(),
                    ChuyenDoi.DinhDangNgay(hd.getNgayTao()),
                    hd.getTongTien(),};
                model2.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        sum();

    }

    public double sum() {
        double tongTien = 0;
        for (int i = 0; i < tblTKDT.getRowCount(); i++) {
            tongTien += ChuyenDoi.ChuyenTien(tblTKDT.getValueAt(i, 2).toString());
        }

        return tongTien;
    }

    void setModelHD(HoaDon model) {
        cboMaDonHang.setSelectedIndex(0);
        index = -1;
//        txtNgay.setText(DateHelper.toString(model.getNgayTao()));

    }

    HoaDon getModelSP() {
        HoaDon model = new HoaDon();
        HoaDon hoaDon = (HoaDon) cboMaDonHang.getSelectedItem();
//        model.setNgayTao(DateHelper.toDate(txtNgay.getText()));
        return null;

    }

    void selectComboBox() {
        HoaDon hoaDon = (HoaDon) cboMaDonHang.getSelectedItem();
    }
//    void fillComboBoxHD() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaDonHang.getModel();
//        model.removeAllElements();
//
//        try {
//            List<HoaDon> list = hd.select();
//            for (HoaDon cd : list) {
//                model.addElement(cd);
//            }
//        } catch (Exception e) {
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//        }
//    }

    void locNgayThangNam() {
         DefaultTableModel model = (DefaultTableModel) tblTKHD.getModel();
        model.setRowCount(0);
        if (cboNgayThangNam.getSelectedIndex() == 0) {
             String year = yr.format(dcChooseDay.getDate());
            List<HoaDon> list = hd.selectByYear(year);
            try {
                model.setRowCount(0);
                for (HoaDon cd : list) {
                    Object[] row = {
                        cd.getMaHD(),
                        cd.getSoHoaDon(),
                        ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                        cd.getMaNV(),
                        cd.getMaKH(),
                        cd.getTongTien(),
                        cd.getGhiChu()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            }
        } else if (cboNgayThangNam.getSelectedIndex() == 1) {
            String date = sdf.format(dcChooseDay.getDate());
            
            List<HoaDon> list = hd.selectByDate(date);
            try {
                model.setRowCount(0);
                for (HoaDon cd : list) {
                    Object[] row = {
                        cd.getMaHD(),
                        cd.getSoHoaDon(),
                        ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                        cd.getMaNV(),
                        cd.getMaKH(),
                        cd.getTongTien(),
                        cd.getGhiChu()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            }
        } else if(cboNgayThangNam.getSelectedIndex() == 2) {
            String month = mth.format(dcChooseDay.getDate());
            String year = yr.format(dcChooseDay.getDate());
            List<HoaDon> list = hd.selectByMONTH(month, year);
            try {
                model.setRowCount(0);
                for (HoaDon cd : list) {
                    Object[] row = {
                        cd.getMaHD(),
                        cd.getSoHoaDon(),
                        ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                        cd.getMaNV(),
                        cd.getMaKH(),
                        cd.getTongTien(),
                        cd.getGhiChu()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            }
        }
    }
    void locNgayThangNam2() {
         DefaultTableModel model2 = (DefaultTableModel) tblTKDT.getModel();
        model2.setRowCount(0);
        if (cboNamNgayThang2.getSelectedIndex() == 0) {
             String year = yr.format(dcChooseDay2.getDate());
           List<HoaDon> list2 = hd.selectByDate(year);

        try {

            for (HoaDon hd : list2) {
                Object[] row = {
                    hd.getMaHD(),
                    ChuyenDoi.DinhDangNgay(hd.getNgayTao()),
                    hd.getTongTien(),};
                model2.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        } else if (cboNamNgayThang2.getSelectedIndex() == 1) {
            String date = sdf.format(dcChooseDay2.getDate());
            
            List<HoaDon> list2 = hd.selectByDate(date);
              try {

            for (HoaDon hd : list2) {
                Object[] row = {
                    hd.getMaHD(),
                    ChuyenDoi.DinhDangNgay(hd.getNgayTao()),
                    hd.getTongTien(),};
                model2.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        } else if(cboNamNgayThang2.getSelectedIndex() == 2) {
            String month = mth.format(dcChooseDay2.getDate());
            String year = yr.format(dcChooseDay2.getDate());
            List<HoaDon> list2 = hd.selectByMONTH(month, year);
            try {

            for (HoaDon hd : list2) {
                Object[] row = {
                    hd.getMaHD(),
                    ChuyenDoi.DinhDangNgay(hd.getNgayTao()),
                    hd.getTongTien(),};
                model2.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        }
    }
    void fillComboBox() {
        try {
            Connection conn = DriverManager.getConnection(dburl);
            java.sql.Statement ST = conn.createStatement();
            String sql2 = "select * from HoaDon";
            ResultSet RS = ST.executeQuery(sql2);
            while (RS.next()) {
                String MaDH = RS.getString(2);
                cboMaDonHang.addItem(MaDH);
                HoaDonBanHang cd = new HoaDonBanHang();

            }
            conn.close();

        } catch (SQLException ex) {
            System.err.println("Cannot connect database, " + ex);
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

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboMaDonHang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTKHD = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtMaNV = new javax.swing.JTextField();
        dcChooseDay = new com.toedter.calendar.JDateChooser();
        cboNgayThangNam = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanelQLDT = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTKDT = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnLMoi = new javax.swing.JButton();
        lblTong = new javax.swing.JLabel();
        btnTKiem2 = new javax.swing.JButton();
        cboNamNgayThang2 = new javax.swing.JComboBox<>();
        dcChooseDay2 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TỔNG HỢP VÀ THỐNG KÊ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 51));
        jLabel1.setText("TỔNG HỢP & THỐNG KÊ");

        tabs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("MÃ ĐƠN HÀNG:");

        cboMaDonHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMaDonHangItemStateChanged(evt);
            }
        });
        cboMaDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMaDonHangMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("MÃ NHÂN VIÊN:");

        tblTKHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Hóa Đơn", "Số Hóa Đơn", "Ngày Tạo Hóa Đơn", "Mã Nhân Viên", "Mã Khách Hàng", "Tổng Tiền", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTKHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTKHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTKHD);

        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(0, 102, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search.png"))); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(0, 102, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/refresh.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        cboNgayThangNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Năm", "Ngày", "Tháng" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("THỐNG KÊ THEO NGÀY / THÁNG / NĂM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(394, 394, 394)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(dcChooseDay, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboNgayThangNam, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dcChooseDay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboMaDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNgayThangNam, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("THỐNG KÊ HÓA ĐƠN", jPanel1);

        tblTKDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Hóa Đơn", "Ngày Tạo", "Tổng Tiền"
            }
        ));
        jScrollPane2.setViewportView(tblTKDT);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 0, 153));
        jLabel8.setText("TỔNG DOANH THU:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("THỐNG KÊ THEO NGÀY/THÁNG/NĂM:");

        btnLMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLMoi.setForeground(new java.awt.Color(0, 102, 255));
        btnLMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/refresh.png"))); // NOI18N
        btnLMoi.setText("LÀM MỚI");
        btnLMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLMoiActionPerformed(evt);
            }
        });

        lblTong.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        lblTong.setForeground(new java.awt.Color(204, 0, 0));
        lblTong.setText("VND");

        btnTKiem2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTKiem2.setForeground(new java.awt.Color(0, 102, 255));
        btnTKiem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search.png"))); // NOI18N
        btnTKiem2.setText("Tìm Kiếm");
        btnTKiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKiem2ActionPerformed(evt);
            }
        });

        cboNamNgayThang2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Năm", "Ngày", "Tháng" }));

        javax.swing.GroupLayout jPanelQLDTLayout = new javax.swing.GroupLayout(jPanelQLDT);
        jPanelQLDT.setLayout(jPanelQLDTLayout);
        jPanelQLDTLayout.setHorizontalGroup(
            jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLDTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanelQLDTLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLDTLayout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(jLabel8)
                        .addGap(50, 50, 50)
                        .addComponent(lblTong)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelQLDTLayout.createSequentialGroup()
                        .addGap(0, 276, Short.MAX_VALUE)
                        .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelQLDTLayout.createSequentialGroup()
                                .addComponent(btnLMoi)
                                .addGap(54, 54, 54)
                                .addComponent(btnTKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelQLDTLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(103, 103, 103)
                                .addComponent(cboNamNgayThang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(dcChooseDay2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(494, 494, 494))))
        );
        jPanelQLDTLayout.setVerticalGroup(
            jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelQLDTLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblTong))
                .addGap(33, 33, 33)
                .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelQLDTLayout.createSequentialGroup()
                        .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboNamNgayThang2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelQLDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcChooseDay2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        tabs.addTab("QUẢN LÝ DOANH THU", jPanelQLDT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMoiActionPerformed
       cboNamNgayThang2.setSelectedIndex(-1); 
        load2();
        lblTong.setText(ChuyenDoi.DinhDangTien(sum()) + "VND");
    }//GEN-LAST:event_btnLMoiActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        txtMaNV.setText("");
       
        cboMaDonHang.removeAllItems();
        fillComboBox();
        cboMaDonHang.setSelectedIndex(-1);
        cboNgayThangNam.setSelectedIndex(-1);
        
        load();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblTKHD.getModel();
        model.setRowCount(0);
        if (cboMaDonHang.getSelectedItem() != null) {
            String value = (String) cboMaDonHang.getSelectedItem();
            List<HoaDon> list = hd.selectByCbo(value);

            try {

                for (HoaDon cd : list) {
                    Object[] row = {
                        cd.getMaHD(),
                        cd.getSoHoaDon(),
                        ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                        cd.getMaNV(),
                        cd.getMaKH(),
                        cd.getTongTien(),
                        cd.getGhiChu()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            }

        } else if (txtMaNV.getText() != null) {
            String keyword = txtMaNV.getText();
            List<HoaDon> list = hd.selectByKeyword(keyword);

            try {

                for (HoaDon cd : list) {
                    Object[] row = {
                        cd.getMaHD(),
                        cd.getSoHoaDon(),
                        ChuyenDoi.DinhDangNgay(cd.getNgayTao()),
                        cd.getMaNV(),
                        cd.getMaKH(),
                        cd.getTongTien(),
                        cd.getGhiChu()
                    };
                    model.addRow(row);
                }
            } catch (Exception e) {
                DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            }

        }  
        if (cboNgayThangNam.getSelectedItem() != null) {
           locNgayThangNam();
            }


    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cboMaDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMaDonHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaDonHangMouseClicked

    private void cboMaDonHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMaDonHangItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMaDonHangItemStateChanged

    private void btnTKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKiem2ActionPerformed
        locNgayThangNam2();
        sum();

        lblTong.setText(ChuyenDoi.DinhDangTien(sum()) + "VND");
    }//GEN-LAST:event_btnTKiem2ActionPerformed

    private void tblTKHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTKHDMouseClicked
    if (evt.getClickCount() >= 2) {
           CTHoaDon myJFrame = new CTHoaDon();
                    myJFrame .setVisible(true);
        }
    }//GEN-LAST:event_tblTKHDMouseClicked
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:

        this.load();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLMoi;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTKiem2;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboMaDonHang;
    private javax.swing.JComboBox<String> cboNamNgayThang2;
    private javax.swing.JComboBox<String> cboNgayThangNam;
    private com.toedter.calendar.JDateChooser dcChooseDay;
    private com.toedter.calendar.JDateChooser dcChooseDay2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelQLDT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblTKDT;
    private javax.swing.JTable tblTKHD;
    private javax.swing.JTextField txtMaNV;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
