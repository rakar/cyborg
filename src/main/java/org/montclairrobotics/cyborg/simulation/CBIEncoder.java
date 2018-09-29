package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface CBIEncoder {

    //int getEncodingScale();

    int getRaw();

    //int get();

    void reset();

    //void setMaxPeriod(double maxPeriod);

    //boolean getStopped();

    //boolean getDirection();

    //double getDistance();

    //double getRate();

    //void setMinRate(double minRate);

    //void setDistancePerPulse(double distancePerPulse);

    //double getDistancePerPulse();

    //void setReverseDirection(boolean reverseDirection);

    //void setSamplesToAverage(int samplesToAverage);

    //int getSamplesToAverage();

    /*
    void setSourceType(PIDSourceType pidSource);
    PIDSourceType getSourceType();
    double pidGet();

    @Override
    void initSendable(SendableBuilder builder);

    public enum IndexingType {
        kResetWhileHigh(0), kResetWhileLow(1), kResetOnFallingEdge(2), kResetOnRisingEdge(3);

        @SuppressWarnings("MemberName")
        public final int value;

        IndexingType(int value) {
            this.value = value;
        }
    }
    */
}
