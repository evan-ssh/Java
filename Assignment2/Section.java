package assignment2;

public class Section {
    private final String id;
    private final Schedule schedule;
    private final Course[] courses;

    public Section(String id) {
        this.id = id;
        this.schedule = new Schedule(50);
        this.courses = new Course[7];
    }

    public String getId() { return id; }

    /**
     * This method belongs in Section because the schedule is part of the section state.
     * This method should be public so the main scheduler can add/remove slots for this section.
     */
    public Schedule getSchedule() { return schedule; }

    /**
     * This method belongs in Section because sections manage their course list.
     * This method should be public so the main program can assign courses to sections.
     */
    public boolean addCourse(Course course) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] == null) {
                courses[i] = course;
                return true;
            }
        }
        return false;
    }

    /**
     * This method belongs in Section because callers need to iterate the section's courses.
     * This method should be public so the main loop can schedule each course.
     */
    public Course[] getCourses() {
        Course[] copy = new Course[courses.length];
        System.arraycopy(courses, 0, copy, 0, courses.length);
        return copy;
    }

    /**
     * This method belongs in Section because calculating scheduled hours is a section responsibility.
     * This method should be public so the main loop can decide whether a course still needs scheduling.
     */
    public int scheduledHoursFor(Course courseToCheck) {
        int minutes = 0;
        for (ScheduleSlot slot : schedule.getSlots()) {
            if (slot == null) continue;
            Course slotCourse = slot.getCourse();
            if (slotCourse == null) continue;
            if (!slotCourse.getId().equals(courseToCheck.getId())) continue;
            Timeslot t = slot.getTimeslot();
            int start = t.getStartHour() * 60 + t.getStartMinute();
            int end = t.getEndHour() * 60 + t.getEndMinute();
            minutes += Math.max(0, end - start);
        }
        return minutes / 60;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Section " + id + "\nCourses:\n");
        for (Course c : courses) sb.append(" - ").append(c == null ? "[empty]" : c).append('\n');
        sb.append("Schedule:\n").append(schedule.toString());
        return sb.toString();
    }
}