package iscot.events;

import iscot.enums.Areas;
import iscot.enums.OnOffMode;

public class  AreaVentilationOrderEvent extends HighLevelOrderEvent{

    private final OnOffMode state;

    public AreaVentilationOrderEvent(Areas area, OnOffMode state) {
        super(area);
        this.state = state;
    }

    public OnOffMode getState() {
        return state;
    }

}
