package Ex1;

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
