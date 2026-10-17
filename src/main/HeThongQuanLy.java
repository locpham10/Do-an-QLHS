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
                    System.out.print("Nhap khoa hoc: ");
                    String khoaHoc = sc.nextLine().trim();
                    System.out.print("Nhap phong hoc: ");
                    String phongHoc = sc.nextLine().trim();
                    System.out.print("Nhap ma giao vien chu nhiem: ");
                    String maGVCN = sc.nextLine().trim();
                    GiaoVien gvcn = dsGiaoVien.timKiem(maGVCN);
                    DanhSachHocSinh dsHsLop = new DanhSachHocSinh();

                    System.out.print("He cua lop (0: Dai tra / 1: Chuyen): ");
                    boolean isLopChuyen = sc.nextLine().trim().equals("1");
                    String monChuyenLop = "";
                    double phuPhiChung = 0.0;

                    if (isLopChuyen) {
                        System.out.print("Nhập môn chuyên của lớp này (VD: Toán, Văn, Anh): ");
                        monChuyenLop = sc.nextLine().trim();
                        while (true) {
                            System.out.print("Nhập phụ phí chuyên đề chung của lớp (VNĐ): ");
                            try {
                                phuPhiChung = Double.parseDouble(sc.nextLine().trim());
                                if (phuPhiChung < 0) {
                                    System.out.println(Color.RED + "Lỗi: Phụ phí không được là số âm. Vui lòng nhập lại!" + Color.RESET);
                                    continue; // Quay lại đầu vòng lặp
                                }
                                break; // Nhập đúng số dương thì thoát vòng lặp
                            } catch (NumberFormatException e) {
                                System.out.println(Color.RED + "Lỗi: Nhập sai định dạng. Hãy nhập lại!" + Color.RESET);
                            }
                        }
                    }

                    // Constructor mới của namtechin có thêm 2 tham số này
                    LopHoc lopMoi = new LopHoc(tenLop, khoaHoc, gvcn, dsHsLop, isLopChuyen,
                            monChuyenLop, phuPhiChung);
                    dsLopHoc.themLopHoc(lopMoi);
                    break;

                case 2:
                    System.out.println("\n[THEM HOC SINH VAO LOP HOC]");
                    System.out.print("Nhap ten lop can them ");
                    String lopCanThem = sc.nextLine().trim();

                    LopHoc lopCanTim = dsLopHoc.timKiemLopTheoTen(lopCanThem);
                    if(lopCanTim == null){
                        System.out.println(Color.RED + "Khong tim thay lop nay!" + Color.RESET);
                        break;
                    }

                    System.out.print("Nhap ho ten: ");
                    String hoTen = sc.nextLine().trim();
                    System.out.print("Nhap ngay sinh (dd/mm/yyyy): ");
                    String ngaySinh = sc.nextLine().trim();
                    System.out.print("Gioi tinh (1: Nam / 0: Nu): ");
                    boolean gioiTinh = sc.nextLine().trim().equals("1");
                    System.out.print("Nhap dia chi: ");
                    String diaChi = sc.nextLine().trim();
                    System.out.print("Nhap so dien thoai: ");
                    String sdt = sc.nextLine().trim();

                    System.out.print("Nhap ma so hoc sinh: ");
                    String mshs = sc.nextLine().trim();

                    double gpa = 0.0;
                    int diemHanhKiem = 100;
                    boolean daDongHocPhi = false;

                    HocSinh hsMoi = null;

                    if(lopCanTim.isLopChuyen()){
                        String monChuyenHs = lopCanTim.getMonChuyenCuaLop();
                        double phuPhi = lopCanTim.getPhuPhiChung();
                        double diemMonChuyen = 0.0;
                        hsMoi = new HocSinhChuyen(hoTen, ngaySinh, gioiTinh, diaChi, sdt,
                                mshs, lopCanThem, gpa, diemHanhKiem, daDongHocPhi,
                                monChuyenHs, diemMonChuyen, phuPhi);
                    } else {
                        hsMoi = new HocSinhDaiTra(hoTen, ngaySinh, gioiTinh, diaChi,
                                sdt, mshs, lopCanThem, gpa, diemHanhKiem, daDongHocPhi);
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

                    int chonXem = -1;
                    while(true) {
                        System.out.print("Nhap lua chon: ");
                        try {
                            chonXem = Integer.parseInt(sc.nextLine().trim());
                            if(chonXem >=1 && chonXem <=3){
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
                    if(chonXem == 1){

                    }
            }
        }
    }

    private void menuQuanLyHocTap() {
        int chon = -1;
        while (chon != 0){
            System.out.println("\n---- 2. QUAN LY HOC TAP ----");
            System.out.println("1. Nhap diem");
            System.out.println("2. Cap nhat hanh kiem");
            System.out.println("3. Xem phieu diem ca nhan");
            System.out.println("4. Bang diem lop hoc");
            System.out.println("0. Quay ve menu chinh");
            chon=nhapLuaChon();

            if(chon == 0) break;
            //Code tiep ở đây
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
