package iscot.events;

import iscot.enums.Areas;

public class LightSensorEvent extends SensorEvent{

    public LightSensorEvent(int eventId, int sensorId, Areas area, double value) {
        super(eventId, sensorId, area, value);
    }

}
