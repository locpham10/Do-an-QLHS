package models;

public class LopHoc 
{
    private String tenLop;
    private String khoaHoc;
    private GiaoVienChuNhiem gvcn;
    private DanhSachHocSinh dsHocSinh;

    public LopHoc() {}

    public LopHoc(String tenLop, String khoaHoc, GiaoVienChuNhiem gvcn, DanhSachHocSinh dsHocSinh)
    {
        this.tenLop = tenLop;
        this.khoaHoc = khoaHoc;
        this.gvcn = gvcn;
        this.dsHocSinh = dsHocSinh;
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

    public GiaoVienChuNhiem getGvcn()
    {
        return gvcn;
    }
    public void setGvcn(GiaoVienChuNhiem gvcn)
    {
        this.gvcn = gvcn;
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
        String tenGV = (gvcn != null) ? gvcn.getHoTen() : "Chưa phân công.";
        return "Lớp: " + tenLop + "| Khóa: "+ khoaHoc + "| GVCN: " + tenGV;
    }
}