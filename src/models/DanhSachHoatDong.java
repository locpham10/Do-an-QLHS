package models;

public class DanhSachHoatDong {
    private HoatDong[] dsHoatDong = new HoatDong[100];
    private int soLuong = 0;

    public DanhSachHoatDong() {
    }

    public void themHoatDong(HoatDong hd) {
        if (soLuong < dsHoatDong.length) {
            dsHoatDong[soLuong] = hd;
            soLuong++;
        } else {
            System.out.println("Danh sách hoạt động đã đầy!");
        }
    }

    public void hienThiDSHoatDong() {
        if (soLuong == 0) {
            System.out.println("Chưa có lịch sử hoạt động nào.");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            System.out.print("  - ");
            dsHoatDong[i].hienThiThongTin();
        }
    }

    public HoatDong[] getDsHoatDong() { return dsHoatDong; }
    public int getSoLuong() { return soLuong; }
}