package collections;

import interfaces.IThongKe;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import models.GiaoVien;
import models.LopHoc;
import models.MonHoc;
import utils.Color;
import utils.DataValidator;

public class DanhSachGiaoVien implements IThongKe {
    private GiaoVien[] arrGV;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/giaovien.txt";

    public DanhSachGiaoVien() {
        this.arrGV = new GiaoVien[0];
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
        if( gv == null ){
            System.out.println("Chua co file " + DUONG_DAN_FILE + ", danh sach giao vien dan trong.");
            return false; // Thêm thất bại (đầy/null)
        }
        if(soLuong >= arrGV.length){
            arrGV = Arrays.copyOf(arrGV,arrGV.length + 1);
        }
        this.arrGV[soLuong] = gv;
        soLuong ++;
        return true; // Thêm thành công
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

    public GiaoVien[] timKiemTheoTen(String tuKhoa){
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getHoTen().toLowerCase().contains(tuKhoa.trim().toLowerCase())){
                dem++;
            }      
        }
        GiaoVien[] ketQua = new GiaoVien[dem];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getHoTen().toLowerCase().contains(tuKhoa.trim().toLowerCase())){
                ketQua[index++] = arrGV[i];
            }   
        }
        return ketQua;
    }

    public GiaoVien[] timKiemTheoMon(String tenMon){
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getMonChuyenMon()!= null && arrGV[i].getMonChuyenMon().getTenMon().toLowerCase().contains(tenMon.trim().toLowerCase())){
                dem++;
            }     
        }
        GiaoVien[] ketQua = new GiaoVien[dem];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getMonChuyenMon()!= null && arrGV[i].getMonChuyenMon().getTenMon().toLowerCase().contains(tenMon.trim().toLowerCase())){
                ketQua[index++] = arrGV[i];
            }   
        }
        return ketQua;
    }

    public GiaoVien[] timKiemTheoChucVu(String chucVu){
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getChucVu().toLowerCase().contains(chucVu.trim().toLowerCase())){
                dem++;
            }  
        }
        GiaoVien[] ketQua = new GiaoVien[dem];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null && arrGV[i].getChucVu().toLowerCase().contains(chucVu.trim().toLowerCase())){
                ketQua[index++] = arrGV[i];
            }     
        }
        return ketQua;
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
    
    public boolean sua(String maGV, Scanner sc){
        int index = timKiemViTri(maGV);
        if(index == -1){
            return false;
        }
        GiaoVien gv = arrGV[index];

        System.out.println("Cập nhật thông tin cho giáo viên có mã là " + maGV);
        System.out.println("Lưu ý nếu không muốn đổi thông tin mục nào vui lòng nhấn ENTER!");

        String s;
        
        while (true) { 
            System.out.print("Nhập họ tên mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){ break;}
            if( !s.contains(",")){
            gv.setHoTen(s);
            break;
            }
        System.out.println(Color.RED + "Lỗi: Không được chứa dấu phẩy" + Color.RESET);
        }

        while (true) { 
            System.out.print("Nhập ngày sinh mới (DD/MM/YYYY): ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            if(DataValidator.ngaySinhHopLe(s)){
                gv.setNgaySinh(s);
                break;
            }
            System.out.println(Color.RED + "Lỗi: Ngày sinh phải đúng định dạng DD/MM/YYYY" + Color.RESET);
        }
        
        while (true) { 
            System.out.print("Nhập giới tính mới (1-Nam / 0-Nữ): ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            if(s.equals("1") || s.equals("0")){
                gv.setGioiTinh(s.equals("1"));
                break;
            }
            System.out.println(Color.RED + "Lỗi: Chỉ nhập 1 hoặc 0" + Color.RESET);
        }

        
        while (true) { 
            System.out.print("Nhập địa chỉ mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            if( !s.contains(",")){
                gv.setDiaChi(s);
                break;
            }
            System.out.println(Color.RED + "Lỗi: Không được chứa dấu phẩy" + Color.RESET);
        }

        while (true) { 
            System.out.print("Nhập số điện thoại mới: ");
            s= sc.nextLine().trim().replaceAll("[\\s.]", "");
            if(s.isEmpty()){break;}
            if(s.matches("^0\\d{9}$")){
                gv.setSoDienThoai(s);
                break;
            }
            System.out.println(Color.RED + "Lỗi: Số điện thoại phải có 10 số và bắt đầu từ số 0" + Color.RESET);
        }

        while (true) { 
            System.out.print("Nhập chức vụ mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            if( !s.contains(",")){
                gv.setChucVu(s);
                break;
            }
            System.out.println(Color.RED + "Lỗi: Không được chứa dấu phẩy" + Color.RESET);
        }

        while (true) { 
            System.out.print("Nhập lương mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            try {
                double luong = Double.parseDouble(s);
                if(luong >= 0 ){
                    gv.setLuong(luong);
                    break;
                }
                else{
                    System.out.println(Color.RED + "Lỗi: Lương không được là số âm" + Color.RESET);
                }
            } catch (NumberFormatException e ) {
                System.out.println(Color.RED + "Lỗi: Lương phải là số thực hợp lệ" + Color.RESET);
            }
        }

        while (true) { 
            System.out.print("Nhập tên môn chuyên môn mới:");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            if(!s.contains(",")){
                MonHoc monTimDuoc = null;
                for(int i = 0; i < MonHoc.KHO_MON_HOC.length; i++){
                    if(MonHoc.KHO_MON_HOC[i] != null && MonHoc.KHO_MON_HOC[i].getTenMon().equalsIgnoreCase(s)){
                        monTimDuoc = MonHoc.KHO_MON_HOC[i];
                        break;
                    }
                }
                if(monTimDuoc != null){
                    gv.setMonChuyenMon(monTimDuoc);
                    break;
                }
                else{
                    System.out.println(Color.RED + "Lỗi: Không tìm thấy môn này trong kho môn học!" + Color.RESET);
                }
            }
            System.out.println(Color.RED + "Lỗi: Không được chứa dấu phẩy" + Color.RESET);
        }
        
        while (true) { 
            System.out.print("Nhập số lượng lớp dạy mới:");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            try {
                int soLop = Integer.parseInt(s);
                if(soLop >= 0){
                    models.LopHoc[] dsLopMoi = new models.LopHoc[soLop];
                    for (int i = 0; i < soLop; i++) {
                        System.out.println("Nhập thông tin lớp thứ " + (i+1) + " - Nhập tên lớp: ");
                        String tenLop = sc.nextLine().trim();
                        dsLopMoi[i] = new models.LopHoc(tenLop, "", gv, null, false, "", 0.0);
                    }
                    gv.setDsLopDay(dsLopMoi);
                    break;
                }
                else{
                    System.out.println(Color.RED + "Lỗi: Số lớp không được là số âm" + Color.RESET);
                }
            } catch (NumberFormatException e ) {
                System.out.println(Color.RED + "Lỗi: Số lớp phải là số nguyên hợp lệ" + Color.RESET);
            }
        }
        return true;
    }

   public boolean ghiFile(){
    File f = new File(DUONG_DAN_FILE);
    File thuMucCha = f.getParentFile();
    if (thuMucCha != null && !thuMucCha.exists()) {
        thuMucCha.mkdirs();}
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
        for (int i = 0; i < soLuong; i++) {
            if (arrGV[i] != null) {
                bw.write(arrGV[i].toFileString());
                bw.newLine();
            }
            }
            System.out.println("Ghi file GiaoVien thành công!");
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file GiaoVien: " + e.getMessage());
            return false;
        }
    }

    public boolean docFile() {
        int soDong = 0;
        int soDongLoi = 0;
        File f = new File(DUONG_DAN_FILE);
        if(!f.exists()){
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            this.soLuong = 0;

            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();

                if(line.isEmpty()) continue;
                String[] parts = line.split(",",-1);
                if(parts.length < 10){
                    System.out.println("Dòng " + soDong + ": Thiếu cột dữ liệu (cần ít nhất 10, có " + parts.length + ")");
                    soDongLoi++;
                    continue;
                }
                for(int i = 0; i < parts.length; i++){
                    parts[i] = parts[i].trim();
                }
                try{
                    String maGV = parts[0];
                    String hoTen = parts[1];
                    String ngaySinh = parts[2];
                    boolean gioiTinh = Boolean.parseBoolean(parts[3]);
                    String diaChi = parts[4];
                    String soDienThoai = parts[5];
                    String chucVu = parts[6];
                    double luong = Double.parseDouble(parts[7]);

                    MonHoc monChuyenMon = null;
                    String tenMonHoc = parts[8];
                    if(!tenMonHoc.equalsIgnoreCase("Không") && !tenMonHoc.isEmpty()){
                        monChuyenMon = MonHoc.timTheoMa(tenMonHoc);
                        for(int i = 0; monChuyenMon == null && i < MonHoc.KHO_MON_HOC.length; i++){
                            if(MonHoc.KHO_MON_HOC[i] != null && MonHoc.KHO_MON_HOC[i].getTenMon().equalsIgnoreCase(tenMonHoc)){
                                monChuyenMon = MonHoc.KHO_MON_HOC[i];
                                break;
                            }
                        }
                    }

                    int soLop = Integer.parseInt(parts[9]);
                    LopHoc[] dsLopDay = null;
                    if(soLop > 0 && parts.length >= 10 + soLop){
                        dsLopDay = new LopHoc[soLop];
                        for(int k = 0; k < soLop; k++){
                            String tenLop = parts[10+k];
                            dsLopDay[k] = new LopHoc(tenLop, "", null, null, false, "", 0.0);
                        }
                    }
                    GiaoVien gv = new GiaoVien(maGV, hoTen, ngaySinh, gioiTinh, diaChi, soDienThoai, chucVu, luong, monChuyenMon, dsLopDay);
                    if (dsLopDay != null) {
                        for(int i = 0; i < dsLopDay.length; i++){
                            if(dsLopDay[i] != null){
                                dsLopDay[i].setGiaoVienChuNhiem(gv);
                            }
                        }
                    }
                    them(gv);
                } catch (NumberFormatException e) {
                    System.out.println("Dòng " + soDong + ": Lỗi định dạng số (lương/số lớp): " + line);
                    soDongLoi++;
                } catch (Exception e) {
                    System.out.println("Dòng " + soDong + ": " + e.getMessage());
                    soDongLoi++;
                }
            }
            System.out.println("Đọc file GiaoVien thành công! Tổng số: " + soLuong + " giáo viên, " + soDongLoi + " dòng lỗi.");
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file GiaoVien: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void hienThiDanhSach() { 
        System.out.println("========== DANH SACH GIAO VIEN ==========");
        if(soLuong == 0){
            System.out.println("Danh sach giao vien dang trong!");
            return;
        }
        System.out.printf("%-10s | %-20s | %-12s | %-10s | %-15s | %-18s | %-15s\n", 
                "Mã GV", "Họ tên", "Ngày sinh", "Giới tính", "SĐT", "Môn chuyên môn", "Chức vụ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            if (arrGV[i] != null) {
                String tenMon = (arrGV[i].getMonChuyenMon() != null) ? arrGV[i].getMonChuyenMon().getTenMon() : "Không";
                String gioiTinhStr = arrGV[i].isGioiTinh() ? "Nam" : "Nữ";
                
                System.out.printf("%-10s | %-20s | %-12s | %-10s | %-15s | %-18s | %-15s\n",
                        arrGV[i].getMaGV(),
                        arrGV[i].getHoTen(),
                        arrGV[i].getNgaySinh(),
                        gioiTinhStr,
                        arrGV[i].getSoDienThoai(),
                        tenMon,
                        arrGV[i].getChucVu());
            }
        }
    }

    @Override
    public void inBaoCaoThongKe() { 
        System.out.println("========== BAO CAO THONG KE ==========");
        if(soLuong == 0){
            System.out.println("Khong co giao vien nao de thong ke!");
            return;
        }
        double tongLuong = 0;
        int chuaDayLop = 0;
        
        String[] danhSachMon = new String[soLuong];
        int[] demMon = new int[soLuong];
        int soMonKhacNhau = 0;

        String[] dachSachCV = new String[soLuong];
        int[] demCV = new int[soLuong];
        int soCVKhacNhau = 0;

        for (int i = 0; i < soLuong; i++) {
            if(arrGV[i] != null){
                GiaoVien gv = arrGV[i];
                tongLuong += gv.getLuong();
                
                LopHoc[] dsLop = gv.getDsLopDay();
                if(dsLop == null || dsLop.length == 0){
                    chuaDayLop++;
                }   
                String tenMon = (gv.getMonChuyenMon() != null) ? gv.getMonChuyenMon().getTenMon() : "Chua duoc phan mon!";
                int idxMon = -1;
                for (int m = 0; m < soMonKhacNhau; m++) {
                    if(danhSachMon[m].equalsIgnoreCase(tenMon)){
                        idxMon = m;
                        break;
                    }
                }
                if(idxMon != -1){
                    demMon[idxMon]++;
                }
                else{
                    danhSachMon[soMonKhacNhau] = tenMon;
                    demMon[soMonKhacNhau] = 1;
                    soMonKhacNhau++;
                }

                String cv = (gv.getChucVu() != null && !gv.getChucVu().trim().isEmpty()) ? gv.getChucVu() : "Chua duoc phan chuc vu!";
                int idxCV = -1;
                for (int c = 0; c < soCVKhacNhau; c++) {
                    if(dachSachCV[c].equalsIgnoreCase(cv)){
                        idxCV = c;
                        break;
                    }
                }
                if(idxCV != -1){
                    demCV[idxCV]++;
                }
                else{
                    dachSachCV[soCVKhacNhau] = cv;
                    demCV[soCVKhacNhau] = 1;
                    soCVKhacNhau++;
                }
            }
        }
        double luongTB = (soLuong > 0) ? (tongLuong / soLuong) : 0;
        System.out.println("Tong so luong giao vien: " + soLuong);
        System.out.println("Tong luong cua tat ca giao vien: " + String.format("%.2f", tongLuong));
        System.out.println("Tong luong trung binh cua cac giao vien: " + String.format("%.2f", luongTB));
        System.out.println("So giao vien chua day lop nao: " + chuaDayLop);

        System.out.println("--- Thong ke theo mon chuyen mon ---");
        for (int i = 0; i < soMonKhacNhau; i++) {
            System.out.println("   + " + danhSachMon[i] + ": " + demMon[i] + " giao vien");
        }            
        
        System.out.println("--- Thong ke theo chuc vu ---");
        for (int j = 0; j < soCVKhacNhau; j++) {
            System.out.println("   + " + dachSachCV[j] + ": " + demCV[j] + " giao vien");
        }  
    }
    }





