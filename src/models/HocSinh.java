package models;

public abstract class HocSinh extends Nguoi {
    protected String mshs;
    protected String tenLop;
    protected double gpa;
    protected String hanhKiem;
    protected boolean daDongHocPhi;

    public HocSinh(){
        super();
    }
    public HocSinh(String hoTen, String ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String mshs, String tenLop, double gpa, String hanhKiem, boolean daDongHocPhi){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.mshs = mshs;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.hanhKiem = hanhKiem;
        this.daDongHocPhi = daDongHocPhi;

    }

    public String getMshs(){
        return mshs;
    }
    public void setMshs(String mshs){
        this.mshs = mshs;
    }

    public String getTenLop(){
        return tenLop;
    }
    public void setTenLop(String tenLop){
        this.tenLop = tenLop;
    }
    
    public double getGpa(){
        return gpa;
    }
    public void setGpa(double gpa){
        this.gpa = gpa;
    }

    public String getHanhKiem(){
        return hanhKiem;
    }
    public void setHanhKiem(String hanhKiem){
        hanhKiem = hanhKiem;
    }

    public boolean isDaDongHocPhi(){
        return daDongHocPhi;
    }
    public void setDaDongHocPhi(boolean daDongHocPhi){
        this.daDongHocPhi = daDongHocPhi;
    }

    public abstract double tinhHocPhi();
    public abstract boolean xetHocBong();
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
                this.hanhKiem,
                trangThaiHocPhi
        );
    }
    public abstract String toFileString();
}


