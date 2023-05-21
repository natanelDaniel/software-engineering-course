package Ex2;
import  Ex1.TelephoneBook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class PhoneSystem {
    private TelephoneBook tb;
    private Calendar cal;
    private SMS sms;
    private MediaApp ma;

    public PhoneSystem(){
        this.tb = new TelephoneBook();
        this.cal = new Calendar();
        this.sms = new SMS();
        this.ma = new MediaApp();
    }

    public void print_menu(){
        System.out.println("Which app do you want to use?");
        System.out.println("1. Telephone Book");
        System.out.println("2. SMS");
        System.out.println("3. Calendar");
        System.out.println("4. Media App");
        System.out.println("5. Print Phone's info");
        System.out.println("6. Exit");
    }

    public void LoadFromFile(String path){
        this.tb.loadFromFile(path);
    }

    public void menu(Scanner scanner){
        Integer choice = 0;
        do {
            print_menu();
            System.out.println("Enter your choice:");
            if (scanner.hasNextInt()){
                choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        this.tb.menu(scanner, this.sms, this.cal);
                        break;
                    case 2:
                        this.sms.menu(scanner, tb);
                        break;
                    case 3:
                        this.cal.menu(scanner, tb);
                        break;
                    case 4:
                        this.ma.menu(scanner);
                        break;
                    case 5:
                        System.out.println("Printing Phone's info");
                        System.out.println("Phonebook:");
                        System.out.println(this.tb);
                        System.out.println("SMS:");
                        System.out.println(this.sms);
                        System.out.println("Calendar:");
                        System.out.println(this.cal);
                        System.out.println("Media App:");
                        this.ma.playAllMedia();
                        break;
                    case 6:
                        System.out.println("Leaving Phone...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
//                scanner.next(); // discard invalid input
            }
        } while (choice != 6);

    }//end menu

}
