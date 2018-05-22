package org.montclairrobotics.cyborg.devices;

public class CBSpeedControllerFaultCriteria {
    boolean basic;
    double minPower, minCurrent, maxCurrent;

    public CBSpeedControllerFaultCriteria() {
    }

    public CBSpeedControllerFaultCriteria setBasic(double minPower, double minCurrent, double maxCurrent) {
        this.basic = true;
        this.minPower = minPower;
        this.minCurrent = minCurrent;
        this.maxCurrent = maxCurrent;

        return this;
    }

    public CBSpeedControllerFault check(double actualCurrent, double actualPower) {

        if (basic) {
            CBSpeedControllerFault fault = new CBSpeedControllerFault();
            if (actualPower > minPower && actualCurrent < minCurrent) {
                fault.underCurrent = true;
                fault.errMsg += "Under Current ";
            }
            if (actualCurrent > maxCurrent) {
                fault.overCurrent = true;
                fault.errMsg += "Over Current ";
            }
            if (fault.underCurrent || fault.overCurrent) {
                return fault;
            }
        }

        return null;
    }
}
