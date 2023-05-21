package Ex2;

import Ex1.TelephoneBook;
import Ex1.TelephoneNode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

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

    public Event findEvent(Date date, Integer duration){
        int day = date.getDate() - 1;
        for (int event_i = 0; event_i < events.get(day).size(); event_i++) {
            if (events.get(day).get(event_i).equals(new Event(date, duration))){
                return events.get(day).get(event_i);
            }
        }
        return null;
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

    public void removeAllEventsWithSpecifContact(String name){
        //this function removes all the events with a specific contact
        for (int day_i = 0; day_i < events.size(); day_i++) {
            int event_i = 0;
            while (event_i < events.get(day_i).size()) {
                if (events.get(day_i).get(event_i) instanceof EventWithContact){
                    if (((EventWithContact) events.get(day_i).get(event_i)).getContact().getName().equals(name)){
                        this.removeEvent(events.get(day_i).get(event_i));
                    }
                    else{
                        event_i++;
                    }
                }
                else{
                    event_i++;
                }
            }
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
    public static boolean isValidDay(int day){
        return day >= 1 && day <= 30;
    }

    public void printMenu() {
        System.out.println("1. Add Event");
        System.out.println("2. Remove Event");
        System.out.println("3. Print Events of a Day");
        System.out.println("4. Print Events with a Specific Contact");
        System.out.println("5. Identify and Remove Collision Events");
        System.out.println("6. Print Calendar");
        System.out.println("7. Exit");
    }

    public Date getDateFromUser(Scanner scanner){
        System.out.print("Enter the day: ");
        Integer day = scanner.nextInt();
        if (!isValidDay(day)){
            System.out.println("Invalid day.");
            return null;
        }
        System.out.print("Enter the hour: ");
        Integer hour = scanner.nextInt();
        if (!isValidHour(hour)){
            System.out.println("Invalid hour.");
            return null;
        }
        System.out.print("Enter the minutes: ");
        Integer minutes = scanner.nextInt();
        if (!isValidMinutes(minutes)){
            System.out.println("Invalid minutes.");
            return null;
        }
        return new Date(2023, 1, day, hour, minutes);
    }

    public Integer getDurationFromUser(Scanner scanner){
        System.out.print("Enter the duration: ");
        Integer duration = scanner.nextInt();
        if (!isValidDuration(duration)){
            System.out.println("Invalid duration.");
            return null;
        }
        return duration;
    }
    public void menu(Scanner scanner, TelephoneBook telephoneBook) {
        Integer choice = 0; // initialize choice to an invalid value
        String name;
        do {
            this.printMenu();
            // Check if input is an integer
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("for event with contact press 1, for event without contact press 2");
                        if (scanner.hasNextInt()) {
                            choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    Date date = getDateFromUser(scanner);
                                    if (date == null){
                                        break;
                                    }
                                    Integer duration = getDurationFromUser(scanner);
                                    if (duration == null){
                                        break;
                                    }
                                    System.out.print("Enter the name of the contact: ");
                                    name = scanner.next();
                                    if (!isValidName(name)) {
                                        System.out.println("Invalid name.");
                                        break;
                                    }
                                    TelephoneNode contact = telephoneBook.findContact(telephoneBook.getHead(), name);
                                    if (contact == null) {
                                        System.out.println("Contact does not exist.");
                                        break;
                                    }
                                    EventWithContact eventWithContact = new EventWithContact(date, duration, contact);
                                    this.addEvent(eventWithContact);
                                    break;
                                case 2:
                                    date = getDateFromUser(scanner);
                                    if (date == null){
                                        break;
                                    }
                                    duration = getDurationFromUser(scanner);
                                    if (duration == null){
                                        break;
                                    }
                                    System.out.print("Enter Description of the event: ");
//                                     read the rest of the line
                                    scanner.nextLine();
                                    String description = scanner.nextLine();

                                    if (!isValidDescription(description)) {
                                        System.out.println("Invalid description.");
                                        break;
                                    }
                                    EventWithoutContact eventWithoutContact = new EventWithoutContact(date, duration, description);
                                    this.addEvent(eventWithoutContact);
                                    break;
                                default:
                                    System.out.println("Invalid choice, returning to main menu.");
                                    break;
                            }
                        } else {
                            System.out.println("Invalid choice, returning to main menu.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter Remove Event");
                        Date date = getDateFromUser(scanner);
                        if (date == null){
                            break;
                        }
                        Integer duration = getDurationFromUser(scanner);
                        if (duration == null){
                            break;
                        }
                        Event event = this.findEvent(date, duration);
                        if (event == null) {
                            System.out.println("Event does not exist.");
                            break;
                        }
                        this.removeEvent(event);
                        break;
                    case 3:
                        System.out.println("Enter day:");
                        Integer day = scanner.nextInt();
                        if (!isValidDay(day)) {
                            System.out.println("Invalid day.");
                            break;
                        }
                        System.out.println(stringOfDailyEvents(day));
                        break;
                    case 4:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        if (!name.isEmpty()) {
                            TelephoneNode matche = telephoneBook.findContact(telephoneBook.getHead(), name);
                            if (matche == null){
                                System.out.println("No matches found for " + name);
                            }
                            else {
                                printEventsWithSpecifContact(matche.getName());
                            }
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 5:
                        System.out.println("Identify and Remove Collision Events");
                        IdentifyAndRemoveCollisionEvents();
                        break;
                    case 6:
                        System.out.println(this);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard invalid input
            }
        } while (choice != 7);
    }

    private boolean isValidDescription(String description) {
        return description != null && !description.isEmpty();
    }

    private boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean isValidDuration(Integer duration) {
        return duration > 0 && duration <= 60;
    }

    private boolean isValidMinutes(Integer minutes) {
        return minutes >= 0 && minutes <= 59;
    }

    private boolean isValidHour(Integer hour) {
        return hour >= 0 && hour <= 23;
    }

    public static void main(String[] args) {
        Calendar calendar = new Calendar();
//        take input from file
        String test1 = "Ex2\\Tests\\calendar.txt";
        TelephoneBook telephoneBook = new TelephoneBook();
        telephoneBook.loadFromFile("Ex2\\Tests\\telephoneBook.txt");
        Boolean fromFile = true;
        if (fromFile) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(test1));
                calendar.menu(scanner, telephoneBook);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            calendar.menu(scanner, telephoneBook);
        }
        System.out.println("Bye Bye");
    }
}