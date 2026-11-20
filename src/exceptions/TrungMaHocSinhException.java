package exceptions;

import utils.Color;

public class TrungMaHocSinhException extends Exception {

    public TrungMaHocSinhException() {
        super(Color.RED + "Loi: Ma so hoc sinh nay da ton tai trong he thong!" + Color.RESET);
    }

    public TrungMaHocSinhException(String message) {
        super(Color.RED + message + Color.RESET);
    }
}