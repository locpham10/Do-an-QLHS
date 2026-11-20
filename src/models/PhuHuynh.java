package models;
public class PhuHuynh {
    private String hoTenBoMe;
    private String sdtKhanCap;
    private String mshs;

    public PhuHuynh(){
    }

    public PhuHuynh (String hoTenBoMe, String sdtKhanCap, String mshs) {
        this.hoTenBoMe = hoTenBoMe;
        this.sdtKhanCap  = sdtKhanCap;
        this.mshs = mshs;
    }

    public String getHoTenBoMe(){
        return hoTenBoMe;
    }
    public void setHoTenBoMe(String hoTenBoMe){
        this.hoTenBoMe = hoTenBoMe;
    }

    public String getSdtKhanCap(){
        return sdtKhanCap;
    }
    public void setSdtKhanCap(String sdtKhanCap){
        this.sdtKhanCap  = sdtKhanCap;
    }

    public String getMshs(){
        return mshs;
    }
    public void setMshs(String mshs){
        this.mshs = mshs;
    }

    public void themMSHS(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) return;
        if (mshs == null || mshs.trim().isEmpty()) {     // chua co con nao
            mshs = maHS.trim();
            return;
        }
        String[] dsMa = mshs.split(";");
        for (int i = 0; i < dsMa.length; i++) {
            if (dsMa[i].trim().equalsIgnoreCase(maHS.trim())) return;   // da co thi thoi
        }
        mshs = mshs + ";" + maHS.trim();
    }

    public String toFileString() {
        return hoTenBoMe + "," + sdtKhanCap + "," + (mshs != null && !mshs.isEmpty() ? mshs : "");
    }
    public void hienThiThongTin() {
        System.out.println("Ho ten bo me   : " + hoTenBoMe);
        System.out.println("SDT khan cap   : " + sdtKhanCap);
        System.out.println("MSHS lien ket  : " + (mshs != null && !mshs.isEmpty() ? mshs : "Chua co"));
    }
}
