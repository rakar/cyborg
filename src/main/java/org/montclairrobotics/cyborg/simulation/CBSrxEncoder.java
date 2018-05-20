package org.montclairrobotics.cyborg.simulation;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.montclairrobotics.cyborg.devices.CBTalonSRX;

public class CBSrxEncoder implements CBIEncoder {
    CBTalonSRX srx;
    int encoderScale = 4;
    double distancePerPulse=1;
    boolean reversed = false;
    int reversedMultiplier =1;

    public CBSrxEncoder(CBTalonSRX srx, FeedbackDevice encoderType, boolean reversed, double distancePerPulse) {
        this.srx = srx;
        switch (encoderType) {
            case QuadEncoder:
                srx.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
                encoderScale = 4;
                break;
        }
    }

    //@Override
    public int getEncodingScale() {
        return encoderScale;
    }

    //@Override
    public int getRaw() {
        return reversedMultiplier * srx.getSelectedSensorPosition(0);
    }

    //@Override
    public int get() {
        return getRaw()/getEncodingScale();
    }

    //@Override
    public void reset() {
        srx.setSelectedSensorPosition(0,0,10);
    }

    //@Override
    public double getPeriod() {
        return 0;
    }

    //@Override
    public void setMaxPeriod(double maxPeriod) {

    }

    //@Override
    public boolean getStopped() {
        return false;
    }

    //@Override
    public boolean getDirection() {
        return false;
    }

    //@Override
    public double getDistance() {
        return get()*getDistancePerPulse();
    }

    //@Override
    public double getRate() {
        return 0;
    }

    //@Override
    public void setMinRate(double minRate) {

    }

    //@Override
    public void setDistancePerPulse(double distancePerPulse) {
        this.distancePerPulse = distancePerPulse;
    }

    //@Override
    public double getDistancePerPulse() {
        return distancePerPulse;
    }

    //@Override
    public void setReverseDirection(boolean reverseDirection) {
        this.reversed = reverseDirection;
        this.reversedMultiplier = reverseDirection?-1:1;
    }

    //@Override
    public void setSamplesToAverage(int samplesToAverage) {

    }

    //@Override
    public int getSamplesToAverage() {
        return 0;
    }

    //@Override
    public void setPIDSourceType(PIDSourceType pidSource) {
    }

    //@Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    //@Override
    public double pidGet() {
        return 0;
    }

    //@Override
    public String getName() {
        return null;
    }

    //@Override
    public void setName(String name) {

    }

    //@Override
    public String getSubsystem() {
        return null;
    }

    //@Override
    public void setSubsystem(String subsystem) {

    }

    //@Override
    public void initSendable(SendableBuilder builder) {

    }
}
