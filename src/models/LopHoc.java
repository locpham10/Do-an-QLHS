public class LopHoc 
{
    private String tenLop;
    private GiaoVienChuNhiem gvcn;
    private DanhSachHocSinh dsHocSinh;

    public LopHoc() {}

    public LopHoc(String tenLop, GiaoVienChuNhiem gvcn, DanhSachHocSinh dsHocSinh)
    {
        this.tenLop = tenLop;
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
        return "Lớp: " + tenLop + "| GVCN: " + tenGV;
    }
}