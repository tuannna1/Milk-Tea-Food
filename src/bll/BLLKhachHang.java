/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import Other.ThongBao;
import Other.KhachHang;
import Other.MyCombobox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Tuan Nguyen
 */
public class BLLKhachHang {
     //3. Hàm đổ dữ liệu vào Combobox Khách Hàng
    public static void DoDuLieuVaoCBBKhachHang(JComboBox cbb, String keyword){
        cbb.removeAllItems();
        try {
            ResultSet rs = Other.KhachHangDao.GetByKeyword(keyword);
            
            DefaultComboBoxModel cbbModel = (DefaultComboBoxModel)cbb.getModel();
           
            while(rs.next()){
                MyCombobox mb = new MyCombobox(rs.getString("HoTenKH"), 
                                            rs.getInt("MaKH"));
                cbbModel.addElement(mb);                
            }
        } catch (SQLException ex) {
            ThongBao.ThongBao("Thông báo", "Lỗi truy vấn dữ liệu.");
        }
    }
    
    
    
    
    public static KhachHang GetKHByMaKH(String MaKH){
        ResultSet rs = Other.KhachHangDao.GetByMaKH(MaKH);
        
        try {
            if(rs.next()){
                KhachHang kh = new KhachHang();
      kh.setMaKH(rs.getString("MaKH"));
      kh.setHotenKH(rs.getString("HoTenKH"));
      kh.setGioitinh(rs.getBoolean("GioiTinh"));
      kh.setDienthoai(rs.getString("DienThoai")); 
      kh.setGhichu(rs.getString("GhiChu"));
      kh.setGmail(rs.getString("Email"));

                
                return kh;
            }
        } catch (SQLException ex) {
            ThongBao.ThongBao("Lỗi lấy khách hàng theo mã", "Thông báo");
           
        }
        return null;
    }
}
