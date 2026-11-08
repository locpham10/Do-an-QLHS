package models;
public class PhuHuynh {
    private String hoTenBoMe;
    private String sdtKhanCap;

    public PhuHuynh(){
    }

    public PhuHuynh (String hoTenBoMe, String sdtKhanCap) {
        this.hoTenBoMe = hoTenBoMe;
        this.sdtKhanCap  = sdtKhanCap;
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

    public String toFileString() {
        return hoTenBoMe + "," + sdtKhanCap;
    }
}
