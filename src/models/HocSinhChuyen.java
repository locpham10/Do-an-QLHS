package models;

public class HocSinhChuyen extends HocSinh { 

    private String monChuyen;
    private double diemMonChuyen;
    private double phuPhiChuyenDe;

    public HocSinhChuyen() {
        super();
    }

    public HocSinhChuyen(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi,
                         String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi,
                         PhuHuynh phuHuynh, DiemMonHoc[] danhSachDiem, int soLuongMon, HoatDong[] lichSuHoatDong,
                         String monChuyen, double diemMonChuyen, double phuPhiChuyenDe) {
        // Trong HocSinh.java, mshs nằm ở vị trí thứ 6 (sau soDienThoai)
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, mshs, tenLop, gpa, diemHanhKiem, daDongHocPhi, phuHuynh, danhSachDiem, soLuongMon, lichSuHoatDong);
        this.monChuyen = monChuyen;
        this.diemMonChuyen = diemMonChuyen;
        this.phuPhiChuyenDe = phuPhiChuyenDe;
    }

    @Override
    public double tinhHocPhi() {
        double hocPhiCoBan = 150000.0;
        return hocPhiCoBan + this.phuPhiChuyenDe;
    }

    @Override
    public boolean xetHocBong() {
        return ( this.gpa >= 8.5 && this.diemHanhKiem >= 80 && this.diemMonChuyen >= 8.0 );
    }

    public String getMonChuyen() {
        return monChuyen;
    }

    public void setMonChuyen(String monChuyen) {
        this.monChuyen = monChuyen;
    }

    public double getDiemMonChuyen() {
        return diemMonChuyen;
    }

    public void setDiemMonChuyen(double diemMonChuyen) {
        this.diemMonChuyen = diemMonChuyen;
    }

    public double getPhuPhiChuyenDe() {
        return phuPhiChuyenDe;
    }

    public void setPhuPhiChuyenDe(double phuPhiChuyenDe) {
        this.phuPhiChuyenDe = phuPhiChuyenDe;
    }

    @Override
    public String toFileString() {
        return String.format("Chuyen,%s,%s,%s,%b,%s,%s,%s,%.1f,%d,%b,%s,%.1f,%.1f",
            mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai,
            tenLop, gpa, diemHanhKiem, daDongHocPhi,
            monChuyen, diemMonChuyen, phuPhiChuyenDe);
    }
}