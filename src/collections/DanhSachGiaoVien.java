package collections;

import java.util.Scanner;
import models.GiaoVien;

public class DanhSachGiaoVien {
    private GiaoVien[] arrGV;
    private int soLuong;

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

        System.out.println("Cập nhât thông tin cho giáo viên có mã là " + maGV);
        
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
            System.out.print("Nhập số lượng lóp dạy mới: ");
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
        System.out.println("Cập nhật  thông tin thành công!")
        return true;
    }

    public boolean ghiFile(){
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter("giaovien.txt"))) {
            for(int i = 0; i < soLuong; i++){
                if(arrGV[i] != null){
                    pw.println(arrGV[i].getMaGV() + " ; " + arrGV[i].getHoTen() + " ; " + arrGV[i].getNgaySinh() + " ; " + arrGV[i].isGioiTinh() + " ; "
                               arrGV[i].getDiaChi() + " ; " + arrGV[i].getSoDienThoai() + " ; " + arrGV[i].getChucVu() + " ; " + arrGV[i].getLuong());
                }
            }
            return true;
        } catch (Exception e) {
           e.printStackTrace();
           return false;
        }
    }

    public boolean docFile(){
        java.io.File file = new java.io.File("giaovien.txt");
        if(!file.exists()){
            return false;
        }
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            soLuong = 0;
            
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if(parts.length >= 8){
                    String maGV = parts[0];
                    String hoTen = parts[1];
                    String ngaySinh = parts[2];
                    boolean gioiTinh = Boolean.parseBoolean(parts[3]);
                    String diaChi = parts[4];
                    String soDienThoai = parts[5];
                    String chucVu = parts[6];
                    double luong = Double.parseDouble(parts[7]);
                    GiaoVien gv = new GiaoVien(maGV, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, chucVu, luong, null, null);
                    if(soLuong < arrGV.length){
                        arrGV[soLuong] = gv;
                        soLuong++;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}



