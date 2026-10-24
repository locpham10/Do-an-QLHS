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

    // KHO MÔN HỌC CỦA TRƯỜNG (Để hiển thị menu cho người dùng chọn môn cho lớp)
    public static final MonHoc[] KHO_MON_HOC = {
            new MonHoc("TOAN", "Toán", 1, true),
            new MonHoc("VAN", "Ngữ Văn", 1, true),
            new MonHoc("ANH", "Tiếng Anh", 1, true),
            new MonHoc("LY", "Vật Lý", 1, true),
            new MonHoc("HOA", "Hóa Học", 1, true),
            new MonHoc("SINH", "Sinh Học", 1, true),
            new MonHoc("SU", "Lịch Sử", 1, true),
            new MonHoc("DIA", "Địa Lý", 1, true),
            new MonHoc("TD", "Thể Dục", 1, false)
    };

    public String getMaMon() 
    {
        return maMon;
    }
    public void setMaMon(String maMon) 
    {
        this.maMon = maMon;
    }

    public String getTenMon() 
    {
        return tenMon;
    }
    public void setTenMon(String tenMon) 
    {
        this.tenMon = tenMon;
    }

    public int getHeSo() 
    {
        return heSo;
    }
    public void setHeSo(int heSo) 
    {
        this.heSo = heSo;
    }

    public boolean isTinhVaoGPA() 
    {
        return tinhVaoGPA;
    }
    public void setTinhVaoGPA(boolean tinhVaoGPA) 
    {
        this.tinhVaoGPA = tinhVaoGPA;
    }

    public

    @Override
    public String toString() 
    {
        return "Môn: " + tenMon + " (" + maMon + ") | Hệ số: " + heSo + " | Tính GPA: " + (tinhVaoGPA ? "Có" : "Không");
    }
}