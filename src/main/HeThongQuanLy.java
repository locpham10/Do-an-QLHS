package main;

import collections.DanhSachGiaoVien;
import collections.DanhSachHocSinh;
import collections.DanhSachLopHoc;
import collections.DanhSachPhuHuynh;
import exceptions.TrungMaHocSinhException;
import interfaces.IThongKe;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import models.*;
import utils.Color;
import utils.DataValidator;



public class HeThongQuanLy {
    private DanhSachHocSinh dsHocSinh;
    private DanhSachGiaoVien dsGiaoVien;
    private DanhSachPhuHuynh dsPhuHuynh;
    private DanhSachLopHoc dsLopHoc;
    private Scanner sc;

    public HeThongQuanLy() {
        dsHocSinh = new DanhSachHocSinh();
        dsGiaoVien = new DanhSachGiaoVien();
        dsPhuHuynh = new DanhSachPhuHuynh();
        dsLopHoc = new DanhSachLopHoc();
        sc = new Scanner(System.in);
    }

    private void hienThiMenuChinh() {
        System.out.println("\n" + Color.CYAN + "========================================");
        System.out.println("    HE THONG QUAN LY HOC SINH");
        System.out.println("========================================" + Color.RESET);
        System.out.println("1. Quan ly ho so");
        System.out.println("2. Quan ly hoc tap");
        System.out.println("3. Quan ly tai chinh");
        System.out.println("4. Thong ke & xet duyet");
        System.out.println("5. Quan ly du lieu");
        System.out.println("6. Quan ly nhan su & phu huynh");
        System.out.println("0. Thoat chuong trinh");
    }

    public void menu() {
        taiDuLieuBanDau();
        int luaChon = -1;
        while (luaChon != 0) {
            try {
                hienThiMenuChinh();
                luaChon = nhapLuaChon(6);

                switch (luaChon) {
                    case 1:
                        menuQuanLyHoSo();
                        break;
                    case 2:
                        menuQuanLyHocTap();
                        break;
                    case 3:
                        menuQuanLyTaiChinh();
                        break;
                    case 4:
                        menuThongKe();
                        break;
                    case 5:
                        menuQuanLyDuLieu();
                        break;
                    case 6:
                        menuQuanLyNhanSuVaPH();
                        break;
                    case 0:
                        System.out.println(Color.GREEN + "Cam on ban da su dung phan mem! Tam biet!" + Color.RESET);
                        break;
                    default:
                        System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                        break;
                }
            } catch (NoSuchElementException | IllegalStateException e){
                System.out.println(Color.RED + "\nKhong con du lieu nhap, thoat chuong trinh." + Color.RESET);
                return;
            } catch (RuntimeException e){
                // Lỗi ngoài dự kiến trong menu con: báo rồi quay về menu chính
                System.out.println(Color.RED + "Loi khong mong muon: " + e + Color.RESET);
            }
        }
    }

    private void taiDuLieuBanDau() {   // sau này Liểu thay bằng taiTatCa(), vẫn giữ lớp try này
        try { dsGiaoVien.docFile(); } catch (RuntimeException e) { baoLoiTai("giaovien.txt", e); }
        try { dsLopHoc.docFile(dsGiaoVien); } catch (RuntimeException e) { baoLoiTai("lophoc.txt", e); }
        try { dsPhuHuynh.docFile(); } catch (RuntimeException e) { baoLoiTai("phuhuynh.txt", e); }
        try { dsHocSinh.docFile(); }  catch (RuntimeException e) { baoLoiTai("hocsinh.txt", e); }
        try { dsLopHoc.phanHocSinhVaoLop(dsHocSinh); } catch (RuntimeException e) { baoLoiTai("lophoc.txt (phan hoc sinh vao lop)", e); }
    }

    private void baoLoiTai(String tenFile, RuntimeException e) {
        System.out.println(Color.RED + "Loi khi doc " + tenFile + ": " + e + Color.RESET);
    }

    private int nhapLuaChon(int max) {
        return DataValidator.nhapSoNguyen(sc, "Nhap lua chon (0-" + max + "): ", 0, max);
    }

    private void menuQuanLyHoSo() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n---- 1. QUAN LY HO SO ----");
            System.out.println("1. Nhap n hoc sinh dau tien");
            System.out.println("2. Them hoc sinh vao lop hoc");
            System.out.println("3. Xuat danh sach hoc sinh");
            System.out.println("4. Sua/Xoa hoc sinh");
            System.out.println("5. Quan ly lop hoc");
            System.out.println("6. Tim kiem hoc sinh");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon(6);

