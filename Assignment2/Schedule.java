package assignment2;

public class Schedule {
    private final ScheduleSlot[] slots;

    public Schedule(int capacity) {
        if (capacity < 1) capacity = 10;
        this.slots = new ScheduleSlot[capacity];
    }

    /**
     * This method belongs in Schedule because adding slots is a Schedule responsibility.
     * This method should be public so callers can attempt to place a ScheduleSlot and detect success/failure.
     * Returns true when added; false on conflict or full.
     */
    public boolean addSlot(ScheduleSlot slot) {
        if (slot == null) return false;
        for (ScheduleSlot s : slots) {
            if (s != null && s.conflictsWith(slot)) return false;
        }
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = slot;
                return true;
            }
        }
        return false;
    }

    /**
     * This method belongs in Schedule because removing previously added slots is a Schedule responsibility.
     * This method should be public so callers can roll back failed attempts.
     */
    public boolean removeSlot(ScheduleSlot slot) {
        if (slot == null) return false;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slot) {
                slots[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * This method belongs in Schedule because external code needs to inspect scheduled slots.
     * This method should be public so schedulers and Sections can compute hours/conflicts.
     */
    public ScheduleSlot[] getSlots() {
        ScheduleSlot[] copy = new ScheduleSlot[slots.length];
        System.arraycopy(slots, 0, copy, 0, slots.length);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ScheduleSlot[] s = getSlots();
        for (int i = 0; i < s.length; i++) {
            sb.append(i).append(": ").append(s[i] == null ? "[empty]" : s[i].toString()).append('\n');
        }
        return sb.toString();
    }
}