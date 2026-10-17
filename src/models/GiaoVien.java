package models;

public class GiaoVien extends Nguoi{
    protected String maGV;
    protected String chucVu;
    protected double luong;
    protected MonHoc monChuyenMon;
    protected LopHoc[] dsLopDay; 
    
    public GiaoVien (){
        super();
    }

    public GiaoVien(String maGV, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai, String chucVu, double luong, MonHoc monChuyenmon, LopHoc[] dsLopDay){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.maGV = maGV;
        this.chucVu = chucVu;
        this.luong = luong;
        this.monChuyenMon = monChuyenMon;
        this.dsLopDay = dsLopDay;
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

    public MonHoc getMonChuyenMon (){
        return monChuyenMon;
    }
    public void setMonChuyenMon (MonHoc monChuyenMon){
        this.monChuyenMon = monChuyenMon;
    }

    public LopHoc[] getDsLopDay(){
        return dsLopDay;
    }
    public void setDsLopDay(LopHoc[] dsLopDay){
        this.dsLopDay = dsLopDay;
    }

    @Override 
    public void hienThiThongTin(){
        System.out.println("THÔNG TIN GIÁO VIÊN");
        System.out.println("Mã giáo viên: " + this.maGV);
        System.out.println("Họ tên: " + getHoTen());
        System.out.println("Chức vụ: " + this.chucVu);
        System.out.println("Lương: " + this.luong);
        System.out.println("Ngày sinh: " + getNgaySinh());
        System.out.println("Giới tính: " + (isGioiTinh() ? "Nam" : "Nữ"));
        System.out.println("Địa chỉ: " + getDiaChi());
        System.out.println("Số điện thoại: " + getSoDienThoai());
        System.out.println("Môn chuyên môn: " + monChuyenMon.getTenMon() + " - Mã: " + monChuyenMon.getMaMon());
        if (dsLopDay != null && dsLopDay.length > 0){
            System.out.println("Danh sách lớp dạy");
            for (int i = 0; i < dsLopDay.length; i ++){
                if (dsLopDay[i] != null){
                    System.out.println("- Tên lớp: " + dsLopDay[i].getTenLop() + " || Khóa: " + dsLopDay[i].getKhoaHoc());
                }
            }
        }
        else {
            System.out.println("Danh sách lớp dạy: Chưa có lớp");
        }
    }
}
