package models;

public class GiaoVien extends Nguoi{
    protected String maGV;
    protected String chucVu;
    protected double luong;
    protected MonHoc monChuyenMon;
    protected LopHoc[] dsLopDay; 
    
    public GiaoVien(String maGV, String hoTen, String ngaySinh, boolean gioiTinh, 
                    String diaChi, String soDienThoai, String chucVu, double luong) {
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.maGV = maGV;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    public GiaoVien(String maGV, String hoTen, String ngaySinh, boolean gioiTinh, String diaChi, String soDienThoai, String chucVu, double luong, MonHoc monChuyenMon, LopHoc[] dsLopDay){
        super(hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai);
        this.maGV = maGV;
        this.chucVu = chucVu;
        this.luong = luong;
        this.monChuyenMon = monChuyenMon;
        this.dsLopDay = dsLopDay;
    }


    public String toFileString() {
        String tenMon = (monChuyenMon != null) ? monChuyenMon.getMaMon() : "Khong";
        int soLop = (dsLopDay != null) ? dsLopDay.length : 0;
        String kq = String.format(java.util.Locale.US, "%s,%s,%s,%b,%s,%s,%s,%.0f,%s,%d",
            maGV, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, chucVu, luong, tenMon, soLop);
        if(soLop > 0 && dsLopDay != null){
            for (int i = 0; i < soLop; i++) {
               if(dsLopDay[i] != null){
                kq = kq + "," + dsLopDay[i].getTenLop();
               }
               else{
                kq = kq + ",Khong";
               } 
            }
        }
        return kq;
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
        System.out.println("THONG TIN GIAO VIEN");
        System.out.println("Ma giao vien: " + this.maGV);
        System.out.println("Ho ten: " + getHoTen());
        System.out.println("Chuc vu: " + this.chucVu);
        System.out.println("Luong: " + this.luong);
        System.out.println("Ngay sinh: " + getNgaySinh());
        System.out.println("Gioi tinh: " + (isGioiTinh() ? "Nam" : "Nu"));
        System.out.println("Dia chi: " + getDiaChi());
        System.out.println("So dien thoai: " + getSoDienThoai());
        System.out.println("Mon chuyen mon: " + (monChuyenMon != null ? monChuyenMon.getTenMon()+ " - Ma: " + monChuyenMon.getMaMon() : "Chua phan mon"));
        if (dsLopDay != null && dsLopDay.length > 0){
            System.out.println("Danh sach lop day");
            for (int i = 0; i < dsLopDay.length; i ++){
                if (dsLopDay[i] != null){
                    System.out.println("- Ten lop: " + dsLopDay[i].getTenLop() + " || Khoa: " + dsLopDay[i].getKhoaHoc());
                }
            }
        }
        else {
            System.out.println("Danh sach lop day: Chua co lop");
        }
    }
    
    public boolean coDayLop(String tenLop){
        if(dsLopDay == null || tenLop == null || tenLop.trim().isEmpty()){
            return false;
        }
        tenLop = tenLop.trim();
        for (int i = 0; i < dsLopDay.length; i++) {
            if(dsLopDay[i] != null && dsLopDay[i].getTenLop().equalsIgnoreCase(tenLop)){
                return true;
            }
        }
        return false;
    }
}
