package iscot.events;

import iscot.enums.Areas;

public class HumiditySensorEvent extends SensorEvent{

    public HumiditySensorEvent(int eventId, int sensorId, Areas area, double value) {
        super(eventId, sensorId, area, value);
    }

}
