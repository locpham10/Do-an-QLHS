package main;

import collections.DanhSachHocSinh;
import collections.DanhSachGiaoVien;
import collections.DanhSachPhuHuynh;
import collections.DanhSachLopHoc;
import models.GiaoVien;
import models.LopHoc;
import utils.Color;
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
                    System.out.println("---- THEM LOP HOC MOI ----");
                    System.out.print("Nhap ten lop: ");
                    String tenLop = sc.nextLine().trim();
                    System.out.print("Nhap khoa hoc: ");
                    String khoaHoc = sc.nextLine().trim();
                    System.out.print("Nhap phong hoc: ");
                    String phongHoc = sc.nextLine().trim();
                    System.out.print("Nhap ma giao vien chu nhiem: ");
                    String maGVCN = sc.nextLine().trim();
                    GiaoVien gvcn = dsGiaoVien.timKiem(maGVCN);
                    DanhSachHocSinh dsHSLop = new DanhSachHocSinh();

                    LopHoc lopMoi = new LopHoc(tenLop, khoaHoc, gvcn, dsHSLop);
                    dsLopHoc.themLopHoc(lopMoi);
                    break;
                case 2:

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
