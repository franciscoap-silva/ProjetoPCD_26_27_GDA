package iscot.events;

import iscot.enums.Areas;
import iscot.enums.OnOffMode;

public class AreaIrrigationOrderEvent extends HighLevelOrderEvent{

    private final OnOffMode state;

    public AreaIrrigationOrderEvent(Areas area, OnOffMode state) {
        super(area);
        this.state = state;
    }
    
    public OnOffMode getState() {
        return state;
    }
}
