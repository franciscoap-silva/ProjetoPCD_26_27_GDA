package iscot.events;

import iscot.enums.Areas;
import iscot.enums.Type;

public class RegistrationEvent extends Event {

    private final Type type;

    public RegistrationEvent(Areas area, Type type) {
        super(area);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
