package Ex2;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Chat {
    private String contact;
    private List<String> messages;

    public Chat(String contact) {
        this.contact = contact;
        this.messages = new ArrayList<>();
    }

    public String getContact() {
        return contact;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void deleteMessages() {
        messages.clear();
    }

    public boolean containsSentence(String sentence) {
        for (String message : messages) {
            if (message.contains(sentence)) {
                return true;
            }
        }
        return false;
    }

    public void printMessages() {
        for (String message : messages) {
            System.out.println(message);
        }
    }
}

public class SMS {
    private HashMap<String, Chat> chatMap;

    public SMS() {
        chatMap = new HashMap<>();
    }

    public void addChat(String contact, String message) {
        Chat chat = chatMap.getOrDefault(contact, new Chat(contact));
        chat.addMessage(message);
        chatMap.put(contact, chat);
    }

    public void deleteChat(String contact) {
        chatMap.remove(contact);
    }

    public void printChat(String contact) {
        Chat chat = chatMap.get(contact);
        if (chat != null) {
            chat.printMessages();
        } else {
            System.out.println("No chat found for contact: " + contact);
        }
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

    public void printAllChat() {
        for (Chat chat : chatMap.values()) {
            System.out.println("Chat with " + chat.getContact() + ":");
            chat.printMessages();
            System.out.println();
        }
    }

    public void menu() {
        SMS sms = new SMS();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("SMS Application");
            System.out.println("1. Send SMS");
            System.out.println("2. Delete Chat");
            System.out.println("3. Print Chat");
            System.out.println("4. Search in Chat");
            System.out.println("5. Print All Chat");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter contact name: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter message: ");
                    String message = scanner.nextLine();
                    sms.addChat(contact, message);
                    System.out.println("SMS was sent successfully.");
                    break;
                case 2:
                    System.out.print("Enter contact name: ");
                    contact = scanner.nextLine();
                    sms.deleteChat(contact);
                    System.out.println("Chat deleted successfully.");
                    break;
                case 3:
                    System.out.print("Enter contact name: ");
                    contact = scanner.nextLine();
                    System.out.println("Chat with " + contact + ":");
                    sms.printChat(contact);
                    break;
                case 4:
                    System.out.print("Enter a sentence to search: ");
                    String sentence = scanner.nextLine();
                    sms.searchChat(sentence);
                    break;
                case 5:
                    System.out.println("Printing all chats:");
                    sms.printAllChat();
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

        scanner.close();
    }
}
