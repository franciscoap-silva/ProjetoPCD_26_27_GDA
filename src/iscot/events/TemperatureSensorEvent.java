package iscot.events;

import iscot.enums.Areas;

public class TemperatureSensorEvent extends SensorEvent{

    public TemperatureSensorEvent(int eventId, int sensorId, Areas area, double value) {
        super(eventId, sensorId, area, value);
    }

}
