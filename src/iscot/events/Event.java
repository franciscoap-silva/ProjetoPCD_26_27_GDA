package iscot.events;

import iscot.enums.Areas;

public abstract class Event {

    private final int eventId;
    private final Areas area;
    private static int countId = 1;

    public Event(Areas area) {
        this.eventId = countId++;
        this.area = area;
    }

    public int getId() {
        return eventId;
    }

    public Areas getArea() {
        return area;
    }
    
}
