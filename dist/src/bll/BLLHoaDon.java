/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import Other.ThongBao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tuan Nguyen
 */
public class BLLHoaDon {
    public static int GetMaHDBySoHoaDon(String SoHoaDon){
        ResultSet rs = Other.DAOHoaDon.GetBySoHoaDon(SoHoaDon);
        try {
            if(rs.next()){
                return rs.getInt("MaHD");
            }
        } catch (SQLException ex) {
            ThongBao.ThongBao("Lỗi lấy mã hóa đơn từ số hóa đơn", "Thông báo lỗi");
        }
        return -1;
    }
    
    
}
