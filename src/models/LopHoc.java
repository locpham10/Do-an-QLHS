package models;

import collections.DanhSachHocSinh;

public class LopHoc
{
    private String tenLop;
    private String khoaHoc;
    private GiaoVien giaoVienChuNhiem;
    private DanhSachHocSinh dsHocSinh;
    boolean isLopChuyen;
    String monChuyenCuaLop;

    public LopHoc() {}

    public LopHoc(String tenLop, String khoaHoc, GiaoVien gvcn, DanhSachHocSinh dsHocSinh, boolean isLopChuyen, String monChuyenCuaLop)
    {
        this.tenLop = tenLop;
        this.khoaHoc = khoaHoc;
        this.giaoVienChuNhiem = gvcn;
        this.dsHocSinh = dsHocSinh;
        this.isLopChuyen = isLopChuyen;
        this.monChuyenCuaLop = monChuyenCuaLop;
    }

    public String getTenLop()
    {
        return tenLop;
    }
    public void setTenLop(String tenLop)
    {
        this.tenLop = tenLop;
    }

    public String getKhoaHoc()
    {
        return khoaHoc;
    }
    public void setKhoaHoc(String khoaHoc)
    {
        this.khoaHoc = khoaHoc;
    }

    public boolean isLopChuyen() { return isLopChuyen; }

    public String getMonChuyenCuaLop() { return monChuyenCuaLop; }

    public GiaoVien getGiaoVienChuNhiem()
    {
        return giaoVienChuNhiem;
    }
    public void setGiaoVienChuNhiem( GiaoVien giaoVienChuNhiem)
    {
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }

    public DanhSachHocSinh getDsHocSinh()
    {
        return dsHocSinh;
    }
    public void setDsHocSinh(DanhSachHocSinh dsHocSinh)
    {
        this.dsHocSinh = dsHocSinh;
    }

    @Override
    public String toString()
    {
        String tenGV = (giaoVienChuNhiem != null) ? giaoVienChuNhiem.getHoTen() : "Chưa phân công.";
        return "Lớp: " + tenLop + "| Khóa: "+ khoaHoc + "| GVCN: " + tenGV;
    }
}