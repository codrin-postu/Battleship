import java.io.File;
import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) throws IllegalArgumentException {
        IllegalArgumentException exception = new IllegalArgumentException();
        switch (number) {
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thu";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
            case 7:
                return "Sun";
            default:
                throw exception;

        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }

    public List<File> getAllFiles(File rootDir) {
        File[] children = rootDir.listFiles();
        if (children == null || children.length == 0) {
            return Collections.emptyList();
        }

        List<File> files = new ArrayList<>();
        for (File child : children) {
            if (child.listFiles(file)) {
                files.addAll(getAllFiles(child));

            } else {
                files.add(child);
            }
        }

        return files;
    }
}