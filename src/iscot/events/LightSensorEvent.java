package iscot.events;

import iscot.enums.Areas;

public class LightSensorEvent extends SensorEvent{

    public LightSensorEvent(int sensorId, Areas area, double value) {
        super(sensorId, area, value);
    }

}
