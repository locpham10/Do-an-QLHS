package collections;

import java.util.Scanner;
import models.DiemMonHoc;
import models.HocSinh;
import models.MonHoc;
import utils.DataValidator;

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

    public HocSinh timKiem(HocSinh hs) {
        if (hs == null) 
            return null;
        return timKiem(hs.getMshs());
    }

    public HocSinh timKiem(String maHS)
    {
        if(maHS == null)
            return null;
        for (int i = 0; i < soLuong; i++) 
        {
            if(arrHS[i] != null && maHS.equalsIgnoreCase(arrHS[i].getMshs()))
                return arrHS[i];            
        }
        return null;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public HocSinh[] getArrHS() {
        return arrHS;
    }

    public void nhapDiemChoHocSinh(MonHoc[] dsMonHoc, int soLuongMon, Scanner sc)
    {
        System.out.println("\n CHUC NANG: NHAP DIEM CHO HOC SINH");
        System.out.print("Nhap Ma Hoc sinh can nhap diem: ");
        String maHS = sc.nextLine().trim();

        HocSinh hsTimThay = this.timKiem(maHS);
        if(hsTimThay == null)
        { 
            System.out.println("Khong tim thay hoc sinh co ma: " + maHS);
            return;
        }

        if(dsMonHoc == null || soLuongMon == 0)
        {
            System.out.println("Danh sach mon hoc cua truong chua co du lieu!");
            return;
        }

        System.out.println("Da tim thay: " + hsTimThay.getHoTen() + " - Ma: " + hsTimThay.getMshs());
        
        DiemMonHoc[] dsDiemMoi = new DiemMonHoc[soLuongMon];
        for (int i = 0; i < soLuongMon; i++) 
        {
            MonHoc mh = dsMonHoc[i];
            if(mh != null)
            {
                System.out.println("\nMon: " + mh.getTenMon() + "(He so: " + mh.getHeSo() + ")");
                double dTX = DataValidator.nhapDiem(sc, "   + Nhap diem TX: ");
                double dGK = DataValidator.nhapDiem(sc, "   + Nhap diem GK: ");
                double dCK = DataValidator.nhapDiem(sc, "   + Nhap diem CK: ");
                dsDiemMoi[i] = new DiemMonHoc(mh, dTX, dGK, dCK);
            }
        }

        hsTimThay.setDanhSachDiem(dsDiemMoi);
        hsTimThay.setSoLuongMon(soLuongMon);
        double gpaMoi = DiemMonHoc.tinhGPATongHop(dsDiemMoi);
        hsTimThay.setGpa(gpaMoi);
        System.out.println("\nDa cap nhat bang diem thanh cong!");
        System.out.println("GPA moi cua " + hsTimThay.getHoTen() + "la: " + gpaMoi);
    }
}
