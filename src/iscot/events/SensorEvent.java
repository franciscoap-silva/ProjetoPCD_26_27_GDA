package iscot.events;

import iscot.enums.Areas;

public abstract class SensorEvent extends Event {

    private final int sensorId;
    private final double value;

    public SensorEvent(int eventId, int sensorId, Areas area, double value) {
        super(eventId, area);
        this.sensorId = sensorId;
        this.value = value;
    }

    public int getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
    }
    
}
