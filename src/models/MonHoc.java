public class MonHoc {
    private String maMon;
    private String tenMon;
    private int heSo;
    private boolean tinhVaoGPA;

    public MonHoc() {

    }

    public MonHoc(String maMon, String tenMon, int heSo, boolean tinhVaoGPA) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.heSo = heSo;
        this.tinhVaoGPA = tinhVaoGPA;
    }

    public String getMaMon() {
        return maMon;
    }
    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }
    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getHeSo() {
        return heSo;
    }
    public void setHeSo(int heSo) {
        this.heSo = heSo;
    }

    public boolean isTinhVaoGPA() {
        return tinhVaoGPA;
    }
    public void setTinhVaoGPA(boolean tinhVaoGPA) {
        this.tinhVaoGPA = tinhVaoGPA;
    }

    @Override
    public String toString() {
        return "Môn: " + tenMon + " (" + maMon + ") | Hệ số: " + heSo + " | Tính GPA: " + (tinhVaoGPA ? "Có" : "Không");
    }
}