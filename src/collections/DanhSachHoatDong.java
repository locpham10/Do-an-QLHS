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

    // Tạo danh sách từ lịch sử hoạt động có sẵn của một học sinh
    public DanhSachHoatDong(HoatDong[] lichSu) {
        this();                          // gọi constructor ở trên: mảng rỗng, soLuong = 0
        if (lichSu == null) return;
        for (int i = 0; i < lichSu.length; i++) {
            themHoatDong(lichSu[i]);
        }
    }

    public void themHoatDong(HoatDong hd) {
        if (hd == null) return;
        danhSachHD = Arrays.copyOf(danhSachHD, soLuong + 1);   // nới mảng thêm 1 ô
        danhSachHD[soLuong] = hd;
        soLuong++;
    }

    public void hienThiDSHoatDong() {
        if (soLuong == 0) {
            System.out.println("Chưa có lịch sử hoạt động.");
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