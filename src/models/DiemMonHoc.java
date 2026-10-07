package models;

public class DiemMonHoc 
{
    private MonHoc monHoc;
    private double diemTX;
    private double diemGK;
    private double diemCK;

    public DiemMonHoc() {}

    public DiemMonHoc(MonHoc monHoc, double diemTX, double diemGK, double diemCK) 
    {
        this.monHoc = monHoc;
        this.diemTX = diemTX;
        this.diemGK = diemGK;
        this.diemCK = diemCK;
    }

    public double tinhDiemTBMon() 
    {
        return (this.diemTX*1.0 + this.diemGK * 2.0 + this.diemCK * 3.0) / 6.0;
    }

    public MonHoc getMonHoc() 
    {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) 
    {
        this.monHoc = monHoc;
    }

    public double getDiemTX() 
    {
        return diemTX;
    }

    public void setDiemTX(double diemTX) 
    {
        this.diemTX = diemTX;
    }

    public double getDiemGK() 
    {
        return diemGK;
    }

    public void setDiemGK(double diemGK) 
    {
        this.diemGK = diemGK;
    }

    public double getDiemCK() 
    {
        return diemCK;
    }

    public void setDiemCK(double diemCK) 
    {
        this.diemCK = diemCK;
    }

    @Override
    public String toString() 
    {
        String tenMonHoc = (monHoc != null) ? monHoc.getTenMon() : "Chưa xác định.";
        return String.format("%s | TX: %.1f | GK: %.1f | CK: %.1f => ĐTB: %.2f", tenMonHoc, diemTX, diemGK, diemCK, tinhDiemTBMon());
    }
}