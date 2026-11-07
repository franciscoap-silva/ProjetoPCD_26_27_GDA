package iscot.events;

import iscot.enums.Areas;

public class GroundMoistureSensorEvent extends SensorEvent{

    public GroundMoistureSensorEvent(int eventId, int sensorId, Areas area, double value) {
        super(eventId, sensorId, area, value);
    }

}
