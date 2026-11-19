package models;

import java.util.Arrays;

public class DanhSachHoatDong {
    private HoatDong[] danhSachHD = new HoatDong[0];

    public DanhSachHoatDong() {
    }

    public void themHoatDong(HoatDong hd) {
        int n = danhSachHD.length;
        danhSachHD = Arrays.copyOf(danhSachHD, n + 1);
        danhSachHD[n] = hd;
    }

    public void hienThiDSHoatDong() {
        if (danhSachHD.length == 0) {
            System.out.println("Chưa có lịch sử hoạt động nào.");
            return;
        }
        for (int i = 0; i < danhSachHD.length; i++) {
            System.out.print("  - ");
            danhSachHD[i].hienThiThongTin();
        }
    }

    public HoatDong[] getDanhSachHD() { return danhSachHD; }
    public int getSoLuong() { return danhSachHD.length; }
}