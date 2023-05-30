package Ex2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestMobilePhone {
    public static void main(String[] args) {
        PhoneSystem phoneSystem = new PhoneSystem();
        String telephoneBookPath = "Ex2\\Tests\\telephoneBook.txt";
        phoneSystem.LoadFromFile(telephoneBookPath);

        String test1 = "Ex2\\Tests\\phoneTest.txt";
        Boolean fromFile = true;
        Scanner scanner;
        if (fromFile) {
            try {
                scanner = new Scanner(new FileInputStream(test1));
                phoneSystem.menu(scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            scanner = new Scanner(System.in);
            phoneSystem.menu(scanner);
        }
        System.out.println("Bye Bye");
    }
}
