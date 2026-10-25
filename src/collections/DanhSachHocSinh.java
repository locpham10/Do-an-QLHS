package collections;

import java.util.Scanner;
import models.DiemMonHoc;
import models.HocSinh;
import models.MonHoc;
import models.HocSinhChuyen;
import models.HocSinhDaiTra;
import utils.DataValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DanhSachHocSinh {
    private HocSinh[] arrHS = new HocSinh[1000];
    private int soLuong=0;
    private final String FILE_PATH = "hocsinh.txt";
    
    public void them(HocSinh hs){
        if(soLuong<1000){
            arrHS[soLuong]= hs;
            soLuong++;
        }
    }

    public void xoa(HocSinh hs){

    }

    public void sua(HocSinh hs){

    }

    public HocSinh timKiem(HocSinh hs) {
        if (hs == null) 
            return null;
        return timKiem(hs.getMshs());
    }

    public HocSinh timKiem(String maHS)
    {
        if(maHS == null)
            return null;
        for (int i = 0; i < soLuong; i++) 
        {
            if(arrHS[i] != null && maHS.equalsIgnoreCase(arrHS[i].getMshs()))
                return arrHS[i];            
        }
        return null;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public HocSinh[] getArrHS() {
        return arrHS;
    }

    public void nhapDiemChoHocSinh(MonHoc[] dsMonHoc, int soLuongMon, Scanner sc)
    {
        System.out.println("\n CHUC NANG: NHAP DIEM CHO HOC SINH");
        System.out.print("Nhap Ma Hoc sinh can nhap diem: ");
        String maHS = sc.nextLine().trim();

        HocSinh hsTimThay = this.timKiem(maHS);
        if(hsTimThay == null)
        { 
            System.out.println("Khong tim thay hoc sinh co ma: " + maHS);
            return;
        }

        if(dsMonHoc == null || soLuongMon == 0)
        {
            System.out.println("Danh sach mon hoc cua truong chua co du lieu!");
            return;
        }

        System.out.println("Da tim thay: " + hsTimThay.getHoTen() + " - Ma: " + hsTimThay.getMshs());
        
        DiemMonHoc[] dsDiemMoi = new DiemMonHoc[soLuongMon];
        for (int i = 0; i < soLuongMon; i++) 
        {
            MonHoc mh = dsMonHoc[i];
            if(mh != null)
            {
                System.out.println("\nMon: " + mh.getTenMon() + "(He so: " + mh.getHeSo() + ")");
                double dTX = DataValidator.nhapDiem(sc, "   + Nhap diem TX: ");
                double dGK = DataValidator.nhapDiem(sc, "   + Nhap diem GK: ");
                double dCK = DataValidator.nhapDiem(sc, "   + Nhap diem CK: ");
                dsDiemMoi[i] = new DiemMonHoc(mh, dTX, dGK, dCK);
            }
        }

        hsTimThay.setDanhSachDiem(dsDiemMoi);
        hsTimThay.setSoLuongMon(soLuongMon);
        double gpaMoi = DiemMonHoc.tinhGPATongHop(dsDiemMoi);
        hsTimThay.setGpa(gpaMoi);
        System.out.println("\nDa cap nhat bang diem thanh cong!");
        System.out.println("GPA moi cua " + hsTimThay.getHoTen() + "la: " + gpaMoi);
    }
    

    public boolean ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrHS[i] != null) {
                    bw.write(arrHS[i].toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file danh sach hoc sinh thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file HocSinh: " + e.getMessage());
            return false;
        }
    }

    public boolean docFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            this.soLuong = 0; 

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (this.soLuong >= arrHS.length) {
                    System.out.println("Canh bao: Mang danh sach hoc sinh da day (Toi da " + arrHS.length + ")!");
                    break;
                }

                String[] data = line.split(",");
                if (data.length < 11) continue; 

                try {
                    String loaiHS = data[0].trim();
                    String mshs = data[1].trim();
                    String hoTen = data[2].trim();
                    String ngaySinh = data[3].trim();
                    boolean gioiTinh = Boolean.parseBoolean(data[4].trim());
                    String diaChi = data[5].trim();
                    String soDienThoai = data[6].trim();
                    String tenLop = data[7].trim();
                    double gpa = Double.parseDouble(data[8].trim());
                    int diemHanhKiem = Integer.parseInt(data[9].trim());
                    boolean daDongHocPhi = Boolean.parseBoolean(data[10].trim());

                    HocSinh hs = null;
                    if (loaiHS.equalsIgnoreCase("DaiTra")) {
                        hs = new HocSinhDaiTra(mshs, hoTen, ngaySinh, gioiTinh, diaChi, 
                                               soDienThoai, tenLop, gpa, diemHanhKiem, daDongHocPhi);
                    } else if (loaiHS.equalsIgnoreCase("Chuyen") && data.length >= 14) {
                        String monChuyen = data[11].trim();
                        double diemMonChuyen = Double.parseDouble(data[12].trim());
                        double phuPhiChuyenDe = Double.parseDouble(data[13].trim());

                        hs = new HocSinhChuyen(mshs, hoTen, ngaySinh, gioiTinh, diaChi, 
                                              soDienThoai, tenLop, gpa, diemHanhKiem, daDongHocPhi, 
                                              monChuyen, diemMonChuyen, phuPhiChuyenDe);
                    }

                    if (hs != null) {
                        this.them(hs);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bo qua dong do loi dinh dang so: " + line);
                }
            }
            System.out.println("Doc file HocSinh thanh cong! Tong so: " + soLuong + " hoc sinh.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file HocSinh: " + e.getMessage());
            return false;
        }
    }
}
