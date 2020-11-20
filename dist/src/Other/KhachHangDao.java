/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anh Tuấn
 */
public class KhachHangDao {
    public static void insert(KhachHang model){ 
          String sql="INSERT INTO KhachHang (HoTenKH,  NgaySinh, GioiTinh,  DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?)";
          JdbcHelper.executeUpdate(sql,
                  model.getHotenKH(),   
                  model.getNgaysinh(),
                  model.isGioitinh(),
                  model.getDienthoai(),
                  model.getGmail(),
                  model.getGhichu(),
                  model.getMaNV());
      } 
      public void update(KhachHang model){
           String sql="UPDATE KhachHang SET HoTenKH=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaKH=?";
     
           JdbcHelper.executeUpdate(sql,
               model.getHotenKH(),
               model.getNgaysinh(),
               model.isGioitinh(),
               model.getDienthoai(),
               model.getGmail(), 
               model.getGhichu(), 
               model.getMaNV(),
               model.getMaKH());
   } 
      
     public void delete(String id){
     String sql="DELETE FROM KhachHang WHERE MaKH=?";
     JdbcHelper.executeUpdate(sql, id);   
}    
     public List<KhachHang> select(){
         String sql="SELECT * FROM KhachHang"; 
         return select(sql);
}   
     public List<KhachHang> selectByKeyword(String keyword){
     String sql="SELECT * FROM KhachHang WHERE HoTenKH LIKE ?";
     return select(sql,"%"+keyword+"%");
}
    public static ResultSet GetAll() {
        String sql = "Select * from KhachHang order by HoTenKH";
        return JdbcHelper.executeQuery(sql);
    }
    
    public static  ResultSet GetByMaKH(String MaKH){
        String sql = "Select * from KhachHang where MaKH = " + MaKH;
        return JdbcHelper.executeQuery(sql);
    }
    
    public static  ResultSet GetByKeyword(String keyword){
        String sql = "Select * from KhachHang where MaKH like '%" + keyword + "%'  or " 
                + " HoTenKH like N'%" + keyword + "%'";
        return JdbcHelper.executeQuery(sql);
    }

     public KhachHang findById(String manh){
     String sql="SELECT * FROM KhachHang WHERE MaKH=?";
     List<KhachHang> list = select(sql, manh);
     return list.size() > 0 ? list.get(0) : null;
}         
     private List<KhachHang> select(String sql, Object...args){
     List<KhachHang> list=new ArrayList<>();
       try { 
       ResultSet rs = null; 
       try {
     rs = JdbcHelper.executeQuery(sql, args);
      while(rs.next()){
      KhachHang model=readFromResultSet(rs);
      list.add(model); 
          } 
     }
     finally{ 
       rs.getStatement().getConnection().close();
          }
       
       }catch (SQLException ex) {
           throw new RuntimeException(ex);
       } 
       return list;
     } 
     
      private KhachHang readFromResultSet(ResultSet rs) throws SQLException{ 
      KhachHang model=new KhachHang();
      model.setMaKH(rs.getString("MaKH"));
      model.setHotenKH(rs.getString("HoTenKH"));
      model.setNgaysinh(rs.getDate("NgaySinh"));
      model.setGioitinh(rs.getBoolean("GioiTinh"));
      model.setDienthoai(rs.getString("DienThoai"));
      model.setGmail(rs.getString("Email")); 
      model.setGhichu(rs.getString("GhiChu"));
      model.setMaNV(rs.getString("MaNV")); 
      model.setNgayDK(rs.getDate("NgayDK"));
      
      return model;     }
}
