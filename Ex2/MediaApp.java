package Ex2;

import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;

public class MediaApp { // this class is a container for Media objects

    private Vector<Media> mediaList; // a vector of Media objects

    public MediaApp() {
        this.mediaList = new Vector<>();
    } // constructor


    public void addMedia(String name, float length, Media.MediaType type) { // adds a new Media object to the vector
        this.mediaList.add(new Media(name, length, type));
    }

    public void deleteMedia(String name) { // deletes a Media object from the vector
        Iterator<Media> itr = this.mediaList.iterator();
        if (!mediaExists(name)) {
            System.out.println("Media does not exist.");
        } else {
            while (itr.hasNext()) if (itr.next().getMediaName().toLowerCase().equals(name.toLowerCase())) {
                itr.remove();
                System.out.println(name + " deleted successfully.");
                return;
            }
        }
    }

    public void playMedia(String name) { // plays a Media object from the vector
        Media media;
        Iterator<Media> itr = this.mediaList.iterator();
        if (!mediaExists(name)) {
            System.out.println("Media does not exist.");
        } else {
            while (itr.hasNext()) {
                media = itr.next();
                if (media.getMediaName().toLowerCase().equals(name.toLowerCase())) {
                    System.out.println(media);
                    return;
                }
            }
        }
    }

    public String toString() { // plays all Media objects from the vector
        Iterator<Media> itr = this.mediaList.iterator();
        String result;
        if (!itr.hasNext()) {
            result = "No media to play.";
        } else {
            result = "Playing all media: \n";
            do {
                result += itr.next() + "\n";
            } while (itr.hasNext());
        }
        return result;
    }

    public void playAllFromType(Media.MediaType type) { // plays all Media objects of a certain type from the vector
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            Media media = itr.next();
            if (media.type == type) {
                System.out.println(media);
            }
        }
    }

    public void printMediaMenu() { // prints the media menu
        System.out.println("1. Add media");
        System.out.println("2. Play media by name");
        System.out.println("3. Delete media by name");
        System.out.println("4. Play all media");
        System.out.println("5. Play all music");
        System.out.println("6. Play all video");
        System.out.println("7. Back to main menu");
    }

    private boolean mediaExists(String name) { // checks if a Media object exists in the vector
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            if (itr.next().getMediaName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void menu(Scanner scanner) { // the media menu
        Integer choice = 0; // initialize choice to an invalid value
        String name;
        Float length;
        String input;
        Integer type_num;
        do {
            printMediaMenu();
            // Check if input is an integer
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Enter media name:");
                        name = scanner.nextLine();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            System.out.println("Enter media length:");
                            input = scanner.next();
                            // Check if input is a float
                            if (!input.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                                System.out.println("Length must be a number.");
                                break;
                            }
                            length = Float.parseFloat(input);
                            if ( ! isValidLength(length)) {
                                System.out.println("Length must be positive.");
                                break;
                            }
                            System.out.println("Enter media type (1 for MUSIC, 2 for VIDEO):");
                            input = scanner.next();
                            // Check if input is an integer
                            if (!input.matches("[0-9]+")) {
                                System.out.println("Type must be a number.");
                                break;
                            }
                            type_num = Integer.parseInt(input);
                            if (type_num < 1 || type_num > 2) {
                                System.out.println("Type must be 1 or 2.");
                                break;
                            }
                            Media.MediaType type = Media.typeValue[(type_num - 1)];
                            this.addMedia(name, length, type);
                            System.out.println(name + " added successfully.");
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            this.playMedia(name);
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            this.deleteMedia(name);
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 4:
                        System.out.println(this);
                        break;
                    case 5:
                        this.playAllFromType(Media.MediaType.MUSIC);
                        break;
                    case 6:
                        this.playAllFromType(Media.MediaType.VIDEO);
                        break;
                    case 7:
                        System.out.println("Leaving Media App...");
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); //  discard the invalid input
            }
        } while (choice != 7);
    }

    private boolean isValidLength(Float length) {
        return length > 0;
    }
}
