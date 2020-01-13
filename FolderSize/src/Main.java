import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
            BigDecimal countBytes = getDirectorySize(new File(wayToFolder));

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
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Папки могут быть очень большего размера, поэтому использовался BigDecimal
    private static BigDecimal getDirectorySize(File directory) {
        // Задаем начальное значение
        BigDecimal size = BigDecimal.ZERO;
        // Если путь ведет к файлу, то считаем его длину
        if (directory.isFile()) {
            size = BigDecimal.valueOf(directory.length());
            // Если не к файлу, а к папке - ищим все вложеные файлы и считаем их длинну
        } else {
            File[] subFiles = directory.listFiles();
            if (subFiles != null) {
                for (File file : subFiles) {
                    if (file.isFile()) {
                        size = size.add(BigDecimal.valueOf(file.length()));
                        // Если в папке есть другие папки, то вновь применяем данный метод
                    } else {
                        size = size.add(getDirectorySize(file));
                    }
                }
            }
        }
        return size;
    }
}
