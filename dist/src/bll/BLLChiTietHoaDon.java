/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import Other.DAOChiTietHoaDon;
import Other.ChiTietHoaDon;

/**
 *
 * @author Tuan Nguyen
 */
public class BLLChiTietHoaDon {
    public static int Them(ChiTietHoaDon cthd){
        int kq = DAOChiTietHoaDon.Them(cthd);
        
        if(kq > 0){
            
        }
        
        return kq;
    }
}
