package collections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import models.PhuHuynh;

public class DanhSachPhuHuynh {
    private final String FILE_PATH = "phuhuynh.txt";
    private PhuHuynh[] arrPH = new PhuHuynh[1000];
    private int soLuong = 0;

    // Hàm thêm tạm thời để docFile() gọi không bị lỗi
    public boolean them(PhuHuynh ph) {
        if (ph != null && soLuong < arrPH.length) {
            this.arrPH[soLuong] = ph;
            soLuong++;
            return true;
        }
        return false;
    }

    public boolean ghiFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < soLuong; i++) {
                if (arrPH[i] != null) {
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

    public boolean docFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            this.soLuong = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (this.soLuong >= arrPH.length) {
                    System.out.println("Canh bao: Mang PhuHuynh da day!");
                    break;
                }

                String[] data = line.split(",");
                if (data.length < 2) continue; 

                String hoTenBoMe = data[0].trim();
                String sdtKhanCap = data[1].trim();

                PhuHuynh ph = new PhuHuynh(hoTenBoMe, sdtKhanCap);
                this.them(ph);
            }
            System.out.println("Doc file PhuHuynh thanh cong! Tong so: " + soLuong + " phu huynh.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file PhuHuynh: " + e.getMessage());
            return false;
        }
    }
}
