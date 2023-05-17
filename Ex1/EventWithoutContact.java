package Ex1;
import java.util.Date;

public class EventWithoutContact extends Event{
    private String description;
    public EventWithoutContact(Date date, int duration, String description) {
        super(date, duration);
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return super.toString() + "Description: " + description;
    }




}
