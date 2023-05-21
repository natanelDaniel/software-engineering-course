package Ex1;

import Ex2.Calendar;
import Ex2.SMS;

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

    public String toString() {
//      This function print all the contacts in the telephone book
        String str = "";
        str += "*********************************\n";
        TelephoneNode curr = this.head;
        for (int i = 0; i < this.size; i++) {
            str += "Contact " + i + ": " + curr + "\n";
            curr = curr.getNext();
        }
        str += "*********************************\n";
        return str;
    }

    public static TelephoneNode findContact(TelephoneNode head, String name) {
//      This function get name of contact, print all the matches to the name and return array-list of the contacts
        TelephoneNode curr = head;
        while (curr != null) {
            if (curr.getName().equals(name)) {
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }

    public ArrayList<TelephoneNode> toArray() {
//      This function return array of the contacts
        TelephoneNode curr = this.head;
        ArrayList<TelephoneNode> arr = new ArrayList<TelephoneNode>();
        for (int i = 0; i < this.size; i++) {
            arr.add(curr);
            curr = curr.getNext();
        }
        return arr;
    }
    public void fromArray(ArrayList<TelephoneNode> arr) {
//      This function get array of the contacts and return the head of the linked list
        this.head = arr.get(0);
        this.tail = arr.get(this.size - 1);
        for (int i = 0; i < this.size - 1; i++) {
            arr.get(i).setNext(arr.get(i + 1));
        }
        this.tail.setNext(null);
    }

    public void sortContactsByName() {
//      this function sort the contacts linked list by name in Lexicographic order
        ArrayList<TelephoneNode> arr = this.toArray();
        Collections.sort(arr, new telephoneNodeComp());
        this.fromArray(arr);
    }

    public void sortContactsByNumber() {
//        this function sort the contacts by their number from the biggest to the smallest
        ArrayList<TelephoneNode> arr = this.toArray();
//        Arrays.sort(arr, Comparator.comparing(TelephoneNode::getNumber));
        Collections.sort(arr, new telephoneNodeCompByNumber());
        this.fromArray(arr);
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
//                check if the contact already exist
                TelephoneNode contact = findContact(this.head, parts[0]);
                if (contact != null) {
                    System.out.println("Contact " + parts[0] + " already exists");
                }
                else {
                    addContact(parts[0], parts[1]);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading contacts from file: " + e.getMessage());
        }
    }

    public void printMenu() {
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

    public void menu(Scanner scanner, SMS sms, Calendar calendar) {
        Integer choice = 0; // initialize choice to an invalid value
        String name;
        String number;

        do {
            this.printMenu();
            // Check if input is an integer
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            if (this.findContact(this.getHead(), name) != null) {
                                System.out.println("contact " + name + " already exists in the phone book.");
                                break;
                            }
                            System.out.println("Enter number:");
                            number = scanner.next();
                            // Check if number is a valid 10-digit phone number
                            if (number.matches("\\d{10}")) {
                                this.addContact(name, number);
                            } else {
                                System.out.println("Invalid phone number format.");
                            }
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            this.deleteContact(name);
                            sms.deleteChat(name);
                            calendar.removeAllEventsWithSpecifContact(name);
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 3:
                        System.out.println(this);
                        break;
                    case 4:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            TelephoneNode matches = this.findContact(this.getHead(), name);
                            if (matches == null){
                                System.out.println("No matches found for " + name);
                            }
                            else {
                                System.out.println(matches);
                            }
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 5:
                        if (this.getSize() == 0) {
                            System.out.println("Phone book is empty.");
                            break;
                        }
                        this.sortContactsByName();
                        break;
                    case 6:
                        if (this.getSize() == 0) {
                            System.out.println("Phone book is empty.");
                            break;
                        }
                        this.sortContactsByNumber();
                        break;
                    case 7:
                        this.removeDuplicates();
                        break;
                    case 8:
                        this.reverse();
                        break;
                    case 9:
                        System.out.println("Enter file name:");
                        name = scanner.next();
                        // Check if file name is not empty
                        if (!name.isEmpty()) {
                            this.saveToFile(name);
                        } else {
                            System.out.println("File name cannot be empty.");
                        }
                        break;
                    case 10:
                        System.out.println("Enter file name:");
                        name = scanner.next();
                        this.loadFromFile(name);
                        break;
                    case 11:
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard invalid input
            }
        } while (choice != 11);
    }

    public int getSize() {return size;}

    public TelephoneNode getHead() {
        return head;
    }
}