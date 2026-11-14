package collections;

import models.LopHoc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import models.GiaoVien;
import models.HocSinh;
import models.MonHoc;

public class DanhSachLopHoc
{
    private LopHoc[] dsLop;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/lophoc.txt";


    public DanhSachLopHoc() {
        this.dsLop = new LopHoc[100];
        this.soLuong = 0;
    }

    public DanhSachLopHoc(int dungLuong) {
        this.dsLop = new LopHoc[dungLuong];
        this.soLuong = 0; }

    public boolean themLop(LopHoc lh) {
        if(lh != null && soLuong < dsLop.length)
        {
            this.dsLop[soLuong] = lh;
            soLuong++;
            return true; //Thêm lớp thành công!!
        }
        return false; //Thêm lớp thất bại vì đối tượng null/mảng đã đầy.
    }

    public LopHoc timKiemLopTheoTen(String tenLop) {
        for(int i = 0; i < soLuong; i++)
            if(dsLop[i] != null && dsLop[i].getTenLop().equalsIgnoreCase(tenLop))
                return dsLop[i];
        return null;
    }

    public void hienThiDanhSachLop()
    {
        if(soLuong == 0)
        {
            System.out.println("Danh sách lớp học đang trống.");
            return;
        }

        System.out.println("| DANH SÁCH CÁC LỚP HỌC |");
        for(int i = 0; i < soLuong; i++)
            System.out.println((i+1) + ". " + dsLop[i]);
    }

    public boolean ghiFile() {
        File f = new File(DUONG_DAN_FILE);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) {
            thuMucCha.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (dsLop[i] != null) {
                    bw.write(dsLop[i].toFileString());
                    bw.newLine();
                }
            }
            System.out.println("Ghi file LopHoc thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file LopHoc: " + e.getMessage());
            return false;
        }
    }

    // đọc & không gắn GVCN
    public boolean docFile() {
        return docFile(null);
    }

    // đọc & gắn GVCN= dsGV.timKiem(maGV), tạo DanhSachHocSinh rỗng cho mỗi lớp
    public boolean docFile(DanhSachGiaoVien dsGV) {
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_FILE))) {
            String line;
            int soDong = 0;
            this.soLuong = 0;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 6) {
                    System.out.println("Dong " + soDong + ": thieu cot (can it nhat 6, co " + p.length + ")");
                    continue;
                }
                for (int i = 0; i < p.length; i++) p[i] = p[i].trim();

                try {
                    String tenLop = p[0];
                    String khoaHoc = p[1];
                    String maGV = p[2];
                    boolean laChuyen = Boolean.parseBoolean(p[3]);
                    String monChuyen = p[4];
                    double phuPhi = Double.parseDouble(p[5]);

                    GiaoVien gvcn = null;
                    if (dsGV != null && !maGV.isEmpty()) {
                        gvcn = dsGV.timKiem(maGV);
                        if (gvcn == null) {
                            System.out.println("Dong " + soDong + ": khong tim thay ma GV '" + maGV + "'");
                        }
                    }

                    LopHoc lop = new LopHoc(tenLop, khoaHoc, gvcn, new DanhSachHocSinh(),
                                            laChuyen, monChuyen, phuPhi);
                    if (p.length >= 7 && !p[6].isEmpty()) {
                        lop.setChuongTrinhHoc(docChuongTrinh(p[6], soDong));
                    }
                    themLop(lop);
                } catch (NumberFormatException e) {
                    System.out.println("Dong " + soDong + ": loi dinh dang so: " + line);
                }
            }
            System.out.println("Doc file LopHoc thanh cong! Tong so: " + soLuong + " lop.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file LopHoc: " + e.getMessage());
            return false;
        }
    }

public void phanHocSinhVaoLop(DanhSachHocSinh dsHS) {
        if (dsHS == null) return;
        for (int i = 0; i < soLuong; i++) {
            if (dsLop[i] == null) continue;
            DanhSachHocSinh dsLopNay = new DanhSachHocSinh();
            for (HocSinh hs : dsHS.timKiemTheoLop(dsLop[i].getTenLop())) {
                dsLopNay.them(hs);
            }
            dsLop[i].setDsHocSinh(dsLopNay);
        }
    }

        private MonHoc[] docChuongTrinh(String cot, int soDong) {
        String[] items = cot.split(";");
        MonHoc[] tam = new MonHoc[items.length];
        int n = 0;
        for (String item : items) {
            item = item.trim();
            if (item.isEmpty()) continue;
            String[] kv = item.split(":");
            if (kv.length != 2) {
                System.out.println("Dong " + soDong + ": chuong trinh hoc sai dinh dang '" + item + "'");
                continue;
            }
            String maMon = kv[0].trim();
            MonHoc goc = MonHoc.timTheoMa(maMon);
            if (goc == null) {
                System.out.println("Dong " + soDong + ": khong tim thay ma mon '" + maMon + "'");
                continue;
            }
            try {
                tam[n++] = goc.copyVoiHeSo(Integer.parseInt(kv[1].trim()));  // copy, không sửa kho môn
            } catch (NumberFormatException e) {
                System.out.println("Dong " + soDong + ": he so khong hop le '" + kv[1] + "'");
            }
        }
        return java.util.Arrays.copyOf(tam, n);
    }

    public LopHoc[] getDsLop() { return dsLop; }
    public void setDsLop(LopHoc[] dsLop) { this.dsLop = dsLop; }

    public int getSoLuong() { return soLuong; }
}
