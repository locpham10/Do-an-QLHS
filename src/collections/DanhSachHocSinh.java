package collections;

import java.util.Arrays;
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
import interfaces.IThongKe;

public class DanhSachHocSinh implements IThongKe {
    private HocSinh[] arrHS;
    private int soLuong;
    private final String FILE_PATH = "hocsinh.txt";

    public DanhSachHocSinh() {
        this.arrHS = new HocSinh[100];
        this.soLuong = 0;
    }   
    public DanhSachHocSinh(int kichThuoc) {
        this.arrHS = new HocSinh[kichThuoc];
        this.soLuong = 0;
    }

    public boolean them(HocSinh hs) {
        if (hs == null) return false;
        if (this.soLuong >= this.arrHS.length) {
            this.arrHS = Arrays.copyOf(this.arrHS, this.arrHS.length + 10);
        }
        this.arrHS[soLuong] = hs;
        this.soLuong++;
        return true; 
    }

    public int them(HocSinh[] dsMoi, int k) {
        int soLuongDaThem = 0;
        for (int i = 0; i < k; i++) {
            if (dsMoi[i] != null) {
                if (this.soLuong >= this.arrHS.length) {
                    this.arrHS = Arrays.copyOf(this.arrHS, this.arrHS.length + 10);
                }
                
                this.arrHS[soLuong] = dsMoi[i];
                this.soLuong++;
                soLuongDaThem++;
            }
        }
        return soLuongDaThem;
    }

