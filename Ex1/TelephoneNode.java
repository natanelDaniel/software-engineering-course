package Ex1;

public class TelephoneNode {
//    This Class represents a node in the linked list of the telephone book.
//    There are fields: name and number, and a pointer to the next node.
    private String name;
    private String number;
    private TelephoneNode next;

    public TelephoneNode(String name_, String number_) {
        this.name = name_;
        this.number = number_;
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
}
