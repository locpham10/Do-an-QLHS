package models;

public class HocSinhDaiTra extends HocSinh {

    public HocSinhDaiTra() {}

    public HocSinhDaiTra(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                         String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi) {
        this.mshs = mshs;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.diemHanhKiem = diemHanhKiem;
        this.daDongHocPhi = daDongHocPhi;
    }

    @Override
    public String toFileString() {
        return String.format("DaiTra,%s,%s,%s,%b,%s,%s,%s,%.1f,%d,%b",
            mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai,
            tenLop, gpa, diemHanhKiem, daDongHocPhi);
    }
}