package models;

import collections.DanhSachHocSinh;

public class LopHoc
{
    private String tenLop;
    private String khoaHoc;
    private GiaoVien giaoVienChuNhiem;
    private DanhSachHocSinh dsHocSinh;
    private boolean isLopChuyen;
    private MonHoc[] chuongTrinhHoc;
    private String monChuyenCuaLop;
    private double phuPhiChung;

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

    //THOI KHOA BIEU
    public static final int SO_NGAY = 6;
    public static final int SO_TIET = 5;

    // lichHoc[ngay][tiet]: ngay 0 = thu 2, tiet 0 = tiet 1; null = o trong
    private TietHoc[][] lichHoc = new TietHoc[SO_NGAY][SO_TIET];

    public TietHoc[][] getLichHoc() { return lichHoc; }

    // thu: 2..7, tiet: 1..5
    public static boolean hopLeViTri(int thu, int tiet) {
        return thu >= 2 && thu <= SO_NGAY + 1 && tiet >= 1 && tiet <= SO_TIET;
    }

    private static void kiemTraViTri(int thu, int tiet) {
        if (!hopLeViTri(thu, tiet))
            throw new IllegalArgumentException("Thu phai tu 2 den " + (SO_NGAY + 1)
                    + ", tiet phai tu 1 den " + SO_TIET);
    }

    public TietHoc getTiet(int thu, int tiet) {
        kiemTraViTri(thu, tiet);
        return lichHoc[thu - 2][tiet - 1];
    }

    public boolean thuocChuongTrinh(MonHoc mon) {
        if (mon == null || chuongTrinhHoc == null) return false;
        for (MonHoc mh : chuongTrinhHoc)
            if (mh != null && mh.getMaMon().equalsIgnoreCase(mon.getMaMon()))
                return true;
        return false;
    }

    public void xepTiet(int thu, int tiet, MonHoc mon, GiaoVien gv) {
        kiemTraViTri(thu, tiet);
        if (mon == null) throw new IllegalArgumentException("Chua chon mon hoc");
        if (gv == null) throw new IllegalArgumentException("Chua chon giao vien");

        TietHoc daCo = lichHoc[thu - 2][tiet - 1];
        if (daCo != null)
            throw new IllegalArgumentException("Thu " + thu + " tiet " + tiet + " cua lop " + tenLop
                    + " da co tiet: " + daCo + ". Hay xoa tiet cu truoc.");

        if (!thuocChuongTrinh(mon))
            throw new IllegalArgumentException("Mon " + mon.getTenMon()
                    + " khong thuoc chuong trinh hoc cua lop " + tenLop);

        MonHoc monGV = gv.getMonChuyenMon();
        if (monGV == null || !monGV.getMaMon().equalsIgnoreCase(mon.getMaMon()))
            throw new IllegalArgumentException("Giao vien " + gv.getHoTen() + " ("
                    + gv.getMaGV() + ") khong day mon " + mon.getTenMon());

        lichHoc[thu - 2][tiet - 1] = new TietHoc(mon, gv);
    }

    public boolean xoaTiet(int thu, int tiet) {
        kiemTraViTri(thu, tiet);
        if (lichHoc[thu - 2][tiet - 1] == null) return false;
        lichHoc[thu - 2][tiet - 1] = null;
        return true;
    }

    public void xoaTatCaTiet() {
        lichHoc = new TietHoc[SO_NGAY][SO_TIET];
    }

    public int demTiet() {
        int dem = 0;
        for (int n = 0; n < SO_NGAY; n++)
            for (int t = 0; t < SO_TIET; t++)
                if (lichHoc[n][t] != null) dem++;
        return dem;
    }

    public void inThoiKhoaBieu() {
        String vien = "+-------";
        for (int n = 0; n < SO_NGAY; n++) vien += "+------------";
        vien += "+";

        System.out.println("\nTHOI KHOA BIEU LOP " + tenLop);
        System.out.println(vien);
        String dong = String.format("| %-5s ", "Tiet");
        for (int n = 0; n < SO_NGAY; n++) dong += String.format("| %-10s ", "Thu " + (n + 2));
        System.out.println(dong + "|");
        System.out.println(vien);
        for (int t = 0; t < SO_TIET; t++) {
            dong = String.format("| %-5d ", t + 1);
            for (int n = 0; n < SO_NGAY; n++) {
                TietHoc th = lichHoc[n][t];
                String o = (th == null) ? "-" : th.getMon().getMaMon() + "-" + th.getGv().getMaGV();
                dong += String.format("| %-10s ", o);
            }
            System.out.println(dong + "|");
        }
        System.out.println(vien);
        System.out.println("Tong so tiet da xep: " + demTiet() + "/" + (SO_NGAY * SO_TIET));
    }

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