package models;

public class HocSinhChuyen extends HocSinh { 

    private String monChuyen;
    private double diemMonChuyen;
    private double phuPhiChuyenDe;

    public HocSinhChuyen() {}

    public HocSinhChuyen(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                         String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi,
                         String monChuyen, double diemMonChuyen, double phuPhiChuyenDe) {
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
        this.monChuyen = monChuyen;
        this.diemMonChuyen = diemMonChuyen;
        this.phuPhiChuyenDe = phuPhiChuyenDe;
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