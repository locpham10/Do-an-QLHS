package main;

import collections.DanhSachHocSinh;
import services.IXuatDuLieu;
import services.XuatDuLieuConsole;
import utils.Color;
import java.util.Scanner;

public class HeThongQuanLy {
    private DanhSachHocSinh dsHocSinh;
    private IXuatDuLieu xuatConsole;
    private Scanner sc;

    public HeThongQuanLy() {
        dsHocSinh = new DanhSachHocSinh();
        xuatConsole = new XuatDuLieuConsole();
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
        System.out.println("0. Thoát chương trình");
    }

    public void start() {
        int luaChon = -1;
        while (luaChon != 0) {

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
            System.out.println("\n---- 1. QUAN LY TAI CHINH ----");
            System.out.println("1. Them lop hoc moi");
            System.out.println("2. Them hoc sinh vao lop hoc");
            System.out.println("3. Xem danh sach lop");
            System.out.println("4. Sua/Xoa hoc sinh");
            System.out.println("0. Quay lai menu chinh");
            chon = nhapLuaChon();

            if (chon == 0) break;
            //Code xử lý vào đây
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

        }
    }
}
