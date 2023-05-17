package Ex2;

import Ex1.TelephoneNode;
import Ex2.Event;

import java.util.Date;

public class EventWithContact extends Event {
    private TelephoneNode contact;

    public EventWithContact(Date date, int duration, TelephoneNode contact) {
        super(date, duration);
        this.contact = contact;
    }

    public TelephoneNode getContact() {
            return contact;
    }

    public void setContact(TelephoneNode contact) {
            this.contact = contact;
    }

    public String toString(){
        return super.toString() + "Contact: " + contact.toString();
    }

}
