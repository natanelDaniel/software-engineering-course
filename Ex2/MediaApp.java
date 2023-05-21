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

    public void printMediaMenu() {
        System.out.println("1. Add media");
        System.out.println("2. Play media by name");
        System.out.println("3. Delete media by name");
        System.out.println("4. Play all media");
        System.out.println("5. Play all music");
        System.out.println("6. Play all video");
        System.out.println("7. Back to main menu");
    }

    private boolean mediaExists(String name) {
        Iterator<Media> itr = this.mediaList.iterator();
        while (itr.hasNext()) {
            if (itr.next().mediaName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void menu(Scanner scanner) {
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
                            Media.MediaType type = Media.typeValue[(scanner.nextInt())-1];
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
                            if (!this.mediaExists(name)) {
                                System.out.println("Media does not exist.");
                            }
                            else {
                                this.playMedia(name);
                            }
                        } else {
                            System.out.println("Name cannot be empty.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter name:");
                        name = scanner.next();
                        // Check if name is not empty
                        if (!name.isEmpty()) {
                            if (!this.mediaExists(name)) {
                                System.out.println("Media does not exist.");
                            }
                            else {
                                this.deleteMedia(name);
                                System.out.println(name + " deleted successfully.");
                            }
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
                scanner.next(); // discard invalid input
            }
        } while (choice != 7);
    }
}
