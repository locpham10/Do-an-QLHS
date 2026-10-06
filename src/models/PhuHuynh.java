public class PhuHuynh {
    private String hoTenBoMe;
    private String sdt;

    public PhuHuynh(){
    }

    public PhuHuynh (String hoTenBoMe, String sdt) {
        this.hoTenBoMe = hoTenBoMe;
        this.sdt = sdt;
    }

    public String getHoTenBoMe(){
        return hoTenBoMe;
    }
    public void setHoTenBoMe(String hoTenBoMe){
        this.hoTenBoMe = hoTenBoMe;
    }

    public String getSdt(){
        return sdt;
    }
    public void setSdt(String sdt){
        this.sdt = sdt;
    }
}