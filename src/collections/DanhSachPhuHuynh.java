package collections;

import java.util.Scanner
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

    private int timKiemViTri(String sdt){
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null && arrPH[i].getSdtKhanCap().equalsIgnoreCase(sdt)){
                return i;
            }
        }
        return -1;
    }

    public PhuHuynh timKiem(String sdt){
        int index = timKiemViTri(sdt);
        if (index != -1){
            return arrPH[index];
        }
        return null;
    }

    public PhuHuynh timtheoMSHS (String mshs){
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null && arrPH[i].getMshs() != null && arrPH[i].getMshs().equalsIgnoreCase(mshs)){
                return arrPH[i];
            }
            return null;
        }
    }

    public boolean xoa(String sdt){
        int index = timKiemViTri(sdt);
        if(index == -1){
            return false;
        }
        for(int i = 0; i < soLuong-1; i++){
            arrPH[i] = arrPH[i+1];
        }
        arrPH[soLuong-1] = null;
        soLuong--;
        return true;
    }

    public boolean sua(String sdt, Scanner sc){
        int index = timKiemViTri(sdt);
        if (index == -1){
            return false;
        }
        PhuHuynh ph = arrPH[index];
        System.out.println("Cập nhật thông tin cho phụ huynh có SĐT khẩn cấp: " + sdt);
        System.out.print("Nhập họ tên phụ huynh mới: ");
        ph.setHoTenBoMe(sc.nextLine());
        System.out.print("Nhập SĐT khẩn cấp mới: ");
        ph.setSdtKhanCap(sc.nextLine());
        System.out.println("Cập nhật thông tin phụ huynh thành công!");
        return true;
    }
    
    @Override
    public void hienThiDanhSach(){
        
    }
    
    @Override
    public void inTop5XuatSac() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inTop5XuatSac'");
    }

    @Override
    public void tinhTyLeDat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tinhTyLeDat'");
    }
}