    public boolean xoa(String mshs) {
        int viTri = -1;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getMshs().equalsIgnoreCase(mshs)) {
                viTri = i;
                break;
            }
        }
        if (viTri == -1) {
            System.out.println("Khong tim thay hoc sinh ma: " + mshs);
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            arrHS[i] = arrHS[i + 1];
        }
        arrHS[soLuong - 1] = null;
        soLuong--;
        System.out.println("Da xoa thanh cong hoc sinh ma: " + mshs);
        return true;
    }

    public boolean sua(String mshs, Scanner sc) {
        HocSinh hs = null;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getMshs().equalsIgnoreCase(mshs)) {
                hs = arrHS[i];
                break;
            }
        }
        if (hs == null) {
            System.out.println("Khong tim thay hoc sinh ma: " + mshs);
            return false;
        }

        System.out.println("Dang sua thong tin cho hoc sinh: " + hs.getHoTen());
        System.out.println("Luu y: Nhan Enter de bo qua neu khong muon doi truong do.");
        System.out.print("Nhap ho ten moi: ");
        String hoTen = sc.nextLine();
        if (!hoTen.trim().isEmpty()) hs.setHoTen(hoTen);
        System.out.print("Nhap ngay sinh moi (dd/MM/yyyy): ");
        String ngaySinh = sc.nextLine();
        if (!ngaySinh.trim().isEmpty()) hs.setNgaySinh(ngaySinh);
        System.out.print("Nhap gioi tinh moi: ");
        String gioiTinh = sc.nextLine();
        if (!gioiTinh.trim().isEmpty()) {
            hs.setGioiTinh(Boolean.parseBoolean(gioiTinh));
        }
        System.out.print("Nhap Dia Chi Moi: ");
        String diaChi = sc.nextLine();
        if (!diaChi.trim().isEmpty()) hs.setDiaChi(diaChi);
        System.out.print("Nhap so dien thoai moi: ");
        String sdt = sc.nextLine();
        if (!sdt.trim().isEmpty()) hs.setSoDienThoai(sdt);
        System.out.println("Cap nhat thong tin thanh cong!");
        return true;
    }


    public HocSinh timKiem(String mshs) { 
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getMshs().equalsIgnoreCase(mshs)) return arrHS[i];
        }
        return null;
    }

    public HocSinh[] timKiemTheoTen(String tuKhoa) {
        int count = 0;
        String key = tuKhoa.toLowerCase();
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getHoTen().toLowerCase().contains(key)) count++;
        }
        HocSinh[] kq = new HocSinh[count];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getHoTen().toLowerCase().contains(key)) kq[index++] = arrHS[i];
        }
        return kq;
    }

    public HocSinh[] timKiemTheoLop(String tenLop) {
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getTenLop().equalsIgnoreCase(tenLop)) count++;
        }
        HocSinh[] kq = new HocSinh[count];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getTenLop().equalsIgnoreCase(tenLop)) kq[index++] = arrHS[i];
        }
        return kq;
    }

    public HocSinh[] timKiemTheoGpa(double min, double max) {
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getGpa() >= min && arrHS[i].getGpa() <= max) count++;
        }
        HocSinh[] kq = new HocSinh[count];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getGpa() >= min && arrHS[i].getGpa() <= max) kq[index++] = arrHS[i];
        }
        return kq;
    }

    public HocSinh[] timKiemTheoHe(boolean laChuyen) {
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            if ((laChuyen && arrHS[i] instanceof models.HocSinhChuyen) || 
                (!laChuyen && arrHS[i] instanceof models.HocSinhDaiTra)) {
                count++;
            }
        }
        HocSinh[] kq = new HocSinh[count];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            if ((laChuyen && arrHS[i] instanceof models.HocSinhChuyen) || 
                (!laChuyen && arrHS[i] instanceof models.HocSinhDaiTra)) {
                    kq[index++] = arrHS[i];
            }
        }
        return kq;
    }

   public HocSinh[] timKiemTheoGioiTinh(boolean gioiTinh) {
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].isGioiTinh() == gioiTinh) count++;
        }
        HocSinh[] kq = new HocSinh[count];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].isGioiTinh() == gioiTinh) kq[index++] = arrHS[i];
        }
        return kq;
    } 

    public void lamRong() {
        this.soLuong = 0; 
    }

    @Override
    public void hienThiDanhSach() {
        if (soLuong == 0) {
            System.out.println("Danh sach trong!");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            if(arrHS[i] != null) arrHS[i].hienThiThongTin();
        }
    }

    public void hienThiTheoLop(String tenLop) {
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getTenLop().equalsIgnoreCase(tenLop)) {
                arrHS[i].hienThiThongTin();
            }
        }
    }

    public void hienThiTheoHe(boolean laChuyen) {
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            if (laChuyen && arrHS[i] instanceof models.HocSinhChuyen) {
                arrHS[i].hienThiThongTin();
            } else if (!laChuyen && arrHS[i] instanceof models.HocSinhDaiTra) {
                arrHS[i].hienThiThongTin();
            }
        }
    }

    public void nhapDiemChoHocSinh(MonHoc[] dsMonHoc, int soLuongMon, Scanner sc){
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
    
    public void inTop5XuatSac() {
        if (soLuong == 0) return;
        HocSinh[] banSao = new HocSinh[soLuong];
        for(int i=0; i<soLuong; i++) banSao[i] = arrHS[i];

        for (int i = 0; i < soLuong - 1; i++) {
            for (int j = i + 1; j < soLuong; j++) {
                if (banSao[i].getGpa() < banSao[j].getGpa()) {
                    HocSinh temp = banSao[i];
                    banSao[i] = banSao[j];
                    banSao[j] = temp;
                }
            }
        }

        int inRa = Math.min(5, soLuong);
        for(int i=0; i<inRa; i++) banSao[i].hienThiThongTin();
    }

    public void tinhTyLeDat() {
        if (soLuong == 0) {
            System.out.println("Ty le dat: 0%");
            return;
        }
        int soHocSinhDat = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getGpa() >= 5.0) {
                soHocSinhDat++;
            }
        }
        double tyLe = (double) soHocSinhDat / soLuong * 100;
        System.out.println("Ty le hoc sinh dat: " + tyLe + "%");
    }

    @Override
    public void inBaoCaoThongKe() {
        if (soLuong == 0) {
            System.out.println("Khong co du lieu hoc sinh de thong ke.");
            return;
        }

        int chuyen = 0, daitra = 0;
        int datHocBong = 0;
        int noHocPhi = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            if (arrHS[i] instanceof models.HocSinhChuyen) chuyen++;
            else if (arrHS[i] instanceof models.HocSinhDaiTra) daitra++;
            if (arrHS[i].xetHocBong()) datHocBong++;
            if (!arrHS[i].isDaDongHocPhi()) noHocPhi++; 
        }

        System.out.println("--- BAO CAO THONG KE TONG HOP HOC SINH ---");
        System.out.println(" Tong so hoc sinh toan truong: " + soLuong);
        System.out.println(" Phan loai he đao tao: " + chuyen + " Chuyen, " + daitra + " Dai tra");
        System.out.println(" So luong hoc sinh đat hoc bong: " + datHocBong);
        System.out.println(" So luong hoc sinh con no hoc phi: " + noHocPhi);
 
        this.tinhTyLeDat();
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