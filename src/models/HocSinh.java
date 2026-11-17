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

    // Hằng số tĩnh theo yêu cầu Li5
    public static final double HOC_PHI_CO_BAN = 150000.0;
    protected static int tongSoHocSinh = 0;

    public HocSinh() {
        super();
        tongSoHocSinh++;
    }

    // Constructor đầy đủ
    public HocSinh(String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai, 
                   String mshs, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi, 
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
        tongSoHocSinh++;
    }

    // Constructor rút gọn (overload) để docFile gọi không bị lỗi thiếu tham số
    public HocSinh(String mshs, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, 
                   String soDienThoai, String tenLop, double gpa, int diemHanhKiem, boolean daDongHocPhi) {
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.mshs = mshs;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.diemHanhKiem = diemHanhKiem;
        this.daDongHocPhi = daDongHocPhi;
        this.phuHuynh = null;
        this.danhSachDiem = null;
        this.soLuongMon = 0;
        this.lichSuHoatDong = new HoatDong[100];
        tongSoHocSinh++;
    }

    public static int getTongSoHocSinh() {
        return tongSoHocSinh;
    }

    public String xepLoaiHocLuc() {
        if (gpa >= 8.0) return "Giỏi";
        if (gpa >= 6.5) return "Khá";
        if (gpa >= 5.0) return "Trung bình";
        return "Yếu";
    }

    // Getters & Setters cơ bản
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
        super.hienThiThongTin();
        System.out.println("MSHS: " + mshs + " | Lớp: " + tenLop + " | GPA: " + String.format("%.2f", gpa) + " | Hạnh kiểm: " + diemHanhKiem);
        System.out.println("Đã đóng học phí: " + (daDongHocPhi ? "Rồi" : "Chưa"));
    }

    public void ghiNhanHoatDong(HoatDong hd) {
        if (lichSuHoatDong == null) {
            lichSuHoatDong = new HoatDong[100];
        }
        for (int i = 0; i < lichSuHoatDong.length; i++) {
            if (lichSuHoatDong[i] == null) {
                lichSuHoatDong[i] = hd;
                // Cộng trừ điểm hạnh kiểm dựa trên hoạt động (giới hạn 0 - 100)
                this.diemHanhKiem += hd.getDiemCongTru();
                if (this.diemHanhKiem > 100) this.diemHanhKiem = 100;
                if (this.diemHanhKiem < 0) this.diemHanhKiem = 0;
                break;
            }
        }
    }

    public String xepLoaiHanhKiem() {
        if (diemHanhKiem >= 80) return "Tốt";
        else if (diemHanhKiem >= 65) return "Khá";
        else if (diemHanhKiem >= 50) return "Trung bình";
        else return "Yếu";
    }
}