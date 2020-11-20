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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class NhanVien implements Serializable{
     private String maNV; 
     private String matKhau;
     private String hoTen;
     private boolean vaiTro = false;
     private Date Ngaysinh;
    private boolean  Gioitinh;
    private String Dienthoai;
    private String Gmail;
    private String Diachi;
    private String hinh;

   


     
@Override 
   public String toString() { 
       return this.hoTen; 
  
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

 public void setNgaysinh(Date Ngaysinh) {
        this.Ngaysinh = Ngaysinh;
    }

   

    public void setGioitinh(boolean Gioitinh) {
        this.Gioitinh = Gioitinh;
    }

    public void setDienthoai(String Dienthoai) {
        this.Dienthoai = Dienthoai;
    }

    public void setGmail(String Gmail) {
        this.Gmail = Gmail;
    }

    public void setDiachi(String Diachi) {
        this.Diachi = Diachi;
    }

    public Date getNgaysinh() {
        return Ngaysinh;
    }

     public boolean isGioitinh() {
        return Gioitinh;
    }

    public String getDienthoai() {
        return Dienthoai;
    }

    public String getGmail() {
        return Gmail;
    }

    public String getDiachi() {
        return Diachi;
    }
       public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
