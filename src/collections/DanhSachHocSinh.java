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
import utils.Color;

public class DanhSachHocSinh implements IThongKe {
    private HocSinh[] arrHS;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/hocsinh.txt";

    public DanhSachHocSinh() {
        this.arrHS = new HocSinh[0];
        this.soLuong = 0;
    }   
    public DanhSachHocSinh(int kichThuoc) {
        this.arrHS = new HocSinh[kichThuoc];
        this.soLuong = 0;
    }

    public HocSinh[] getArrHS() { return arrHS; }
    public int getSoLuong() { return soLuong; }

    public boolean them(HocSinh hs) {
        if (hs == null) return false;
        if (this.soLuong >= this.arrHS.length) {
            this.arrHS = Arrays.copyOf(this.arrHS, this.arrHS.length + 1);
        }
        this.arrHS[soLuong] = hs;
        this.soLuong++;
        return true; 
    }

    public int them(HocSinh[] dsMoi, int k) {
        int soLuongDaThem = 0;
        for (int i = 0; i < k; i++) {
            if (them(dsMoi[i]))
                soLuongDaThem++;
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
            return false;
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            arrHS[i] = arrHS[i + 1];
        }
        arrHS[soLuong - 1] = null;
        soLuong--;
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
            return false;
        }

        System.out.println("Dang sua thong tin cho hoc sinh: " + hs.getHoTen());
        System.out.println("Luu y: Nhan Enter de bo qua neu khong muon doi truong do.");
        String s;
        while (true) {                           
            System.out.print("Nhap ho ten moi: ");
            s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            if (!s.contains(",")) { hs.setHoTen(s); break; }
            System.out.println(Color.RED + "Loi: Khong duoc chua dau phay (,)!" + Color.RESET);
        }
        while (true) {                            
            System.out.print("Nhap ngay sinh moi (dd/MM/yyyy): ");
            s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            if (DataValidator.ngaySinhHopLe(s)) { hs.setNgaySinh(s); break; }
            System.out.println(Color.RED + "Loi: Ngay sinh phai dung dinh dang DD/MM/YYYY!" + Color.RESET);
        }
        while (true) {                           
            System.out.print("Nhap gioi tinh moi (1: Nam / 0: Nu): ");
            s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            if (s.equals("1") || s.equals("0")) { hs.setGioiTinh(s.equals("1")); break; }
            System.out.println(Color.RED + "Loi: Chi nhap 1 hoac 0!" + Color.RESET);
        }
        while (true) {
            System.out.print("Nhap dia chi moi: ");
            s = sc.nextLine().trim();
            if (s.isEmpty()) break;
            if (!s.contains(",")) { hs.setDiaChi(s); break; }
            System.out.println(Color.RED + "Loi: Khong duoc chua dau phay (,)!" + Color.RESET);
        }
        while (true) { 
            System.out.print("Nhap so dien thoai moi: ");
            s = sc.nextLine().trim().replaceAll("[\\s.]", "");
            if (s.isEmpty()) break;
            if (s.matches("^0\\d{9}$")) { hs.setSoDienThoai(s); break; }
            System.out.println(Color.RED + "Loi: So dien thoai phai co 10 so va bat dau bang 0!" + Color.RESET);
        }
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
        this.arrHS = new HocSinh[0];
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
    System.out.printf("Len lop: %d/%d (%.1f%%), o lai: %d%n",
            soHocSinhDat, soLuong, tyLe, soLuong - soHocSinhDat);
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
        System.out.println(" Phan loai he dao tao: " + chuyen + " Chuyen, " + daitra + " Dai tra");
        System.out.println(" So luong hoc sinh dat hoc bong: " + datHocBong);
        System.out.println(" So luong hoc sinh con no hoc phi: " + noHocPhi);
 
