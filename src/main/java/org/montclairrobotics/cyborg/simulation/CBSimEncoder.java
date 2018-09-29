package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.montclairrobotics.cyborg.Cyborg;

public class CBSimEncoder implements CBIEncoder {
    CBSimEncoderData simData;

    public class CBSimEncoderData {
        int channelA, channelB;
        DigitalSource sourceA, sourceB;
        int edges;
        boolean reverseDirection;
        int encodingScale;
        double period;
        double maxPeriod;
        boolean stopped;
        boolean lastDirection;
        double distancePerPulse;
        CounterBase.EncodingType encodingType;
        double rate;
        double minRate;
        int samplesToAverage;
        PIDSourceType pidSource;
    }

    public CBSimEncoder(final int channelA, final int channelB, boolean reverseDirection,
                        final CounterBase.EncodingType encodingType) {
        simData = new CBSimEncoderData();
        simData.channelA = channelA;
        simData.channelB = channelB;
        simData.reverseDirection = reverseDirection;
        simData.encodingType = encodingType;
        Cyborg.simLink.encoders.add(simData);
    }

    public CBSimEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection,
                        final CounterBase.EncodingType encodingType) {
        simData = new CBSimEncoderData();
        simData.sourceA = sourceA;
        simData.sourceB = sourceB;
        simData.reverseDirection = reverseDirection;
        simData.encodingType = encodingType;
        Cyborg.simLink.encoders.add(simData);
    }


    //@Override
    //public int getEncodingScale() {
    //    return simData.encodingScale;
    //}

    @Override
    public int getRaw() {
        return simData.edges;
    }

    @Override
    public void reset() {
        simData.edges = 0;
    }

    /*
    @Override
    public int get() {
        return simData.edges/simData.encodingScale;
    }
    */

    /*
    @Override
    public double getPeriod() {
        return simData.period;
    }

    @Override
    public void setMaxPeriod(double maxPeriod) {
        simData.maxPeriod=maxPeriod;
    }

    @Override
    public boolean getStopped() {
        return simData.stopped;
    }

    @Override
    public boolean getDirection() {
        return simData.lastDirection;
    }

    @Override
    public double getDistance() {
        return get()*simData.distancePerPulse;
    }

    @Override
    public double getRate() {
        return simData.rate;
    }

    @Override
    public void setMinRate(double minRate) {
        simData.minRate = minRate;
    }

    @Override
    public void setDistancePerPulse(double distancePerPulse) {
        simData.distancePerPulse = distancePerPulse;
    }

    @Override
    public double getDistancePerPulse() {
        return simData.distancePerPulse;
    }

    @Override
    public void setReverseDirection(boolean reverseDirection) {
        simData.reverseDirection = reverseDirection;
    }

    @Override
    public void setSamplesToAverage(int samplesToAverage) {
        simData.samplesToAverage = samplesToAverage;
    }

    @Override
    public int getSamplesToAverage() {
        return simData.samplesToAverage;
    }

    @Override
    public void setSourceType(PIDSourceType pidSource) {
        simData.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getSourceType() {
        return simData.pidSource;
    }

    @Override
    public double pidGet() {
        switch (simData.pidSource) {
            case kDisplacement:
                return getDistance();
            case kRate:
                return getRate();
            default:
                return 0.0;
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getSubsystem() {
        return null;
    }

    @Override
    public void setSubsystem(String subsystem) {

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        if (simData.encodingType.value == EncodingType.k4X.value) {
            builder.setSmartDashboardType("Quadrature Encoder");
        } else {
            builder.setSmartDashboardType("Encoder");
        }

        builder.addDoubleProperty("Speed", this::getRate, null);
        builder.addDoubleProperty("Distance", this::getDistance, null);
        builder.addDoubleProperty("Distance per Tick", this::getDistancePerPulse, null);

    }
    */
}
