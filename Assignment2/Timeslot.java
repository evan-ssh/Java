package assignment2;

public class Timeslot {
    private final Day day;
    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;

    public Timeslot(Day day, int startHour, int startMinute, int endHour, int endMinute) {
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public Day getDay() { return day; }
    public int getStartHour() { return startHour; }
    public int getStartMinute() { return startMinute; }
    public int getEndHour() { return endHour; }
    public int getEndMinute() { return endMinute; }

    private int toMinutes(int h, int m) { return h * 60 + m; }
    public boolean overlaps(Timeslot other) {
        if (other == null) return false;
        if (this.day != other.day) return false;
        int startA = toMinutes(this.startHour, this.startMinute);
        int endA   = toMinutes(this.endHour, this.endMinute);
        int startB = toMinutes(other.startHour, other.startMinute);
        int endB   = toMinutes(other.endHour, other.endMinute);
        return startA < endB && startB < endA;
    }

    @Override
    public String toString() {
        return String.format("%s %02d:%02d-%02d:%02d", day, startHour, startMinute, endHour, endMinute);
    }
}