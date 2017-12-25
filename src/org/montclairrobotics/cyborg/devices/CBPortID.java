package org.montclairrobotics.cyborg.devices;

public class CBPortID<T> {
    private int channel;
    public CBPortID(int c) {
        channel=c;
    }
    public int get() {
        return channel;
    }
}