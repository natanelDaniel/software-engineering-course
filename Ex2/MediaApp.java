package Ex2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;

public class MediaApp {

         public static class Media {
            public enum MediaType {
                MUSIC, VIDEO
            }

            static MediaType[] typeValue = MediaType.values();
            private final MediaType type;
            private String mediaName;
            private float mediaLength;

            public Media(String name, float length, MediaType type) {
                this.mediaName = name;
                this.mediaLength = length;
                this.type = type;
            }
        }

    private Vector<Media> mediaList;

    public MediaApp() {
        this.mediaList = new Vector<>();
    }


    public void addMedia(String name, float length, Media.MediaType type) {
        this.mediaList.add(new Media(name, length, type));
    }

    public void deleteMedia(String name) {
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) if (itr.next().mediaName.equals(name)) {
            itr.remove();
            return;
        }
    }

    public void playMedia(String name) {
        Media media;
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            media = itr.next();
            if (media.mediaName.equals(name)) {
                printMediaMessage(media);
                return;
            }
        }
    }
    public void playAllMedia() {
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            printMediaMessage(itr.next());
        }
    }

    public void playAllFromType(Media.MediaType type) {
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            Media media = itr.next();
            if (media.type == type) {
                printMediaMessage(media);
            }
        }
    }


    public void printMediaMessage(Media media) {
        System.out.println(media.mediaName + " is now playing for " + media.mediaLength + " time");
    }

    public static void printMediaMenu() {
        System.out.println("1. Add media");
        System.out.println("2. Play media by name");
        System.out.println("3. Delete media by name");
        System.out.println("4. Play all media");
        System.out.println("5. Play all music");
        System.out.println("6. Play all video");
        System.out.println("7. Back to main menu");
    }

    public static void menu(MediaApp mediaApp, Scanner scanner) {
        Integer choice = 0; // initialize choice to an invalid value
        String name;
        Float length;
        do {
            printMediaMenu();
            // Check if input is an integer
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter media name:");
                        name = scanner.next();
                        // Check if name is not empty
                            System.out.println("Enter media length:");
                            length = scanner.nextFloat();
                            System.out.println("Enter media type (1 for MUSIC, 2 for VIDEO):");
                            Media.MediaType type = Media.typeValue[(scanner.nextInt())-1];
                            mediaApp.addMedia(name, length, type);
                            System.out.println(name + " added successfully.");
                        break;
                    case 2:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            mediaApp.playMedia(name);
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            mediaApp.deleteMedia(name);
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 4:
                        mediaApp.playAllMedia();
                        break;
                    case 5:
                        mediaApp.playAllFromType(Media.MediaType.MUSIC);
                        break;
                    case 6:
                        mediaApp.playAllFromType(Media.MediaType.VIDEO);
                        break;
                    case 7:
                        System.out.println("Leaving Media App...");
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

    public static void main(String[] args) {
        MediaApp mediaApp = new MediaApp();
//        take input from file
      String test1 = "Ex1\\test1.txt";

        Boolean fromFile = false;

        if (fromFile) {
            try {
                Scanner scanner = new Scanner(new FileInputStream(test1));
                menu(mediaApp, scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            menu(mediaApp, scanner);
        }
        System.out.println("Bye Bye");
    }
}
