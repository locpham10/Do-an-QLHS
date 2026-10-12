package models;

public abstract class HocSinh extends Nguoi {
    protected String mshs;
    protected String tenLop;
    protected double gpa;
    protected String HanhKiem;
    protected boolean daDongHocPhi;

    public HocSinh(){
        super();
    }
    public HocSinh(String hoten, String ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String mssh, String tenLop, double gpa, String HanhKiem, boolean daoDongHocPhi){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.mshs = mshs;
        this.tenLop = tenLop;
        this.gpa = gpa;
        this.HanhKiem = HanhKiem;
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
        return HanhKiem;
    }
    public void setHanhKiem(String hanhKiem){
        HanhKiem = hanhKiem;
    }

    public boolean isDaDongHocPhi(){
        return daDongHocPhi;
    }
    public void setDaDongHocPhi(boolean daDongHocPhi){
        this.daDongHocPhi = daDongHocPhi;
    }

    public abstract double tinhHocPhi();
    public abstract boolean xetHocBong();
    public void hienthiThongTin(){
        system.out.printf("| %-10s | %-20s | %-12s| %-10s | %-5.2f | %-5d | %-15s |\n",
        this.mshs,
        this.hoTen,
        this.ngaySinh,
        this.gioiTinh,
        this.tenLop,
        this.gpa,
        this.HanhKiem,
        );
        if(daDongHocPhi){
            system.out.printf("Da dong hoc phi");
        else
            system.out.printf("Chua dong hoc phi");
             

    }
    public abstract String toFileString();


}

