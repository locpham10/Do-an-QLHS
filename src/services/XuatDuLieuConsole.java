package services;

import collections.DanhSachHocSinh;
import models.HocSinh;
import utils.Color;

public class XuatDuLieuConsole implements IXuatDuLieu {
    public static void inDuongKe() {
        System.out.println("+------------+----------------------+--------------+------------+-------+------------+-----------------+");
    }

    public static void inTieuDe() {
        inDuongKe();
        System.out.printf(Color.YELLOW + "| %-10s | %-20s | %-12s | %-10s | %-5s | %-10s | %-15s |\n" + Color.RESET,
                "MSHS", "Họ và tên", "Ngày sinh", "Lớp", "GPA", "Hạnh kiểm", "Học phí");
        inDuongKe();
    }

    @Override
    public void xuatBaoCao(DanhSachHocSinh ds){
        int soLuong=ds.getSoLuong();
        HocSinh[] arr = ds.getArrHS();

        if(soLuong==0){
            System.out.println(Color.RED + "Danh sach hien dang trong!" + Color.RESET);
            return;
        }
        inTieuDe();
        for(int i=0; i<soLuong; i++){
            if(arr[i] != null){
                arr[i].hienThiThongTin();
            }
        }
        inDuongKe();
        System.out.println(Color.GREEN + "Tong so hoc sinh: " + Color.RESET);
    }

}
