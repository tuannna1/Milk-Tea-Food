/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.util.Date;

public class KhachHang {
    private String MaKH;
    private String HotenKH;
    private Date Ngaysinh;
    private boolean  Gioitinh;
    private String Dienthoai;
    private String Gmail;
    private String Ghichu;
    private String MaNV;
    private Date NgayDK = DateHelper.now();
    
      @Override 
      public String toString() {
          return this.HotenKH; 
      } 

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getHotenKH() {
        return HotenKH;
    }

    public void setHotenKH(String HotenKH) {
        this.HotenKH = HotenKH;
    }

    public Date getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(Date Ngaysinh) {
        this.Ngaysinh = Ngaysinh;
    }

    public boolean isGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(boolean Gioitinh) {
        this.Gioitinh = Gioitinh;
    }

    public String getDienthoai() {
        return Dienthoai;
    }

    public void setDienthoai(String Dienthoai) {
        this.Dienthoai = Dienthoai;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String Gmail) {
        this.Gmail = Gmail;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String Ghichu) {
        this.Ghichu = Ghichu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayDK() {
        return NgayDK;
    }

    public void setNgayDK(Date NgayDK) {
        this.NgayDK = NgayDK;
    }

   

  
 
 
}