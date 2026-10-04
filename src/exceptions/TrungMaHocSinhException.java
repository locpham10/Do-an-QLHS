package exceptions;

import utils.Color;

public class TrungMaHocSinhException extends Exception {

    public TrungMaHocSinhException() {
        super(Color.RED + "Lỗi: Mã số học sinh này đã tồn tại trong hệ thống!" + Color.RESET);
    }

    public TrungMaHocSinhException(String message) {
        super(Color.RED + message + Color.RESET);
    }
}