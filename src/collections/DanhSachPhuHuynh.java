package collections;

import interfaces.IThongKe;
import models.PhuHuynh;

public class DanhSachPhuHuynh implements IThongKe {
    private PhuHuynh[] arrPH;
    private int soLuong;

    public DanhSachPhuHuynh(){
        this.arrPH = new PhuHuynh[100];
        this.soLuong = 0;
    }

    public DanhSachPhuHuynh(int dungluong){
        this.arrPH = new PhuHuynh[dungluong];
        this.soLuong = 0;
    }

    public PhuHuynh[] getarrPH(){
        return arrPH;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public boolean them(PhuHuynh ph){
        if(ph != null && soLuong < arrPH.length){
            arrPH[soLuong] = ph;
            soLuong ++;
            return true; //them thanh cong
        }
        return false; // them that bai (day/null)
    } 

    @Override
    public void hienThiDanhSach() { 
        // Mai viết code của Mai nha
    }

    @Override
    public void inBaoCaoThongKe() { 
        // Mai viết code của Mai nha
    }
}
