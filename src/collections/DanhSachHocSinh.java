package collections;

import models.HocSinh;

public class DanhSachHocSinh {
    private HocSinh[] arrHS = new HocSinh[1000];
    private int soLuong=0;
    
    public boolean them(HocSinh hs){
        if(soLuong<1000){
            arrHS[soLuong]= hs;
            soLuong++;
            return true;
        }
        return false;
    }

    public void hienThiDanhSach(){

    }

    public void xoa(HocSinh hs){

    }

    public void sua(HocSinh hs){

    }

    public HocSinh timKiem(String mshs) {

        return null;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public HocSinh[] getArrHS() {
        return arrHS;
    }


}
