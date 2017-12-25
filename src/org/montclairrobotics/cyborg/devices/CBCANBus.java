package org.montclairrobotics.cyborg.devices;

public class CBCANBus extends CBBus<CBCANBus> {
    public CBCANBus() {
        super(1, 100); // TODO: check can bus address limits
    }
}
