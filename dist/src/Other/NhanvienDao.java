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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanvienDao {
     public void insert(NhanVien model){ 
         String sql="INSERT INTO NhanVien ( MatKhau, HoTen, VaiTro, NgaySinh, GioiTinh, DienThoai, Email, ĐiaChi, Hinh) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     JdbcHelper.executeUpdate(sql,
             model.getMatKhau(),
             model.getHoTen(),
             model.isVaiTro(),
             model.getNgaysinh(),
             model.isGioitinh(),
             model.getDienthoai(),
             model.getGmail(), 
             model.getDiachi(),
             model.getHinh());
}    
     public void update(NhanVien model){ 
String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, ĐiaChi=?, Hinh=?  WHERE MaNV=?"; 
    JdbcHelper.executeUpdate(sql,
            model.getMatKhau(),
            model.getHoTen(),
            model.isVaiTro(),
            model.getNgaysinh(),
            model.isGioitinh(),
            model.getDienthoai(),
            model.getGmail(), 
            model.getDiachi(),
            model.getHinh(),
            model.getMaNV()); 
    
}
     public void update1(Doipass model){ 
String sql="UPDATE NhanVien SET MatKhau=? WHERE MaNV=?"; 
    JdbcHelper.executeUpdate(sql,
            model.getMatkhau(),
            model.getMaNV()); 
    
}
    public void delete(String MaNV){ 
     String sql="DELETE FROM NhanVien WHERE MaNV=?";  
   JdbcHelper.executeUpdate(sql, MaNV);   
}   
    public List<NhanVien> select(){ 
        String sql="SELECT * FROM NhanVien"; 
        return select(sql);
    } 
         public List<NhanVien> selectByKeyword(String keyword){
     String sql="SELECT * FROM NhanVien WHERE HoTen LIKE ?";
     return select(sql,"%"+keyword+"%");
}
    public NhanVien findById(String manv){
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv); 
        return list.size() > 0 ? list.get(0) : null;
    }
    
    private List<NhanVien> select(String sql, Object...args){ 
        List<NhanVien> list=new ArrayList<>(); 
        try { 
            ResultSet rs = null;
            try {
                System.out.println(args);
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    NhanVien model=readFromResultSet(rs);
                    list.add(model); 
                }
            }  
            finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }         return list;
    }
    private NhanVien readFromResultSet(ResultSet rs) throws SQLException{
        NhanVien model=new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        model.setNgaysinh(rs.getDate("NgaySinh"));
        model.setGioitinh(rs.getBoolean("GioiTinh"));
        model.setDienthoai(rs.getString("DienThoai"));
        model.setGmail(rs.getString("Email")); 
        model.setDiachi(rs.getString("ĐiaChi"));
        model.setHinh(rs.getString("hinh"));
        return model;
    }  


}
