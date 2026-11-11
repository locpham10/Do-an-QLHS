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

    public String toFileString() {
        return hoTenBoMe + "," + sdtKhanCap + "," + (mshs != null ? mshs : "");
    }
}
