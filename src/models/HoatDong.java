package models;

public class HoatDong {
    private String tenHoatDong;
    private int diemCongTru;
    private String ngayGhiNhan;

    public HoatDong() {
    }

    public HoatDong(String tenHoatDong, int diemCongTru, String ngayGhiNhan) {
        this.tenHoatDong = tenHoatDong;
        this.diemCongTru = diemCongTru;
        this.ngayGhiNhan = ngayGhiNhan;
    }

    public String getTenHoatDong() { return tenHoatDong; }
    public void setTenHoatDong(String tenHoatDong) { this.tenHoatDong = tenHoatDong; }
    public int getDiemCongTru() { return diemCongTru; }
    public void setDiemCongTru(int diemCongTru) { this.diemCongTru = diemCongTru; }
    public String getNgayGhiNhan() { return ngayGhiNhan; }
    public void setNgayGhiNhan(String ngayGhiNhan) { this.ngayGhiNhan = ngayGhiNhan; }

    public void hienThiThongTin() {
        System.out.println("Hoạt động: " + tenHoatDong + " | Điểm cộng/trừ: " + diemCongTru + " | Ngày: " + ngayGhiNhan);
    }

    public String toFileString() {
        return String.format("%s,%d,%s", tenHoatDong, diemCongTru, ngayGhiNhan);
    }
}