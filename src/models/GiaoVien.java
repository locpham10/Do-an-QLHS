package models;

public class GiaoVien extends Nguoi{
    protected String maGV;
    protected String chucVu;
    protected double luong;
    
    public GiaoVien (){
    }

    public GiaoVien(String hoTen, String ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String maGV, String chucVu, double luong){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.maGV = maGV;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    public String getMaGV (){
        return maGV;
    }
    public void setMaGV(String maGV){
        this.maGV = maGV;
    }

    public String getChucVu (){
        return chucVu;
    }
    public void setChucVu (String chucVu){
        this.chucVu = chucVu;
    }

    public double getLuong (){
        return luong;
    }
    public void setLuong(double luong){
        this.luong = luong;
    }

    @Override 
    public void hienThiThongTin(){
        System.out.println("THÔNG TIN GIÁO VIÊN");
        System.out.println("Mã giáo viên: " + this.maGV);
        System.out.println("Họ tên: " + getHoTen());
        System.out.println("Chức vụ: " + this.chucVu);
        System.out.println("Lương: " + this.luong);
        System.out.println("Ngày sinh: " + getNgaySinh());
        System.out.println("Giới tính: " + getGioiTinh());
        System.out.println("Địa chỉ: " + getDiaChi());
        System.out.println("Số điện thoại: " + getSoDienThoai());
    }
}