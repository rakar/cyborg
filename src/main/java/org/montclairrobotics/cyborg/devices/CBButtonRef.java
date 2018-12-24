package org.montclairrobotics.cyborg.devices;

public class CBButtonRef {
    public int stickID;
    public int index;

    public CBButtonRef() {
        stickID = -1;
        index = -1;
    }

    public CBButtonRef(int stickID, int index) {
        this.stickID = stickID;
        this.index = index;
    }

    public boolean isDefined() {
        return stickID >= 0;
    }

    public static CBButtonRef undefined() {
        return new CBButtonRef(-1, -1);
    }
}
