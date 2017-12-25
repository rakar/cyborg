package org.montclairrobotics.cyborg.devices;

public class CBDualID<T> {
    private int a, b;
    public CBDualID(int a, int b) {
        this.a=a;
        this.b=b;
    }
    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
}
