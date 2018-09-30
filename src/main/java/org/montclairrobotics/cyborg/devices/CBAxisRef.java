package org.montclairrobotics.cyborg.devices;

public class CBAxisRef {
    public int stickID;
    public int index;

    public CBAxisRef() {
        stickID  = -1;
        index = -1;
    }

    public CBAxisRef(int stickID, int index) {
        this.stickID = stickID;
        this.index = index;
    }

    public boolean isDefined() {
        return stickID>=0;
    }

    public static CBAxisRef undefined() {
        return new CBAxisRef(-1,-1);
    }
}
