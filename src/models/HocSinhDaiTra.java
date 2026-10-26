package models;

public class HocSinhDaiTra extends HocSinh {

    public HocSinhDaiTra() {
        super();
    }

    public HocSinhDaiTra(String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai, 
                         String mshs, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi, 
                         PhuHuyh phuHuynh, DiemMonHoc[] danhSachDiem, int soLuongMon, HoatDong[] lichSuHoatDong) {
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, mshs, tenLop, gpa, diemHanhKiem, 
              daDongHocPhi, phuHuynh, danhSachDiem, soLuongMon, lichSuHoatDong);
    }

    @Override
    public double tinhHocPhi() {
        return 150000.0;
    }

    @Override
    public boolean xetHocBong() {
        return (this.gpa >= 8.0 && this.diemHanhKiem >= 80);
    }

    @Override
    public String toFileString() {
        return String.format("DAITRA,%s,%s,%s,%b,%s,%s,%.2f,%d,%b", 
                getMshs(), getHoTen(), getTenLop(), isGioiTinh(), getNgaySinh(), 
                getSoDienThoai(), getGpa(), getDiemHanhKiem(), isDaDongHocPhi());
    }
}