            if (chon == 0) break;
            switch (chon){

                case 1:
                    nhapNHocSinhDauTien();
                    break;

                case 2:
                    themKHocSinh();
                    break;

                case 3:
                    xuatDanhSachHocSinh();
                    break;

                case 4:
                    System.out.println("\n[SUA / XOA THONG TIN HOC SINH]");
                    System.out.print("Nhap MSHS can xu ly (Nhap 0 de quay lai): ");
                    String mshsCanXuLy = sc.nextLine().trim();
                    if (mshsCanXuLy.equals("0")) break;

                    HocSinh hsTimThay = dsHocSinh.timKiem(mshsCanXuLy);
                    if (hsTimThay == null) {
                        System.out.println(Color.RED + "Loi: Khong tim thay hoc sinh nay!" + Color.RESET);
                        break;
                    }

                    System.out.println(Color.GREEN + "Tim thay: " + hsTimThay.getHoTen()
                            + " - Lop: " + hsTimThay.getTenLop() + Color.RESET);
                    System.out.println("1. Sua thong tin hoc sinh");
                    System.out.println("2. Xoa hoc sinh khoi he thong");
                    System.out.println("0. Huy thao tac");

                    int thaoTac = -1;
                    while (true) {
                        System.out.print("Chon thao tac: ");
                        try {
                            thaoTac = Integer.parseInt(sc.nextLine().trim());
                            if (thaoTac >= 0 && thaoTac <= 2) break;
                            System.out.println(Color.RED + "Loi: Thao tac khong hop le! Vui long chon lai!" + Color.RESET);
                        } catch (NumberFormatException e) {
                            System.out.println(Color.RED + "Loi: Nhap sai dinh dang so! Vui long nhap lai!" + Color.RESET);
                        }
                    }

                    if (thaoTac == 1) {
                        if (dsHocSinh.sua(hsTimThay.getMshs(), sc)) {
                            System.out.println(Color.GREEN + "Cap nhat thong tin thanh cong!" + Color.RESET);
                        } else {
                            System.out.println(Color.YELLOW + "Khong co thay doi duoc luu!" + Color.RESET);
                        }
                    } else if (thaoTac == 2) {
                        System.out.print(Color.YELLOW + "Xac nhan xoa hoc sinh " + hsTimThay.getHoTen()
                                + "? (Y/N): " + Color.RESET);
                        String xacNhan = sc.nextLine().trim().toUpperCase();

                        if (xacNhan.equals("Y")) {
                            String tenLopCuaHs = hsTimThay.getTenLop();
                            boolean xoaTruong = dsHocSinh.xoa(hsTimThay.getMshs());

                            LopHoc lopCuaHs = dsLopHoc.timKiemLopTheoTen(tenLopCuaHs);
                            if (lopCuaHs != null && lopCuaHs.getDsHocSinh() != null) {
                                lopCuaHs.getDsHocSinh().xoa(hsTimThay.getMshs());
                            }

                            if (xoaTruong) {
                                System.out.println(Color.GREEN + "Da xoa hoc sinh " + hsTimThay.getHoTen() + "!" + Color.RESET);
                            } else {
                                System.out.println(Color.RED + "Xoa that bai!" + Color.RESET);
                            }
                        } else {
                            System.out.println(Color.YELLOW + "Da huy lenh xoa." + Color.RESET);
                        }
                    } else {
                        System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
                    }
                    break;

                case 5:
                    menuQuanLyLop();
                    break;

                case 6:
                    menuTimKiem();
                    break;

                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;
            }
        }
    }
    private void menuQuanLyLop() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n---- 1.5 QUAN LY LOP HOC ----");
            System.out.println("1. Them lop moi");
            System.out.println("2. Xem danh sach lop");
            System.out.println("3. Sua thong tin lop");
            System.out.println("4. Xoa lop");
            System.out.println("5. Chuyen lop cho hoc sinh");
            System.out.println("0. Quay lai");
            chon = nhapLuaChon(5);

            if (chon == 0) break;
            switch (chon) {
                case 1:
                    themLopHocMoi();
                    break;
                case 2:
                    System.out.println("\n--- DANH SACH CAC LOP HOC ---");
                    dsLopHoc.hienThiDanhSachLop();
                    break;
                case 3:
                    System.out.print("Nhap ten lop can sua (0 = quay lai): ");
                    String tenSua = sc.nextLine().trim();
                    if (tenSua.equals("0")) break;
                    dsLopHoc.sua(tenSua, sc, dsGiaoVien, dsHocSinh);
                    break;
                case 4:
                    System.out.print("Nhap ten lop can xoa (0 = quay lai): ");
                    String tenXoa = sc.nextLine().trim();
                    if (tenXoa.equals("0")) break;
                    dsLopHoc.xoa(tenXoa, sc, dsHocSinh);
                    break;
                case 5:
                    chuyenHocSinhSangLop();
                    break;
                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;
            }
        }
    }
    private void themLopHocMoi() {
        System.out.println("\n[THEM LOP HOC MOI]");

        // 1. Ten lop: khong rong, khong trung, khong co dau phay (file luu dang CSV)
        String tenLop;
        while (true) {
            System.out.print("Nhap ten lop (0 = quay lai): ");
            tenLop = sc.nextLine().trim();
            if (tenLop.equals("0")) return;
            if (tenLop.isEmpty() || tenLop.contains(",")) {
                System.out.println(Color.RED + "Loi: Ten lop khong duoc de trong va khong duoc chua dau phay!" + Color.RESET);
            } else if (dsLopHoc.timKiemLopTheoTen(tenLop) != null) {
                System.out.println(Color.RED + "Loi: Lop " + tenLop + " da ton tai trong he thong!" + Color.RESET);
            } else {
                break;
            }
        }

        // 2. Khoa hoc dang yyyy-yyyy (VD: 2026-2029), nam sau phai lon hon nam dau
        String khoaHoc;
        while (true) {
            System.out.print("Nhap khoa hoc (VD: 2026-2029): ");
            khoaHoc = sc.nextLine().trim();
            if (khoaHoc.matches("\\d{4}-\\d{4}")
                    && Integer.parseInt(khoaHoc.substring(5)) > Integer.parseInt(khoaHoc.substring(0, 4))) {
                break;
            }
            System.out.println(Color.RED + "Loi: Khoa hoc phai dung dang nam-nam (VD: 2026-2029)!" + Color.RESET);
        }

        // 3. Giao vien chu nhiem: Enter = de trong, nhap sai ma thi nhap lai
        GiaoVien gvcn = null;
        while (true) {
            System.out.print("Nhap ma giao vien chu nhiem (Enter = de trong): ");
            String maGVCN = sc.nextLine().trim();
            if (maGVCN.isEmpty()) {
                System.out.println(Color.YELLOW + "Lop tam thoi chua co giao vien chu nhiem." + Color.RESET);
                break;
            }
            gvcn = dsGiaoVien.timKiem(maGVCN);
            if (gvcn != null) break;
            System.out.println(Color.RED + "Loi: Khong tim thay ma giao vien " + maGVCN + "!" + Color.RESET);
        }

        DanhSachHocSinh dsHsLop = new DanhSachHocSinh(0);

        // 4. Chuong trinh hoc
        System.out.println("\n--- THIET LAP CHUONG TRINH HOC LOP " + tenLop.toUpperCase() + " ---");
        for (int i = 0; i < MonHoc.KHO_MON_HOC.length; i++) {
            MonHoc m = MonHoc.KHO_MON_HOC[i];
            System.out.println((i + 1) + ". " + m.getTenMon() + " (Mã: " + m.getMaMon() + ")");
        }

        int soMonCuaLop = 0;
        while (true) {
            System.out.print("So mon hoc cua lop (1-" + MonHoc.KHO_MON_HOC.length + "): ");
            try {
                soMonCuaLop = Integer.parseInt(sc.nextLine().trim());
                if (soMonCuaLop >= 1 && soMonCuaLop <= MonHoc.KHO_MON_HOC.length) break;
                System.out.println(Color.RED + "So luong mon khong hop le!" + Color.RESET);
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Loi: Vui long nhap so nguyen" + Color.RESET);
            }
        }

        MonHoc[] chuongTrinhCuaLop = new MonHoc[soMonCuaLop];
        for (int i = 0; i < soMonCuaLop; i++) {
            while (true) {
                System.out.print("Chon STT mon hoc thu " + (i + 1) + " (1-" + MonHoc.KHO_MON_HOC.length + "): ");
                try {
                    int chonMon = Integer.parseInt(sc.nextLine().trim());
                    if (chonMon < 1 || chonMon > MonHoc.KHO_MON_HOC.length) {
                        System.out.println(Color.RED + "Loi: Vui long chon tu 1 den " + MonHoc.KHO_MON_HOC.length + "!" + Color.RESET);
                        continue;
                    }
                    MonHoc monGoc = MonHoc.KHO_MON_HOC[chonMon - 1];

                    // Kiem tra trung mon da chon
                    boolean biTrung = false;
                    for (int j = 0; j < i; j++) {
                        if (chuongTrinhCuaLop[j].getMaMon().equals(monGoc.getMaMon())) {
                            biTrung = true;
                            break;
                        }
                    }
                    if (biTrung) {
                        System.out.println(Color.RED + "Loi: Mon " + monGoc.getTenMon() + " da ton tai trong chuong trinh!" + Color.RESET);
                        continue;
                    }

                    // Tao ban sao voi he so 1 mac dinh (khong sua vao KHO_MON_HOC)
                    chuongTrinhCuaLop[i] = monGoc.copyVoiHeSo(1);
                    System.out.println("  -> Da them: " + monGoc.getTenMon());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(Color.RED + "Loi: Vui long nhap so!" + Color.RESET);
                }
            }
        }

        // 5. He cua lop: Dai tra / Chuyen. Lop chuyen can it nhat 1 mon tinh GPA de lam mon chuyen
        boolean coMonTinhGPA = false;
        for (int i = 0; i < soMonCuaLop; i++) {
            if (chuongTrinhCuaLop[i].isTinhVaoGPA()) coMonTinhGPA = true;
        }

        boolean isLopChuyen = false;
        String monChuyenLop = "";
        String tenMonChuyen = "";
        double phuPhiChung = 0.0;

        if (!coMonTinhGPA) {
            System.out.println(Color.YELLOW + "Chuong trinh chi co mon khong tinh GPA nen lop chi co the la lop Dai tra." + Color.RESET);
        } else {
            while (true) {
                System.out.print("He cua lop (0: Dai tra / 1: Chuyen): ");
                String heLop = sc.nextLine().trim();
                if (heLop.equals("0") || heLop.equals("1")) {
                    isLopChuyen = heLop.equals("1");
                    break;
                }
                System.out.println(Color.RED + "Loi: Chi nhap 0 hoac 1!" + Color.RESET);
            }
        }

        if (isLopChuyen) {
            System.out.println("\nDanh sach cac mon cua lop " + tenLop + ":");
            for (int i = 0; i < soMonCuaLop; i++) {
                System.out.println((i + 1) + ". " + chuongTrinhCuaLop[i].getTenMon()
                        + (chuongTrinhCuaLop[i].isTinhVaoGPA() ? "" : " (khong tinh GPA)"));
            }
            while (true) {
                System.out.print("Chon STT mon lam mon chuyen (1-" + soMonCuaLop + "): ");
                try {
                    int sttChuyen = Integer.parseInt(sc.nextLine().trim());
                    if (sttChuyen < 1 || sttChuyen > soMonCuaLop) {
                        System.out.println(Color.RED + "Loi: Chi chon 1 den " + soMonCuaLop + "!" + Color.RESET);
                        continue;
                    }
                    MonHoc monDuocChon = chuongTrinhCuaLop[sttChuyen - 1];

                    // Mon khong tinh GPA (The duc) khong the lam mon chuyen
                    if (!monDuocChon.isTinhVaoGPA()) {
                        System.out.println(Color.RED + "Mon khong tinh GPA (" + monDuocChon.getTenMon() + ") khong the lam mon chuyen!" + Color.RESET);
                        continue;
                    }

                    // Nang he so mon chuyen len 2 va luu ten mon chuyen
                    monDuocChon.setHeSo(2);
                    monChuyenLop = monDuocChon.getMaMon();
                    tenMonChuyen = monDuocChon.getTenMon();
                    System.out.println(Color.GREEN + "  -> Thiet lap mon chuyen: " + tenMonChuyen + Color.RESET);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(Color.RED + "Loi: Vui long nhap so!" + Color.RESET);
                }
            }

            // Phu phi chuyen de: so huu han, khong am
            while (true) {
                System.out.print("Nhap phu phi chuyen de cua lop (VND): ");
                try {
                    phuPhiChung = Double.parseDouble(sc.nextLine().trim());
                    if (!Double.isNaN(phuPhiChung) && !Double.isInfinite(phuPhiChung) && phuPhiChung >= 0) break;
                    System.out.println(Color.RED + "Loi: Phu phi phai la so khong am!" + Color.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(Color.RED + "Loi: Sai dinh dang so!" + Color.RESET);
                }
            }
        }

        // 6. Tom tat + xac nhan truoc khi tao
        System.out.println("\n--- XAC NHAN TAO LOP ---");
        System.out.println("Ten lop   : " + tenLop);
        System.out.println("Khoa hoc  : " + khoaHoc);
        System.out.println("GVCN      : " + (gvcn != null ? gvcn.getHoTen() + " (" + gvcn.getMaGV() + ")" : "Chua phan cong"));
        System.out.println("He        : " + (isLopChuyen ? "Chuyen - mon chuyen " + tenMonChuyen + ", phu phi " + String.format("%,.0f", phuPhiChung) + " VND" : "Dai tra"));
        System.out.print("Chuong trinh: ");
        for (int i = 0; i < soMonCuaLop; i++) {
            System.out.print(chuongTrinhCuaLop[i].getTenMon() + "(x" + chuongTrinhCuaLop[i].getHeSo() + ")" + (i < soMonCuaLop - 1 ? ", " : "\n"));
        }
        System.out.print(Color.YELLOW + "Xac nhan tao lop? (Y/N): " + Color.RESET);
        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println(Color.YELLOW + "Da huy tao lop." + Color.RESET);
            return;
        }

        // 7. Tao lop va them vao danh sach (kiem tra ket qua: danh sach co the da day)
        LopHoc lopMoi = new LopHoc(tenLop, khoaHoc, gvcn, dsHsLop, isLopChuyen, monChuyenLop, phuPhiChung);
        lopMoi.setChuongTrinhHoc(chuongTrinhCuaLop);

        if (dsLopHoc.themLop(lopMoi)) {
            System.out.println(Color.GREEN + "Da tao lop " + tenLop + " thanh cong!" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Tao lop that bai: danh sach lop da day!" + Color.RESET);
        }
    }
        // Chuyen hoc sinh sang lop khac (goi tu menu 1.5, muc 5)
    private void chuyenHocSinhSangLop() {
        System.out.println("\n[CHUYEN HOC SINH SANG LOP KHAC]");
        System.out.print("Nhap MSHS can chuyen (0 = quay lai): ");
        String mshs = sc.nextLine().trim();
        if (mshs.equals("0")) return;

        HocSinh hs = dsHocSinh.timKiem(mshs);
        if (hs == null) {
            System.out.println(Color.RED + "Loi: Khong tim thay hoc sinh nay!" + Color.RESET);
            return;
        }
        System.out.println(Color.GREEN + "Tim thay: " + hs.getHoTen() + " - Lop hien tai: " + hs.getTenLop() + Color.RESET);

        System.out.print("Nhap ten lop moi (0 = quay lai): ");
        String tenLopMoi = sc.nextLine().trim();
        if (tenLopMoi.equals("0")) return;

        System.out.print(Color.YELLOW + "Xac nhan chuyen " + hs.getHoTen() + " sang lop " + tenLopMoi + "? (Y/N): " + Color.RESET);
        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
            return;
        }
        dsLopHoc.chuyenLop(mshs, tenLopMoi, dsHocSinh);
    }
    
    private LopHoc chonLop(LopHoc macDinh) {
        while (true) {
            if (macDinh != null) {
                System.out.print("Nhap ten lop (Enter = giu lop " + macDinh.getTenLop() + ", 0 = dung): ");
            } else {
                System.out.print("Nhap ten lop (0 = huy): ");
            }
            String ten = sc.nextLine().trim();
            if (ten.equals("0")) return null;
            if (ten.isEmpty() && macDinh != null) return macDinh;

            LopHoc lop = dsLopHoc.timKiemLopTheoTen(ten);
            if (lop != null) {
                if (lop.getDsHocSinh() == null) lop.setDsHocSinh(new DanhSachHocSinh(0));
                return lop;
            }
            System.out.println(Color.RED + "Khong tim thay lop " + ten + "!" + Color.RESET);
        }
    }

    private HocSinh taoHocSinh(LopHoc lop, DanhSachHocSinh dsTam) {
        String mshs;
        while (true) {
            mshs = DataValidator.nhapChuoiKhongRong(sc, "Nhap ma so hoc sinh (MSHS): ").toUpperCase();
            try {
                DataValidator.kiemTraTonTaiMa(mshs, dsHocSinh);
                DataValidator.kiemTraTonTaiMa(mshs, dsTam);
                break;
            } catch (TrungMaHocSinhException e) {
                System.out.println(e.getMessage());
            }
        }

        String hoTen = DataValidator.nhapChuoiKhongRong(sc, "Nhap ho ten: ");
        String ngaySinh = DataValidator.nhapNgaySinh(sc, "Nhap ngay sinh (dd/mm/yyyy): ");
        boolean gioiTinh = DataValidator.nhapSoNguyen(sc, "Gioi tinh (1: Nam / 0: Nu): ", 0, 1) == 1;
        String diaChi = DataValidator.nhapChuoiKhongRong(sc, "Nhap dia chi: ");
        String sdt = DataValidator.nhapSoDienThoai(sc, "Nhap so dien thoai: ");
        System.out.println("--- Thong tin Phu huynh ---");
        String tenPH = DataValidator.nhapChuoiKhongRong(sc, "Nhap ho ten Bo/Me: ");
        String sdtPH = DataValidator.nhapSoDienThoai(sc, "Nhap SDT khan cap cua Phu huynh: ");
        PhuHuynh phuHuynhMoi = new PhuHuynh(tenPH, sdtPH, mshs);

        DiemMonHoc[] dsDiemBanDau = new DiemMonHoc[0];
        HoatDong[] lichSuHDBanDau = new HoatDong[0];

        if (lop.isLopChuyen()) {
            return new HocSinhChuyen(mshs, hoTen, ngaySinh, gioiTinh, diaChi, sdt,
                    lop.getTenLop(), 0.0, 100, false,
                    phuHuynhMoi, dsDiemBanDau, 0, lichSuHDBanDau,
                    lop.getMonChuyenCuaLop(), 0.0, lop.getPhuPhiChung());
        }
        return new HocSinhDaiTra(hoTen, ngaySinh, gioiTinh, diaChi, sdt,
                mshs, lop.getTenLop(), 0.0, 100, false,
                phuHuynhMoi, dsDiemBanDau, 0, lichSuHDBanDau);
    }

    // Nhap 1 hoc sinh va them ngay vao truong + lop. Tra ve true neu them thanh cong.
    private boolean nhapMotHocSinh(LopHoc lop) {
        HocSinh hs = taoHocSinh(lop, null);
        if(dsHocSinh.them(hs) && lop.getDsHocSinh().them(hs)){
            luuPhuHuynh(hs);
            return true;
        }
        return false;
    }
    private void luuPhuHuynh(HocSinh hs) {
        PhuHuynh ph = hs.getPhuHuynh();
        if (ph == null) return;

        PhuHuynh daCo = dsPhuHuynh.timKiem(ph.getSdtKhanCap());
        if (daCo != null) {
            hs.setPhuHuynh(daCo);
            System.out.println(Color.YELLOW + "SDT " + ph.getSdtKhanCap() + " da co (phu huynh "
                    + daCo.getHoTenBoMe() + "), dung lai phu huynh nay." + Color.RESET);
        } else if (!dsPhuHuynh.them(ph)) {
            System.out.println(Color.RED + "Khong them duoc phu huynh " + ph.getHoTenBoMe() + "!" + Color.RESET);
        }
    }
    // Muc 1.2: them k hoc sinh vao lop (k = 1 la them 1 hoc sinh)
    private void themKHocSinh() {
        System.out.println("\n[THEM HOC SINH VAO LOP HOC]");
        LopHoc lop = chonLop(null);
        if (lop == null) return;

        // int conTrong = dsHocSinh.getArrHS().length - dsHocSinh.getSoLuong();

        int k;
        while (true) {
            System.out.print("Nhap so hoc sinh can them k (k >= 1): ");
            try {
                k = Integer.parseInt(sc.nextLine().trim());
                // if (k >= 1 && k <= conTrong) break;
                if (k >= 1) break; 
                System.out.println(Color.RED + "Loi: k phai lon hon hoac bang 1!" + Color.RESET);
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Loi: Vui long nhap so nguyen!" + Color.RESET);
            }
        }

        // Nhap du k hoc sinh vao danh sach tam, sau do them 1 lan vao truong va lop
        DanhSachHocSinh dsTam = new DanhSachHocSinh();
        for (int i = 1; i <= k; i++) {
            System.out.println("\n--- Hoc sinh thu " + i + "/" + k + " ---");
            dsTam.them(taoHocSinh(lop, dsTam));
        }

        int daThem = dsHocSinh.them(dsTam.getArrHS(), dsTam.getSoLuong()); 
        lop.getDsHocSinh().them(dsTam.getArrHS(), daThem);
        for (int i = 0; i < daThem; i++) {
            luuPhuHuynh(dsTam.getArrHS()[i]);
        }

        System.out.println(Color.GREEN + "Da them " + daThem + "/" + k
                + " hoc sinh vao lop " + lop.getTenLop() + "!" + Color.RESET);
    }

    // Muc 1.1: nhap n hoc sinh dau tien (khoi tao lai danh sach)
    private void nhapNHocSinhDauTien() {
        System.out.println("\n[NHAP N HOC SINH DAU TIEN]");

        if (dsLopHoc.getSoLuong() == 0) {
            System.out.println(Color.RED + "Chua co lop hoc nao! Hay tao lop truoc (muc 5>1. Them lop moi)." + Color.RESET);
            return;
        }

        if (dsHocSinh.getSoLuong() > 0) {
            System.out.print(Color.YELLOW + "Danh sach dang co " + dsHocSinh.getSoLuong()
                    + " hoc sinh. Nhap lai tu dau se XOA danh sach trong bo nho "
                    + "(file chi bi ghi de khi ban chon Luu du lieu). Tiep tuc? (Y/N): " + Color.RESET);
            if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
                System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
                return;
            }
            dsHocSinh.lamRong();
            for (int i = 0; i < dsLopHoc.getSoLuong(); i++) {
                LopHoc l = dsLopHoc.getDsLop()[i];
                if (l != null && l.getDsHocSinh() != null) l.getDsHocSinh().lamRong();
            }
        }

        int n;
        while (true) {
            System.out.print("Nhap so hoc sinh n (n >= 1):");
            try {
                n = Integer.parseInt(sc.nextLine().trim());
                if (n >= 1) break;
                System.out.println(Color.RED + "Loi: n phai lon hon hoac bang 1!" + Color.RESET);
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Loi: Vui long nhap so nguyen!" + Color.RESET);
            }
        }

        int daThem = 0;
        LopHoc lopHienTai = null;
        for (int i = 1; i <= n; i++) {
            System.out.println("\n--- Hoc sinh thu " + i + "/" + n + " ---");
            lopHienTai = chonLop(lopHienTai);
            if (lopHienTai == null) break;
            if (nhapMotHocSinh(lopHienTai)) {
                daThem++;
            } else {
                System.out.println(Color.RED + "Them hoc sinh that bai, dung nhap!" + Color.RESET);
                break;
            }
        }
        System.out.println(Color.GREEN + "Da nhap " + daThem + "/" + n + " hoc sinh." + Color.RESET);
    }
    private void xuatDanhSachHocSinh() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n---- 1.3 XUAT DANH SACH HOC SINH ----");
            System.out.println("1. Toan truong");
            System.out.println("2. Theo lop");
            System.out.println("3. Theo he (Chuyen / Dai tra)");
            System.out.println("0. Quay lai");
            chon = nhapLuaChon(3);

            switch (chon) {
                case 1:
                    System.out.println("\n--- DANH SACH HOC SINH TOAN TRUONG ---");
                    dsHocSinh.hienThiDanhSach();
                    break;

                case 2:
                    System.out.print("Nhap ten lop can xem (nhap 0 de huy): ");
                    String tenLopXem = sc.nextLine().trim();
                    if (tenLopXem.equals("0")) break;

                    LopHoc lopXem = dsLopHoc.timKiemLopTheoTen(tenLopXem);
                    if (lopXem != null) {
                        System.out.println("\n" + Color.CYAN + "========================================");
                        System.out.println(" THONG TIN LOP: " + lopXem.getTenLop().toUpperCase());
                        System.out.println("========================================" + Color.RESET);
                        if (lopXem.isLopChuyen()) {
                            MonHoc monChuyen = MonHoc.timTheoMa(lopXem.getMonChuyenCuaLop());
                            String tenMonChuyen = (monChuyen != null) ? monChuyen.getTenMon() : lopXem.getMonChuyenCuaLop();
                            System.out.println("- He: Chuyen " + tenMonChuyen);
                        } else {
                            System.out.println("- He: Dai tra");
                        }
                        if (lopXem.getGiaoVienChuNhiem() != null) {
                            System.out.println("- Giao vien chu nhiem: " + lopXem.getGiaoVienChuNhiem().getHoTen());
                        } else {
                            System.out.println("- Giao vien chu nhiem: " + Color.YELLOW + "Chua phan cong" + Color.RESET);
                        }
                    } else {
                        System.out.println(Color.YELLOW + "Chua co thong tin lop " + tenLopXem
                                + " trong he thong, chi liet ke hoc sinh co ten lop nay." + Color.RESET);
                    }
                    System.out.println("\n--- DANH SACH HOC SINH LOP " + tenLopXem.toUpperCase() + " ---");
                    dsHocSinh.hienThiTheoLop(tenLopXem);
                    break;

                case 3:
                    int chonHe = -1;
                    while (true) {
                        System.out.print("Chon he (1: Chuyen / 2: Dai tra): ");
                        try {
                            chonHe = Integer.parseInt(sc.nextLine().trim());
                            if (chonHe == 1 || chonHe == 2) break;
                            System.out.println(Color.RED + "Loi: Chi nhap 1 hoac 2!" + Color.RESET);
                        } catch (NumberFormatException e) {
                            System.out.println(Color.RED + "Loi: Vui long nhap so nguyen!" + Color.RESET);
                        }
                    }
                    System.out.println("\n--- DANH SACH HOC SINH " + (chonHe == 1 ? "CHUYEN" : "DAI TRA") + " ---");
                    dsHocSinh.hienThiTheoHe(chonHe == 1);
                    break;

                case 0:
                    break;

                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
            }
        }
    }

    private void menuQuanLyHocTap() {
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 2. QUAN LY HOC TAP ----");
            System.out.println("1. Nhap / cap nhat diem");
            System.out.println("2. Danh gia diem hanh kiem & ghi nhan hoat dong ");
            System.out.println("3. Bang diem lop hoc");
            System.out.println("4. Bang diem ca nhan");
            System.out.println("5. Quan ly thoi khoa bieu");
            System.out.println("0. Quay ve menu chinh");
            chon=nhapLuaChon(5);

            if(chon == 0) break;
            switch (chon) {
                case 1:
                    nhapDiemHocSinh();
                    break;
                case 2:   // Linh (Li3, Li4): danh gia hanh kiem & ghi nhan hoat dong
                case 3:   // Nam (N3): bang diem lop hoc (GPA giam dan)
                case 4:   // Nam (N3): bang diem ca nhan
                case 5:   // Nam (N5): quan ly thoi khoa bieu

                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;

            }
        }
    }

    private void nhapDiemHocSinh() {
        System.out.println("\n[NHAP / CAP NHAT DIEM]");
        System.out.print("Nhap MSHS can nhap diem (nhap 0 de huy): ");
        String mshs = sc.nextLine().trim();
        if (mshs.equals("0")) return;

        HocSinh hs = dsHocSinh.timKiem(mshs);
        if (hs == null) {
            System.out.println(Color.RED + "Khong tim thay hoc sinh co ma " + mshs + "!" + Color.RESET);
            return;
        }

        LopHoc lop = dsLopHoc.timKiemLopTheoTen(hs.getTenLop());
        if (lop == null) {
            System.out.println(Color.RED + "Loi: Khong tim thay lop " + hs.getTenLop() + "!" + Color.RESET);
            return;
        }

        MonHoc[] chuongTrinh = lop.getChuongTrinhHoc();
        int soMonCT = demMonHoc(chuongTrinh);
        if (soMonCT == 0) {
            System.out.println(Color.RED + "Loi: Lop " + lop.getTenLop()
                    + " chua duoc thiet lap cac mon hoc!" + Color.RESET);
            return;
        }

        System.out.println(Color.CYAN + "Hoc sinh: " + hs.getHoTen() + " - Lop " + hs.getTenLop() + Color.RESET);

        DiemMonHoc[] diemCu = hs.getDanhSachDiem();
        int soMonCu = hs.getSoLuongMon();
        DiemMonHoc[] bangDiem;
        int soMon;

        if (diemCu != null && soMonCu > 0) {
            // Da co diem: cho chon nhap lai tat ca hay chi sua mot mon
            System.out.println("Bang diem hien tai:");
            inBangDiem(diemCu, soMonCu);
            System.out.println("1. Nhap lai tat ca cac mon");
            System.out.println("2. Sua diem mot mon");
            System.out.println("0. Huy");
            int cachNhap = nhapLuaChon(2);
            if (cachNhap == 0) {
                System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
                return;
            }

            if (cachNhap == 2) {
                MonHoc mh = chonMonTrongChuongTrinh(chuongTrinh, soMonCT);
                if (mh == null) return;
                DiemMonHoc diemMoi = nhapDiemMotMon(mh);

                int viTri = -1;
                for (int i = 0; i < soMonCu; i++) {
                    if (diemCu[i] != null && diemCu[i].getMonHoc() != null
                            && diemCu[i].getMonHoc().getMaMon().equalsIgnoreCase(mh.getMaMon())) {
                        viTri = i;
                        break;
                    }
                }
                // Mang vua du so mon, khong cap phat du
                soMon = (viTri >= 0) ? soMonCu : soMonCu + 1;
                bangDiem = new DiemMonHoc[soMon];
                for (int i = 0; i < soMonCu; i++) bangDiem[i] = diemCu[i];
                bangDiem[(viTri >= 0) ? viTri : soMonCu] = diemMoi;
            } else {
                bangDiem = nhapDiemTatCaMon(chuongTrinh, soMonCT);
                soMon = soMonCT;
            }
        } else {
            bangDiem = nhapDiemTatCaMon(chuongTrinh, soMonCT);
            soMon = soMonCT;
        }

        // Cap nhat vao hoc sinh
        hs.setDanhSachDiem(bangDiem);
        hs.setSoLuongMon(soMon);
        double gpaMoi = DiemMonHoc.tinhGPATongHop(bangDiem);
        hs.setGpa(gpaMoi);

        if (hs instanceof HocSinhChuyen) {
            capNhatDiemMonChuyen((HocSinhChuyen) hs, bangDiem, soMon);
        }

        System.out.println("\n" + Color.GREEN + "Da cap nhat bang diem thanh cong!" + Color.RESET);
        inBangDiem(bangDiem, soMon);
        System.out.println("=> GPA moi cua " + hs.getHoTen() + ": " + gpaMoi);
    }

    private int demMonHoc(MonHoc[] chuongTrinh) {
        if (chuongTrinh == null) return 0;
        int dem = 0;
        for (MonHoc mh : chuongTrinh) {
            if (mh != null) dem++;
        }
        return dem;
    }

    // Nhap TX, GK, CK cho mot mon
    private DiemMonHoc nhapDiemMotMon(MonHoc mh) {
        System.out.println("\n--- Mon: " + Color.YELLOW + mh.getTenMon() + Color.RESET
                + " (He so: " + mh.getHeSo() + ")");
        double dTX = DataValidator.nhapDiem(sc, "   + Diem TX: ");
        double dGK = DataValidator.nhapDiem(sc, "   + Diem GK: ");
        double dCK = DataValidator.nhapDiem(sc, "   + Diem CK: ");
        return new DiemMonHoc(mh, dTX, dGK, dCK);
    }

    // Nhap diem cho tat ca cac mon trong chuong trinh lop
    private DiemMonHoc[] nhapDiemTatCaMon(MonHoc[] chuongTrinh, int soMonCT) {
        DiemMonHoc[] bangDiem = new DiemMonHoc[soMonCT];
        int dem = 0;
        for (MonHoc mh : chuongTrinh) {
            if (mh == null) continue;
            bangDiem[dem++] = nhapDiemMotMon(mh);
        }
        return bangDiem;
    }

    // In danh sach mon cua lop de chon mot mon; tra ve null neu huy
    private MonHoc chonMonTrongChuongTrinh(MonHoc[] chuongTrinh, int soMonCT) {
        MonHoc[] dsMon = new MonHoc[soMonCT];
        int dem = 0;
        for (MonHoc mh : chuongTrinh) {
            if (mh != null) dsMon[dem++] = mh;
        }
        System.out.println("Chon mon can sua diem:");
        for (int i = 0; i < dem; i++) {
            System.out.println((i + 1) + ". " + dsMon[i].getTenMon());
        }
        System.out.println("0. Huy");
        int chonMon = nhapLuaChon(dem);
        if (chonMon == 0) {
            System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
            return null;
        }
        return dsMon[chonMon - 1];
    }

    private void inBangDiem(DiemMonHoc[] bangDiem, int soMon) {
        for (int i = 0; i < soMon; i++) {
            if (bangDiem[i] != null) System.out.println("  " + bangDiem[i]);
        }
    }

    // Mon chuyen hien luu bang ten (vd "Toan"); sau viec M8 se luu bang ma (vd "TOAN").
    // So ca ma lan ten de chay dung voi ca hai kieu du lieu.
    private boolean laMonChuyen(MonHoc mh, String monChuyen) {
        if (mh == null || monChuyen == null) return false;
        String mc = monChuyen.trim();
        return mh.getMaMon().equalsIgnoreCase(mc) || mh.getTenMon().equalsIgnoreCase(mc);
    }

    // Hoc sinh chuyen: diem mon chuyen = diem TB cua mon chuyen
    private void capNhatDiemMonChuyen(HocSinhChuyen hsc, DiemMonHoc[] bangDiem, int soMon) {
        for (int i = 0; i < soMon; i++) {
            if (bangDiem[i] != null && laMonChuyen(bangDiem[i].getMonHoc(), hsc.getMonChuyen())) {
                hsc.setDiemMonChuyen(Math.round(bangDiem[i].tinhDiemTBMon() * 100.0) / 100.0);
                return;
            }
        }
        System.out.println(Color.YELLOW + "Canh bao: mon chuyen \"" + hsc.getMonChuyen()
                + "\" khong co trong bang diem, chua cap nhat diem mon chuyen." + Color.RESET);
    }

    private void menuQuanLyTaiChinh() {
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 3. QUAN LY TAI CHINH ----");
            System.out.println("1. Tra cuu hoc phi ca nhan");
            System.out.println("2. Xet duyet hoc bong");
            System.out.println("3. Xuat bao cao thu phi");
            System.out.println("4. Cap nhat trang thai thu tien");
            System.out.println("5. Danh sach hoc sinh no hoc phi");
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon(5);

            if(chon == 0) break;

            switch (chon) {
                case 1:   // Linh (Li7): tra cuu hoc phi ca nhan
                case 2:   // Linh (Li7): xet duyet hoc bong
                case 3:   // Linh (Li7): bao cao thu phi
                case 4:   // Linh (Li7): cap nhat thu tien & danh sach no
                case 5:   // Linh (Li7): danh sach hoc sinh no hoc phi

                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;
            }
        }
    }

    private void menuThongKe(){
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 4. THONG KE & XET DUYET ----");
            System.out.println("1. Thong ke ty le len lop / o lai lop");
            System.out.println("2. Top 5 hoc sinh xuat sac nhat");
            System.out.println("3. Bao cao thong ke tong hop");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon(3);

            if(chon == 0) break;
            switch (chon) {
                case 1:
                    System.out.println("\n--- TY LE LEN LOP / O LAI ---");
                    dsHocSinh.tinhTyLeDat();
                    System.out.println(" Ghi chu: Quy tac len lop (GPA >= 5.0)");
                    break;
                case 2:
                    System.out.println("\n--- TOP 5 HOC SINH XUAT SAC NHAT ---");
                    dsHocSinh.inTop5XuatSac();
                    break;
                case 3:
                    dsHocSinh.inBaoCaoThongKe();
                    break;
                default:
                    System.out.println(Color.RED + "Lua chon khong hop le, vui long nhap lai!" + Color.RESET);
                    break;
            }
        }
    }

    private void menuQuanLyDuLieu(){
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 5. QUAN LY DU LIEU ----");
            System.out.println("1. Luu du lieu xuong file");
            System.out.println("2. Tai du lieu tu file len he thong");
            System.out.println("3. Xem du lieu tong quan hien co");
            System.out.println("4. Xoa du lieu bo nho");
            System.out.println("5. Xuat bao cao thong ke ra file baocao.txt");
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon(5);

            if(chon == 0) break;
            switch (chon) {
                case 1:   // Lieu (Le5): goi luuTatCa(), dung chung voi muc 0 khi thoat
                case 2:   // Lieu (Le5): goi taiTatCa(), dung chung voi luc khoi dong
                case 3:   // Lieu (Le6): xem tong quan (so HS, GV, lop, PH)
                case 4:   // Lieu (Le6): xoa du lieu bo nho (hoi xac nhan)
                    System.out.println(Color.YELLOW + "Chuc nang dang duoc xay dung." + Color.RESET);
                    break;
                case 5:
                    xuatBaoCaoRaFile();
                    break;
                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;
            }
        }
    }
    // Muc 5.5 (Lieu - Le8): ghi bao cao tong hop ra file baocao.txt
    public static final String DUONG_DAN_BAO_CAO = "src/data/baocao.txt";

    private void xuatBaoCaoRaFile() {
        IThongKe[] dsThongKe = { dsHocSinh, dsGiaoVien, dsPhuHuynh };
        PrintStream manHinh = System.out;
        ByteArrayOutputStream boDem = new ByteArrayOutputStream();
        String noiDung;
        try {
            System.setOut(new PrintStream(boDem, true, "UTF-8"));
            for (int i = 0; i < dsThongKe.length; i++) {
                dsThongKe[i].inBaoCaoThongKe();  
                System.out.println();
            }
            System.out.flush();
            noiDung = boDem.toString("UTF-8");
        } catch (IOException e) {
            System.out.println(Color.RED + "Loi khi xuat bao cao: " + e.getMessage() + Color.RESET);
            return;
        } finally {
            System.setOut(manHinh);             
        }

        noiDung = noiDung.replaceAll("\\u001B\\[[;\\d]*m", "");

        File f = new File(DUONG_DAN_BAO_CAO);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), "UTF-8"))) {
            bw.write(noiDung);
            System.out.println(Color.GREEN + "Da xuat bao cao ra " + DUONG_DAN_BAO_CAO + Color.RESET);
        } catch (IOException e) {
            System.out.println(Color.RED + "Loi khi ghi bao cao: " + e.getMessage() + Color.RESET);
        }
    }

    private void menuQuanLyNhanSuVaPH(){
        int chon=-1;
        while(chon != 0) {
            System.out.println("\n---- 6. QUAN LY NHAN SU & PHU HUYNH ----");
            System.out.println("1. Quan ly danh sach giao vien");
            System.out.println("2. Quan ly danh sach phu huynh");
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon(2);

            if(chon == 0) break;
            switch (chon) {
                case 1:
                    menuQuanLyGiaoVien();
                    break;
                case 2:
                    menuQuanLyPhuHuynh();
                    break;
                default:
                    System.out.println(Color.RED + "Lua chon khong hop le!" + Color.RESET);
                    break;
            }
        }
    }
    private void menuQuanLyGiaoVien() {
        int chon = -1;
    while (chon != 0) {
        System.out.println("\n---- QUAN LY DANH SACH GIAO VIEN ----");
        System.out.println("1. Xem danh sach giao vien");
        System.out.println("2. Them giao vien moi");
        System.out.println("3. Sua thong tin giao vien");
        System.out.println("4. Xoa giao vien");
        System.out.println("5. Tim kiem giao vien");
        System.out.println("6. Bao cao thong ke giao vien");
        System.out.println("7. Phan cong GVCN / Lop day");
        System.out.println("0. Quay lai");
        chon = nhapLuaChon(7);
        if(chon == 0){break;}
        switch (chon){
            case 1:
                dsGiaoVien.hienThiDanhSach();
                break;
            
            case 2:
                System.out.println("THEM GIAO VIEN MOI");
                String maGV = DataValidator.nhapChuoiKhongRong(sc, "Nhap ma giao vien: ").toUpperCase();
                if(dsGiaoVien.timKiem(maGV) != null){
                    System.out.println(Color.RED + "Loi: Ma giao vien " + maGV + " da ton tai!" + Color.RESET);
                    break;
                }
                String hoTen = DataValidator.nhapChuoiKhongRong(sc, "Nhap ho ten: ");
                String ngaySinh = DataValidator.nhapNgaySinh(sc, "Nhap ngay sinh (DD/MM/YYYY): ");
                boolean gioiTinh = DataValidator.nhapSoNguyen(sc, "Gioi tinh (1: Nam / 0: Nu): ", 0, 1) == 1;
                String diaChi = DataValidator.nhapChuoiKhongRong(sc, "Nhap dia chi: ");
                String sdt = DataValidator.nhapSoDienThoai(sc, "Nhap so dien thoai: ");
                String chucVu = DataValidator.nhapChuoiKhongRong(sc, "Nhap chuc vu (VD: Giao vien, To truong): ");
                
                double luong = 0;
                while (true) { 
                    System.out.print("Nhap muc luong: ");
                    try {
                        luong = Double.parseDouble(sc.nextLine().trim());
                        if (luong >= 0) break;
                        System.out.println(Color.RED + "Loi: Luong khong duoc la so am" + Color.RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(Color.RED + "Loi: Luong phai la so thuc hop le" + Color.RESET);
                    }
                }

                System.out.println("--- Chon mon chuyen mon ---");
                for (int i = 0; i < MonHoc.KHO_MON_HOC.length; i++) {
                    System.out.println((i + 1) + ". " + MonHoc.KHO_MON_HOC[i].getTenMon() + " (" + MonHoc.KHO_MON_HOC[i].getMaMon() + ")");
                }
                System.out.println("0. Chua phan mon");
                int chonMon = nhapLuaChon(MonHoc.KHO_MON_HOC.length);
                MonHoc monChuyenMon = (chonMon > 0) ? MonHoc.KHO_MON_HOC[chonMon - 1] : null;

                int maxLop = (dsLopHoc.getSoLuong() > 0) ? dsLopHoc.getSoLuong() : 20;
                int soLop = DataValidator.nhapSoNguyen(sc, "Nhap so lop giang day ban dau (0 = chua co): ", 0, maxLop);
                LopHoc[] dsLopDay = null;
                if (soLop > 0) {
                    dsLopDay = new LopHoc[soLop];
                    for (int i = 0; i < soLop; i++) {
                        System.out.print("  + Nhap ten lop thu " + (i + 1) + ": ");
                        String tenLop = sc.nextLine().trim();
                        LopHoc lopTim = dsLopHoc.timKiemLopTheoTen(tenLop);
                        dsLopDay[i] = (lopTim != null) ? lopTim : new LopHoc(tenLop, "", null, null, false, "", 0.0);
                    }
                }
                GiaoVien gvMoi = new GiaoVien(maGV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, chucVu, luong, monChuyenMon, dsLopDay);
                if(dsGiaoVien.them(gvMoi)){
                    System.out.println(Color.GREEN + "Them giao vien thanh cong!" + Color.RESET);
                    dsGiaoVien.ghiFile();
                }
                else{
                    System.out.println(Color.RED + "Them giao vien that bai!" + Color.RESET);
                }
                break;
            
            case 3:
                System.out.print("Nhap ma giao vien can sua: ");
                String maSua = sc.nextLine().trim();
                if(dsGiaoVien.sua(maSua, sc)){
                    System.out.println(Color.GREEN + "Cap nhat thong tin giao vien thanh cong!" + Color.RESET);
                    dsGiaoVien.ghiFile();
                }else {
                    System.out.println(Color.RED + "Khong tim thay giao vien co ma " + maSua + "!" + Color.RESET);
                }
                break;
            
            case 4:
                System.out.print("Nhap ma giao vien can xoa: ");
                String maXoa = sc.nextLine().trim();
                GiaoVien gvXoa = dsGiaoVien.timKiem(maXoa);
                if(gvXoa == null){
                    System.out.println(Color.RED + "Khong tim thay giao vien co ma " + maXoa + "!" + Color.RESET);
                    break;
                }
                System.out.print(Color.YELLOW + "Xac nhan xoa giao vien " + gvXoa.getHoTen() + "? (Y/N): " + Color.RESET);
                if(sc.nextLine().trim().equalsIgnoreCase("Y")){
                    if(dsGiaoVien.xoa(maXoa)){
                        System.out.println(Color.GREEN + "Da xoa giao vien thanh cong!" + Color.RESET);
                        dsGiaoVien.ghiFile();
                    }
                }
                else{
                    System.out.println(Color.YELLOW + "Da huy lenh xoa." + Color.RESET);    
                    }
                break;
            case 5:
                System.out.println("\n--- TIM KIEM GIAO VIEN ---");
                System.out.println("1. Tim chinh xac theo ma GV");
                System.out.println("2. Tim gan dung theo ten");
                System.out.println("3. Tim theo mon");
                System.out.println("4. Tim theo chuc vu");
                System.out.println("0. Quay lai");
                int loaiTim = nhapLuaChon(4);
                
                if (loaiTim == 1) {
                    System.out.print("Nhap ma giao vien: ");
                    GiaoVien gv = dsGiaoVien.timKiem(sc.nextLine().trim());
                    if(gv != null){
                        gv.hienThiThongTin();
                    }
                    else{
                        System.out.println(Color.RED + "Khong tim thay giao vien!" + Color.RESET);
                    }
                }
                else if (loaiTim == 2) {
                    System.out.print("Nhap ten giao vien can tim: ");
                    String tuKhoa = sc.nextLine().trim().toLowerCase();
                    GiaoVien[] arrGV = dsGiaoVien.getArrGV();
                    boolean thay = false;
                    for (int i = 0; i < dsGiaoVien.getSoLuong(); i++) {
                        if(arrGV[i] != null && arrGV[i].getHoTen().toLowerCase().contains(tuKhoa)){
                            arrGV[i].hienThiThongTin();
                            System.out.println("-------------------------");
                            thay = true;
                        }
                    }
                    if(!thay){
                        System.out.println(Color.RED + "Khong tim thay giao vien nao phu hop!" + Color.RESET);
                    }
                }
                else if (loaiTim == 3) {
                    System.out.println("--- Chon mon chuyen mon ---");
                    for (int i = 0; i < MonHoc.KHO_MON_HOC.length; i++) {
                        System.out.println((i + 1) + ". " + MonHoc.KHO_MON_HOC[i].getTenMon() + " (" + MonHoc.KHO_MON_HOC[i].getMaMon() + ")");
                    }

                    int chonMonTim = nhapLuaChon(MonHoc.KHO_MON_HOC.length);
                    if(chonMonTim > 0){
                        MonHoc monCanTim = MonHoc.KHO_MON_HOC[chonMonTim - 1];
                        GiaoVien[] ketqua = dsGiaoVien.timKiemTheoMon(monCanTim.getTenMon());
                        if(ketqua.length > 0){
                            for (int i = 0; i < ketqua.length; i++) {
                                ketqua[i].hienThiThongTin();
                                System.out.println("-------------------------");
                            }
                        } 
                        else{
                            System.out.println(Color.RED + "Khong tim thay giao vien nao phu hop!" + Color.RESET);
                    }
                        }                 
                    }
                else if (loaiTim == 4) {
                    System.out.print("Nhap chuc vu can tim (VD: Giao vien, To truong): ");
                    String chucVuTim = sc.nextLine().trim().toLowerCase();
                    GiaoVien[] arrGV = dsGiaoVien.getArrGV();
                    boolean thay = false;
                    for (int i = 0; i < dsGiaoVien.getSoLuong(); i++) {
                        if(arrGV[i] != null && arrGV[i].getChucVu() != null && arrGV[i].getChucVu().toLowerCase().contains(chucVuTim)){
                            arrGV[i].hienThiThongTin();
                            System.out.println("-------------------------");
                            thay = true;
                        }
                    }
                    if(!thay){
                        System.out.println(Color.RED + "Khong tim thay giao vien nao phu hop!" + Color.RESET);
                    }
                }
                break;
            case 6:
                dsGiaoVien.inBaoCaoThongKe();
                break;
            case 7:
                System.out.println("\n--- PHAN CONG GVCN / LOP DAY ---");
                System.out.print("Nhap ma giao vien can phan cong: ");
                String maPC = sc.nextLine().trim();
                GiaoVien gvPC = dsGiaoVien.timKiem(maPC);
                if (gvPC == null) {
                    System.out.println(Color.RED + "Khong tim thay giao vien co ma " + maPC + "!" + Color.RESET);
                    break;
                }
                System.out.println("Giao vien: " + Color.CYAN + gvPC.getHoTen() + Color.RESET);
                System.out.println("1. Phan cong lam Giao vien chu nhiem (GVCN)");
                System.out.println("2. Cap nhat danh sach lop giang day");
                System.out.println("0. Huy");
                int chonPC = nhapLuaChon(2);

                if(chonPC == 1){
                    System.out.print("Nhap ten lop chu nhiem: ");
                    String tenLopCN = sc.nextLine().trim();
                    LopHoc lopCN = dsLopHoc.timKiemLopTheoTen(tenLopCN);
                    if(lopCN != null){
                        lopCN.setGiaoVienChuNhiem(gvPC);
                        System.out.println(Color.GREEN + "Da phan cong " + gvPC.getHoTen() + " lam GVCN lop " + lopCN.getTenLop() + "!" + Color.RESET);
                    }
                    else{
                        System.out.println(Color.RED + "Khong tim thay lop " + tenLopCN + " trong he thong!" + Color.RESET);
                    }
                }
                else if (chonPC == 2) {
                    int maxLopMoi = (dsLopHoc.getSoLuong() > 0) ? dsLopHoc.getSoLuong() : 20;
                    int soLopMoi = DataValidator.nhapSoNguyen(sc, "Nhap tong so lop day moi: ", 0, maxLopMoi);
                    LopHoc[] dsLopMoi = new LopHoc[soLopMoi];
                    for (int i = 0; i < soLopMoi; i++) {
                        System.out.print("  + Nhap ten lop thu " + (i + 1) + ": ");
                        String tenL = sc.nextLine().trim();
                        LopHoc lopTim = dsLopHoc.timKiemLopTheoTen(tenL);
                        dsLopMoi[i] = (lopTim != null) ? lopTim : new LopHoc(tenL, "", null, null, false, "", 0.0);
                    }
                    gvPC.setDsLopDay(dsLopMoi);
                    System.out.println(Color.GREEN + "Da cap nhat danh sach lop day cho giao vien!" + Color.RESET);
                }
                break;
            }
        }
    }
    private void menuQuanLyPhuHuynh() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n---- QUAN LY DANH SACH PHU HUYNH ----");
            System.out.println("1. Xem danh sach phu huynh");
            System.out.println("2. Them phu huynh moi");
            System.out.println("3. Sua thong tin phu huynh");
            System.out.println("4. Xoa phu huynh");
            System.out.println("5. Tim kiem phu huynh theo ten/sdt");
            System.out.println("6. Tra cuu phu huynh cua hoc sinh (theo MSHS)");
            System.out.println("0. Quay lai");
            chon = nhapLuaChon(6);
            switch (chon) {
                case 1:
                    dsPhuHuynh.hienThiDanhSach();
                    break;
                case 2:
                    System.out.println("THEM MOI PHU HUYNH");
                    String sdtKhanCap = DataValidator.nhapSoDienThoai(sc,"Nhap so dien thoai khan cap: ");
                    if(dsPhuHuynh.timKiem(sdtKhanCap) != null){
                        System.out.println(Color.RED + "Loi: So dien thoai " + sdtKhanCap + " da ton tai!" + Color.RESET);
                        break;
                    }
                    String hoTenBoMe = DataValidator.nhapChuoiKhongRong(sc, "Nhap ho ten phu huynh: ");
                    String mshsMoi = "";
                    while (true) {
                        System.out.print("Nhap so luong MSHS lien ket (nhap 0 neu chua co): ");
                        String s = sc.nextLine().trim();
                        if (s.isEmpty()) {
                            break;
                        }
                        try {
                            int soLuongHS = Integer.parseInt(s);
                            if (soLuongHS >= 0) {
                                if (soLuongHS == 0) {
                                    mshsMoi = "";
                                    break;
                                }
                                boolean nhapThanhCong = true;
                                String tempMshs = "";
                                for (int i = 0; i < soLuongHS; i++) {
                                    System.out.print("  + Nhap MSHS thu " + (i + 1) + ": ");
                                    String maHS = sc.nextLine().trim();
                                    if (maHS.isEmpty() || maHS.contains(",")) {
                                        System.out.println(Color.RED + "Loi: MSHS khong duoc de trong hoac chua dau phay! Vui long nhap lai tu dau" + Color.RESET);
                                        nhapThanhCong = false;
                                        break;
                                    }
                                    if (tempMshs.isEmpty()) {
                                        tempMshs = maHS;
                                    } else {
                                        tempMshs = tempMshs + ";" + maHS;
                                    }
                                }
                                if (nhapThanhCong) {
                                    mshsMoi = tempMshs;
                                    break;
                                }
                            } else {
                                System.out.println(Color.RED + "Loi: So luong khong duoc la so am" + Color.RESET);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(Color.RED + "Loi: So luong phai la so nguyen hop le" + Color.RESET);
                        }
                    }
                    PhuHuynh phMoi = new PhuHuynh(hoTenBoMe, sdtKhanCap, mshsMoi);
                    if(dsPhuHuynh.them(phMoi)){
                        System.out.println(Color.GREEN + "Them phu huynh moi thanh cong!" + Color.RESET);
                        dsPhuHuynh.ghiFile();
                    }
                    else{
                        System.out.println(Color.RED + "Them phu huynh moi that bai!" + Color.RESET);
                    }
                    break;
                case 3:
                    System.out.print("Nhap so dien thoai khan cap cua phu huynh can sua: ");
                    String sdtSua = sc.nextLine().trim();
                    if(dsPhuHuynh.sua(sdtSua, sc)){
                        System.out.println(Color.GREEN + "Cap nhat thong tin phu huynh thanh cong!" + Color.RESET);
                        dsPhuHuynh.ghiFile();
                    } else {
                        System.out.println(Color.RED + "Khong tim thay phu huynh co SĐT nay!" + Color.RESET);
                    }
                    break;
                case 4:
                    System.out.println("Nhap so dien thoai khan cap cua phu huynh can xoa: ");
                    String sdtXoa = sc.nextLine().trim();
                    PhuHuynh phXoa = dsPhuHuynh.timKiem(sdtXoa);
                    if(phXoa == null){
                        System.out.println(Color.RED + "Khong tim thay phu huynh co SĐT nay!" + Color.RESET);
                    }
                    System.out.print(Color.YELLOW + "Xac nhan xoa phu huynh " + phXoa.getHoTenBoMe() + "? (Y/N): " + Color.RESET);
                    if(sc.nextLine().trim().equalsIgnoreCase("Y")){
                        if(dsPhuHuynh.xoa(sdtXoa)){
                            System.out.println(Color.GREEN + "Da xoa phu huynh thanh cong!" + Color.RESET);
                            dsPhuHuynh.ghiFile();
                        }  
                    } else{
                        System.out.println(Color.YELLOW + "Da huy lenh xoa." + Color.RESET); 
                    }
                    break;
                case 5:
                    System.out.println("\n--- TIM KIEM PHU HUYNH ---");
                    System.out.println("1. Tim chinh xac theo so dien thoai");
                    System.out.println("2. Tim gan dung theo ten");
                    System.out.println("0. Quay lai");
                    int loaiTim = nhapLuaChon(2);

                    if(loaiTim == 1){
                        System.out.print("Nhap so dien thoai cua phu huynh can  tim: ");
                        PhuHuynh ph = dsPhuHuynh.timKiem(sc.nextLine().trim());
                        if(ph != null){
                            ph.toFileString();
                        }
                        else{
                            System.out.println(Color.RED + "Khong tim thay phu huynh nao co so dien thoai nay!" + Color.RESET);
                        }
                    }
                    else if(loaiTim == 2){
                        System.out.print("Nhap ten cua phu huynh can  tim: ");
                        PhuHuynh[] arrPH = dsPhuHuynh.timKiemTheoTen(sc.nextLine().trim());
                        if(arrPH.length > 0)
                            for (int i = 0; i < arrPH.length; i++) {
                                arrPH[i].toFileString();

                            }
                        }
                    }
                         
        }
    }
    private void menuTimKiem() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n TIM KIEM HOC SINH");
            System.out.println("1. Tim chinh xac theo Ma hoc sinh");
            System.out.println("2. Tim gan dung theo ten (tu khoa)");
            System.out.println("3. Tim theo lop");
            System.out.println("4. Tim theo he dao tao (Chuyen/Dai tra)");
            System.out.println("5. Tim theo gioi tinh (Nam/Nu)");
            System.out.println("6. Tim theo khoang diem GPA");
            System.out.println("0. Quay lai");
            chon = nhapLuaChon(6);

            if (chon == 0) break;
            models.HocSinh[] kq = null;
            switch (chon) {
                case 1:
                    System.out.print("Nhap MSHS can tim: ");
                    String mshs = sc.nextLine().trim();
                    models.HocSinh hs = dsHocSinh.timKiem(mshs);
                    if (hs != null) kq = new models.HocSinh[]{hs}; 
                    break;
                case 2:
                    System.out.print("Nhap ten hoac tu khoa can tim: ");
                    kq = dsHocSinh.timKiemTheoTen(sc.nextLine().trim());
                    break;
                case 3:
                    System.out.print("Nhap ten lop (vd: 10A1): ");
                    kq = dsHocSinh.timKiemTheoLop(sc.nextLine().trim());
                    break;
                case 4:
                    int chonHe = DataValidator.nhapSoNguyen(sc, "Chon he (1: Chuyen, 2: Dai tra): ", 1, 2);
                    kq = dsHocSinh.timKiemTheoHe(chonHe == 1);
                    break;
                case 5:
                    int chonGT = DataValidator.nhapSoNguyen(sc, "Chon gioi tinh (1: Nam, 2: Nu): ", 1, 2);
                    kq = dsHocSinh.timKiemTheoGioiTinh(chonGT == 1);
                    break;
                case 6:
                    try {
                        System.out.print("Nhap diem GPA thap nhat: ");
                        double min = Double.parseDouble(sc.nextLine().trim());
                        System.out.print("Nhap diem GPA cao nhat: ");
                        double max = Double.parseDouble(sc.nextLine().trim());
                        kq = dsHocSinh.timKiemTheoGpa(min, max);
                    } catch (NumberFormatException e) {
                        System.out.println(" Diem GPA phai la so thuc hop le!");
                    }
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long nhap lai!");
                    continue; 
            }

            // --- IN KET QUA ---
            if (chon >= 1 && chon <= 6) {
                if (kq == null || kq.length == 0) {
                    System.out.println(" Khong tim thay ket qua nao phu hop!");
                } else {
                    System.out.println("\n=> Da tim thay " + kq.length + " hoc sinh:");
                    for (models.HocSinh h : kq) {
                        h.hienThiThongTin();
                        System.out.println("-------------------------");
                    }
                }
            }
        }
    }

}
