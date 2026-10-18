package models;

public class HocSinhDaiTra extends HocSinh{
    



    @Override
    public String toFileString(){
        return String.format("DaiTra,%s,%s,%s,%b,%s,%s,%s,%.1f,%d,%b",
            mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai,
            tenLop, gpa, diemHanhKiem, daDongHocPhi);
    }
}
