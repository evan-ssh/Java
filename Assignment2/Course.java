package assignment2;

public class Course {
    private final String id;
    private final String name;
    private final RoomType roomType;
    private final int hoursPerWeek;

    public Course(String id, String name, RoomType roomType, int hoursPerWeek) {
        this.id = id;
        this.name = name;
        this.roomType = roomType;
        this.hoursPerWeek = hoursPerWeek;
    }

    /**
     * This method belongs in Course because it exposes the course identifier.
     * This method should be public so other classes can match and schedule courses.
     */
    public String getId() { return id; }
    public String getName() { return name; }
    public RoomType getRoomType() { return roomType; }
    public int getHoursPerWeek() { return hoursPerWeek; }

    @Override
    public String toString() { return name + " (" + id + ")"; }
}