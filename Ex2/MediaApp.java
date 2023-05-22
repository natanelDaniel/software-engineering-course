package Ex2;

import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;

public class MediaApp { // this class is a container for Media objects

    public static class Media {
        public enum MediaType {
            MUSIC, VIDEO
        }

        static MediaType[] typeValue = MediaType.values(); // returns an array of all the enum values
        private final MediaType type;
        private String mediaName;
        private float mediaLength;

        public Media(String name, float length, MediaType type) { // constructor
            this.mediaName = name;
            this.mediaLength = length;
            this.type = type;
        }
    }

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
            while (itr.hasNext()) if (itr.next().mediaName.equals(name)) {
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
                if (media.mediaName.equals(name)) {
                    printMediaMessage(media);
                    return;
                }
            }
        }
    }

    public void playAllMedia() { // plays all Media objects from the vector
        Iterator<Media> itr = this.mediaList.iterator();
        if (!itr.hasNext()) {
            System.out.println("No media to play.");
        } else {
            System.out.println("Playing all media.");
            do {
                printMediaMessage(itr.next());
            } while (itr.hasNext());
        }
    }

    public void playAllFromType(Media.MediaType type) { // plays all Media objects of a certain type from the vector
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            Media media = itr.next();
            if (media.type == type) {
                printMediaMessage(media);
            }
        }
    }


    public void printMediaMessage(Media media) { // prints a message when playing a Media object
        System.out.println(media.mediaName + " is now playing for " + media.mediaLength + " time");
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
            if (itr.next().mediaName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void menu(Scanner scanner) { // the media menu
        Integer choice = 0; // initialize choice to an invalid value
        String name;
        Float length;
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
                            length = scanner.nextFloat();
                            System.out.println("Enter media type (1 for MUSIC, 2 for VIDEO):");
                            Media.MediaType type = Media.typeValue[(scanner.nextInt()) - 1];
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
                        this.playAllMedia();
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
}


//    public static void main(String[] args) {
//        MediaApp mediaApp = new MediaApp();
////        take input from file
//        String test1 = "Ex1\\test1.txt";
//
//        Boolean fromFile = false;
//
//        if (fromFile) {
//            try {
//                Scanner scanner = new Scanner(new FileInputStream(test1));
//                mediaApp.menu( scanner);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            Scanner scanner = new Scanner(System.in);
//            mediaApp.menu( scanner);        }
//        System.out.println("Bye Bye");
//    }
//}

