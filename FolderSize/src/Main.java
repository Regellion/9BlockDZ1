import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main
{
    private static final BigDecimal KbSize = BigDecimal.valueOf(1024);
    private static final BigDecimal MbSize = KbSize.multiply(BigDecimal.valueOf(1024));
    private static final BigDecimal GbSize = MbSize.multiply(BigDecimal.valueOf(1024));

    public static void main(String[] args) {
        System.out.println("Введите адрес папки, размер которой вы хотели бы измерить: ");
        Scanner scanner = new Scanner(System.in);

        try {
            String wayToFolder = scanner.nextLine();
            BigDecimal countBytes = getDirectorySize(wayToFolder);
            printSize(countBytes);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void printSize(BigDecimal countBytes){
        if (countBytes.compareTo(KbSize) < 0) {
            System.out.println("Ваша папка весит " + countBytes.setScale(2, RoundingMode.CEILING) + " байт");
        } else if (countBytes.compareTo(MbSize) < 0) {
            System.out.println("Ваша папка весит " + (countBytes.divide(KbSize)).setScale(2, RoundingMode.CEILING) + " килобайт");
        } else if (countBytes.compareTo(GbSize) < 0) {
            System.out.println("Ваша папка весит " + (countBytes.divide(MbSize)).setScale(2, RoundingMode.CEILING) + " мегабайт");
        } else {
            System.out.println("Ваша папка весит " + (countBytes.divide(GbSize)).setScale(2,RoundingMode.CEILING) + " гигабайт");
        }
    }

    // Папки могут быть очень большего размера, поэтому использовался BigDecimal
    private static BigDecimal getDirectorySize(String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        long sum = Files.walk(dirPath).filter(path -> path.toFile().isFile())
                .mapToLong(value -> value.toFile().length()).sum();
        return BigDecimal.valueOf(sum);
    }
}
