package models;

public class GiaoVienChuNhiem extends GiaoVien {
    private String lopChuNhiem;

    public GiaoVienChuNhiem (){
    }

    public GiaoVienChuNhiem (String hoTen, String ngaySinh, String gioiTinh, String diaChi, String soDienThoai, String maGV, String chucVu, double luong, String lopChuNhiem){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, maGV, chucVu, luong);
        this.lopChuNhiem = lopChuNhiem;
    }

    public String getLopChuNhiem(){
        return lopChuNhiem;
    }
    public void setLopChuNhiem(String lopChuNhiem){
        this.lopChuNhiem = lopChuNhiem;
    }
}