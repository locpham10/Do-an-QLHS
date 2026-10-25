package collections;

import java.util.Scanner;
import models.GiaoVien;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DanhSachGiaoVien {
    private GiaoVien[] arrGV;
    private int soLuong;
    private final String FILE_PATH = "giaovien.txt";

    public DanhSachGiaoVien() {
        this.arrGV = new GiaoVien[100];
        this.soLuong = 0;
    }

    public DanhSachGiaoVien(int dungluong){
        this.arrGV =  new GiaoVien[dungluong];
        this.soLuong = 0;
    }

    public GiaoVien[] getArrGV(){
        return arrGV;
    }
    public void setArrGV(GiaoVien[] arrGV){
        this.arrGV = arrGV;
    }
    
    public int getSoLuong(){
        return soLuong;
    }

    public boolean them(GiaoVien gv){
        if( gv != null && soLuong < arrGV.length){
            this.arrGV[soLuong] = gv;
            soLuong ++;
            return true; // Thêm thành công
        }
        return false; // Thêm thất bại (đầy/null)
    }

    private int timKiemViTri(String maGV){
        for (int i = 0; i < soLuong; i++){
            if(arrGV[i] != null && arrGV[i].getMaGV().equalsIgnoreCase(maGV)){
                return i;
            }
        }
        return -1;
    }

    public GiaoVien timKiem(String maGV){
        int index = timKiemViTri(maGV);
        if(index != -1){
            return arrGV[index];
        }
        return null;
    }

    public boolean xoa(String maGV){
        int index = timKiemViTri(maGV);
        if(index == -1){
            return false; // Không tìm thấy
        }
        for(int i = index ; i < soLuong - 1; i++){
            arrGV[i] = arrGV[i+1];
        }
        arrGV[soLuong-1] = null;
        soLuong--;
        return true;
    }
    
    public boolean sua(String maGV){
        int index = timKiemViTri(maGV);
        if(index == -1){
            return false;
        }
        GiaoVien gv = arrGV[index];
        java.util.Scanner sc = new Scanner(System.in);

        System.out.println("Cập nhật thông tin cho giáo viên có mã là " + maGV);
        
        System.out.print("Nhập họ tên mới: ");
        gv.setHoTen(sc.nextLine());
        
        System.out.print("Nhập ngày sinh mới: ");
        gv.setNgaySinh(sc.nextLine());
        
        System.out.print("Nhập giới tính mới (true-Nam / false-Nữ): ");
        gv.setGioiTinh(Boolean.parseBoolean(sc.nextLine()));

        System.out.print("Nhập địa chỉ mới: ");
        gv.setDiaChi(sc.nextLine());

        System.out.print("Nhập số điện thoại mới: ");
        gv.setSoDienThoai(sc.nextLine());

        System.out.print("Nhập chức vụ mới: ");
        gv.setChucVu(sc.nextLine());

        System.out.print("Nhập lương mới: ");
        gv.setLuong(Double.parseDouble(sc.nextLine()));

        System.out.print("Bạn có muốn thay đổi môn chuyên môn không? (y/n): ");
        String chonMon = sc.nextLine();
        if(chonMon.equalsIgnoreCase("y")){
            System.out.print("Nhập tên môn chuyên môn mới: ");
            String tenMon = sc.nextLine();
            models.MonHoc monMoi = new models.MonHoc(tenMon);
            gv.setMonChuyenMon(monMoi);
        }

        System.out.print("Bạn có muốn thay đổi danh sách lớp dạy không? (y/n): ");
        String chonLop = sc.nextLine();
        if(chonLop.equalsIgnoreCase("y")){
            System.out.print("Nhập số lượng lớp dạy mới: ");
            int soLop = Integer.parseInt(sc.nextLine());
            models.LopHoc[] dsLopMoi = new models.LopHoc[soLop];

            for(int i = 0 ; i < soLop; i++){
                System.out.println("Nhập thông tin lớp thứ " + (i+1) + " :");
                System.out.print("Nhập tên lớp mới: ");
                String tenLop = sc.nextLine();
                dsLopMoi[i] = new models.LopHoc(tenLop);  
            }
            gv.setDsLopDay(dsLopMoi);
        }
        System.out.println("Cập nhật  thông tin thành công!");
        return true;
    }


    public boolean ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrGV[i] != null) {
                    bw.write(arrGV[i].toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file GiaoVien thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file GiaoVien: " + e.getMessage());
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

                if (this.soLuong >= arrGV.length) {
                    System.out.println("Canh bao: Mang GiaoVien da day!");
                    break;
                }

                String[] data = line.split(",");
                if (data.length < 8) continue; 
                try {
                    String maGV = data[0].trim();
                    String hoTen = data[1].trim();
                    String ngaySinh = data[2].trim();
                    boolean gioiTinh = Boolean.parseBoolean(data[3].trim());
                    String diaChi = data[4].trim();
                    String soDienThoai = data[5].trim();
                    String chucVu = data[6].trim();
                    double luong = Double.parseDouble(data[7].trim());

                    GiaoVien gv = new GiaoVien(maGV, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, chucVu, luong);
                    this.them(gv);
                } catch (NumberFormatException e) {
                    System.out.println("Bo qua dong GiaoVien loi dinh dang so: " + line);
                }
            }
            System.out.println("Doc file GiaoVien thanh cong! Tong so: " + soLuong + " giao vien.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file GiaoVien: " + e.getMessage());
            return false;
        }
    }
}



