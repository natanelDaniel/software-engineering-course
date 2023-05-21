package Ex2;
import  Ex1.TelephoneBook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class Phone {
    private String name;
    private String phone;
    public Phone(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
    public Phone(){
        this.name = "empty name";
        this.phone = "empty number";
    }
    public String getName(){
        return this.name;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    SMS sms = new SMS();
    TelephoneBook tb = new TelephoneBook();
    Calendar cal = new Calendar();
    MediaApp ma = new MediaApp();
    public void print_menu(){
        System.out.println("Which app do you want to use?");
        System.out.println("1. Telephone Book");
        System.out.println("2. SMS");
        System.out.println("3. Calendar");
        System.out.println("4. Media App");
        System.out.println("5. Print Phone's info");
        System.out.println("6. Exit");
    }
    public void menu(Scanner scanner){
        System.out.println("Welcome to the phone of " + this.name);
        System.out.println("Phone: " + this.phone);
        int choice=1;
        do {
            print_menu();
            System.out.println("Enter your choice:");
            if (scanner.hasNextInt()){
                choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        tb.menu(scanner);
                        break;
                    case 2:
                        //sms.menu(scanner);
                        break;
                    case 3:
                        cal.menu(scanner, tb);
                        break;
                    case 4:
                        ma.menu(scanner);
                        break;
                    case 5:
                        System.out.println("Printing Phone's info");
                        System.out.println("Phonebook:");
                        System.out.println(tb);
                        System.out.println("SMS:");
                        //sms.printAll();
                        System.out.println(cal);
                        System.out.println("Media App:");
                        ma.playAllMedia();
                        break;
                    case 6:
                        System.out.println("Leaving Phone...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard invalid input
            }
        } while (choice != 6);

    }//end menu

    public static void main(String[] args) {
        //enter name and phone number

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phoneNum = scanner.nextLine();
        Phone phone = new Phone(name, phoneNum);
        String test1 = "Ex2\\Tests\\phoneTest.txt";
        Boolean fromFile = true;
        if (fromFile) {
            try {
                scanner = new Scanner(new FileInputStream(test1));
                phone.menu(scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            scanner = new Scanner(System.in);
            phone.menu(scanner);
        }
        System.out.println("Bye Bye");
    }
}
