package iscot.events;

import iscot.enums.Areas;
import iscot.enums.OnOffMode;

public class AreaLightingOrderEvent extends HighLevelOrderEvent{

    private final OnOffMode state;

    public AreaLightingOrderEvent(int eventId, Areas area, OnOffMode state) {
        super(eventId, area);
        this.state = state;
    }

    public OnOffMode getState() {
        return state;
    }
}
