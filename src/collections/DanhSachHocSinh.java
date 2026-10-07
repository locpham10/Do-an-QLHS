package collections;

import models.HocSinh;

public class DanhSachHocSinh {
    private HocSinh[] arrHS = new HocSinh[1000];
    private int soLuong=0;
    
    public void them(HocSinh hs){
        if(soLuong<1000){
            arrHS[soLuong]= hs;
            soLuong++;
        }
    }

    public void xoa(HocSinh hs){

    }

    public void sua(HocSinh hs){

    }
}
