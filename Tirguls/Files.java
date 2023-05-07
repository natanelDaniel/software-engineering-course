package Tirguls;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Files {
    public static String readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File does not exist!");
            return null;
        }
        if (!file.isFile()) {
            System.out.println("This is not a file!");
            return null;
        }
        Scanner scanner = new Scanner(file);
        return "";
    }
    public static void writeFile(String path, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true); // true - append
        fileWriter.write(content);
        fileWriter.close();
    }
    public static void main(String[] args) {
        try {
            String content = readFile("C:\\Users\\User\\Desktop\\test.txt");
            System.out.println(content);
            writeFile("C:\\Users\\User\\Desktop\\test.txt", "Hello World!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }
}
