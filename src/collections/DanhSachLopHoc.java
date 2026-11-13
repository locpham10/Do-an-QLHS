package collections;

import models.LopHoc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DanhSachLopHoc
{
    private LopHoc[] dsLop;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/lophoc.txt";


    public DanhSachLopHoc() {
        this.dsLop = new LopHoc[100];
        this.soLuong = 0;
    }

    public DanhSachLopHoc(int dungLuong) {
        this.dsLop = new LopHoc[dungLuong];
        this.soLuong = 0; }

    public boolean themLop(LopHoc lh) {
        if(lh != null && soLuong < dsLop.length)
        {
            this.dsLop[soLuong] = lh;
            soLuong++;
            return true; //Thêm lớp thành công!!
        }
        return false; //Thêm lớp thất bại vì đối tượng null/mảng đã đầy.
    }

    public LopHoc timKiemLopTheoTen(String tenLop) {
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

    public boolean ghiFile() {
        File f = new File(DUONG_DAN_FILE);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (dsLop[i] != null) {
                    bw.write(dsLop[i].toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file LopHoc thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file LopHoc: " + e.getMessage());
            return false;
        }
    }

    public LopHoc[] getDsLop() { return dsLop; }
    public void setDsLop(LopHoc[] dsLop) { this.dsLop = dsLop; }

    public int getSoLuong() { return soLuong; }
}
