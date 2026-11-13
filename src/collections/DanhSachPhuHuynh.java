package collections;

import interfaces.IThongKe;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import models.PhuHuynh;

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

    public boolean them(PhuHuynh ph){
        if(ph != null && soLuong < arrPH.length){
            arrPH[soLuong] = ph;
            soLuong ++;
            return true; //them thanh cong
        }
        return false; // them that bai (day/null)
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

    public PhuHuynh timtheoMSHS (String mshs){
        for(int i = 0; i < soLuong; i++){
            if(arrPH[i] != null && arrPH[i].getMshs() != null && arrPH[i].getMshs().equalsIgnoreCase(mshs)){
                return arrPH[i];
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
        System.out.print("Nhập họ tên phụ huynh mới: ");
        ph.setHoTenBoMe(sc.nextLine());
        System.out.print("Nhập SĐT khẩn cấp mới: ");
        ph.setSdtKhanCap(sc.nextLine());
        System.out.println("Cập nhật thông tin phụ huynh thành công!");
        return true;
    }

    public boolean docFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_FILE))){
            String line;
            this.soLuong = 0;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if(line.isEmpty()) continue;
                String[] parts = line.split(",");
                if(parts.length >=2){
                    String hoTenBoMe = parts[0].trim();
                    String sdtKhanCap = parts[1].trim();
                    String mshs = ((parts.length > 2) ? parts[2].trim() : "");

                    PhuHuynh ph = new PhuHuynh(hoTenBoMe, sdtKhanCap, mshs);
                    if(soLuong < arrPH.length){
                        arrPH[soLuong] = ph;
                        soLuong++;
                    }
                }
            }
            System.out.println("Doc file PhuHuynh thanh cong! Tong: " + soLuong + " phu huynh.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file PhuHuynh: " + e.getMessage());
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
            System.out.println("Ghi file PhuHuynh thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file PhuHuynh: " + e.getMessage());
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
