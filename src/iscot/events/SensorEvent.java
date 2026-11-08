package iscot.events;

import iscot.enums.Areas;

public abstract class SensorEvent extends Event {

    private final int sensorId;
    private final double value;

    public SensorEvent(int sensorId, Areas area, double value) {
        super(area);
        this.sensorId = sensorId;
        this.value = value;
    }

    public int getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
    }

     @Override
    public String toString() {
        return String.format(getClass().getSimpleName() + "[eventId=%d, sensorId=%d, area=%s, value=%.1f%%]",
                            getId(), getSensorId(), getArea(), getValue());
    }
    
}
