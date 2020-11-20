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

public class LoaiSanPham {

    private String maSP;
    private String tenLSP;
    private String tenSP;
    private double gia;
    private String kichThuoc;
    private String nguoiTaoSP;

    public LoaiSanPham() {
    }

    public LoaiSanPham(String maSP, String tenLSP, String tenSP, double gia, String kichThuoc,  String nguoiTaoSP) {
        this.maSP = maSP;
        this.tenLSP = tenLSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.kichThuoc = kichThuoc;
        this.nguoiTaoSP = nguoiTaoSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }

    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getMaNV() {
        return nguoiTaoSP;
    }

    public void setMaNV(String nguoiTaoSP) {
        this.nguoiTaoSP = nguoiTaoSP;
    }


}
