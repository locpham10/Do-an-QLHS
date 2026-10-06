import java.io.File;
import java.io.IOException;

public class FileService 
{
    public static final String FILE_PATH = "Data_HocSinh.txt";
    public static void kiemTraVaTaoFile() 
    {
        File file = new File(FILE_PATH);
        try 
        {
            if (!file.exists()) 
            {
                file.createNewFile();
                System.out.println("Đã tạo file: " + file.getAbsolutePath());
            } 
            else 
            {
                System.out.println("File đã tồn tại: " + FILE_PATH);
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}