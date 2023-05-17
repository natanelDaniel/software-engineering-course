package Ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Calendar {
    private List<List<Event>> events;

    public Calendar() {
        events = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            events.add(new ArrayList<>());
        }
    }

    public void addEvent(Event event) {
        int day = event.getDateObject().getDate() - 1;
        events.get(day).add(event);
        events.get(day).sort(Comparator.comparing(Event::getHour));
    }

    public void removeEvent(Event event){
        int day = event.getDateObject().getDate() - 1;
        events.get(day).remove(event);
    }

    public String stringOfDailyEvents(int day){
        //this function returns a string representation of the events of a given day
        String str = "Day " + (day + 1) + ":\n";
        int j;
        for (int event_i = 0; event_i < events.get(day).size(); event_i++) {
            j = event_i + 1;
            str += "\t Event " + j +": \t"+ events.get(day).get(event_i).toString() + "\n";
        }
        return str;
    }


    public String toString(){
        //this function returns a string representation of the calendar
        String str = "Calendar:\n";
        for (int day_i = 0; day_i < events.size(); day_i++) {
            str += stringOfDailyEvents(day_i) + "\n";
        }
        return str;
    }
    public void printEventsWithSpecifContact(String name){
        //this function prints all the events with a specific contact
        boolean found = false;
        for (int day_i = 0; day_i < events.size(); day_i++) {
            for (int event_i = 0; event_i < events.get(day_i).size(); event_i++) {
                if (events.get(day_i).get(event_i) instanceof EventWithContact){
                    if (((EventWithContact) events.get(day_i).get(event_i)).getContact().getName().equals(name)){
                        if (!found){
                            System.out.print("Events with " + name + ":\n");
                        }
                        System.out.print("Day " + (day_i + 1) + ":\n");
                        System.out.println("\t"+events.get(day_i).get(event_i));
                        found = true;
                    }
                }
            }
        }
        if (!found){
            System.out.println("No events with " + name + " found");
        }
    }

    public void IdentifyAndRemoveCollisionEvents(){
        int current_event_end;
        int hours_current_event;
        int hours_next_event;
        int minutes_current_event;
        int minutes_next_event;
        int duration_current_event;
        int event_i;
        for(int day_i=0; day_i < events.size(); day_i++){
            event_i = 0;
            while (events.get(day_i).size() > 1 && event_i + 1 < events.get(day_i).size()) {
                hours_current_event = events.get(day_i).get(event_i).getDateObject().getHours();
                hours_next_event = events.get(day_i).get(event_i + 1).getDateObject().getHours();
                minutes_current_event = events.get(day_i).get(event_i).getDateObject().getMinutes();
                minutes_next_event = events.get(day_i).get(event_i + 1).getDateObject().getMinutes();
                duration_current_event = events.get(day_i).get(event_i).getDuration();
                current_event_end = hours_current_event * 60 + minutes_current_event + duration_current_event;
                if (current_event_end > hours_next_event * 60 + minutes_next_event) {
                    events.get(day_i).remove(event_i + 1);
                } else {
                    event_i++;
                }
            }
        }
    }


    public static void main(String[] args){
        Calendar calendar = new Calendar();
        TelephoneNode contact1 = new TelephoneNode("John", "0521234567");
        TelephoneNode contact2 = new TelephoneNode("Jane", "0527654321");
        EventWithContact event1 = new EventWithContact(new java.util.Date(2020, 11, 1, 10, 0), 60, contact1);
        EventWithContact event2 = new EventWithContact(new java.util.Date(2020, 11, 1, 10, 30), 60, contact2);
        EventWithoutContact event3 = new EventWithoutContact(new java.util.Date(2020, 11, 1, 10, 50), 20, "Lunch");
        EventWithoutContact event4 = new EventWithoutContact(new java.util.Date(2020, 11, 1, 11, 5), 30, "Lunch");
        EventWithContact event5 = new EventWithContact(new java.util.Date(2020, 11, 1, 11, 30), 60, contact1);

        calendar.addEvent(event1);
        calendar.addEvent(event2);
        calendar.addEvent(event3);
        calendar.addEvent(event4);
        calendar.addEvent(event5);
//        calendar.removeEvent(event2);
        System.out.println(calendar);

//        calendar.printEventsWithSpecifContact(contact1.getName());
        calendar.IdentifyAndRemoveCollisionEvents();
        System.out.println(calendar);

    }
}
