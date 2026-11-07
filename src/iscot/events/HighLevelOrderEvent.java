package iscot.events;

import iscot.enums.Areas;

public abstract class HighLevelOrderEvent extends Event {

    public HighLevelOrderEvent(int eventId, Areas area) {
        super(eventId, area);
    }
    
}
