package iscot.events;

import iscot.enums.Areas;

public class HumiditySensorEvent extends SensorEvent{

    public HumiditySensorEvent(int sensorId, Areas area, double value) {
        super(sensorId, area, value);
    }

}
