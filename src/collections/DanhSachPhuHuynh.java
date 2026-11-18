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
import models.PhuHuynh;
import utils.Color;

public class DanhSachPhuHuynh implements IThongKe {
    private PhuHuynh[] arrPH;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/phuhuynh.txt";

    public DanhSachPhuHuynh(){
        this.arrPH = new PhuHuynh[100];
        this.soLuong = 0;
    }

    public DanhSachPhuHuynh(int dungluong){
        this.arrPH = new PhuHuynh[dungluong];
        this.soLuong = 0;
    }

    public PhuHuynh[] getarrPH(){
        return arrPH;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public boolean them(PhuHuynh ph) {
        if (ph == null) {
            return false;
        }
        if (soLuong >= arrPH.length) {
            arrPH = Arrays.copyOf(arrPH, arrPH.length * 2); 
        }
        arrPH[soLuong] = ph;
        soLuong++;
        return true;
    }

    private int timKiemViTri(String sdt){
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null && arrPH[i].getSdtKhanCap().equalsIgnoreCase(sdt)){
                return i;
            }
        }
        return -1;
    }

    public PhuHuynh timKiem(String sdt){
        int index = timKiemViTri(sdt);
        if (index != -1){
            return arrPH[index];
        }
        return null;
    }

    public PhuHuynh[] timKiemTheoTen(String tuKhoa){
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrPH[i] != null && arrPH[i].getHoTenBoMe().toLowerCase().contains(tuKhoa.trim().toLowerCase())){
                dem++;
            }      
        }
        PhuHuynh[] ketQua = new PhuHuynh[dem];
        int index = 0;
        for (int i = 0; i < soLuong; i++) {
            if(arrPH[i] != null && arrPH[i].getHoTenBoMe().toLowerCase().contains(tuKhoa.trim().toLowerCase())){
                ketQua[index++] = arrPH[i];
            }   
        }
        return ketQua;
    }

    public PhuHuynh timtheoMSHS (String mshs){
        if(mshs == null || mshs.trim().isEmpty()){
            return null;
        }
        mshs = mshs.trim();
        for (int i = 0; i < soLuong; i++) {
            PhuHuynh ph = arrPH[i];
            if(ph != null && ph.getMshs() != null && !ph.getMshs().isEmpty()){
                String[] dsMaCon = ph.getMshs().split(";");
                for (int j = 0; j < dsMaCon.length; j++) {
                    if(dsMaCon[j].trim().equalsIgnoreCase(mshs)){
                        return ph;
                    }
                }
            }
        }
        return null;
    }

    public boolean xoa(String sdt){
        int index = timKiemViTri(sdt);
        if(index == -1){
            return false;
        }
        for(int i = index; i < soLuong-1; i++){
            arrPH[i] = arrPH[i+1];
        }
        arrPH[soLuong-1] = null;
        soLuong--;
        return true;
    }

    public boolean sua(String sdt, Scanner sc){
        int index = timKiemViTri(sdt);
        if (index == -1){
            return false;
        }
        PhuHuynh ph = arrPH[index];
        System.out.println("Cập nhật thông tin cho phụ huynh có SĐT khẩn cấp: " + sdt);
        System.out.println("Lưu ý nếu không muốn đổi thông tin mục nào vui lòng nhấn ENTER!");

        String s;
        
        while (true) { 
            System.out.print("Nhập họ tên mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){ break;}
            if( !s.contains(",")){
            ph.setHoTenBoMe(s);
            break;
            }
        System.out.println(Color.RED + "Lỗi: Không được chứa dấu phẩy" + Color.RESET);
        }

        while (true) { 
            System.out.print("Nhập số điện thoại khẩn cấp mới: ");
            s= sc.nextLine().trim().replaceAll("[\\s.]", "");
            if(s.isEmpty()){break;}
            if(s.matches("^0\\d{9}$")){
                ph.setSdtKhanCap(s);
                break;
            }
            System.out.println(Color.RED + "Lỗi: Số điện thoại phải có 10 số và bắt đầu từ số 0" + Color.RESET);
        }
        while (true) { 
            System.out.println("Nhập số lượng MSHS mới: ");
            s = sc.nextLine().trim();
            if(s.isEmpty()){break;}
            try {
                int soLuongHS = Integer.parseInt(s);
                if(soLuongHS >= 0){
                    if(soLuongHS == 0){
                        ph.setMshs("");
                        break;
                    }
                    String mshsMoi = "";
                    boolean nhapThanhCong = true;
                    for (int i = 0; i < soLuongHS; i++) {
                        System.out.print("  + Nhập MSHS thứ " + (i + 1) + ": ");
                        String maHS = sc.nextLine().trim();
                        if(maHS.isEmpty() || maHS.contains(",")){
                            System.out.println(Color.RED + "Lỗi: MSHS không được để trống hoặc chứa dấu phẩy! Vui lòng nhập lại từ đầu" + Color.RESET);
                            nhapThanhCong = false;
                            break;
                        }
                        if(mshsMoi.isEmpty()){
                            mshsMoi = maHS;
                        }
                        else{
                            mshsMoi = mshsMoi + ";" + maHS;
                        }
                    }
                    if(nhapThanhCong){
                        ph.setMshs(mshsMoi);
                        break;
                    }
                }
                else{
                        System.out.println(Color.RED + "Lỗi: Số lượng không được là số âm" + Color.RESET);
                    }
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Lỗi: Số lượng phải là số nguyên hợp lệ" + Color.RESET);
            }
        }
        return true;
    }

    public boolean docFile(){
        int soDong = 0;
        int soDongLoi = 0;
        File f = new File(DUONG_DAN_FILE);
        if(!f.exists()){
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_FILE))){
            String line;
            this.soLuong = 0;

            while ((line = br.readLine()) != null){
                line = line.trim();
                soDong++;

                if(line.isEmpty()) continue;
                String[] parts = line.split(",",-1);
                if(parts.length < 3){
                    System.out.println("Dòng " + soDong + ": Thiếu cột dữ liệu (cần ít nhất 3, có " + parts.length + ")");
                    soDongLoi++;
                    continue;
                }
                for(int i = 0; i < parts.length; i++){
                    parts[i] = parts[i].trim();
                }
                try {
                    String hoTenBoMe = parts[0].trim();
                    String sdtKhanCap = parts[1].trim();
                    String mshs = ((parts.length >= 3) ? parts[2].trim() : "");

                    if(hoTenBoMe.isEmpty() || sdtKhanCap.isEmpty()){
                        System.out.println("Dòng " + soDong + ": Họ tên hoặc SĐT không được để trống!");
                        soDongLoi++;
                        continue;
                    }

                    PhuHuynh ph = new PhuHuynh(hoTenBoMe, sdtKhanCap, mshs);
                    them(ph);
                } catch (Exception e) {
                    System.out.println("Dòng " + soDong + ": " + e.getMessage());
                    soDongLoi++;
                }
            }
            System.out.println("Đọc file PhuHuynh thành công! Tổng số: " + soLuong + " phụ huynh, " + soDongLoi + " dòng lỗi.");
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file PhuHuynh: " + e.getMessage());
            return false;
        }
    } 

    public boolean ghiFile(){
        File f = new File(DUONG_DAN_FILE);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
                thuMucCha.mkdirs();}
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
            for (int i = 0; i < soLuong; i++) {
                if(arrPH[i] != null){
                    bw.write(arrPH[i].toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file PhuHuynh thành công!");
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file PhuHuynh: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public void hienThiDanhSach() { 
        System.out.println("========== DANH SACH PHU HUYNH ==========");
        if(soLuong == 0){
            System.out.println("Danh sach phu huynh dang trong!");
            return;
        }
        System.out.printf("%-25s | %-15s | %-10s\n", "Ho ten bo me", "So dien thoai", "MSHS");
        System.out.println("-------------------------------------------------------------------");
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null){
                System.out.printf("%-25s | %-15s | %-10s\n", 
                arrPH[i].getHoTenBoMe(), 
                arrPH[i].getSdtKhanCap(), 
                arrPH[i].getMshs() != null ? arrPH[i].getMshs() : "");
            }
        }
    }

    @Override
    public void inBaoCaoThongKe() { 
        System.out.println("========== BAO CAO THONG KE ==========");
        if(soLuong == 0){
            System.out.println("Khong co phu huynh nao de thong ke!");
            return;
        }
        int daLienKet = 0;
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null && arrPH[i].getMshs() != null && !arrPH[i].getMshs().isEmpty()){
                daLienKet++;
            }
        }
        System.out.println("Tong so luong phu huynh: " + soLuong);
        System.out.println("So phu huynh da lien ket hoc sinh: " + daLienKet);
        System.out.println("So phu huynh chua lien ket hoc sinh: " + (soLuong - daLienKet));
    }
}
