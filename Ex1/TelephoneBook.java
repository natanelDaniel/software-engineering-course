package Ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TelephoneBook {
//    This Class represents a telephone book, which is a linked list of TelephoneNodes.
//    Each TelephoneNode represents a contact in the telephone book.
    private TelephoneNode head;
    private TelephoneNode tail;
    private int size;

    public TelephoneBook() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addContact(String name, String number) {
        TelephoneNode newNode = new TelephoneNode(name, number);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    public void deleteContact(String name) {
//        write here your code
    }

    public void printContacts() {
        System.out.println("*********************************");
        TelephoneNode curr = this.head;
        for (int i = 0; i < this.size; i++) {
            System.out.println("Contact " + i + ": Name: " + curr.getName() + ", Phone Number: " + curr.getNumber());
            curr = curr.getNext();
        }
        System.out.println("*********************************");
    }

    public TelephoneNode findContact(String name) {
//        write here your code
        return null;
    }

    public void sortContactsByName() {
//        write here your code
    }

    public void sortContactsByNumber() {
//        write here your code
    }

    public void removeDuplicates() {
//        write here your code
    }

    public void reverse() {
//        write here your code
    }

    public void saveToFile(String fileName) {
//        write here your code
    }

    public void loadFromFile(String fileName) {
//        write here your code
    }

    public static void printMenu() {
        System.out.println("1. Add contact");
        System.out.println("2. Delete contact");
        System.out.println("3. Print contacts");
        System.out.println("4. Find contact");
        System.out.println("5. Sort contacts by name");
        System.out.println("6. Sort contacts by number");
        System.out.println("7. Remove duplicates");
        System.out.println("8. Reverse");
        System.out.println("9. Save to file");
        System.out.println("10. Load from file");
        System.out.println("11. Exit");
    }

    public static void menu(TelephoneBook telephoneBook, Scanner scanner){
        int choice;
        String name;
        String number;
        TelephoneNode node;
        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    name = scanner.next();
                    System.out.println("Enter number:");
                    number = scanner.next();
                    telephoneBook.addContact(name, number);
                    break;
                case 2:
                    System.out.println("Enter name:");
                    name = scanner.next();
                    telephoneBook.deleteContact(name);
                    break;
                case 3:
                    telephoneBook.printContacts();
                    break;
                case 4:
                    System.out.println("Enter name:");
                    name = scanner.next();
                    node = telephoneBook.findContact(name);
                    if (node != null) {
                        System.out.println(node.getName() + " " + node.getNumber());
                    } else {
                        System.out.println("Contact not found");
                    }
                    break;
                case 5:
                    telephoneBook.sortContactsByName();
                    break;
                case 6:
                    telephoneBook.sortContactsByNumber();
                    break;
                case 7:
                    telephoneBook.removeDuplicates();
                    break;
                case 8:
                    telephoneBook.reverse();
                    break;
                case 9:
                    System.out.println("Enter file name:");
                    name = scanner.next();
                    telephoneBook.saveToFile(name);
                    break;
                case 10:
                    System.out.println("Enter file name:");
                    name = scanner.next();
                    telephoneBook.loadFromFile(name);
                    break;
                case 11:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 11);
    }

    public static void main(String[] args) {
        TelephoneBook telephoneBook = new TelephoneBook();
//        take input from file
        String test1 = "Ex1\\test1.txt";

        Boolean fromFile = false;

        if (fromFile) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(test1));
                menu(telephoneBook, scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            Scanner scanner = new Scanner(System.in);
            menu(telephoneBook, scanner);
        }
        System.out.println("Bye Bye");
    }
}
