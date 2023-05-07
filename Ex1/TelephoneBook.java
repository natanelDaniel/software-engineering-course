package Ex1;

import java.io.*;
import java.util.*;

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

    public ArrayList<TelephoneNode> findContact(String name) {
//      This function get name of contact, print all the matches to the name and return array-list of the contacts
        TelephoneNode curr = this.head;
        ArrayList<TelephoneNode> matches = new ArrayList<TelephoneNode>();
        for (int i = 0; i < this.size; i++) {
            if (curr.getName().equals(name)) {
                matches.add(curr);
                System.out.println("Contact " + i + ": Name: " + curr.getName() + ", Phone Number: " + curr.getNumber());
            }
            curr = curr.getNext();
        }
        if (matches.isEmpty()) {
            System.out.println("Contact " + name + " not found");
        }
        return matches;
    }

    public void sortContactsByName() {
//      this function sort the contacts linked list by name in Lexicographic order
        TelephoneNode curr = this.head;
        TelephoneNode[] arr = new TelephoneNode[this.size];
        for (int i = 0; i < this.size; i++) {
            arr[i] = curr;
            curr = curr.getNext();
        }
        Arrays.sort(arr, Comparator.comparing(TelephoneNode::getName));
        this.head = arr[0];
        this.tail = arr[this.size - 1];
        for (int i = 0; i < this.size - 1; i++) {
            arr[i].setNext(arr[i + 1]);
        }
        this.tail.setNext(null);
    }

    public void sortContactsByNumber() {
//        this function sort the contacts by their number from the biggest to the smallest
        TelephoneNode curr = this.head;
        TelephoneNode[] arr = new TelephoneNode[this.size];
        for (int i = 0; i < this.size; i++) {
            arr[i] = curr;
            curr = curr.getNext();
        }
        Arrays.sort(arr, Comparator.comparing(TelephoneNode::getNumber));
        this.head = arr[0];
        this.tail = arr[this.size - 1];
        for (int i = 0; i < this.size - 1; i++) {
            arr[i].setNext(arr[i + 1]);
        }
        this.tail.setNext(null);
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
//          this function save the phone book to a txt file.
//          inputs: file name with full path from the user
        // get the file name from the user
        if (!fileName.endsWith(".txt")) {
            fileName += ".txt";
        }
        // write the phone book to the file
        try {
            File file = new File(fileName);
            FileWriter newFile = new FileWriter(file);
            TelephoneNode curr = this.head;
            for (int i = 0; i < this.size; i++) {
                newFile.write(curr.getName() + "," + curr.getNumber() + "\n");
                curr = curr.getNext();
            }
            newFile.close();
            System.out.println("Successfully written.");
        }
        catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }

    public void loadFromFile(String fileName) {
        //This function loads contacts from a file into the linked list
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("\\s*[,-]\\s*|[,-]\\s+");
                addContact(parts[0], parts[1]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading contacts from file: " + e.getMessage());
        }
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
        String choice;
        String name;
        String number;
        ArrayList<TelephoneNode> matches = new ArrayList<TelephoneNode>();
        do {
            printMenu();
            choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("Enter name:");
                    name = scanner.next();
                    System.out.println("Enter number:");
                    number = scanner.next();
                    telephoneBook.addContact(name, number);
                    break;
                case "2":
                    System.out.println("Enter name:");
                    name = scanner.next();
                    telephoneBook.deleteContact(name);
                    break;
                case "3":
                    telephoneBook.printContacts();
                    break;
                case "4":
                    System.out.println("Enter name:");
                    name = scanner.next();
                    matches = telephoneBook.findContact(name);
                    break;
                case "5":
                    telephoneBook.sortContactsByName();
                    break;
                case "6":
                    telephoneBook.sortContactsByNumber();
                    break;
                case "7":
                    telephoneBook.removeDuplicates();
                    break;
                case "8":
                    telephoneBook.reverse();
                    break;
                case "9":
                    System.out.println("Enter file name:");
                    name = scanner.next();
                    telephoneBook.saveToFile(name);
                    break;
                case "10":
                    System.out.println("Enter file name:");
                    name = scanner.next();
                    telephoneBook.loadFromFile(name);
                    break;
                case "11":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != "11");
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
