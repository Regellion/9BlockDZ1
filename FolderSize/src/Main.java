import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main
{
    private static final int KbSize = 1024;
    private static final int MbSize = KbSize * 1024;
    private static final int GbSize = MbSize * 1024;

    public static void main(String[] args) {
        System.out.println("Введите адрес папки, размер которой вы хотели бы измерить: ");
        Scanner scanner = new Scanner(System.in);

            String wayToFolder = scanner.nextLine();
            try {
                double countBytes = getDirectorySize(wayToFolder);
                printSize(countBytes);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

    }

    private static void printSize(double countBytes){
        if (countBytes <= KbSize) {
            System.out.printf("Ваша папка весит %.2f байт.", countBytes);
        } else if (countBytes <= MbSize) {
            System.out.printf("Ваша папка весит %.2f килобайт", countBytes/KbSize);
        } else if (countBytes <= GbSize) {
            System.out.printf("Ваша папка весит %.2f мегабайт", countBytes/MbSize);
        } else {
            System.out.printf("Ваша папка весит %.2f гигабайт", countBytes/GbSize);
        }
    }

    // Папки могут быть очень большего размера, поэтому использовался BigDecimal
    private static long getDirectorySize(String directory) throws IOException{
        Path dirPath = Paths.get(directory);
            long sum  = Files.walk(dirPath).filter(path -> path.toFile().isFile())
                    .mapToLong(value -> value.toFile().length()).sum();
        return sum;
    }
}
