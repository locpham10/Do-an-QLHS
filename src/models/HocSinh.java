package models;

public abstract class HocSinh extends Nguoi {
    // Các thuộc tính được để ở mức protected (#) để các lớp con (Đại trà, Chuyên) có thể sử dụng
    protected String mshs;
    protected String tenLop;
    protected double gpa;
    protected String diemHanhKiem; // Đã đổi thành diemHanhKiem
    protected boolean daDongHocPhi;

    public abstract double tinhHocPhi();

    public abstract boolean xetHocBong();

    public abstract String toFileString();

    // ==========================================
    // CÁC PHƯƠNG THỨC THỰC THI & GHI ĐÈ
    // ==========================================

    @Override
    public void hienThiThongTin() {
        String trangThaiHocPhi = "";
        if(this.daDongHocPhi)
            trangThaiHocPhi="Da dong hoc phi";
        else
            trangThaiHocPhi="Chua dong hoc phi";
        System.out.printf("| %-10s | %-20s | %-12s | %-10s | %-5.2f | %-10s | %-15s |\n",
                this.mshs,
                this.hoTen,
                this.ngaySinh,
                this.tenLop,
                this.gpa,
                this.diemHanhKiem,
                trangThaiHocPhi
        );
        }
    }