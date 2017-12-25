package org.montclairrobotics.cyborg.devices;

public class CBPDBSlots extends CBBus<CBPDBSlots> {
    public CBPDBSlots() {
        super(1, 16); // TODO: check can bus address limits
    }
}
