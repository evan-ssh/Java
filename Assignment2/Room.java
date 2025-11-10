package assignment2;

public class Room {
    private final String id;
    private final RoomType type;
    private final Schedule schedule;

    public Room(String id, RoomType type, int capacity) {
        this.id = id;
        this.type = type;
        this.schedule = new Schedule(capacity);
    }

    public String getId() { return id; }

    /**
     * This method belongs in Room because room type is a room property.
     * This method should be public so scheduler can match course requirements.
     */
    public RoomType getType() { return type; }

    /**
     * This method belongs in Room because booking is a room responsibility.
     * This method should be public so external code can attempt to book the room.
     * Returns true on success; false on conflict or wrong-target.
     */
    public boolean book(ScheduleSlot slot) {
        if (slot == null) return false;
        if (!id.equals(slot.getRoomId())) return false;
        return schedule.addSlot(slot);
    }

    /**
     * This method belongs in Room because cancelling is a room responsibility.
     * This method should be public so external code can roll back bookings.
     */
    public boolean cancel(ScheduleSlot slot) { return schedule.removeSlot(slot); }

    @Override
    public String toString() {
        return "Room " + id + " (" + type + ")\n" + schedule.toString();
    }
}