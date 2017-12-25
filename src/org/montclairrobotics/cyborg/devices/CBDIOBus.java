package org.montclairrobotics.cyborg.devices;

public class CBDIOBus extends CBBus<CBDIOBus> {
    public CBDIOBus() {
        super(1, 16); // TODO: check DIO bus address limits
    }
}

