package models;

public class TietHoc {
    private MonHoc mon;
    private GiaoVien gv;

    public TietHoc() {}

    public TietHoc(MonHoc mon, GiaoVien gv) {
        this.mon = mon;
        this.gv = gv;
    }

    public MonHoc getMon() { return mon; }

    public GiaoVien getGv() { return gv; }

    @Override
    public String toString() {
        String tenMon = (mon != null) ? mon.getTenMon() : "Chưa xác định";
        String tenGV = (gv != null) ? gv.getHoTen() + " (" + gv.getMaGV() + ")" : "Chưa phân công";
        return tenMon + " - " + tenGV;
    }
}
