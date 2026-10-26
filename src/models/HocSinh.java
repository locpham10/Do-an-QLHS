package models;

import models.HoatDong;

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

    public HocSinh() {
        super();
    }

    public HocSinh(String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai, 
                   String mshs, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi, 
                   PhuHuynh phuhuynh, DiemMonHoc[] danhSachDiem, HoatDong[] lichSuHoatDong)
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

    public String getMshs() {
        return mshs;
    }

    public void setMshs(String mshs) {
        this.mshs = mshs;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getDiemHanhKiem() {
        return diemHanhKiem;
    }

    public void setDiemHanhKiem(int diemHanhKiem) {
        this.diemHanhKiem = diemHanhKiem;
    }

    public boolean isDaDongHocPhi() {
        return daDongHocPhi;
    }

    public void setDaDongHocPhi(boolean daDongHocPhi) {
        this.daDongHocPhi = daDongHocPhi;
    }

    public PhuHuynh getPhuHuynh() {
        return phuHuynh;
    }

    public void setPhuHuynh(PhuHuynh phuHuynh) {
        this.phuHuynh = phuHuynh;
    }

    public DiemMonHoc[] getDanhSachDiem() {
        return danhSachDiem;
    }

    public void setDanhSachDiem(DiemMonHoc[] danhSachDiem) {
        this.danhSachDiem = danhSachDiem;
    }

    public int getSoLuongMon() {
        return soLuongMon;
    }

    public void setSoLuongMon(int soLuongMon) {
        this.soLuongMon = soLuongMon;
    }

    public HoatDong[] getLichSuHoatDong() {
        return lichSuHoatDong;
    }

    public void setLichSuHoatDong(HoatDong[] lichSuHoatDong) {
        this.lichSuHoatDong = lichSuHoatDong;
    }

    public abstract double tinhHocPhi();

    public abstract boolean xetHocBong();

    public abstract String toFileString();

    @Override
    public void hienThiThongTin() {

        System.out.printf("Đã đóng học phí: " + (daDongHocPhi ? "Rồi" : "Chưa"));
    }

    public void ghiNhanHoatDong(HoatDong hd){}

    public String xepLoaiHanhKiem() {
        if (diemHanhKiem >= 80) {
            return "Tốt";
        } else if (diemHanhKiem >= 65) {
            return "Khá";
        } else if (diemHanhKiem >= 50) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}