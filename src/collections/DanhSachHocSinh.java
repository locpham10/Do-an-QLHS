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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import interfaces.IThongKe;
import exceptions.TrungMaHocSinhException;

public class DanhSachHocSinh implements IThongKe {
    private HocSinh[] arrHS;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/hocsinh.txt";

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
        File f = new File(DUONG_DAN_FILE);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
                thuMucCha.mkdirs();}
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
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
        final int TOI_DA = 1000;
        int soDong = 0;
        int soLoi = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_FILE))) {
            String line;
            this.soLuong = 0;

            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                if (this.soLuong >= TOI_DA) {
                    System.out.println("Dong " + soDong + ": danh sach da du " + TOI_DA
                            + " hoc sinh, bo qua phan con lai cua file");
                    soLoi++;
                    break;
                }

                String[] data = line.split(",", -1);
                if (data.length < 11) {
                    System.out.println("Dong " + soDong + ": thieu cot (can it nhat 11, co " + data.length + ")");
                    soLoi++;
                    continue;
                }
                for (int i = 0; i < data.length; i++) data[i] = data[i].trim();

                try {
                    String loaiHS = data[0];
                    String mshs = data[1];
                    String hoTen = data[2];
                    String ngaySinh = data[3];
                    boolean gioiTinh = Boolean.parseBoolean(data[4]);
                    String diaChi = data[5];
                    String soDienThoai = data[6];
                    String tenLop = data[7];
                    double gpa = Double.parseDouble(data[8]);
                    int diemHanhKiem = Integer.parseInt(data[9]);
                    boolean daDongHocPhi = Boolean.parseBoolean(data[10]);

                    if (timKiem(mshs) != null) {
                        throw new TrungMaHocSinhException("Dong " + soDong + ": MSHS '" + mshs
                                + "' bi trung, bo qua dong nay");
                    }

                    HocSinh hs;
                    if (loaiHS.equalsIgnoreCase("DaiTra")) {
                        hs = new HocSinhDaiTra(mshs, hoTen, ngaySinh, gioiTinh, diaChi,
                                               soDienThoai, tenLop, gpa, diemHanhKiem, daDongHocPhi);
                    } else if (loaiHS.equalsIgnoreCase("Chuyen")) {
                        if (data.length < 14) {
                            System.out.println("Dong " + soDong + ": hoc sinh chuyen thieu cot (can 14, co "
                                    + data.length + ")");
                            soLoi++;
                            continue;
                        }
                        String monChuyen = data[11];
                        double diemMonChuyen = Double.parseDouble(data[12]);
                        double phuPhiChuyenDe = Double.parseDouble(data[13]);
                        hs = new HocSinhChuyen(mshs, hoTen, ngaySinh, gioiTinh, diaChi,
                                               soDienThoai, tenLop, gpa, diemHanhKiem, daDongHocPhi,
                                               monChuyen, diemMonChuyen, phuPhiChuyenDe);
                    } else {
                        System.out.println("Dong " + soDong + ": loai hoc sinh khong hop le '" + loaiHS + "'");
                        soLoi++;
                        continue;
                    }
                    this.them(hs);
                } catch (NumberFormatException e) {
                    System.out.println("Dong " + soDong + ": loi dinh dang so: " + line);
                    soLoi++;
                } catch (TrungMaHocSinhException e) {
                    System.out.println(e.getMessage());
                    soLoi++;
                }
            }
            System.out.println("Doc file HocSinh thanh cong! Tong so: " + soLuong
                    + " hoc sinh, " + soLoi + " dong loi.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file HocSinh: " + e.getMessage());
            return false;
        }
    }

}