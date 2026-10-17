package models;

public abstract class Nguoi{
    protected String hoTen;
    protected String ngaySinh;
    protected boolean gioiTinh;
    protected String diaChi;
    protected String soDienThoai;

    public Nguoi(){
    }

    public Nguoi(String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai){
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen (){
        return hoTen;
    } 
    public void setHoTen (String hoTen){
        this.hoTen = hoTen;
    }

    public String getNgaySinh (){
        return ngaySinh;
    } 
    public void setNgaySinh (String ngaySinh){
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh (){
        return gioiTinh;
    } 
    public void setGioiTinh (boolean gioiTinh){
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi (){
        return diaChi;
    } 
    public void setDiaChi (String diaChi){
        this.diaChi = diaChi;
    }

    public String getSoDienThoai (){
        return soDienThoai;
    } 
    public void setSoDienThoai (String soDienThoai){
        this.soDienThoai = soDienThoai;
    }

    public abstract void hienThiThongTin();
}
