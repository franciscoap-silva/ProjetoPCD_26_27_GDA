package iscot.events;

import iscot.enums.Areas;

public class GroundMoistureSensorEvent extends SensorEvent{

    public GroundMoistureSensorEvent(int sensorId, Areas area, double value) {
        super(sensorId, area, value);
    }

}
