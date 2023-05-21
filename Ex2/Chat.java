package Ex2;

import java.util.ArrayList;
import java.util.List;

public class Chat {
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