        this.tinhTyLeDat();
    }

    private void inDongThongKe(String nhan, int soHs) {
        double tyLe = (soLuong == 0) ? 0 : soHs * 100.0 / soLuong;
        System.out.printf("  %-22s: %3d hoc sinh (%5.1f%%)%n", nhan, soHs, tyLe);
    }

    private boolean khongCoDuLieu() {
        if (soLuong == 0) {
            System.out.println("Khong co du lieu hoc sinh de thong ke.");
            return true;
        }
        return false;
    }


    public void thongKeTheoLop() {
        System.out.println("\n--- THONG KE THEO LOP ---");
        if (khongCoDuLieu()) return;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            String tenLop = arrHS[i].getTenLop();
            boolean daDem = false;
            for (int j = 0; j < i; j++) {
                if (arrHS[j] != null && arrHS[j].getTenLop().equalsIgnoreCase(tenLop)) {
                    daDem = true;
                    break;
                }
            }
            if (daDem) continue;
            int dem = 0;
            for (int j = i; j < soLuong; j++) {
                if (arrHS[j] != null && arrHS[j].getTenLop().equalsIgnoreCase(tenLop)) dem++;
            }
            inDongThongKe("Lop " + tenLop, dem);
        }
    }

   
    public void thongKeTheoHe() {
        System.out.println("\n--- THONG KE THEO HE ---");
        if (khongCoDuLieu()) return;
        int chuyen = 0, daiTra = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] instanceof HocSinhChuyen) chuyen++;
            else if (arrHS[i] instanceof HocSinhDaiTra) daiTra++;
        }
        inDongThongKe("Chuyen", chuyen);
        inDongThongKe("Dai tra", daiTra);
    }

   
    public void thongKeTheoGioiTinh() {
        System.out.println("\n--- THONG KE THEO GIOI TINH ---");
        if (khongCoDuLieu()) return;
        int nam = 0, nu = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            if (arrHS[i].isGioiTinh()) nam++;
            else nu++;
        }
        inDongThongKe("Nam", nam);
        inDongThongKe("Nu", nu);
    }


    private int[] demTheoXepLoai(String[] mucXepLoai, boolean laHocLuc) {
        int[] dem = new int[mucXepLoai.length];
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            String xl = laHocLuc ? arrHS[i].xepLoaiHocLuc() : arrHS[i].xepLoaiHanhKiem();
            for (int k = 0; k < mucXepLoai.length; k++) {
                if (mucXepLoai[k].equals(xl)) {
                    dem[k]++;
                    break;
                }
            }
        }
        return dem;
    }

    public void thongKeTheoHocLuc() {
        System.out.println("\n--- THONG KE THEO HOC LUC (GPA) ---");
        if (khongCoDuLieu()) return;
        String[] muc = {"Gioi", "Kha", "Trung binh", "Yeu"};
        String[] nhan = {"Gioi (>= 8.0)", "Kha (6.5 - 7.9)", "Trung binh (5.0 - 6.4)", "Yeu (< 5.0)"};
        int[] dem = demTheoXepLoai(muc, true);
        for (int k = 0; k < muc.length; k++) inDongThongKe(nhan[k], dem[k]);
    }


    public void thongKeTheoHanhKiem() {
        System.out.println("\n--- THONG KE THEO HANH KIEM ---");
        if (khongCoDuLieu()) return;
        String[] muc = {"Tot", "Kha", "Trung binh", "Yeu"};
        String[] nhan = {"Tot (>= 80)", "Kha (65 - 79)", "Trung binh (50 - 64)", "Yeu (< 50)"};
        int[] dem = demTheoXepLoai(muc, false);
        for (int k = 0; k < muc.length; k++) inDongThongKe(nhan[k], dem[k]);
    }


    public void thongKeHocBongVaNoHocPhi() {
        System.out.println("\n--- THONG KE HOC BONG VA NO HOC PHI ---");
        if (khongCoDuLieu()) return;
        int hbChuyen = 0, hbDaiTra = 0, soNo = 0;
        double tongNo = 0;
        for (int i = 0; i < soLuong; i++) {
            HocSinh hs = arrHS[i];
            if (hs == null) continue;
            if (hs.xetHocBong()) {
                if (hs instanceof HocSinhChuyen) hbChuyen++;
                else hbDaiTra++;
            }
            if (!hs.isDaDongHocPhi()) {
                soNo++;
                tongNo += hs.tinhHocPhi();
            }
        }
        inDongThongKe("Hoc bong - Chuyen", hbChuyen);
        inDongThongKe("Hoc bong - Dai tra", hbDaiTra);
        inDongThongKe("Con no hoc phi", soNo);
        System.out.printf("  %-22s: %,.0f VND%n", "Tong tien con no", tongNo);
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

        public static final String DUONG_DAN_DIEM = "src/data/diem.txt";

    // Ghi bang diem cua moi hoc sinh, moi dong: MSHS,maMon,TX,GK,CK
    public boolean ghiDiem() {
        File f = new File(DUONG_DAN_DIEM);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrHS[i] == null || arrHS[i].getDanhSachDiem() == null) continue;
                for (DiemMonHoc d : arrHS[i].getDanhSachDiem()) {
                    if (d == null) continue;
                    bw.write(arrHS[i].getMshs() + "," + d.toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file diem thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file diem: " + e.getMessage());
            return false;
        }
    }

    // doc diem.txt vao bang diem tung hs
    public boolean docDiem() {
        // phai doc hocsinh.txt truoc thi moi co hoc sinh de gan diem
        if (soLuong == 0) {
            System.out.println("Chua co hoc sinh nao, hay doc hocsinh.txt truoc khi doc diem.");
            return false;
        }
        int soDong = 0, soLoi = 0, soDiem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_DIEM))) {
            // lam moi de k bi nhan doi
            for (int i = 0; i < soLuong; i++) {
                if (arrHS[i] != null) {
                    arrHS[i].setDanhSachDiem(new DiemMonHoc[0]);
                    arrHS[i].setSoLuongMon(0);
                }
            }
            String line;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length != 5) {
                    System.out.println("Dong " + soDong + ": can dung 5 cot (MSHS,maMon,TX,GK,CK), co " + p.length);
                    soLoi++;
                    continue;
                }
                HocSinh hs = timKiem(p[0].trim());
                if (hs == null) {
                    System.out.println("Dong " + soDong + ": khong tim thay MSHS '" + p[0].trim() + "'");
                    soLoi++;
                    continue;
                }
                try {
                    DiemMonHoc d = DiemMonHoc.tuChuoi(line);
                    if (laMonChuyenCua(hs, d.getMonHoc())) {
                        d.setMonHoc(d.getMonHoc().copyVoiHeSo(2));
                    }
                    DiemMonHoc[] cu = hs.getDanhSachDiem();
                    boolean trung = false;
                    for (DiemMonHoc x : cu) {
                        if (x.getMonHoc().getMaMon().equals(d.getMonHoc().getMaMon())) {
                            trung = true;
                            break;
                        }
                    }
                    if (trung) {
                        System.out.println("Dong " + soDong + ": hoc sinh " + hs.getMshs()
                                + " da co diem mon " + d.getMonHoc().getMaMon() + ", bo qua");
                        soLoi++;
                        continue;
                    }
                    DiemMonHoc[] moi = Arrays.copyOf(cu, cu.length + 1);
                    moi[cu.length] = d;
                    hs.setDanhSachDiem(moi);
                    soDiem++;
                } catch (IllegalArgumentException e) {
                    System.out.println("Dong " + soDong + ": " + e.getMessage());
                    soLoi++;
                }
            }
        } catch (IOException e) {
            System.out.println("Loi khi doc file diem: " + e.getMessage());
            return false;
        }

        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            DiemMonHoc[] ds = arrHS[i].getDanhSachDiem();
            if (ds != null && ds.length > 0) {
                arrHS[i].setSoLuongMon(ds.length);
                arrHS[i].setGpa(DiemMonHoc.tinhGPATongHop(ds));
            }
        }
        System.out.println("Doc file diem thanh cong! " + soDiem + " diem, " + soLoi + " dong loi.");
        return true;
    }

    public static final String DUONG_DAN_HOAT_DONG = "src/data/hoatdong.txt";

    // Ghi lich su hoat dong cua tat ca hoc sinh: MSHS,tenHoatDong,diemCongTru,ngayGhiNhan
    public boolean ghiHoatDong() {
        File f = new File(DUONG_DAN_HOAT_DONG);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrHS[i] == null || arrHS[i].getLichSuHoatDong() == null) continue;
                for (models.HoatDong hd : arrHS[i].getLichSuHoatDong()) {
                    if (hd == null) continue;
                    bw.write(arrHS[i].getMshs() + "," + hd.toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file HoatDong thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file HoatDong: " + e.getMessage());
            return false;
        }
    }

    // Doc file hoatdong.txt: MSHS,tenHoatDong,diemCongTru,ngayGhiNhan
    public boolean docHoatDong() {
        if (soLuong == 0) {
            System.out.println("Chua co hoc sinh nao, hay doc hocsinh.txt truoc khi doc hoat dong.");
            return false;
        }
        File f = new File(DUONG_DAN_HOAT_DONG);
        if (!f.exists()) {
            System.out.println("Chua co file " + DUONG_DAN_HOAT_DONG + ", bo qua doc hoat dong.");
            return false;
        }
        int soDong = 0, soLoi = 0, soHD = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrHS[i] != null) {
                    arrHS[i].setLichSuHoatDong(new models.HoatDong[0]);
                }
            }
            String line;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length != 4) {
                    System.out.println("Dong " + soDong + " (HoatDong): can dung 4 cot (MSHS,tenHD,diem,ngay), co " + p.length);
                    soLoi++;
                    continue;
                }
                HocSinh hs = timKiem(p[0].trim());
                if (hs == null) {
                    System.out.println("Dong " + soDong + " (HoatDong): khong tim thay MSHS '" + p[0].trim() + "'");
                    soLoi++;
                    continue;
                }
                try {
                    String tenHD = p[1].trim();
                    int diemCongTru = Integer.parseInt(p[2].trim());
                    String ngay = p[3].trim();
                    models.HoatDong hd = new models.HoatDong(tenHD, diemCongTru, ngay);

                    models.HoatDong[] cu = hs.getLichSuHoatDong();
                    if (cu == null) cu = new models.HoatDong[0];
                    models.HoatDong[] moi = Arrays.copyOf(cu, cu.length + 1);
                    moi[cu.length] = hd;
                    hs.setLichSuHoatDong(moi);
                    soHD++;
                } catch (NumberFormatException e) {
                    System.out.println("Dong " + soDong + " (HoatDong): diem cong/tru phai la so nguyen");
                    soLoi++;
                }
            }
            System.out.println("Doc file HoatDong thanh cong! " + soHD + " hoat dong, " + soLoi + " dong loi.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file HoatDong: " + e.getMessage());
            return false;
        }
    }

    private boolean laMonChuyenCua(HocSinh hs, MonHoc mh) {
        if (!(hs instanceof HocSinhChuyen) || mh == null) return false;
        String monChuyen = ((HocSinhChuyen) hs).getMonChuyen();
        if (monChuyen == null) return false;
        monChuyen = monChuyen.trim();
        return mh.getMaMon().equalsIgnoreCase(monChuyen) || mh.getTenMon().equalsIgnoreCase(monChuyen);
    }

