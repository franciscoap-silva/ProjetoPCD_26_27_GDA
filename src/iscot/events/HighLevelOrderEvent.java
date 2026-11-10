package iscot.events;

import iscot.enums.Areas;

public abstract class HighLevelOrderEvent extends Event {

    public HighLevelOrderEvent(Areas area) {
        super(area);
    }
    
}
