package collections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Scanner;
import models.GiaoVien;
import models.HocSinh;
import models.HocSinhChuyen;
import models.LopHoc;
import models.MonHoc;
import models.TietHoc;

public class DanhSachLopHoc
{
    private LopHoc[] dsLop;
    private int soLuong;
    public static final String DUONG_DAN_FILE = "src/data/lophoc.txt";


    public DanhSachLopHoc() {
        this.dsLop = new LopHoc[0];
        this.soLuong = 0;
    }

    public boolean themLop(LopHoc lh) {
        if(lh == null) return false; //Them lop that bai vi doi tuong null

        this.dsLop = java.util.Arrays.copyOf(dsLop, soLuong +1);
        this.dsLop[soLuong] = lh;
        soLuong++;
        return true; //Them lop thanh cong!!
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
    private int soCanhBao;
    // đọc & gắn GVCN= dsGV.timKiem(maGV), tạo DanhSachHocSinh rỗng cho mỗi lớp
    public boolean docFile(DanhSachGiaoVien dsGV) {
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_FILE))) {
            String line;
            int soDong = 0;
            int soDongLoi = 0;
            this.soCanhBao = 0;
            this.dsLop = new LopHoc[0];
            this.soLuong = 0;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 6) {
                    System.out.println("Dong " + soDong + ": thieu cot (can it nhat 6, co " + p.length + ")");
                    soDongLoi++;
                    continue;}
                for (int i = 0; i < p.length; i++) p[i] = p[i].trim();

                try {
                    String tenLop = p[0];
                    if (tenLop.isEmpty()) {
                        System.out.println("Dong " + soDong + ": ten lop bi trong, bo qua dong nay");
                        soDongLoi++;
                        continue;
                    }
                    if (timKiemLopTheoTen(tenLop) != null) {
                        System.out.println("Dong " + soDong + ": ten lop '" + tenLop + "' bi trung, bo qua dong nay");
                        soDongLoi++;
                        continue;}
                    String khoaHoc = p[1];
                    String maGV = p[2];
                    boolean laChuyen = Boolean.parseBoolean(p[3]);
                    String monChuyen = p[4];
                    double phuPhi = Double.parseDouble(p[5]);

                    GiaoVien gvcn = null;
                    if (dsGV != null && !maGV.isEmpty()) {
                        gvcn = dsGV.timKiem(maGV);
                        if (gvcn == null) {
                            System.out.println("Dong " + soDong + ": khong tim thay ma GV '" + maGV + "', lop chua co GVCN");
                            soCanhBao++;
                        }
                    }

                    LopHoc lop = new LopHoc(tenLop, khoaHoc, gvcn, new DanhSachHocSinh(),
                                            laChuyen, monChuyen, phuPhi);
                    if (p.length >= 7 && !p[6].isEmpty()) {
                        lop.setChuongTrinhHoc(docChuongTrinh(p[6], soDong));
                    }
                    if (!themLop(lop)) {
                        System.out.println("Dong " + soDong + ": danh sach lop da day (" + dsLop.length
                                + "), khong them duoc lop '" + tenLop + "'");
                        soDongLoi++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Dong " + soDong + ": loi dinh dang so: " + line);
                    soDongLoi++;
                }

            }
            System.out.println("Doc file LopHoc thanh cong! Tong so: " + soLuong + " lop, "
                    + soDongLoi + " dong loi, " + soCanhBao + " canh bao.");
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
                soCanhBao++;
                continue;
            }

            int heSo;
            try {
                heSo = Integer.parseInt(kv[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Dong " + soDong + ": he so khong hop le '" + kv[1] + "'");
                continue;
            }
            if (heSo < 1) {
                System.out.println("Dong " + soDong + ": he so cua mon '" + maMon + "' phai >= 1 (dang la " + heSo + ")");
                continue;
            }

            boolean lap = false;
            for (int i = 0; i < n; i++) {
                if (tam[i].getMaMon().equalsIgnoreCase(goc.getMaMon())) {
                    lap = true;
                    break;
                }
            }
            if (lap) {
                System.out.println("Dong " + soDong + ": ma mon '" + maMon + "' bi lap trong lop, bo qua");
                continue;
            }

            tam[n++] = goc.copyVoiHeSo(heSo);   
        }
        return java.util.Arrays.copyOf(tam, n);
    }

    // ================= b. SỬA =================
    // Sửa: tên lớp, khóa học, GVCN. Enter = giữ nguyên.
    // Hệ chuyên/đại trà, môn chuyên, phụ phí, chương trình học KHÔNG sửa ở đây
    // vì ảnh hưởng tới điểm và học phí của học sinh trong lớp.
    public boolean sua(String tenLop, Scanner sc, DanhSachGiaoVien dsGV, DanhSachHocSinh dsTruong) {
        LopHoc lop = timKiemLopTheoTen(tenLop);
        if(lop == null)
        {
            System.out.println("Khong tim thay lop: " + tenLop);
            return false;
        }

        System.out.println("Dang sua thong tin lop: " + lop.getTenLop());
        System.out.println("Luu y: Nhan Enter de bo qua neu khong muon doi truong do.");
        boolean daDoi = false;

        // 1. Tên lớp: không rỗng, không có dấu phẩy (file CSV), không trùng lớp khác
        while(true)
        {
            System.out.print("Nhap ten lop moi (hien tai " + lop.getTenLop() + "): ");
            String tenMoi = sc.nextLine().trim();
            if(tenMoi.isEmpty()) break;
            if(tenMoi.contains(","))
            {
                System.out.println("Loi: Ten lop khong duoc chua dau phay!");
                continue;
            }
            LopHoc trung = timKiemLopTheoTen(tenMoi);
            if(trung != null && trung != lop)
            {
                System.out.println("Loi: Lop " + tenMoi + " da ton tai trong he thong!");
                continue;
            }
            if(!tenMoi.equals(lop.getTenLop()))
            {
                String tenCu = lop.getTenLop();
                // Học sinh lưu tên lớp dạng chuỗi nên phải đổi theo, nếu không sẽ "mồ côi"
                HocSinh[] dsHs = (dsTruong != null) ? dsTruong.timKiemTheoLop(tenCu) : new HocSinh[0];
                lop.setTenLop(tenMoi);
                for(HocSinh hs : dsHs)
                    hs.setTenLop(tenMoi);
                System.out.println("  -> Da doi ten lop " + tenCu + " thanh " + tenMoi
                        + " (cap nhat " + dsHs.length + " hoc sinh)");
                daDoi = true;
            }
            break;
        }

        // 2. Khóa học dạng yyyy-yyyy, năm sau lớn hơn năm đầu
        while(true)
        {
            System.out.print("Nhap khoa hoc moi (hien tai " + lop.getKhoaHoc() + ", VD: 2026-2029): ");
            String khoaMoi = sc.nextLine().trim();
            if(khoaMoi.isEmpty()) break;
            if(khoaMoi.matches("\\d{4}-\\d{4}")
                    && Integer.parseInt(khoaMoi.substring(5)) > Integer.parseInt(khoaMoi.substring(0, 4)))
            {
                if(!khoaMoi.equals(lop.getKhoaHoc()))
                {
                    lop.setKhoaHoc(khoaMoi);
                    System.out.println("  -> Da doi khoa hoc thanh " + khoaMoi);
                    daDoi = true;
                }
                break;
            }
            System.out.println("Loi: Khoa hoc phai dung dang nam-nam (VD: 2026-2029)!");
        }

        // 3. Giáo viên chủ nhiệm: nhập mã GV, "-" để bỏ GVCN
        while(true)
        {
            GiaoVien hienTai = lop.getGiaoVienChuNhiem();
            System.out.print("Nhap ma GVCN moi (hien tai " + (hienTai != null ? hienTai.getMaGV() : "chua co")
                    + ", Enter = giu nguyen, - = bo GVCN): ");
            String ma = sc.nextLine().trim();
            if(ma.isEmpty()) break;
            if(ma.equals("-"))
            {
                if(hienTai != null)
                {
                    lop.setGiaoVienChuNhiem(null);
                    System.out.println("  -> Da bo GVCN cua lop.");
                    daDoi = true;
                }
                break;
            }
            GiaoVien gv = (dsGV != null) ? dsGV.timKiem(ma) : null;
            if(gv == null)
            {
                System.out.println("Loi: Khong tim thay ma giao vien " + ma + "!");
                continue;
            }
            lop.setGiaoVienChuNhiem(gv);
            System.out.println("  -> Da doi GVCN thanh " + gv.getHoTen() + " (" + gv.getMaGV() + ")");
            daDoi = true;
            break;
        }

        System.out.println(daDoi ? "Cap nhat thong tin lop thanh cong!" : "Khong co thay doi nao duoc luu.");
        return daDoi;
    }

    // ================= c. XÓA =================
    // Lớp trống: xóa luôn. Lớp còn học sinh: phải xác nhận, và các học sinh đó bị xóa theo
    // (học sinh không thể không thuộc lớp nào).
    public boolean xoa(String tenLop, Scanner sc, DanhSachHocSinh dsTruong) {
        int viTri = -1;
        for(int i = 0; i < soLuong; i++)
        {
            if(dsLop[i] != null && dsLop[i].getTenLop().equalsIgnoreCase(tenLop))
            {
                viTri = i;
                break;
            }
        }
        if(viTri == -1)
        {
            System.out.println("Khong tim thay lop: " + tenLop);
            return false;
        }

        LopHoc lop = dsLop[viTri];
        HocSinh[] hsTrongLop = (dsTruong != null) ? dsTruong.timKiemTheoLop(lop.getTenLop()) : new HocSinh[0];

        if(dsTruong != null && hsTrongLop.length > 0)
        {
            System.out.println("Lop " + lop.getTenLop() + " con " + hsTrongLop.length + " hoc sinh.");
            System.out.println("Xoa lop se xoa luon cac hoc sinh nay khoi he thong!");
            System.out.print("Xac nhan xoa lop va " + hsTrongLop.length + " hoc sinh? (Y/N): ");
            if(!sc.nextLine().trim().equalsIgnoreCase("Y"))
            {
                System.out.println("Da huy lenh xoa.");
                return false;
            }
            for(HocSinh hs : hsTrongLop)
                dsTruong.xoa(hs.getMshs());
        }

        // Dồn các phần tử phía sau lên rồi co mảng lại đúng soLuong - 1
        for(int i = viTri; i < soLuong - 1; i++)
            dsLop[i] = dsLop[i + 1];
        soLuong--;
        dsLop = Arrays.copyOf(dsLop, soLuong);

        System.out.println("Da xoa lop " + lop.getTenLop() + "!");
        return true;
    }

    // ================= d. TÌM KIẾM =================
    // Tìm gần đúng theo khóa học (VD: "2026" ra mọi lớp có khóa chứa 2026)
    public LopHoc[] timKiemTheoKhoaHoc(String tuKhoa) {
        String key = chuanHoa(tuKhoa);
        boolean[] khop = new boolean[soLuong];
        for(int i = 0; i < soLuong; i++)
            khop[i] = dsLop[i] != null && chuanHoa(dsLop[i].getKhoaHoc()).contains(key);
        return gom(khop);
    }

    // Tìm theo hệ: true = lớp chuyên, false = lớp đại trà
    public LopHoc[] timKiemTheoHe(boolean laChuyen) {
        boolean[] khop = new boolean[soLuong];
        for(int i = 0; i < soLuong; i++)
            khop[i] = dsLop[i] != null && dsLop[i].isLopChuyen() == laChuyen;
        return gom(khop);
    }

    // Tìm gần đúng theo họ tên GVCN, gõ không dấu cũng ra ("duyen" khớp "Trần Mỹ Duyên")
    public LopHoc[] timKiemTheoTenGVCN(String tuKhoa) {
        String key = chuanHoa(tuKhoa);
        boolean[] khop = new boolean[soLuong];
        for(int i = 0; i < soLuong; i++)
        {
            GiaoVien gv = (dsLop[i] != null) ? dsLop[i].getGiaoVienChuNhiem() : null;
            khop[i] = gv != null && chuanHoa(gv.getHoTen()).contains(key);
        }
        return gom(khop);
    }

    // ================= e. THỐNG KÊ =================
    // Sĩ số lấy từ danh sách học sinh toàn trường (theo tên lớp của từng học sinh)
    public int demSiSo(String tenLop, DanhSachHocSinh dsTruong) {
        if(dsTruong == null) return 0;
        return dsTruong.timKiemTheoLop(tenLop).length;
    }

    public int demLopChuyen() {
        int dem = 0;
        for(int i = 0; i < soLuong; i++)
            if(dsLop[i] != null && dsLop[i].isLopChuyen()) dem++;
        return dem;
    }

    public int demLopDaiTra() {
        return soLuong - demLopChuyen();
    }

    public LopHoc[] timLopChuaCoGVCN() {
        boolean[] khop = new boolean[soLuong];
        for(int i = 0; i < soLuong; i++)
            khop[i] = dsLop[i] != null && dsLop[i].getGiaoVienChuNhiem() == null;
        return gom(khop);
    }

    public void inBaoCaoThongKe(DanhSachHocSinh dsTruong) {
        if(soLuong == 0)
        {
            System.out.println("Danh sach lop hoc dang trong, chua co gi de thong ke.");
            return;
        }

        System.out.println("\n===== THONG KE LOP HOC =====");
        System.out.println("Tong so lop: " + soLuong
                + " (chuyen: " + demLopChuyen() + ", dai tra: " + demLopDaiTra() + ")");

        System.out.println("\n--- Si so tung lop ---");
        int tongHs = 0;
        for(int i = 0; i < soLuong; i++)
        {
            int siSo = demSiSo(dsLop[i].getTenLop(), dsTruong);
            tongHs += siSo;
            System.out.println((i + 1) + ". " + dsLop[i].getTenLop()
                    + " (" + (dsLop[i].isLopChuyen() ? "chuyen" : "dai tra") + "): " + siSo + " hoc sinh");
        }
        System.out.println("Tong cong: " + tongHs + " hoc sinh");

        System.out.println("\n--- Lop chua co GVCN ---");
        LopHoc[] chuaCo = timLopChuaCoGVCN();
        if(chuaCo.length == 0)
            System.out.println("Tat ca cac lop deu da co GVCN.");
        else
            for(LopHoc l : chuaCo)
                System.out.println("- " + l.getTenLop());
    }

    // ================= f. CHUYỂN LỚP =================
    // Chuyển học sinh (theo MSHS) sang lớp mới. ds = danh sách học sinh toàn trường.
    // - Chỉ chuyển trong cùng hệ (chuyên <-> chuyên, đại trà <-> đại trà), vì học sinh chuyên
    //   có thêm môn chuyên và phụ phí mà học sinh đại trà không có.
    // - Bảng điểm cũ được giữ nguyên; muốn tính theo chương trình lớp mới thì nhập lại điểm.
    public boolean chuyenLop(String mshs, String tenLopMoi, DanhSachHocSinh ds) {
        if(ds == null) return false;

        HocSinh hs = ds.timKiem(mshs);
        if(hs == null)
        {
            System.out.println("Khong tim thay hoc sinh ma: " + mshs);
            return false;
        }

        LopHoc lopMoi = timKiemLopTheoTen(tenLopMoi);
        if(lopMoi == null)
        {
            System.out.println("Khong tim thay lop: " + tenLopMoi);
            return false;
        }

        String tenLopCu = hs.getTenLop();
        if(lopMoi.getTenLop().equalsIgnoreCase(tenLopCu))
        {
            System.out.println("Hoc sinh " + hs.getHoTen() + " dang o lop " + tenLopCu + " roi!");
            return false;
        }

        boolean laHsChuyen = hs instanceof HocSinhChuyen;
        if(laHsChuyen != lopMoi.isLopChuyen())
        {
            System.out.println("Khong the chuyen: " + hs.getHoTen() + " la hoc sinh "
                    + (laHsChuyen ? "chuyen" : "dai tra") + " nhung lop " + lopMoi.getTenLop()
                    + " la lop " + (lopMoi.isLopChuyen() ? "chuyen" : "dai tra") + ".");
            return false;
        }

        // Rút khỏi danh sách của lớp cũ (nếu có)
        LopHoc lopCu = timKiemLopTheoTen(tenLopCu);
        if(lopCu != null && lopCu.getDsHocSinh() != null && lopCu.getDsHocSinh().timKiem(hs.getMshs()) != null)
            lopCu.getDsHocSinh().xoa(hs.getMshs());

        // Thêm vào danh sách của lớp mới
        if(lopMoi.getDsHocSinh() == null)
            lopMoi.setDsHocSinh(new DanhSachHocSinh());
        if(lopMoi.getDsHocSinh().timKiem(hs.getMshs()) == null)
            lopMoi.getDsHocSinh().them(hs);

        hs.setTenLop(lopMoi.getTenLop());

        // Học sinh chuyên: cập nhật môn chuyên và phụ phí theo lớp mới
        if(laHsChuyen)
        {
            HocSinhChuyen hsc = (HocSinhChuyen) hs;
            String monMoi = (lopMoi.getMonChuyenCuaLop() != null) ? lopMoi.getMonChuyenCuaLop() : "";
            if(!chuanHoa(monMoi).equals(chuanHoa(hsc.getMonChuyen())))
            {
                hsc.setDiemMonChuyen(0.0); // đổi môn chuyên thì điểm môn chuyên cũ không còn đúng
                System.out.println("Luu y: doi mon chuyen " + hsc.getMonChuyen() + " -> " + monMoi
                        + ", diem mon chuyen duoc dat ve 0.0, can nhap lai.");
            }
            hsc.setMonChuyen(monMoi);
            hsc.setPhuPhiChuyenDe(lopMoi.getPhuPhiChung());
        }

        System.out.println("Da chuyen " + hs.getHoTen() + " tu lop " + tenLopCu + " sang lop " + lopMoi.getTenLop() + "!");
        System.out.println("Luu y: bang diem cu van giu nguyen, hay nhap lai diem theo chuong trinh lop moi.");
        return true;
    }

    public LopHoc[] getDsLop() { return dsLop; }

    // THOI KHOA BIEU
    public static final String DUONG_DAN_TKB = "src/data/thoikhoabieu.txt";
 
    // Trả về lớp KHÁC (không tính boQua) đang có chính GV này dạy ở thu/tiet đó; không có thì null
    public LopHoc kiemTraTrungTiet(GiaoVien gv, int thu, int tiet, LopHoc boQua) {
        if (gv == null || !LopHoc.hopLeViTri(thu, tiet)) return null;
        for (int i = 0; i < soLuong; i++) {
            LopHoc l = dsLop[i];
            if (l == null || l == boQua) continue;
            TietHoc t = l.getTiet(thu, tiet);
            if (t != null && t.getGv() != null
                    && t.getGv().getMaGV().equalsIgnoreCase(gv.getMaGV()))
                return l;
        }
        return null;
    }
 
    // Xếp tiết cho lớp tenLop, kiểm tra đủ ràng buộc (ném IllegalArgumentException nếu sai)
    public void xepTiet(String tenLop, int thu, int tiet, MonHoc mon, GiaoVien gv) {
        LopHoc lop = timKiemLopTheoTen(tenLop);
        if (lop == null)
            throw new IllegalArgumentException("Khong tim thay lop " + tenLop);
 
        LopHoc trung = kiemTraTrungTiet(gv, thu, tiet, lop);
        if (trung != null)
            throw new IllegalArgumentException("Giao vien " + gv.getHoTen() + " ("
                    + gv.getMaGV() + ") da day lop " + trung.getTenLop()
                    + " vao thu " + thu + " tiet " + tiet);
 
        lop.xepTiet(thu, tiet, mon, gv);
    }
 
    // In lịch dạy trong tuần của một giáo viên (tổng hợp từ thời khóa biểu mọi lớp)
    public void inLichDay(String maGV) {
        String[][] o = new String[LopHoc.SO_NGAY][LopHoc.SO_TIET];
        String tenGV = null;
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (dsLop[i] == null) continue;
            for (int n = 0; n < LopHoc.SO_NGAY; n++) {
                for (int t = 0; t < LopHoc.SO_TIET; t++) {
                    TietHoc th = dsLop[i].getLichHoc()[n][t];
                    if (th != null && th.getGv() != null
                            && th.getGv().getMaGV().equalsIgnoreCase(maGV.trim())) {
                        o[n][t] = dsLop[i].getTenLop() + "-" + th.getMon().getMaMon();
                        tenGV = th.getGv().getHoTen();
                        dem++;
                    }
                }
            }
        }
 
        if (dem == 0) {
            System.out.println("Giao vien " + maGV + " chua co tiet day nao.");
            return;
        }
 
        String vien = "+-------";
        for (int n = 0; n < LopHoc.SO_NGAY; n++) vien += "+------------";
        vien += "+";
        System.out.println("\nLICH DAY: " + tenGV + " (" + maGV.trim().toUpperCase() + ")");
        System.out.println(vien);
        String dong = String.format("| %-5s ", "Tiet");
        for (int n = 0; n < LopHoc.SO_NGAY; n++) dong += String.format("| %-10s ", "Thu " + (n + 2));
        System.out.println(dong + "|");
        System.out.println(vien);
        for (int t = 0; t < LopHoc.SO_TIET; t++) {
            dong = String.format("| %-5d ", t + 1);
            for (int n = 0; n < LopHoc.SO_NGAY; n++)
                dong += String.format("| %-10s ", o[n][t] == null ? "-" : o[n][t]);
            System.out.println(dong + "|");
        }
        System.out.println(vien);
        System.out.println("Tong so tiet day trong tuan: " + dem);
    }
 
    // Ghi thoikhoabieu.txt: mỗi dòng  tenLop,thu,tiet,maMon,maGV
    public boolean ghiThoiKhoaBieu() {
        File f = new File(DUONG_DAN_TKB);
        File thuMucCha = f.getParentFile();
        if (thuMucCha != null && !thuMucCha.exists()) thuMucCha.mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (int i = 0; i < soLuong; i++) {
                if (dsLop[i] == null) continue;
                for (int n = 0; n < LopHoc.SO_NGAY; n++) {
                    for (int t = 0; t < LopHoc.SO_TIET; t++) {
                        TietHoc th = dsLop[i].getLichHoc()[n][t];
                        if (th == null) continue;
                        bw.write(dsLop[i].getTenLop() + "," + (n + 2) + "," + (t + 1) + ","
                                + th.getMon().getMaMon() + "," + th.getGv().getMaGV());
                        bw.newLine();
                    }
                }
            }
            System.out.println("Ghi file thoi khoa bieu thanh cong!");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi ghi file thoi khoa bieu: " + e.getMessage());
            return false;
        }
    }
 
    // Đọc thoikhoabieu.txt (phải đọc lophoc.txt và giaovien.txt trước). Mỗi dòng đi qua xepTiet()
    // nên cũng bị kiểm tra đủ ràng buộc; dòng sai chỉ báo lỗi rồi bỏ qua.
    public boolean docThoiKhoaBieu(DanhSachGiaoVien dsGV) {
        if (soLuong == 0) {
            System.out.println("Chua co lop nao, hay doc lophoc.txt truoc khi doc thoi khoa bieu.");
            return false;
        }
        for (int i = 0; i < soLuong; i++)
            if (dsLop[i] != null) dsLop[i].xoaTatCaTiet();
 
        int soDong = 0, soLoi = 0, soTiet = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(DUONG_DAN_TKB))) {
            String line;
            while ((line = br.readLine()) != null) {
                soDong++;
                line = line.trim();
                if (line.isEmpty()) continue;
 
                String[] p = line.split(",", -1);
                if (p.length != 5) {
                    System.out.println("Dong " + soDong + ": can dung 5 cot (tenLop,thu,tiet,maMon,maGV), co " + p.length);
                    soLoi++;
                    continue;
                }
                try {
                    int thu = Integer.parseInt(p[1].trim());
                    int tiet = Integer.parseInt(p[2].trim());
                    MonHoc mon = MonHoc.timTheoMa(p[3]);
                    GiaoVien gv = (dsGV != null) ? dsGV.timKiem(p[4].trim()) : null;
                    if (mon == null) throw new IllegalArgumentException("Khong tim thay ma mon '" + p[3].trim() + "'");
                    if (gv == null) throw new IllegalArgumentException("Khong tim thay ma GV '" + p[4].trim() + "'");
                    xepTiet(p[0].trim(), thu, tiet, mon, gv);
                    soTiet++;
                } catch (NumberFormatException e) {
                    System.out.println("Dong " + soDong + ": thu/tiet phai la so nguyen: " + line);
                    soLoi++;
                } catch (IllegalArgumentException e) {
                    System.out.println("Dong " + soDong + ": " + e.getMessage());
                    soLoi++;
                }
            }
            System.out.println("Doc file thoi khoa bieu thanh cong! " + soTiet + " tiet, " + soLoi + " dong loi.");
            return true;
        } catch (IOException e) {
            System.out.println("Loi khi doc file thoi khoa bieu: " + e.getMessage());
            return false;
        }
    }

    public void setDsLop(LopHoc[] dsLopMoi) {
        if(dsLopMoi == null) dsLopMoi = new LopHoc[0];

        LopHoc[] gon = new LopHoc[dsLopMoi.length];
        int dem = 0;
        for(LopHoc l : dsLopMoi)
            if(l != null) gon[dem++] = l;
        this.dsLop = java.util.Arrays.copyOf(gon, dem);
        this.soLuong = dem;
    }

    public int getSoLuong() { return soLuong; }

    // ================= HÀM PHỤ (private) =================
    // Gom các lớp có khop[i] = true thành mảng kết quả vừa khít
    private LopHoc[] gom(boolean[] khop) {
        int dem = 0;
        for(boolean b : khop)
            if(b) dem++;
        LopHoc[] kq = new LopHoc[dem];
        int k = 0;
        for(int i = 0; i < soLuong; i++)
            if(khop[i]) kq[k++] = dsLop[i];
        return kq;
    }

    // Bỏ dấu + chữ thường để so sánh gần đúng ("Duyên" và "duyen" là một)
    private static String chuanHoa(String s) {
        if(s == null) return "";
        String t = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return t.replace('đ', 'd').replace('Đ', 'D').toLowerCase().trim();
    }
}