// ================= BLOCK TAI CHINH  =================

    public void inDanhSachNoHocPhi() {
        System.out.println("\n--- DANH SACH HOC SINH CHUA DONG HOC PHI ---");
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && !arrHS[i].isDaDongHocPhi()) {
                System.out.printf("%d. MSHS: %s | Ho ten: %s | Lop: %s | So tien: %,.0f VND\n",
                    (count + 1), arrHS[i].getMshs(), arrHS[i].getHoTen(), arrHS[i].getTenLop(), arrHS[i].tinhHocPhi());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Khong co hoc sinh nao no hoc phi.");
        }
    }

    public double tongHocPhiPhaiThu() {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null) {
                tong += arrHS[i].tinhHocPhi();
            }
        }
        return tong;
    }

    public double tongHocPhiDaThu() {
        double tong = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].isDaDongHocPhi()) {
                tong += arrHS[i].tinhHocPhi();
            }
        }
        return tong;
    }

    public void xetDuyetHocBong() {
        System.out.println("\n--- XET DUYET HOC BONG TOAN TRUONG ---");
        int count = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].xetHocBong()) {
                count++;
                System.out.printf("%d. MSHS: %s | Ho ten: %s | Lop: %s | GPA: %.2f | Hanh kiem: %d\n",
                    count, arrHS[i].getMshs(), arrHS[i].getHoTen(), arrHS[i].getTenLop(), arrHS[i].getGpa(), arrHS[i].getDiemHanhKiem());
            }
        }
        if (count == 0) {
            System.out.println("Khong co hoc sinh nao du dieu kien nhan hoc bong.");
        }
    }
    public void capNhatDongHocPhiChoHS(String mshs, boolean trangThai) {
        HocSinh hs = timKiem(mshs);
        if (hs == null) {
            System.out.println("Khong tim thay hoc sinh co MSHS: " + mshs);
            return;
        }
        hs.capNhatDongHocPhi(trangThai);
        System.out.println("Da cap nhat hoc phi cua " + hs.getHoTen() + ": "
                + (trangThai ? "Da dong" : "Chua dong"));
    }

    public void baoCaoThuPhiToanTruong() {
        inBaoCaoThuPhi("TOAN TRUONG", soLuong, tongHocPhiPhaiThu(), tongHocPhiDaThu());
    }

    public void baoCaoThuPhiTheoLop(String tenLop) {
        int soHS = 0;
        double phaiThu = 0, daThu = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] != null && arrHS[i].getTenLop().equalsIgnoreCase(tenLop)) {
                soHS++;
                phaiThu += arrHS[i].tinhHocPhi();
                if (arrHS[i].isDaDongHocPhi()) daThu += arrHS[i].tinhHocPhi();
            }
        }
        if (soHS == 0) {
            System.out.println("Lop " + tenLop + " khong co hoc sinh nao (hoac sai ten lop).");
            return;
        }
        inBaoCaoThuPhi("LOP " + tenLop.toUpperCase(), soHS, phaiThu, daThu);
    }

    // laChuyen = true: he chuyen, false: he dai tra (giong hienThiTheoHe)
    public void baoCaoThuPhiTheoHe(boolean laChuyen) {
        int soHS = 0;
        double phaiThu = 0, daThu = 0;
        for (int i = 0; i < soLuong; i++) {
            if (arrHS[i] == null) continue;
            boolean dungHe = laChuyen ? (arrHS[i] instanceof HocSinhChuyen)
                    : (arrHS[i] instanceof HocSinhDaiTra);
            if (dungHe) {
                soHS++;
                phaiThu += arrHS[i].tinhHocPhi();   // da hinh: moi he tinh hoc phi khac nhau
                if (arrHS[i].isDaDongHocPhi()) daThu += arrHS[i].tinhHocPhi();
            }
        }
        inBaoCaoThuPhi(laChuyen ? "HE CHUYEN" : "HE DAI TRA", soHS, phaiThu, daThu);
    }

    // in chung cho 3 bao cao thu phi
    private void inBaoCaoThuPhi(String tieuDe, int soHS, double phaiThu, double daThu) {
        System.out.println("\n--- BAO CAO THU PHI " + tieuDe + " ---");
        System.out.println("So hoc sinh: " + soHS);
        System.out.printf("Phai thu: %,.0f d%n", phaiThu);
        System.out.printf("Da thu:   %,.0f d%n", daThu);
        System.out.printf("Con no:   %,.0f d%n", phaiThu - daThu);
    }

    public void traCuuHocPhi(String mshs) {
        HocSinh hs = timKiem(mshs);
        if (hs == null) {
            System.out.println("Khong tim thay hoc sinh co MSHS: " + mshs);
            return;
        }
        System.out.println("\n--- THONG TIN HOC PHI CA NHAN ---");
        System.out.println("Ma HS: " + hs.getMshs() + " | Ho ten: " + hs.getHoTen());
        if (hs instanceof HocSinhChuyen) {
            HocSinhChuyen hsc = (HocSinhChuyen) hs;
            System.out.printf("Hoc phi co ban: %,.0f d%n", HocSinh.HOC_PHI_CO_BAN);
            System.out.printf("Phu phi chuyen de: %,.0f d%n", hsc.getPhuPhiChuyenDe());
        }
        System.out.printf("Tong hoc phi phai dong: %,.0f d%n", hs.tinhHocPhi());
        System.out.println("Trang thai: " + (hs.isDaDongHocPhi() ? "Da dong" : "Chua dong"));
    }
}
