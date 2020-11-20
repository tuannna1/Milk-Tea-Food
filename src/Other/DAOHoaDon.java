/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import bll.ChuyenDoi;
import Other.HoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan Nguyen
 */
public class DAOHoaDon {

    public static ResultSet CountSoHoaDon(String SoHoaDon) {
        String sql = "select Count(*) from hoadon where SoHoaDon like N'%" + SoHoaDon + "%'";
        return JdbcHelper.executeQuery(sql);
    }

    //7 Hàm lấy theo SoHoaDon
    public static ResultSet GetBySoHoaDon(String SoHoaDon) {
        String sql = "select * from hoadon where SoHoaDon = N'" + SoHoaDon + "'";
        return JdbcHelper.executeQuery(sql);
    }

    public static int ThemHoaDon(HoaDon hd) {
        String sql = "set dateformat dmy INSERT INTO [dbo].[HoaDon]  "
                + "           ([SoHoaDon]  "
                + "           ,[NgayTaoHD]  "
                + "           ,[MaNV]  "
                + "           ,[MaKH]  "
                + "           ,[TongTien]  "
                + "           ,[GhiChu])  "
                + "     VALUES  "
                + "           ('" + hd.getSoHoaDon() + "'  "
                + "           ,'" + ChuyenDoi.DinhDangNgay(hd.getNgayTao()) + "'  "
                + "           ," + hd.getMaNV() + "  "
                + "           ," + hd.getMaKH() + "  "
                + "           ," + hd.getTongTien() + "  "
                + "           ,N'" + hd.getGhiChu() + "')";

        System.out.println(sql);
        return JdbcHelper.executeUpdate(sql);
    }

    public List<HoaDon> select() {
        String sql = "SELECT * FROM HoaDon";
        return select(sql);
    }

    public List<HoaDon> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM HoaDon WHERE MaNV LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public List<HoaDon> selectByCbo(String keyword) {
        String sql = "SELECT * FROM HoaDon WHERE SoHoaDon LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

    public List<HoaDon> selectByDate(String day) {
        String sql = "SELECT * FROM HoaDon WHERE NgayTaoHD LIKE ?";
        return select(sql, "%" + day + "%");
    }

    public List<HoaDon> selectByMONTH(String month, String year) {
        String sql = "SELECT * FROM HoaDon WHERE YEAR(NgayTaoHD) LIKE ? AND MONTH(NgayTaoHD) LIKE ?";
        String sql2 = "AND MONTH(NgayTaoHD) LIKE ";
        return select(sql, "%" + year + "%", "%" + month + "%");
    }
    
    public List<HoaDon> selectByYear(String year) {
        String sql = "SELECT * FROM HoaDon WHERE YEAR(NgayTaoHD) LIKE ?";
        return select(sql, "%" + year + "%");
    }

    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDon model = readFromResultSet(rs);
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

    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon model = new HoaDon();
        model.setMaHD(rs.getInt("MaHD"));
        model.setSoHoaDon(rs.getString("SoHoaDon"));
        model.setNgayTao(rs.getDate("NgayTaoHD"));
        model.setMaNV(rs.getInt("MaNV"));
        model.setMaKH(rs.getInt("MaKH"));
        model.setTongTien(rs.getDouble("TongTien"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
