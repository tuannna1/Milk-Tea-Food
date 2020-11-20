/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

/**
 *
 * @author Anh Tuấn
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiSanPhamDao {

    public void insertSP(LoaiSanPham model) {
        String sql = "INSERT INTO LoaiSanPham (TenLSP, TenSP,Gia, KichThuoc, MaNV) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getTenLSP(),
                model.getTenSP(),
                model.getGia(),
                model.getKichThuoc(),
                model.getMaNV()
        );
    }

    public void updateSP(LoaiSanPham model) {
        String sql = "UPDATE LoaiSanPham SET TenLSP=?, TenSP=?, Gia=?, KichThuoc=?, MaNV=? WHERE MaSP=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenLSP(),
                model.getTenSP(),
                model.getGia(),
                model.getKichThuoc(),
                model.getMaNV(),
                model.getMaSP()
        );
    }

    public void deleteSP(String MaSP) {
        String sql = "DELETE FROM LoaiSanPham WHERE MaSP=?";
        JdbcHelper.executeUpdate(sql, MaSP);
    }

    public List<LoaiSanPham> select() {
        String sql = "SELECT * FROM LoaiSanPham";
        return select(sql);
    }

    public static ResultSet GetAll() {
        String sql = "SELECT * FROM LoaiSanPham";
        return JdbcHelper.executeQuery(sql);
    }

    public static ResultSet Search(String MaLoai, String keyword) {
        System.out.println(MaLoai);
        if (MaLoai.equals("Tất cả")) {
            MaLoai = "";
        }
        String sql = "SELECT * FROM LoaiSanPham where (TenLSP  like N'%" + MaLoai + "%' and TenSP like N'%" + keyword + "%' ) ";
        if (MaLoai.equalsIgnoreCase("0")) {
            sql = "SELECT * FROM LoaiSanPham where TenSP like N'%" + keyword + "%' ";
        }
        System.out.println(sql);
        return JdbcHelper.executeQuery(sql);
    }

    public LoaiSanPham findByIdSP(Integer masp) {
        String sql = "SELECT * FROM LoaiSanPham WHERE MaSP=?";
        List<LoaiSanPham> list = select(sql, masp);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<LoaiSanPham> select(String sql, Object... args) {
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    LoaiSanPham model = readFromResultSetSP(rs);
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

    private LoaiSanPham readFromResultSetSP(ResultSet rs) throws SQLException {
        LoaiSanPham model = new LoaiSanPham();
        model.setMaSP(rs.getString("MaSP"));
        model.setTenLSP(rs.getString("TenLSP"));
        model.setTenSP(rs.getString("TenSP"));
        model.setGia(rs.getDouble("Gia"));
        model.setKichThuoc(rs.getString("KichThuoc"));
        model.setMaNV(rs.getString("MaNV"));

        return model;
    }

    public List<LoaiSanPham> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM LoaiSanPham WHERE TenSP LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

}
