package Ex2;

public class Media {
    public enum MediaType {
        MUSIC, VIDEO
    }

    static MediaType[] typeValue = MediaType.values(); // returns an array of all the enum values
    final MediaType type;
    private String mediaName;
    private float mediaLength;

    public Media(String name, float length, MediaType type) { // constructor
        this.mediaName = name;
        this.mediaLength = length;
        this.type = type;
    }
    public String toString() { // prints a message when playing a Media object
        return this.mediaName + " is now playing for " + this.mediaLength + " time";
    }
    public String getMediaName() {
        return this.mediaName;
    }
    public float getMediaLength() {
        return this.mediaLength;
    }
    public MediaType getMediaType() {
        return this.type;
    }
    public void setMediaName(String name) {
        this.mediaName = name;
    }
    public void setMediaLength(float length) {
        this.mediaLength = length;
    }
}