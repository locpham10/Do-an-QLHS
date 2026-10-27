package collections;

import java.util.Scanner;
import models.GiaoVien;
import models.LopHoc;
import models.MonHoc;
import interfaces.IThongKe;

public class DanhSachGiaoVien implements IThongKe {
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
            models.MonHoc monMoi = new models.MonHoc("", tenMon, 1, true);
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
                dsLopMoi[i] = new models.LopHoc(tenLop, "", null, null, false, "", 0.0);  
            }
            gv.setDsLopDay(dsLopMoi);
        }
        System.out.println("Cập nhật  thông tin thành công!");
        sc.close();
        return true;
    }

   public boolean ghiFile(){
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter("giaovien.txt"))) {
            for(int i = 0; i < soLuong; i++){
                GiaoVien gv = arrGV[i];
                if(gv != null){
                    String tenMon = (gv.getMonChuyenMon() != null) ? gv.getMonChuyenMon().getTenMon() : "Không";
                    LopHoc [] dsLop = gv.getDsLopDay();
                    int soLop = (dsLop != null) ? dsLop.length : 0; 
                    pw.print(gv.getMaGV() + "," + gv.getHoTen() + "," + gv.getNgaySinh() + "," + gv.isGioiTinh() + 
                    "," + gv.getDiaChi() + "," + gv.getSoDienThoai() + "," + gv.getChucVu() + "," + gv.getLuong() +","+
                               tenMon + "," + soLop);
                    if(soLop > 0){
                        for(int  j = 0; j < soLop; j++){
                            if(dsLop[j] != null){
                                pw.print("," + dsLop[j].getTenLop());
                            }
                            else{
                                pw.print("Không");
                            }
                        }
                    }
                    pw.println();
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
                if(line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if(parts.length >= 10){
                    String maGV = parts[0];
                    String hoTen = parts[1];
                    String ngaySinh = parts[2];
                    boolean gioiTinh = Boolean.parseBoolean(parts[3]);
                    String diaChi = parts[4];
                    String soDienThoai = parts[5];
                    String chucVu = parts[6];
                    double luong = Double.parseDouble(parts[7]);
                    MonHoc monChuyenMon = null;
                    if(!parts[8].equalsIgnoreCase("Không")){
                        monChuyenMon = new MonHoc("", parts[8].trim(), 1, true);
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
                    if(soLuong < arrGV.length){
                        arrGV[soLuong] = gv;
                        soLuong++; 
                    }
                }
            }
            System.out.println("Doc file GiaoVien thanh cong! Tong so: " + soLuong + " giao vien.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file GiaoVien: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void inTop5XuatSac() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inTop5XuatSac'");
    }

    @Override
    public void tinhTyLeDat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tinhTyLeDat'");
    }
    }





