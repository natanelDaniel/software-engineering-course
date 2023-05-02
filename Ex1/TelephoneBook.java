package Ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
        // this function get name of contact and delete it from the telephone book
        boolean found = false;
        TelephoneNode curr = this.head;
        TelephoneNode prev = null;
        for (int i = 0; i < this.size; i++) {
            if (curr.getName().equals(name)) {
                found = true;
                this.size--;
                if (curr == this.head) {
                    this.head = curr.getNext();
                } else if (curr == this.tail) {
                    this.tail = prev;
                    this.tail.setNext(null);
                } else {
                    prev.setNext(curr.getNext());
                }
//                call garbage collector
                System.gc();
                break;
            }
            prev = curr;
            curr = curr.getNext();
        }
        if (!found) {
            System.out.println("Contact " + name + " not found");
        }
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
//      This function get name of contact, print it and return the node of the contact
//      if the name exist multiple times, the function will print all the contacts and return the last one
        TelephoneNode curr = this.head;
        TelephoneNode contact = null;
        for (int i = 0; i < this.size; i++) {
            if (curr.getName().equals(name)) {
                contact = curr;
                System.out.println("Contact " + i + ": Name: " + curr.getName() + ", Phone Number: " + curr.getNumber());
            }
            curr = curr.getNext();
        }
        if (contact == null) {
            System.out.println("Contact " + name + " not found");
        }
        return contact;
    }

    public void sortContactsByName() {
//        write here your code
    }

    public void sortContactsByNumber() {
//        write here your code
    }

    public void removeDuplicates() {
//        This function remove all the duplicates
//        lets create dict  - key is the name + number and value is the number of times the key appear
//        dict in java is HashMap
        HashMap<String, Integer> dict = new HashMap<>();
        TelephoneNode curr = this.head;
        TelephoneNode prev = null;
        while (curr != null) {
            String key = curr.getName() + curr.getNumber();
            if (dict.containsKey(key)) {
                if (curr == this.tail) {
                    this.tail = prev;
                    prev.setNext(null);
                } else {
                    prev.setNext(curr.getNext());
                }
                this.size--;
                System.gc();
            } else {
                dict.put(key, 1);
            }
            prev = curr;
            curr = curr.getNext();
        }
    }

    public void reverse() {
//this function reverse the order of the list
        TelephoneNode curr = this.head;
        TelephoneNode prev = null;
        TelephoneNode next;
        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        this.tail = this.head;
        this.head = prev;
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

    public static void menu(TelephoneBook telephoneBook, Scanner scanner) {
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

        Boolean fromFile = true;

        if (fromFile) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(test1));
                menu(telephoneBook, scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            menu(telephoneBook, scanner);
        }
        System.out.println("Bye Bye");
    }
}
