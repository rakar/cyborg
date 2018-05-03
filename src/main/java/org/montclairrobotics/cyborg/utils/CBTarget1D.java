package org.montclairrobotics.cyborg.utils;

public class CBTarget1D extends CBEdgeTrigger {
    double xTarget;
    double xRange;
    double xPosition;
    boolean active;

    public CBTarget1D() {
        xPosition = 0;
    }

    public CBTarget1D setTarget(double xTarget, double xRange) {
        this.xTarget = xTarget;
        this.xRange = xRange;
        return this;
    }

    public CBTarget1D setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public CBTarget1D setPosition(double x) {
        xPosition = x;
        return this;
    }

    public CBTarget1D update(double x) {
        xPosition = x;
        super.update(active && (Math.abs(xTarget - xPosition) < xRange));
        return this;
    }

    public double getXPosition() {
        return xPosition;
    }
}


