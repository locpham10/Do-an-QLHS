package utils;

import collections.DanhSachHocSinh;
import exceptions.TrungMaHocSinhException;
import models.HocSinh;

import java.util.Scanner;

public class DataValidator {
    public static double nhapSoThuc (Scanner sc, String thongBao) {
        double so=0;
        boolean hopLe=false;
        while (!hopLe) {
            try{
                System.out.print(thongBao);
                so = Double.parseDouble(sc.nextLine().trim());
                hopLe = true;
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Lỗi: Không đúng định dạng số, vui lòng nhập lại!"  + Color.RESET);
            }
        }
        return so;
    }
    public static double nhapDiem(Scanner sc, String thongBao) {
        double diem = -1;
        while (true) {
            diem = nhapSoThuc(sc, thongBao);
            if (diem >= 0.0 && diem <= 10.0) {
                break;
            }
            System.out.println(Color.RED + "Lỗi: Điểm phải nằm trong đoạn từ 0 đến 10!" + Color.RESET);
        }
        return diem;
    }
    public static boolean ngaySinhHopLe(String s) {
        return s != null && s.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$");
    }

    public static String nhapNgaySinh(Scanner sc, String thongBao) {
        String ngaySinh = "";
        while (true) {
            System.out.print(thongBao);
            ngaySinh = sc.nextLine().trim();
            if (ngaySinhHopLe(ngaySinh)) {
                break;
            }
            System.out.println(Color.RED + "Lỗi: Ngày sinh phải theo chuẩn dd/MM/yyyy (VD: 01/05/2007)!" + Color.RESET);
        }
        return ngaySinh;
    }
    public static boolean kiemTraTonTaiMa(String mshs, DanhSachHocSinh ds) throws TrungMaHocSinhException {
        if (ds.timKiem(mshs) != null) {
            throw new TrungMaHocSinhException("Loi: MSHS nay da ton tai!");
        }
        return false; //Ko trùng mã
    }
    public static String nhapChuoiKhongRong(Scanner sc, String thongBao){
        String chuoi="";
        while(true){
            System.out.println(thongBao);
            chuoi=sc.nextLine().trim();
            if(chuoi.isEmpty()){
                System.out.println(Color.RED + "Loi: Khong duoc de trong, vui long nhap lai!" + Color.RESET);
            } else if(chuoi.contains(",")){
                System.out.println(Color.RED + "Loi: Chuoi khong duoc chua dau phay (,), vui long nhap lai!" + Color.RESET);
            } else {
                break;
            }
        }
        return chuoi;
    }
    public static int nhapSoNguyen(Scanner sc, String thongBao){

    }
}