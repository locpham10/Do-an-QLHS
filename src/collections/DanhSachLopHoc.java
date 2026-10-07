package collections;

import models.LopHoc;

public class DanhSachLopHoc
{
    private LopHoc[] dsLop;
    private int soLuong;

    public DanhSachLopHoc()
    {
        this.dsLop = new LopHoc[100];
        this.soLuong = 0;
    }

    public DanhSachLopHoc(int dungLuong)
    {
        this.dsLop = new LopHoc[dungLuong];
        this.soLuong = 0;
    }

    public boolean themLopHoc(LopHoc lh)
    {
        if(lh != null && soLuong < dsLop.length)
        {
            this.dsLop[soLuong] = lh;
            soLuong++;
            return true; //Thêm lớp thành công!!
        }
        return false; //Thêm lớp thất bại vì đối tượng null/mảng đã đầy.
    }

    public LopHoc timKiemLopTheoTen(String tenLop)
    {
        for(int i = 0; i < soLuong; i++)
            if(dsLop[i] != null && dsLop[i].getTenLop().equalsIgnoreCase(tenLop))
                return dsLop[i];
            return null;
    }

    public void hienThiDanhSachLop()
    {
        if(soLuong == 0)
        {
            System.out.println("Danh sách lớp học đang trống.");
            return;
        }

        System.out.println("| DANH SÁCH CÁC LỚP HỌC |");
        for(int i = 0; i < soLuong; i++)
            System.out.println((i+1) + ". " + dsLop[i]);
    }

    public LopHoc[] getDsLop()
    {
        return dsLop;
    }
    public void setDsLop(LopHoc[] dsLop)
    {
        this.dsLop = dsLop;
    }

    public int getSoLuong()
    {
        return soLuong;
    }
}