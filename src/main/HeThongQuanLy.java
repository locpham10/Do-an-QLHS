package main;

import collections.DanhSachHocSinh;
import collections.DanhSachGiaoVien;
import collections.DanhSachPhuHuynh;
import collections.DanhSachLopHoc;
import exceptions.TrungMaHocSinhException;
import models.*;
import utils.Color;
import utils.DataValidator;

import java.sql.SQLOutput;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
        System.out.println("    HỆ THỐNG QUẢN LÝ HỌC SINH");
        System.out.println("========================================" + Color.RESET);
        System.out.println("1. Quản lý hồ sơ");
        System.out.println("2. Quản lý học tập");
        System.out.println("3. Quản lý tài chính");
        System.out.println("4. Thống kê & xét duyệt");
        System.out.println("5. Quản lý dữ liệu");
        System.out.println("6. Quan ly nhan su & phu huynh");
        System.out.println("0. Thoát chương trình");
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
                        System.out.println(Color.GREEN + "Cảm ơn bạn đã sử dụng phần mềm. Tạm biệt!" + Color.RESET);
                        break;
                }
            } catch (NoSuchElementException | IllegalStateException e){
                System.out.println(Color.RED + "\nKhong con du lieu nhap, thoat chuong trinh." + Color.RESET);
            } catch (RuntimeException e){
                // Lỗi ngoài dự kiến trong menu con: báo rồi quay về menu chính
                System.out.println(Color.RED + "Loi khong mong muon: " + e + Color.RESET);
            }
        }
    }

    private void taiDuLieuBanDau() {   // sau này Liểu thay bằng taiTatCa(), vẫn giữ lớp try này
        try { dsGiaoVien.docFile(); } catch (RuntimeException e) { baoLoiTai("giaovien.txt", e); }
        try { dsLopHoc.docFile(); }   catch (RuntimeException e) { baoLoiTai("lophoc.txt", e); }
        try { dsHocSinh.docFile(); }  catch (RuntimeException e) { baoLoiTai("hocsinh.txt", e); }
        try { dsPhuHuynh.docFile(); } catch (RuntimeException e) { baoLoiTai("phuhuynh.txt", e); }
    }

    private void baoLoiTai(String tenFile, RuntimeException e) {
        System.out.println(Color.RED + "Loi khi doc " + tenFile + ": " + e + Color.RESET);
    }

    private int nhapLuaChon(int max) {
        return DataValidator.nhapSoNguyen(sc, "Nhap lua chon (0-" + max + "): ", 0, max);
    }

    private int nhapLuaChon() {
        int luaChon = -1;
        try {
            System.out.print("Nhap lua chon cua ban: ");
            luaChon = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(Color.RED + "Lỗi: Vui lòng nhập số hợp lệ" + Color.RESET);
        }
        return luaChon;
    }

    private void menuQuanLyHoSo() {
        int chon = -1;
        while (chon != 0) {
            System.out.println("\n---- 1. QUAN LY HO SO ----");
            System.out.println("1. Nhap n hoc sinh dau tien");
            System.out.println("2. Them hoc sinh vao lop hoc");
            System.out.println("3. Xuat danh sach hoc sinh");
            System.out.println("4. Sua/Xoa hoc sinh");
            System.out.println("5. Them lop hoc moi");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon();

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

                case 5:
                    themLopHocMoi();
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

        DanhSachHocSinh dsHsLop = new DanhSachHocSinh();

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
                    monChuyenLop = monDuocChon.getTenMon();
                    System.out.println(Color.GREEN + "  -> Thiet lap mon chuyen: " + monChuyenLop + Color.RESET);
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
        System.out.println("He        : " + (isLopChuyen ? "Chuyen - mon chuyen " + monChuyenLop + ", phu phi " + String.format("%,.0f", phuPhiChung) + " VND" : "Dai tra"));
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

        if (dsLopHoc.themLopHoc(lopMoi)) {
            System.out.println(Color.GREEN + "Da tao lop " + tenLop + " thanh cong!" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Tao lop that bai: danh sach lop da day (toi da "
                    + dsLopHoc.getDsLop().length + " lop)!" + Color.RESET);
        }
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
                if (lop.getDsHocSinh() == null) lop.setDsHocSinh(new DanhSachHocSinh());
                return lop;
            }
            System.out.println(Color.RED + "Khong tim thay lop " + ten + "!" + Color.RESET);
        }
    }

    private HocSinh taoHocSinh(LopHoc lop, DanhSachHocSinh dsTam) {
        String mshs;
        while (true) {
            System.out.print("Nhap ma so hoc sinh (MSHS): ");
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

        int soMonLop = (lop.getChuongTrinhHoc() != null) ? lop.getChuongTrinhHoc().length : 10;
        DiemMonHoc[] dsDiemBanDau = new DiemMonHoc[soMonLop];
        HoatDong[] lichSuHDBanDau = new HoatDong[20];

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
        return dsHocSinh.them(hs) && lop.getDsHocSinh().them(hs);
    }

    // Muc 1.2: them k hoc sinh vao lop (k = 1 la them 1 hoc sinh)
    private void themKHocSinh() {
        System.out.println("\n[THEM HOC SINH VAO LOP HOC]");
        LopHoc lop = chonLop(null);
        if (lop == null) return;

        int conTrong = dsHocSinh.getArrHS().length - dsHocSinh.getSoLuong();
        if (conTrong == 0) {
            System.out.println(Color.RED + "Danh sach hoc sinh toan truong da day!" + Color.RESET);
            return;
        }

        int k;
        while (true) {
            System.out.print("Nhap so hoc sinh can them k (1-" + conTrong + "): ");
            try {
                k = Integer.parseInt(sc.nextLine().trim());
                if (k >= 1 && k <= conTrong) break;
                System.out.println(Color.RED + "Loi: k phai tu 1 den " + conTrong + "!" + Color.RESET);
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

        int daThem = dsHocSinh.them(dsTam.getArrHS(), k);
        lop.getDsHocSinh().them(dsTam.getArrHS(), daThem);

        System.out.println(Color.GREEN + "Da them " + daThem + "/" + k
                + " hoc sinh vao lop " + lop.getTenLop() + "!" + Color.RESET);
    }

    // Muc 1.1: nhap n hoc sinh dau tien (khoi tao lai danh sach)
    private void nhapNHocSinhDauTien() {
        System.out.println("\n[NHAP N HOC SINH DAU TIEN]");

        if (dsLopHoc.getSoLuong() == 0) {
            System.out.println(Color.RED + "Chua co lop hoc nao! Hay tao lop truoc (muc 5)." + Color.RESET);
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

        int toiDa = dsHocSinh.getArrHS().length;
        int n;
        while (true) {
            System.out.print("Nhap so hoc sinh n (1-" + toiDa + "): ");
            try {
                n = Integer.parseInt(sc.nextLine().trim());
                if (n >= 1 && n <= toiDa) break;
                System.out.println(Color.RED + "Loi: n phai tu 1 den " + toiDa + "!" + Color.RESET);
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
                System.out.println(Color.RED + "Danh sach da day, dung nhap!" + Color.RESET);
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
            System.out.println("4. Danh sach cac lop hoc");
            System.out.println("0. Quay lai");
            chon = nhapLuaChon();

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
                        System.out.println(lopXem.isLopChuyen()
                                ? "- He: Chuyen " + lopXem.getMonChuyenCuaLop()
                                : "- He: Dai tra");
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

                case 4:
                    System.out.println("\n--- DANH SACH CAC LOP HOC ---");
                    dsLopHoc.hienThiDanhSachLop();
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
            System.out.println("2. Danh gia diem hanh kiem");
            System.out.println("3. Xet hoc bong toan truong");
            System.out.println("4. Bang diem lop hoc");
            System.out.println("5. Bang diem ca nhan");
            System.out.println("0. Quay ve menu chinh");
            chon=nhapLuaChon();

            if(chon == 0) break;
            switch (chon) {
                case 1:
                    System.out.println("\n[NHAP / CAP NHAT DIEM]");
                    System.out.print("Nhap MSHS can nhap diem (nhap 0 de huy): ");
                    String mshsCapNhat = sc.nextLine().trim();
                    if (mshsCapNhat.equals("0")) break;

                    HocSinh hsCapNhat = dsHocSinh.timKiem(mshsCapNhat);
                    if (hsCapNhat == null) {
                        System.out.println(Color.RED + "Khong tim thay hoc sinh co ma " + mshsCapNhat + "!" + Color.RESET);
                        break;
                    }

                    LopHoc lopCuaHs = dsLopHoc.timKiemLopTheoTen(hsCapNhat.getTenLop());
                    if (lopCuaHs == null) {
                        System.out.println(Color.RED + "Loi: Khong tim thay lop " + hsCapNhat.getTenLop() + "!" + Color.RESET);
                        break;
                    }

                    MonHoc[] chuongTrinh = lopCuaHs.getChuongTrinhHoc();
                    if (chuongTrinh == null || chuongTrinh.length == 0 || chuongTrinh[0] == null) {
                        System.out.println(Color.RED + "Loi: Lop " + lopCuaHs.getTenLop()
                                + " chua duoc thiet lap cac mon hoc!" + Color.RESET);
                        break;
                    }

                    System.out.println(Color.CYAN + "Dang cap nhat diem hoc sinh " + hsCapNhat.getHoTen()
                            + " - Lop " + hsCapNhat.getTenLop() + Color.RESET);

                    DiemMonHoc[] bangDiemMoi = new DiemMonHoc[chuongTrinh.length];
                    int demMon = 0;

                    for (int i = 0; i < chuongTrinh.length; i++) {
                        MonHoc mh = chuongTrinh[i];
                        if (mh == null) continue;

                        System.out.println("\n--- Mon: " + Color.YELLOW + mh.getTenMon() + Color.RESET
                                + " (He so: " + mh.getHeSo() + ")");
                        double dTX = DataValidator.nhapDiem(sc, "   + Diem TX: ");
                        double dGK = DataValidator.nhapDiem(sc, "   + Diem GK: ");
                        double dCK = DataValidator.nhapDiem(sc, "   + Diem CK: ");

                        bangDiemMoi[demMon++] = new DiemMonHoc(mh, dTX, dGK, dCK);
                    }

                    // Cap nhat vao hoc sinh
                    hsCapNhat.setDanhSachDiem(bangDiemMoi);
                    hsCapNhat.setSoLuongMon(demMon);
                    double gpaMoi = DiemMonHoc.tinhGPATongHop(bangDiemMoi);
                    hsCapNhat.setGpa(gpaMoi);

                    // Hoc sinh chuyen: cap nhat diem mon chuyen (= DTB cua mon chuyen)
                    if (hsCapNhat instanceof HocSinhChuyen) {
                        HocSinhChuyen hsc = (HocSinhChuyen) hsCapNhat;
                        for (int i = 0; i < demMon; i++) {
                            MonHoc m = bangDiemMoi[i].getMonHoc();
                            if (m.getTenMon().equalsIgnoreCase(hsc.getMonChuyen())) {
                                hsc.setDiemMonChuyen(Math.round(bangDiemMoi[i].tinhDiemTBMon() * 100.0) / 100.0);
                                break;
                            }
                        }
                    }

                    // In ket qua
                    System.out.println("\n" + Color.GREEN + "Da cap nhat bang diem thanh cong!" + Color.RESET);
                    for (int i = 0; i < demMon; i++) {
                        System.out.println("  " + bangDiemMoi[i]);
                    }
                    System.out.println("=> GPA moi cua " + hsCapNhat.getHoTen() + ": " + gpaMoi);
                    break;

                case 2:

            }
        }
    }

    private void menuQuanLyTaiChinh() {
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 3. QUAN LY TAI CHINH ----");
            System.out.println("1. Tra cuu hoc phi ca nhan");
            System.out.println("2. Xet duyet danh sach hoc bong");
            System.out.println("3. Xuat bao cao thu phi toan truong");
            System.out.println("4. Cap nhat trang thai thu tien & danh sach no hoc phi");
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon();

            if(chon == 0) break;
        }
    }

    private void menuThongKe(){
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 4. THONG KE & XET DUYET ----");
            System.out.println("1. Thong ke ty le len lop / o lai lop");
            System.out.println("2. Top 5 hoc sinh xuat sac nhat");
            System.out.println("3. Thong ke chi tiet hoc bong theo dien (dai tra / chuyen)");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon();

            if(chon == 0) break;
            //Code tiep o day
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
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon();

            if(chon == 0) break;
            //Code tiep
        }
    }
    private void menuQuanLyNhanSuVaPH(){
        int chon=-1;
        while(chon != 0) {
            System.out.println("\n---- 6. QUAN LY NHAN SU & PHU HUYNH ----");
            System.out.println("1. Quan ly danh sach giao vien");
            System.out.println("2. Quan ly danh sach phu huynh");
            System.out.println("0. Quay lai menu chinh");
            chon=nhapLuaChon();

            if(chon == 0) break;
            //code tiếp ở đây
        }
    }
}
