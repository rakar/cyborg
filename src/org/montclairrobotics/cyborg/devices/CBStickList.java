package org.montclairrobotics.cyborg.devices;

public class CBStickList extends CBBus<CBStickList> {
    public CBStickList() {
        super(0, 5); // TODO: check Stick list address limits
    }
}