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
public class SanPhamDao {

   
    //câu lệnh thêm dữ liệu vào sql
    public void insert(SanPham model){ 
        String sql="INSERT INTO SanPham  VALUES ( ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getTenSP(),
                model.getSoLuong(),
                model.getNguoiTao(),
                model.getMoTa());
    }
    //câu lệnh cập nhập dữ liệu vào sql
     public void update(SanPham model){
         String sql="UPDATE SanPham SET TenLSP=?, SoLuong=?, NguoiTao=?, MoTa=? WHERE MaLoaiSP=?";
         JdbcHelper.executeUpdate(sql,
                 model.getTenSP(),
                 model.getSoLuong(),
                 model.getNguoiTao(),
                 model.getMoTa(),
                 model.getLoaiSP());
     } 
     //câu lệnh xóa dữ liệu  sql
      public void delete(String MaLoaiSP){
          String sql="DELETE FROM SanPham WHERE MaLoaiSP=?";
          JdbcHelper.executeUpdate(sql, MaLoaiSP);
      }
      
      public List<SanPham> select(){ 
          String sql="SELECT * FROM SanPham";
          return select(sql);
      }
public static ResultSet GetAll() {
        String sql = "Select * from SanPham order by TenLSP";
        return JdbcHelper.executeQuery(sql);
    }
      public void tim(){
          String sql="SELECT * FROM SanPham";
      };
      public SanPham findById(String loaisp){
          String sql="SELECT * FROM SanPham WHERE MaLoaiSP=?";
          List<SanPham> list = select(sql, loaisp);
          return list.size() > 0 ? list.get(0) : null;
      }  
      
       private List<SanPham> select(String sql, Object...args){
           List<SanPham> list=new ArrayList<>();  
       try {
         ResultSet rs = null;
       try {
         rs = JdbcHelper.executeQuery(sql, args);
         while(rs.next()){ 
             SanPham model=readFromResultSet(rs);
             list.add(model);
         }
       }
       finally{
           rs.getStatement().getConnection().close();
         }
       } 
       catch (SQLException ex) {
           throw new RuntimeException(ex);
        }
       return list;         
  }
           public static ResultSet Search(String MaLoai,String keyword ){
        String sql="SELECT * FROM SanPham where "
                + "(MaSP  like N'%"  + keyword 
                + "%' or TenSP like N'%" + keyword + "%' or '' = '" + keyword 
                + "') and (MaLoaiSP  = " + MaLoai + " or 0 = " + MaLoai + ")";        
        return JdbcHelper.executeQuery(sql);
    }
      private SanPham readFromResultSet(ResultSet rs) throws SQLException{ 
          SanPham model=new SanPham();
          model.setLoaiSP(rs.getString("MaLoaiSP"));
          model.setTenSP(rs.getString("TenLSP"));
          model.setSoLuong(rs.getInt("SoLuong"));
          model.setNguoiTao(rs.getString("NguoiTao"));
          model.setMoTa(rs.getString("MoTa"));
          return model; 
      }

}
