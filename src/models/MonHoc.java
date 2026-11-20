package models;

public class MonHoc 
{
    private String maMon;
    private String tenMon;
    private int heSo;
    private boolean tinhVaoGPA;

    public MonHoc() {}

    public MonHoc(String maMon, String tenMon, int heSo, boolean tinhVaoGPA) 
    {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.heSo = heSo;
        this.tinhVaoGPA = tinhVaoGPA;
    }

    public MonHoc copyVoiHeSo(int heSoMoi) {
        return new MonHoc(this.maMon, this.tenMon, heSoMoi, this.tinhVaoGPA);
    }

    // KHO MON HOC CUA TRUONG (De hien thi menu cho nguoi dung chon mon cho lop)
    public static final MonHoc[] KHO_MON_HOC = {
            new MonHoc("TOAN", "Toan", 1, true),
            new MonHoc("VAN", "Ngu Van", 1, true),
            new MonHoc("ANH", "Tieng Anh", 1, true),
            new MonHoc("LY", "Vat Ly", 1, true),
            new MonHoc("HOA", "Hoa Hoc", 1, true),
            new MonHoc("SINH", "Sinh Hoc", 1, true),
            new MonHoc("SU", "Lich Su", 1, true),
            new MonHoc("DIA", "Dia Ly", 1, true),
            new MonHoc("TD", "The Duc", 1, false)
    };

    public static MonHoc timTheoMa(String maMon) {
        if(maMon == null) return null;
        for(MonHoc m : KHO_MON_HOC)
            if(m.getMaMon().equalsIgnoreCase(maMon.trim()))
                return m;
        return null;
    }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public int getHeSo() { return heSo; }
    public void setHeSo(int heSo) { this.heSo = heSo; }

    public boolean isTinhVaoGPA() { return tinhVaoGPA; }
    public void setTinhVaoGPA(boolean tinhVaoGPA)  { this.tinhVaoGPA = tinhVaoGPA; }

    @Override
    public String toString() {
        return "Mon: " + tenMon + " (" + maMon + ") | He so: " + heSo + " | Tinh GPA: " + (tinhVaoGPA ? "Co" : "Khong");
    }
}