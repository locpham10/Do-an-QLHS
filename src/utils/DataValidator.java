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
    public static String nhapNgaySinh(Scanner sc, String thongBao) {
        String ngaySinh = "";
        while (true) {
            System.out.print(thongBao);
            ngaySinh=sc.nextLine().trim();
            if(ngaySinh.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$")) {
                break;
            }
            System.out.println(Color.RED + "Lỗi: Ngày sinh phải theo chuẩn dd/MM/yyyy (VD: 01/05/2007)!" + Color.RESET);
        }
        return ngaySinh;
    }
    public static boolean kiemTraTonTaiMa(HocSinh hs, DanhSachHocSinh ds) throws TrungMaHocSinhException {
        if (ds.timKiem(hs) != null) {
            throw new TrungMaHocSinhException("Học sinh này đã tồn tại");
        }
        return false; //Ko trùng mã
    }
}