package models;

import collections.DanhSachHocSinh;

public class LopHoc
{
    private String tenLop;
    private String khoaHoc;
    private GiaoVien giaoVienChuNhiem;
    private DanhSachHocSinh dsHocSinh;
    boolean isLopChuyen;
    private MonHoc[] chuongTrinhHoc;
    String monChuyenCuaLop;
    double phuPhiChung;

    public LopHoc() {}

    public LopHoc(String tenLop, String khoaHoc, GiaoVien gvcn, DanhSachHocSinh dsHocSinh,
                  boolean isLopChuyen, String monChuyenCuaLop, double phuPhiChung) {
        this.tenLop = tenLop;
        this.khoaHoc = khoaHoc;
        this.giaoVienChuNhiem = gvcn;
        this.dsHocSinh = dsHocSinh;
        this.isLopChuyen = isLopChuyen;
        this.monChuyenCuaLop = monChuyenCuaLop;
        this.phuPhiChung = phuPhiChung;
    }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }

    public String getKhoaHoc() { return khoaHoc; }
    public void setKhoaHoc(String khoaHoc) { this.khoaHoc = khoaHoc; }

    public boolean isLopChuyen() { return isLopChuyen; }

    public MonHoc[] getChuongTrinhHoc() { return chuongTrinhHoc; }

    public void setChuongTrinhHoc(MonHoc[] chuongTrinhHoc) {
        this.chuongTrinhHoc = chuongTrinhHoc;
    }

    public String getMonChuyenCuaLop() { return monChuyenCuaLop; }

    public double getPhuPhiChung() { return phuPhiChung; }

    public GiaoVien getGiaoVienChuNhiem() { return giaoVienChuNhiem; }
    public void setGiaoVienChuNhiem( GiaoVien giaoVienChuNhiem) { this.giaoVienChuNhiem = giaoVienChuNhiem; }

    public DanhSachHocSinh getDsHocSinh() { return dsHocSinh; }
    public void setDsHocSinh(DanhSachHocSinh dsHocSinh) { this.dsHocSinh = dsHocSinh; }

    @Override
    public String toString() {
        String tenGV = (giaoVienChuNhiem != null) ? giaoVienChuNhiem.getHoTen() : "Chưa phân công.";
        return "Lớp: " + tenLop + "| Khóa: "+ khoaHoc + "| GVCN: " + tenGV;
    }

    public String toFileString() {
        String maGV = (giaoVienChuNhiem != null && giaoVienChuNhiem.getMaGV() != null)
                ? giaoVienChuNhiem.getMaGV() : "";

        String monChuyenGhi = "";
        if (isLopChuyen && monChuyenCuaLop != null) {
            monChuyenGhi = monChuyenCuaLop;
            if (chuongTrinhHoc != null) {
                for (MonHoc mh : chuongTrinhHoc) {
                    if (mh != null && mh.getTenMon() != null
                            && mh.getTenMon().equalsIgnoreCase(monChuyenCuaLop)) {
                        monChuyenGhi = mh.getMaMon();
                        break;
                    }
                }
            }
        }

        StringBuilder ct = new StringBuilder();
        if (chuongTrinhHoc != null) {
            for (MonHoc mh : chuongTrinhHoc) {
                if (mh == null) continue;
                if (ct.length() > 0) ct.append(";");
                ct.append(mh.getMaMon()).append(":").append(mh.getHeSo());
            }
        }

        return tenLop + "," + khoaHoc + "," + maGV + "," + isLopChuyen + ","
                + monChuyenGhi + "," + phuPhiChung + "," + ct;
    }
}