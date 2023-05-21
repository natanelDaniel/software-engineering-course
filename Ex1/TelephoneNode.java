package Ex1;
import java.util.*;


public class TelephoneNode {
    //    This Class represents a node in the linked list of the telephone book.
//    There are fields: name and number, and a pointer to the next node.
    private String name;
    private String number;
    private ArrayList<String> chat;
    private TelephoneNode next;



    public TelephoneNode(String name_, String number_) {
        this.name = name_;
        this.number = number_;
        this.chat = new ArrayList<>();
        this.next = null;
    }

    public String getName() {return this.name;}

    public String getNumber() {
        return this.number;
    }

    public TelephoneNode getNext() {
        return this.next;
    }

    public void setNext(TelephoneNode next_) {
        this.next = next_;
    }

    public String toString() {
        return "Name: " + this.name + ", Phone Number: " + this.number;
    }

    public ArrayList<String> getChat() {
        return this.chat;
    }
    public void sendSMS(String sms) {
        this.chat.add(sms);
    }

    public void printSMSHistory () {
        ArrayList<String> chatHistory = this.getChat();
        System.out.println("*********************");
        System.out.println("History with " + this.getName() + " :");
        System.out.println("*********************");
        for (String message : chatHistory) {
            System.out.println(message);
        }
    }

    public void deleteChat() {
        this.chat.clear();
    }

    public String searchChat(String sentence) {
        for (String message : chat) {
            if (message.contains(sentence)) {
                System.out.println("messege found in Chat with " + this.getName() + message);
                return message;
            }
        }
        System.out.println("message not found.");
        return null;
    }

}
