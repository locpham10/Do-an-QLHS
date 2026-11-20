package collections;

import models.HoatDong;

import java.util.Arrays;

// Diagram: -danhSachHD: HoatDong[], -soLuong: int, +themHoatDong(hd), +hienThiDSHoatDong()
public class DanhSachHoatDong {
    private HoatDong[] danhSachHD;
    private int soLuong;

    public DanhSachHoatDong() {
        this.danhSachHD = new HoatDong[0];
        this.soLuong = 0;
    }

    // Tao danh sach tu lich su hoat dong co san cua mot hoc sinh
    public DanhSachHoatDong(HoatDong[] lichSu) {
        this();                          // goi constructor o tren: mang rong, soLuong = 0
        if (lichSu == null) return;
        for (int i = 0; i < lichSu.length; i++) {
            themHoatDong(lichSu[i]);
        }
    }

    public void themHoatDong(HoatDong hd) {
        if (hd == null) return;
        danhSachHD = Arrays.copyOf(danhSachHD, soLuong + 1);   // noi mang them 1 o
        danhSachHD[soLuong] = hd;
        soLuong++;
    }

    public void hienThiDSHoatDong() {
        if (soLuong == 0) {
            System.out.println("Chua co lich su hoat dong.");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print("  " + (i + 1) + ". ");
            danhSachHD[i].hienThiThongTin();
        }
    }

    public HoatDong[] getDanhSachHD() { return danhSachHD; }
    public int getSoLuong() { return soLuong; }
}