package Ex2;

import java.util.HashMap;
import java.util.Scanner;

public class SMS {
    private HashMap<String, Chat> chatMap;

    public SMS() {
        chatMap = new HashMap<>();
    }

    public void addChat(String contact, String message) {
//        check if contact exists in chatMap
//        if yes, add message to chat
//        if no, create new chat and add message to chat
        if (chatMap.containsKey(contact)) {
            chatMap.get(contact).addMessage(message);
        } else {
            Chat chat = new Chat(contact);
            chat.addMessage(message);
            chatMap.put(contact, chat);
        }
    }

    public void deleteChat(String contact) {
        chatMap.remove(contact);
    }

    public void searchChat(String sentence) {
        boolean found = false;
        for (Chat chat : chatMap.values()) {
            if (chat.containsSentence(sentence)) {
                System.out.println("Sentence found in chat with contact: " + chat.getContact());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Sentence not found in any chat.");
        }
    }

    public String toString() {
        String result = "SMS Application:\n";
        for (Chat chat : chatMap.values()) {
            result += chat.toString() + "\n";
        }
        return result;
    }
    public void printMenu() {
        System.out.println("SMS Application");
        System.out.println("1. Send SMS to contact");
        System.out.println("2. Delete Chat with contact");
        System.out.println("3. Print Chat with contact");
        System.out.println("4. Search in Chat by sentence");
        System.out.println("5. Print All Chats");
        System.out.println("6. Exit");
    }
    private boolean isExistsInTelephoneBook(TelephoneBook telephoneBook, String name) {
        TelephoneNode contact = telephoneBook.findContact(telephoneBook.getHead(), name);
        if (contact == null) {
            System.out.println("Contact does not exist.");
            return false;
        }
        return true;
    }
    public void menu(Scanner scanner, TelephoneBook telephoneBook) {
        boolean exit = false;
        String name;
        while (!exit) {
            this.printMenu();
            Integer choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter contact name: ");
                    name = scanner.nextLine();
                    if (!this.isExistsInTelephoneBook(telephoneBook, name)) {
                        break;
                    }
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    this.addChat(name, message);
                    System.out.println("SMS was sent successfully.");
                    break;
                case 2:
                    System.out.print("Enter contact name: ");
                    name = scanner.nextLine();
                    if (!this.isExistsInTelephoneBook(telephoneBook, name)) {
                        break;
                    }
                    this.deleteChat(name);
                    System.out.println("Chat deleted successfully.");
                    break;
                case 3:
                    System.out.print("Enter contact name: ");
                    name = scanner.nextLine();
                    if (!this.isExistsInTelephoneBook(telephoneBook, name)) {
                        break;
                    }
                    System.out.println(this.chatMap.get(name));
                    break;
                case 4:
                    System.out.print("Enter a sentence to search: ");
                    String sentence = scanner.nextLine();
                    this.searchChat(sentence);
                    break;
                case 5:
                    System.out.println(this);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting SMS application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        }
    }
}
