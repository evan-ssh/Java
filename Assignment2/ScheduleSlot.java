package assignment2;

public class ScheduleSlot {
    private final Course course;
    private final Timeslot timeslot;
    private final String sectionId;
    private final String roomId;

    public ScheduleSlot(Course course, Timeslot timeslot, String sectionId, String roomId) {
        this.course = course;
        this.timeslot = timeslot;
        this.sectionId = sectionId;
        this.roomId = roomId;
    }

    /**
     * This method belongs in ScheduleSlot because other objects need the Course it represents.
     * This method should be public so scheduling code can inspect the slot's course.
     */
    public Course getCourse() { return course; }

    /**
     * This method belongs in ScheduleSlot because other objects need the Time information.
     * This method should be public so scheduling code can check conflicts.
     */
    public Timeslot getTimeslot() { return timeslot; }

    public String getSectionId() { return sectionId; }
    public String getRoomId() { return roomId; }

    /**
     * This method belongs in ScheduleSlot because conflict logic between slots is a slot-level concern.
     * This method should be public so Schedule.addSlot can call it.
     */
    public boolean conflictsWith(ScheduleSlot other) {
        if (other == null) return false;
        return this.timeslot.overlaps(other.timeslot);
    }

    @Override
    public String toString() {
        return course + " @ " + timeslot + " sec=" + sectionId + " room=" + roomId;
    }
}