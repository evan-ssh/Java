package assignment2;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many rooms? ");
        int roomCount = readInt(sc);
        Room[] rooms = new Room[roomCount];
        for (int i = 0; i < roomCount; i++) {
            System.out.print("Room id: ");
            String rid = sc.next();
            RoomType rt = readRoomType(sc);
            rooms[i] = new Room(rid, rt, 50);
        }

        System.out.print("How many courses? ");
        int courseCount = readInt(sc);
        Course[] courses = new Course[courseCount];
        for (int i = 0; i < courseCount; i++) {
            System.out.print("Course id: ");
            String cid = sc.next();
            System.out.print("Course name (one token): ");
            String cname = sc.next();
            System.out.print("Preferred room type: ");
            RoomType prt = readRoomType(sc);
            System.out.print("Hours per week: ");
            int hpw = readInt(sc);
            courses[i] = new Course(cid, cname, prt, hpw);
        }

        System.out.print("How many sections? ");
        int sectionCount = readInt(sc);
        Section[] sections = new Section[sectionCount];
        for (int i = 0; i < sectionCount; i++) {
            System.out.print("Section id: ");
            String sid = sc.next();
            Section s = new Section(sid);
            System.out.println("Add course indices to section (enter -1 to stop):");
            while (true) {
                System.out.print("Course index: ");
                int idx = readInt(sc);
                if (idx == -1) break;
                if (idx < 0 || idx >= courseCount) { System.out.println("Bad index"); continue; }
                if (!s.addCourse(courses[idx])) { System.out.println("Section full"); break; }
            }
            sections[i] = s;
        }

        Day[] days = Day.values();
        Timeslot[] defaults = new Timeslot[days.length];
        System.out.println("Optional: enter default start/end for each day (-1 to skip):");
        for (int i = 0; i < days.length; i++) {
            System.out.println("Day: " + days[i]);
            System.out.print("Start hour (-1 to skip): ");
            int sh = readInt(sc);
            if (sh == -1) continue;
            System.out.print("Start minute: ");
            int sm = readInt(sc);
            System.out.print("End hour: ");
            int eh = readInt(sc);
            System.out.print("End minute: ");
            int em = readInt(sc);
            defaults[i] = new Timeslot(days[i], sh, sm, eh, em);
        }

        for (Section s : sections) {
            for (Course c : s.getCourses()) {
                if (c == null) continue;
                while (s.scheduledHoursFor(c) < c.getHoursPerWeek()) {
                    System.out.println("Scheduling " + c + " for section " + s.getId());
                    Day day = readDay(sc);
                    System.out.print("Start hour: "); int sh = readInt(sc);
                    System.out.print("Start minute: "); int sm = readInt(sc);
                    System.out.print("End hour: "); int eh = readInt(sc);
                    System.out.print("End minute: "); int em = readInt(sc);
                    System.out.print("Choose room index (0.." + (rooms.length - 1) + "): ");
                    int ridx = readInt(sc);
                    if (ridx < 0 || ridx >= rooms.length) { System.out.println("Bad room index"); continue; }
                    Room chosen = rooms[ridx];
                    Timeslot ts = new Timeslot(day, sh, sm, eh, em);
                    ScheduleSlot slot = new ScheduleSlot(c, ts, s.getId(), chosen.getId());

                    boolean addedToSection = s.getSchedule().addSlot(slot);
                    if (!addedToSection) {
                        System.out.println("Section conflict or full. Try again.");
                        continue;
                    }
                    boolean addedToRoom = chosen.book(slot);
                    if (!addedToRoom) {
                        s.getSchedule().removeSlot(slot);
                        System.out.println("Room conflict or wrong room. Rolled back. Try again.");
                        continue;
                    }
                    System.out.println("Scheduled: " + slot);
                }
            }
        }

        System.out.println("=== Room Schedules ===");
        for (Room r : rooms) System.out.println(r);

        System.out.println("=== Section Schedules ===");
        for (Section s : sections) System.out.println(s);

        sc.close();
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Please enter integer: "); }
        return sc.nextInt();
    }

    private static RoomType readRoomType(Scanner sc) {
        while (true) {
            System.out.print("Room type (CLASSROOM, COMPUTER_LAB, CHEMISTRY_LAB, AUDITORIUM, OTHER): ");
            String v = sc.next();
            for (RoomType rt : RoomType.values()) if (rt.name().equalsIgnoreCase(v)) return rt;
            System.out.println("Invalid room type");
        }
    }

    private static Day readDay(Scanner sc) {
        while (true) {
            System.out.print("Day (MONDAY..SUNDAY): ");
            String v = sc.next();
            for (Day d : Day.values()) if (d.name().equalsIgnoreCase(v)) return d;
            System.out.println("Invalid day");
        }
    }
}