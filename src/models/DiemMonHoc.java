package models;

public class DiemMonHoc 
{
    private MonHoc monHoc;
    private double diemTX;
    private double diemGK;
    private double diemCK;

    public DiemMonHoc() {}

    public DiemMonHoc(MonHoc monHoc, double diemTX, double diemGK, double diemCK) {
        this.monHoc = monHoc;
        this.diemTX = diemTX;
        this.diemGK = diemGK;
        this.diemCK = diemCK;
    }

    //Cong thuc 1: Tinh diem Trung binh mon hoc theo he so (TX:1) (GK:2) (CK:3)
    // Cong thuc: DTB\_Mon = (DTX * 1 + DGK * 2 + DCK * 3) / 6
    public double tinhDiemTBMon() {
        double dtb = (this.diemTX*1.0 + this.diemGK * 2.0 + this.diemCK * 3.0) / 6.0;
        return Math.round(dtb * 100.0) / 100.0;
    }

    //Cong thuc 2: Tinh GPA Tong hop Toan bo Mon hoc (Trung Binh Hoc Ki)
    // Cong thuc: GPA = Tong(DTB_Mon_i * HeSo_i) / Tong(HeSo_i) 

    public static double tinhGPATongHop(DiemMonHoc[] dsDiem) {
        if(dsDiem == null || dsDiem.length == 0)
            return 0.0;

        double tongDiemNhanHeSo = 0.0;
        double tongHeSo = 0.0;

        for(DiemMonHoc diem : dsDiem)
        {
            if(diem != null && diem.getMonHoc() != null)
            {
                MonHoc mh = diem.getMonHoc();
                //Buoc 1: Loc mon hoc co tinh vao GPA
                if(mh.isTinhVaoGPA())
                {
                    double diemTBM = diem.tinhDiemTBMon(); //Lay diem TBM
                    double heSo = mh.getHeSo(); //Lay he so mon

                    //Buoc 2: Tich luy Diem nhan he so va Tong he so
                    tongDiemNhanHeSo += diemTBM * heSo;
                    tongHeSo += heSo;
                }
            }
        }
        if(tongHeSo == 0) return 0.0;

        //Buoc 3: Chia lay trung binh & Lam tron 2 chu so sau dau phay
        double gpa = tongDiemNhanHeSo / tongHeSo;
        return Math.round(gpa * 100.0) / 100.0;
    }

    public MonHoc getMonHoc() { return monHoc; }
    public void setMonHoc(MonHoc monHoc) { this.monHoc = monHoc; }

    public double getDiemTX() { return diemTX; }
    public void setDiemTX(double diemTX)  { this.diemTX = diemTX; }

    public double getDiemGK() { return diemGK; }
    public void setDiemGK(double diemGK) { this.diemGK = diemGK; }

    public double getDiemCK() { return diemCK; }
    public void setDiemCK(double diemCK) { this.diemCK = diemCK; }

    @Override
    public String toString()  {
        String tenMonHoc = (monHoc != null) ? monHoc.getTenMon() : "Chua xac dinh.";
        return String.format("%s | TX: %.1f | GK: %.1f | CK: %.1f => DTB: %.2f", tenMonHoc, diemTX, diemGK, diemCK, tinhDiemTBMon());
    }

    public String toFileString() {
        String ma = (monHoc != null && monHoc.getMaMon() != null) ? monHoc.getMaMon() : "";
        return ma + "," + diemTX + "," + diemGK + "," + diemCK;
    }

    public static DiemMonHoc tuChuoi(String dong) {
        if(dong == null || dong.trim().isEmpty())
            throw new IllegalArgumentException("Dong diem rong!");

        String[] p = dong.split(",", -1);
        if(p.length != 4 && p.length != 5)
            throw new IllegalArgumentException("Dong diem phai co 4 hoac 5 cot: " + dong);
        int dau = (p.length == 5) ? 1 : 0; //5 cot thi bo cot MSHS

        String maMon = p[dau].trim().toUpperCase();
        MonHoc mh = null;
        for(MonHoc m : MonHoc.KHO_MON_HOC) {
            if(m.getMaMon().equals(maMon)) { 
                mh = m; 
                break; 
            }
        }
        if(mh == null) throw new IllegalArgumentException("Khong ton tai mon co ma '" + maMon + "'.");
        
        double tx, gk, ck;
        try {
            tx = Double.parseDouble(p[dau + 1].trim());
            gk = Double.parseDouble(p[dau + 2].trim());
            ck = Double.parseDouble(p[dau + 3].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Diem khong phai so hoac khong dung dinh dang: " + dong);
        }
        if (tx < 0 || tx > 10 || gk < 0 || gk > 10 || ck < 0 || ck > 10)
            throw new IllegalArgumentException("Diem phai nam trong [0,10]: " + dong);

        return new DiemMonHoc(mh, tx, gk, ck);
    }
}