package models;

import collections.DanhSachHoatDong;

public abstract class HocSinh extends Nguoi {
    protected String mshs;
    protected String tenLop;
    protected double gpa;
    protected int diemHanhKiem;
    protected boolean daDongHocPhi;
    protected PhuHuynh phuHuynh;
    protected DiemMonHoc[] danhSachDiem;
    protected int soLuongMon;
    protected HoatDong[] lichSuHoatDong;

    // Hang so tinh theo yeu cau Li5
    public static final double HOC_PHI_CO_BAN = 150000.0;

    public HocSinh() {
        super();
    }

    // Constructor day du
    public HocSinh(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi,
                   String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi,
                   PhuHuynh phuHuynh, DiemMonHoc[] danhSachDiem, int soLuongMon, HoatDong[] lichSuHoatDong){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.mshs = mshs;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.diemHanhKiem = diemHanhKiem;
        this.daDongHocPhi = daDongHocPhi;
        this.phuHuynh = phuHuynh;
        this.danhSachDiem = danhSachDiem;
        this.soLuongMon = soLuongMon;
        this.lichSuHoatDong = lichSuHoatDong;
    }

    // Constructor rut gon (overload) de docFile goi khong bi loi thieu tham so
    public HocSinh(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                   String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi) {
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.mshs = mshs;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.diemHanhKiem = diemHanhKiem;
        this.daDongHocPhi = daDongHocPhi;
        this.phuHuynh = null;
        this.danhSachDiem = new DiemMonHoc[0];
        this.soLuongMon = 0;
        this.lichSuHoatDong = new HoatDong[0];
    }

    public String xepLoaiHocLuc() {
        if (gpa >= 8.0) return "Gioi";
        if (gpa >= 6.5) return "Kha";
        if (gpa >= 5.0) return "Trung binh";
        return "Yeu";
    }

    // Getters & Setters co ban
    public String getMshs() { return mshs; }
    public void setMshs(String mshs) { this.mshs = mshs; }
    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public int getDiemHanhKiem() { return diemHanhKiem; }
    public void setDiemHanhKiem(int diemHanhKiem) { this.diemHanhKiem = diemHanhKiem; }
    public boolean isDaDongHocPhi() { return daDongHocPhi; }
    public void setDaDongHocPhi(boolean daDongHocPhi) { this.daDongHocPhi = daDongHocPhi; }
    public PhuHuynh getPhuHuynh() { return phuHuynh; }
    public void setPhuHuynh(PhuHuynh phuHuynh) { this.phuHuynh = phuHuynh; }
    public DiemMonHoc[] getDanhSachDiem() { return danhSachDiem; }
    public void setDanhSachDiem(DiemMonHoc[] danhSachDiem) { this.danhSachDiem = danhSachDiem; }
    public int getSoLuongMon() { return soLuongMon; }
    public void setSoLuongMon(int soLuongMon) { this.soLuongMon = soLuongMon; }
    public HoatDong[] getLichSuHoatDong() { return lichSuHoatDong; }
    public void setLichSuHoatDong(HoatDong[] lichSuHoatDong) { this.lichSuHoatDong = lichSuHoatDong; }

    public abstract double tinhHocPhi();
    public abstract boolean xetHocBong();
    public abstract String toFileString();

    @Override
    public void hienThiThongTin() {
        System.out.println("Ho ten: " + hoTen + " | Ngay sinh: " + ngaySinh + " | SDT: " + soDienThoai);
        System.out.println("MSHS: " + mshs + " | Lop: " + tenLop + " | GPA: " + String.format("%.2f", gpa)
                + " (" + xepLoaiHocLuc() + ") | Hanh kiem: " + diemHanhKiem + " (" + xepLoaiHanhKiem() + ")");
        System.out.println("Da dong hoc phi: " + (daDongHocPhi ? "Roi" : "Chua"));
    }

    public void ghiNhanHoatDong(HoatDong hd) {
        if (hd == null) return;
        if (lichSuHoatDong == null) lichSuHoatDong = new HoatDong[0];
        lichSuHoatDong = java.util.Arrays.copyOf(lichSuHoatDong, lichSuHoatDong.length + 1);
        lichSuHoatDong[lichSuHoatDong.length - 1] = hd;
        // cong / tru diem hanh kiem, giu trong khoang 0 - 100
        diemHanhKiem += hd.getDiemCongTru();
        if (diemHanhKiem > 100) diemHanhKiem = 100;
        if (diemHanhKiem < 0) diemHanhKiem = 0;
    }

    public void hienThiLichSuHoatDong() {
        System.out.println("\n--- LICH SU HOAT DONG ---");
        new DanhSachHoatDong(lichSuHoatDong).hienThiDSHoatDong();
    }

    public String xepLoaiHanhKiem() {
        if (diemHanhKiem >= 80) return "Tot";
        else if (diemHanhKiem >= 65) return "Kha";
        else if (diemHanhKiem >= 50) return "Trung binh";
        else return "Yeu";
    }

    public void capNhatDongHocPhi(boolean daDong) {
        this.daDongHocPhi = daDong;
    }
}