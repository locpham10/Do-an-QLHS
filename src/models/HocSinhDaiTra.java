package models;

public class HocSinhDaiTra extends HocSinh {

    public HocSinhDaiTra() {
        super();
    }

    // Constructor đầy đủ (đã đưa mshs lên đầu)
    public HocSinhDaiTra(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                         String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi, 
                         PhuHuynh phuHuynh, DiemMonHoc[] danhSachDiem, int soLuongMon, HoatDong[] lichSuHoatDong) {
        super(mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, tenLop, gpa, diemHanhKiem, 
              daDongHocPhi, phuHuynh, danhSachDiem, soLuongMon, lichSuHoatDong);
    }

    // Constructor rút gọn phục vụ đọc file
    public HocSinhDaiTra(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                         String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi) {
        super(mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, tenLop, gpa, diemHanhKiem, daDongHocPhi);
    }

    @Override
    public double tinhHocPhi() {
        return HocSinh.HOC_PHI_CO_BAN;
    }

    @Override
    public boolean xetHocBong() {
        return (this.gpa >= 8.0 && this.diemHanhKiem >= 80);
    }

    @Override
    public String toFileString() {
        return String.format(java.util.Locale.US,"DAITRA,%s,%s,%s,%b,%s,%s,%s,%.2f,%d,%b",
                getMshs(), getHoTen(), getNgaySinh(), isGioiTinh(), getDiaChi(), 
                getSoDienThoai(), getTenLop(), getGpa(), getDiemHanhKiem(), isDaDongHocPhi());
    }
}