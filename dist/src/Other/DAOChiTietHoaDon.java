/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import Other.ChiTietHoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan Nguyen
 */
public class DAOChiTietHoaDon {
    public static int Them(ChiTietHoaDon cthd){
        String sql = "INSERT INTO [dbo].[ChiTietHoaDon]  " +
"           ([MaHD]  " +
"           ,[MaSP]  " +
"           ,[SoLuong]  " +
"           ,[ThanhTien]  " +
"           ,[KichThuoc])  " +
"     VALUES  " +
"           (" + cthd.getMaHD() + 
"           ," + cthd.getMaSP()+ 
"           ," + cthd.getSoLuong() + 
"           ," + cthd.getThanhTien() + 
"           ,N'" + cthd.getKichThuoc()+ "')";
        
        System.out.println(sql);
        return JdbcHelper.executeUpdate(sql);
    }
    public List<ChiTietHoaDon> select() {
        String sql = "SELECT * FROM ChiTietHoaDon";
        return select(sql);
    }
    private List<ChiTietHoaDon> select(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietHoaDon model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {

                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
     private ChiTietHoaDon readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietHoaDon model = new ChiTietHoaDon();
        model.setMaChiTietHD(rs.getInt("MaChiTietHD"));
        model.setMaHD(rs.getInt("MaHD"));
        model.setMaSP(rs.getInt("MaSP"));
        model.setSoLuong(rs.getInt("Soluong"));
        model.setThanhTien(rs.getDouble("ThanhTien"));
        model.setKichThuoc(rs.getString("KichThuoc"));
        return model;
    }
}

