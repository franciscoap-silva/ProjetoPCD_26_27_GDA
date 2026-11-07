package iscot.events;

import iscot.enums.Areas;

public abstract class Event {

    private final int eventId;
    private final Areas area;

    public Event(int eventId, Areas area) {
        this.eventId = eventId;
        this.area = area;
    }

    public int getId() {
        return eventId;
    }

    public Areas getArea() {
        return area;
    }

    
}
