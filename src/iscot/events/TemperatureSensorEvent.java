package iscot.events;

import iscot.enums.Areas;

public class TemperatureSensorEvent extends SensorEvent{

    public TemperatureSensorEvent(int sensorId, Areas area, double value) {
        super(sensorId, area, value);
    }
}
