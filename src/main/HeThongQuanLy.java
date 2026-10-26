package main;

import collections.DanhSachHocSinh;
import collections.DanhSachGiaoVien;
import collections.DanhSachPhuHuynh;
import collections.DanhSachLopHoc;
import models.*;
import utils.Color;

import java.sql.SQLOutput;
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

    public void start() {
        int luaChon = -1;
        while (luaChon != 0) {
            hienThiMenuChinh();
            luaChon=nhapLuaChon();

            switch(luaChon) {
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
                default:
                    System.out.println(Color.RED + "Lựa chọn không hợp lệ. Vui lòng chọn từ 0 đến 6!" + Color.RESET);
            }
        }
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
            System.out.println("1. Them lop hoc moi");
            System.out.println("2. Them hoc sinh vao lop hoc");
            System.out.println("3. Xem danh sach lop");
            System.out.println("4. Sua/Xoa hoc sinh");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon();

            if (chon == 0) break;
            switch (chon){

                case 1:
                    System.out.println("\n[THEM LOP HOC MOI]");
                    System.out.print("Nhap ten lop: ");
                    String tenLop = sc.nextLine().trim();

                    if (dsLopHoc.timKiemLopTheoTen(tenLop) != null) {
                        System.out.println(Color.RED + "Loi: " + tenLop + " da ton tai trong he thong!" + Color.RESET);
                        break;
                    }

                    System.out.print("Nhap khoa hoc: ");
                    String khoaHoc = sc.nextLine().trim();
                    System.out.print("Nhap ma giao vien chu nhiem: ");
                    String maGVCN = sc.nextLine().trim();
                    GiaoVien gvcn = dsGiaoVien.timKiem(maGVCN);

                    if (gvcn == null) {
                        System.out.println(Color.YELLOW + "Loi: Khong tim thay ma giao vien " + maGVCN + ". Tam thoi de trong!." + Color.RESET);
                    }

                    DanhSachHocSinh dsHsLop = new DanhSachHocSinh();

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
                                if (chonMon >= 1 && chonMon <= MonHoc.KHO_MON_HOC.length) {
                                    MonHoc monGoc = MonHoc.KHO_MON_HOC[chonMon - 1];

                                    // Kiểm tra trùng môn đã chọn
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

                                    // Tạo bản sao với Hệ số 1 mặc định
                                    chuongTrinhCuaLop[i] = monGoc.copyVoiHeSo(1);
                                    System.out.println("  -> Da them: " + monGoc.getTenMon());
                                    break;
                                } else {
                                    System.out.println(Color.RED + "Loi: Vui long chon tu 1 den " + MonHoc.KHO_MON_HOC.length + "!" + Color.RESET);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(Color.RED + "Loi: Vui long nhap so!" + Color.RESET);
                            }
                        }
                    }

                    System.out.print("He cua lop (0: Dai tra / 1: Chuyen): ");
                    boolean isLopChuyen = sc.nextLine().trim().equals("1");
                    String monChuyenLop = "";
                    double phuPhiChung = 0.0;

                    if (isLopChuyen) {
                        System.out.println("\nDanh sach cac mon cua lop " + tenLop + ":");
                        for (int i = 0; i < soMonCuaLop; i++) {
                            System.out.println((i + 1) + ". " + chuongTrinhCuaLop[i].getTenMon());
                        }
                        while (true) {
                            System.out.print("Chon STT mon lam mon chuyen (1-" + soMonCuaLop + "): ");
                            try {
                                int sttChuyen = Integer.parseInt(sc.nextLine().trim());
                                if (sttChuyen >= 1 && sttChuyen <= soMonCuaLop) {
                                    MonHoc monDuocChon = chuongTrinhCuaLop[sttChuyen - 1];

                                    // Chặn trường hợp chọn nhầm môn Thể dục (không tính GPA) làm môn chuyên
                                    if (!monDuocChon.isTinhVaoGPA()) {
                                        System.out.println(Color.RED + "Mon khong tinh GPA (" + monDuocChon.getTenMon() + ") khong the lam mon chuyen!" + Color.RESET);
                                        continue;
                                    }

                                    // Nâng hệ số môn chuyên lên 2 và lưu tên môn chuyên
                                    monDuocChon.setHeSo(2);
                                    monChuyenLop = monDuocChon.getTenMon();
                                    System.out.println(Color.GREEN + "  -> Thiet lap chuyen  " + monChuyenLop + Color.RESET);
                                    break;
                                } else {
                                    System.out.println(Color.RED + "Loi: Chi chon 1 den " + soMonCuaLop + "!" + Color.RESET);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(Color.RED + "Loi: Vui long nhap so!" + Color.RESET);
                            }
                        }

                        // Nhập phụ phí chuyên đề
                        while (true) {
                            System.out.print("Nhap phu phi chuyen de cua lop (VNĐ): ");
                            try {
                                phuPhiChung = Double.parseDouble(sc.nextLine().trim());
                                if (phuPhiChung >= 0) break;
                                System.out.println(Color.RED + "Loi: Phu phi phai la so duong!" + Color.RESET);
                            } catch (NumberFormatException e) {
                                System.out.println(Color.RED + "Loi: Sai dinh dang so!" + Color.RESET);
                            }
                        }
                    }

                    // Khởi tạo đối tượng LopHoc và gán chương trình học
                    LopHoc lopMoi = new LopHoc(tenLop, khoaHoc, gvcn, dsHsLop, isLopChuyen, monChuyenLop, phuPhiChung);
                    lopMoi.setChuongTrinhHoc(chuongTrinhCuaLop);

                    dsLopHoc.themLopHoc(lopMoi);
                    System.out.println(Color.GREEN + "✅ Đã tạo lớp " + tenLop + " hoàn tất!" + Color.RESET);
                    break;

                case 2:
                    System.out.println("\n[THEM HOC SINH VAO LOP HOC]");
                    System.out.print("Nhap ten lop can them (Nhap 0 de quay lai): ");
                    String lopCanThem = sc.nextLine().trim();

                    if(lopCanThem.equals("0")) break;

                    LopHoc lopCanTim = dsLopHoc.timKiemLopTheoTen(lopCanThem);
                    if(lopCanTim == null){
                        System.out.println(Color.RED + "Khong tim thay lop nay!" + Color.RESET);
                        break;
                    }

                    String mshs = "";
                    while (true) {
                        System.out.print("Nhap ma so hoc sinh (MSHS): ");
                        mshs = sc.nextLine().trim();
                        try {
                            if (mshs.isEmpty()) {
                                System.out.println(Color.RED + "Loi: MSHS khong duoc de trong!" + Color.RESET);
                                continue;
                            }
                            // Gọi hàm kiểm tra trùng mã (ném TrungMaHocSinhException nếu trùng)
                            utils.DataValidator.kiemTraTonTaiMa(mshs, dsHocSinh);
                            break; // Không trùng thì thoát vòng lặp đi tiếp
                        } catch ( Exception e ) { // Hoặc bắt đích danh TrungMaHocSinhException
                            System.out.println(Color.RED + e.getMessage() + Color.RESET);
                        }
                    }

                    System.out.print("Nhap ho ten: ");
                    String hoTen = sc.nextLine().trim();
                    String ngaySinh = utils.DataValidator.nhapNgaySinh(sc, "Nhap ngay sinh (dd/mm/yyyy): ");
                    System.out.print("Gioi tinh (1: Nam / 0: Nu): ");
                    boolean gioiTinh = sc.nextLine().trim().equals("1");
                    System.out.print("Nhap dia chi: ");
                    String diaChi = sc.nextLine().trim();
                    System.out.print("Nhap so dien thoai: ");
                    String sdt = sc.nextLine().trim();

                    System.out.println("--- Thong tin Phu huynh ---");
                    System.out.print("Nhap ho ten Bo/Me: ");
                    String tenPH = sc.nextLine().trim();
                    System.out.print("Nhap SDT khan cap cua Phu huynh: ");
                    String sdtPH = sc.nextLine().trim();
                    PhuHuynh phuHuynhMoi = new PhuHuynh(tenPH, sdtPH);

                    String tenLopChuan = lopCanTim.getTenLop();
                    double gpa = 0.0;
                    int diemHanhKiem = 100;
                    boolean daDongHocPhi = false;

                    int soMonLop = (lopCanTim.getChuongTrinhHoc() != null) ? lopCanTim.getChuongTrinhHoc().length : 10;
                    DiemMonHoc[] dsDiemBanDau = new DiemMonHoc[soMonLop];
                    int soLuongMonBanDau = 0;
                    HoatDong[] lichSuHDBanDau = new HoatDong[20];

                    HocSinh hsMoi = null;

                    if(lopCanTim.isLopChuyen()){
                        String monChuyenHs = lopCanTim.getMonChuyenCuaLop();
                        double phuPhi = lopCanTim.getPhuPhiChung();
                        double diemMonChuyen = 0.0;
                        hsMoi = new HocSinhChuyen(mshs, hoTen, ngaySinh, gioiTinh, diaChi, sdt,
                                tenLopChuan, gpa, diemHanhKiem, daDongHocPhi,
                                phuHuynhMoi, dsDiemBanDau, soLuongMonBanDau, lichSuHDBanDau,
                                monChuyenHs, diemMonChuyen, phuPhi);
                    } else {
                        hsMoi = new HocSinhDaiTra(hoTen, ngaySinh, gioiTinh, diaChi, sdt,
                                mshs, tenLopChuan, gpa, diemHanhKiem, daDongHocPhi,
                                phuHuynhMoi, dsDiemBanDau, soLuongMonBanDau, lichSuHDBanDau);
                    }

                    boolean themVaoTruong = dsHocSinh.them(hsMoi);
                    boolean themVaoLop = lopCanTim.getDsHocSinh().them(hsMoi);

                    if(themVaoTruong && themVaoLop){
                        System.out.println(Color.GREEN + "Da them hoc sinh " + hoTen +
                                " vao lop " + lopCanThem + "!" + Color.RESET);
                    } else {
                        System.out.println(Color.RED + "Them hoc sinh that bai! " +
                                "(danh sach hoc sinh cua lop hoac cua truong da day)" + Color.RESET);
                    }
                    break;

                case 3:
                    System.out.println("\n[XEM DANH SACH LOP]");
                    System.out.println("1. Xem danh sach tat ca lop");
                    System.out.println("2. Xem danh sach hoc sinh cua mot lop");
                    System.out.println("3. Xem danh sach hoc sinh toan truong");
                    System.out.println("0. Quay lại");

                    int chonXem = -1;
                    while(true) {
                        System.out.print("Nhap lua chon: ");
                        try {
                            chonXem = Integer.parseInt(sc.nextLine().trim());
                            if(chonXem >=0 && chonXem <=3){
                                break;
                            } else {
                                System.out.print(Color.RED +
                                        "Loi: vui long nhap cac lua chon hop le!" + Color.RESET);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println( Color.RED +
                                    "Loi: Nhap sai dinh dang so! Vui long nhap lai!" + Color.RESET);
                        }
                    }
                    if (chonXem == 0) {
                        break;
                    } else if(chonXem == 1){
                        System.out.println("\n ---- DANH SACH CAC LOP HOC ----");
                        dsLopHoc.hienThiDanhSachLop();
                    } else if (chonXem == 2){
                        System.out.print("Nhap ten lop can xem: ");
                        String tenLopCanXem = sc.nextLine().trim();
                        LopHoc lopCanXem = dsLopHoc.timKiemLopTheoTen(tenLopCanXem);

                        if(lopCanXem != null){
                            System.out.println("\n" + Color.CYAN + "========================================");
                            System.out.println(" THÔNG TIN LỚP: " + lopCanXem.getTenLop().toUpperCase());
                            System.out.println("========================================" + Color.RESET);

                            if(lopCanXem.isLopChuyen()){
                                System.out.println("- He: Chuyen " + lopCanXem.getMonChuyenCuaLop());
                            } else {
                                System.out.println("- He: Dai tra");
                            }

                            if (lopCanXem.getGiaoVienChuNhiem() != null) {
                                System.out.println("- Giao vien chu nhiem: " + lopCanXem.getGiaoVienChuNhiem().getHoTen());
                            } else {
                                System.out.println("- Giao vien chu nhiem: " + Color.YELLOW + "Chua phan cong" + Color.YELLOW);
                            }

                            System.out.println("----------------------------------------");
                            System.out.println("DANH SÁCH HỌC SINH TRONG LỚP:");
                            lopCanXem.getDsHocSinh().hienThiDanhSach();
                        } else {
                            System.out.println(Color.RED + "Loi: Lop khong ton tai trong he thong" + Color.RESET);
                        }

                    } else if (chonXem == 3){
                        System.out.println("\n--- DANH SÁCH HỌC SINH TOÀN TRƯỜNG ---");
                        dsHocSinh.hienThiDanhSach();
                    } else {
                        System.out.println(Color.RED + "Lựa chọn không hợp lệ!" + Color.RESET);
                    }
                    break;

                case 4:
                    System.out.println("\n[SUA / XOA THONG TIN HOC SINH]");
                    System.out.print("Nhap MSHS can xu ly (Nhap 0 de quay lai): ");
                    String mshsCanXuLy = sc.nextLine().trim();

                    if(mshsCanXuLy.equals("0")){
                        break;
                    }

                    HocSinh hsTimThay = dsHocSinh.timKiem(mshsCanXuLy);

                    if (hsTimThay == null){
                        System.out.println(Color.RED + "Loi: Khong tim thay hoc sinh nay!" + Color.RESET);
                        break;
                    }

                    System.out.println(Color.GREEN + "Tim thay: " + hsTimThay.getHoTen() + " - Lop: "
                            + hsTimThay.getTenLop() + Color.RESET);
                    System.out.println("1. Sua thong tin hoc sinh");
                    System.out.println("2. Xoa hoc sinh khoi he thong");
                    System.out.println("0. Huy thao tac");

                    int thaoTac = -1;
                    while (true) {
                        System.out.println("Chon thao tac: ");
                        try {
                            thaoTac = Integer.parseInt(sc.nextLine().trim());

                            if (thaoTac >= 0 && thaoTac <= 2) {
                                break;
                            } else {
                                System.out.println(Color.RED + "Loi: Thao tac khong hop le! Vui long chon lai!" + Color.RESET);
                            }
                        } catch (NumberFormatException e){
                            System.out.println(Color.RED + "Loi: Nhap sai dinh dang so! vui long nhap lai!");
                        }
                    }

                    if(thaoTac == 0){
                        System.out.println(Color.YELLOW + "Da huy thao tac." + Color.RESET);
                    } else if (thaoTac == 1){
                        dsHocSinh.sua(hsTimThay);
                        System.out.println(Color.GREEN + "Cap nhat thong tin thanh cong!" + Color.RESET);
                    } else if (thaoTac == 2) {
                        String tenLopCuaHs = hsTimThay.getTenLop();

                        System.out.print(Color.YELLOW + "Xac nhan xoa hoc sinh " + hsTimThay.getHoTen()
                                + "? (Y/N): ");
                        String xacNhan = sc.nextLine().trim().toUpperCase();

                        if(xacNhan.equals("Y")){
                            dsHocSinh.xoa(hsTimThay);

                            LopHoc lopCuaHs = dsLopHoc.timKiemLopTheoTen(tenLopCuaHs);
                            if (lopCuaHs != null){
                                lopCuaHs.getDsHocSinh().xoa(hsTimThay);
                            }

                            System.out.println(Color.GREEN + "Da xoa hoc sinh " + hsTimThay.getHoTen()
                                    + "!" + Color.RESET);
                        } else {
                            System.out.println(Color.YELLOW + "Da huy lenh xoa." + Color.RESET);
                        }
                    }
                    break;

                default:
                    System.out.println(Color.RED + "Loi: Lua chon khong hop le! Vui long chon lai!");
                    break;
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
                    System.out.println("\n [NHAP / CAP NHAT DIEM] /");
                    System.out.println("Nhap MSHS can nhap diem (nhap 0 de huy): ");
                    String mshsCapNhat = sc.nextLine().trim();

                    if (mshsCapNhat.equals("0")) break;
                    HocSinh hsCapNhat = dsHocSinh.timKiem(mshsCapNhat);

                    if (hsCapNhat == null) {
                        System.out.println(Color.RED + "Khong tim thay hoc sinh co ma " + mshsCapNhat + "!" + Color.RESET);
                        break;
                    }

                    LopHoc lopCuaHs = dsLopHoc.timKiemLopTheoTen(hsCapNhat.getTenLop());
                    MonHoc[] chuongTrinh = lopCuaHs.getChuongTrinhHoc();

                    if(chuongTrinh == null || chuongTrinh.length == 0 || chuongTrinh[0] == null){
                        System.out.println(Color.RED + "Loi: Lop " + lopCuaHs + "chua duoc thiet lap cac mon hoc!" + Color.RESET);
                        break;
                    }

                    System.out.println(Color.CYAN + "Dang cap nhat diem hoc sinh " + hsCapNhat.getHoTen() +
                                " - " + hsCapNhat.getTenLop() + ":" + Color.RESET);

                    DiemMonHoc[] bangDiemMoi = new DiemMonHoc[chuongTrinh.length];
                    int demSomon = 0;

                    double tongDiemNhanHeSo = 0;
                    int tongHeSo = 0;

                    for(int i =0; i < chuongTrinh.length; i++){
                        MonHoc mh = chuongTrinh[i];
                        if (mh==null) continue;

                        System.out.println("\n--- Mon: " + Color.YELLOW + mh.getTenMon() + Color.RESET);
                    }


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
