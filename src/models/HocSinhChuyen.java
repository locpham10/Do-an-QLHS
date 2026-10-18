package models;

public class HocSinhChuyen {




    @Override
    public String toFileString(){
        return String.format("Chuyen,%s,%s,%s,%b,%s,%s,%s,%.1f,%d,%b,%s,%.1f,%.1f",
            mshs, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai,
            tenLop, gpa, diemHanhKiem, daDongHocPhi,
            monChuyen, diemMonChuyen, phuPhiChuyenDe);
    }
}
