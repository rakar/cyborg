package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.montclairrobotics.cyborg.Cyborg;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class CBEncoder extends CBEncoderBase {

    // Common code FRC/FTC
    private int edgeValue = 0;
    private int pulseValue = 0;
    private double distanceValue = 0;
    private int lastEdgeValue = 0;
    private int lastPulseValue = 0;
    private double lastDistanceValue = 0;

    private int offEdgeValue = 0;
    private int offPulseValue = 0;
    private double offDistanceValue = 0;

    private int stoppedMargin = 0;
    private long ms = 0;
    private long lastUpdate;
    private long pulseRate = 0;
    private double speed = 0;
    private PIDSourceType sourceType;

    private int edgesPerPulse = 0;

    private double distancePerPulse = 1;

    private boolean reversed = false;
    private int reversedScale = 1;

    private int offsetPulses = 0;
    private int offsetEdges = 0;
    private double offsetDistance = 0;
    private boolean indexed = false;
    private ArrayList<CBIndexEntry> indexEntries = new ArrayList<>();

    public CBEncoder(int aChannel, int bChannel, EncodingType encodingType, boolean reversed, double distancePerPulse) {
        super(aChannel, bChannel, encodingType, reversed, distancePerPulse);
        setReverseDirection(reversed);
        setTickConversion(encodingType);
        setDistancePerPulse(distancePerPulse);
    }

    public CBEncoder(DigitalSource aSource, DigitalSource bSource, EncodingType encodingType, boolean reversed, double distancePerPulse) {
        super(aSource, bSource, encodingType, reversed, distancePerPulse);
        setReverseDirection(reversed);
        setTickConversion(encodingType);
        setDistancePerPulse(distancePerPulse);
    }

    public CBEncoder(CBDeviceID controllerId, FeedbackDevice encoderType, boolean reversed, double distancePerPulse) {
        super(controllerId, encoderType, reversed, distancePerPulse);
        setReverseDirection(reversed);
        setTickConversion(EncodingType.k4X);
        setDistancePerPulse(distancePerPulse);
    }

    public CBEncoder(String name, boolean reversed, double distancePerPulse) {
        super(name, reversed, distancePerPulse);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubsystem() {
        return subsystem;
    }

    @Override
    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public CBEncoderBase setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBEncoderBase setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }


    public class CBIndexEntry {
        CBDigitalInput trigger;
        CBDeviceID triggerId;
        boolean activeState;
        double distance;

        public CBIndexEntry(CBDeviceID triggerId, boolean activeState, double distance) {
            this.triggerId = triggerId;
            this.activeState = activeState;
            this.distance = distance;
            this.trigger = Cyborg.hardwareAdapter.getDigitalInput(triggerId);
        }
    }

    private void setTickConversion(EncodingType encodingType) {
        switch (encodingType) {
            case k1X:
                edgesPerPulse = 1;
                break;
            case k2X:
                edgesPerPulse = 2;
                break;
            case k4X:
                edgesPerPulse = 4;
                break;
        }
    }

    public CBEncoder setDistance(double distance) {
        reset();
        offsetDistance = distance;
        offsetPulses = (int) (distance / distancePerPulse);
        offsetEdges = (int) (distance * edgesPerPulse / distancePerPulse);
        indexed = true;
        //SmartDashboard.putNumber("setting dist dist",distance);
        //SmartDashboard.putNumber("setting dist edges", offsetEdges);
        return this;
    }

    public CBEncoder setPulses(int pulses) {
        reset();
        offsetDistance = distancePerPulse * pulses;
        offsetPulses = pulses;
        offsetEdges = offsetPulses * edgesPerPulse;
        indexed = true;
        return this;
    }

    public CBEncoder setEdges(int edges) {
        reset();
        offsetEdges = edges;
        offsetPulses = edges / edgesPerPulse;
        offsetDistance = distancePerPulse * offsetPulses;
        indexed = true;
        return this;
    }

    public boolean wasIndexed() {
        return indexed;
    }

    public CBEncoder setReverseDirection(boolean reverseDirection) {
        if (reverseDirection != reversed) {
            reset();
        }
        this.reversed = reverseDirection;
        this.reversedScale = reversed ? -1 : 1;
        return this;
    }

	/*
	public CBEncoder addIndexEntry(CBIndexEntry indexEntry) {
		removeIndexEntry(indexEntry.triggerId);
		indexEntries.add(indexEntry);
		return this;
	}

	public CBEncoder removeIndexEntry(CBDeviceID triggerId) {
		int trg = -1;
		for(int i=0;i<indexEntries.size();i++) {
			if(indexEntries.get(i).triggerId==triggerId) {
				trg=i;
				break;
			}
		}
		if(trg>-1) {
			indexEntries.remove(trg);
		}
		return this;
	}
	*/

    public CBEncoder setDistancePerPulse(double distancePerPulse) {
        if (distancePerPulse == 0) distancePerPulse = 1;
        this.distancePerPulse = distancePerPulse;
        return this;
    }

	/*
	public CBEncoder setMaxPeriod(double maxPeriod) {
		encoder.setMaxPeriod(maxPeriod);
		return this;
	}
	*/
	/*
	public CBEncoder setMinRate(double minRate) {
		encoder.setMinRate(minRate);
		return this;
	}
	*/

    public CBEncoder setSourceType(PIDSourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public PIDSourceType getSourceType() {
        return sourceType;
    }

    // have to hijack this since our offset system
    // prevents using the encoder's pidGet
    public double pidGet() {
        switch (sourceType) {
            case kDisplacement:
                return getDistance();
            case kRate:
                return getSpeed();
            default:
                return 0;
        }
    }

	/*
	public CBEncoder setSamplesToAverage(int samplesToAverage) {
		encoder.setSamplesToAverage(samplesToAverage);
		return this;
	}
	*/

	/*
	public CBEncoder updateTable() {
		//super.updateTable();
		return this;
	}
    */

    public int getRaw() {
        return offEdgeValue;
    }

    public double get() {
        return pidGet();
    }

    public double getDistance() {
        return offDistanceValue;
    }

    public boolean getDirection() {
        return (pulseValue - lastPulseValue) > 0;
    }

    public int getEncodingScale() {
        return edgesPerPulse;
    }

    public double getRate() {
        return pulseRate;
    }

    public double getSpeed() {
        return speed;
    }

    public int getEdges() {
        return edgeValue + offsetEdges;
    }

	/*
	public int getSamplesToAverage() {
		return encoder.getSamplesToAverage();
	}
    */

    public boolean getStopped() {
        return Math.abs(pulseRate) < stoppedMargin;
    }

    public CBEncoder reset() {
        baseReset();
        edgeValue = reversedScale * baseGetRaw();
        pulseValue = edgeValue / edgesPerPulse;
        distanceValue = pulseValue * distancePerPulse;
        offsetEdges = 0;
        offsetPulses = 0;
        offsetDistance = 0;
        lastEdgeValue = 0;
        lastPulseValue = 0;
        lastDistanceValue = 0;
        offsetEdges = 0;
        offsetPulses = 0;
        offsetDistance = 0;
        offEdgeValue = edgeValue + offsetEdges;
        offPulseValue = pulseValue + offsetPulses;
        offDistanceValue = distanceValue + offsetDistance;
        lastUpdate = currentTimeMillis();
        return this;
    }

    @Override
    public CBDeviceControl getDeviceControl() {
        return deviceControl;
    }

    CBDeviceControl deviceControl = new CBDeviceControl() {
        @Override
        public void init() {
        }

        @Override
        public void senseUpdate() {
            lastEdgeValue = edgeValue;


            edgeValue = reversedScale * baseGetRaw();
            pulseValue = edgeValue / edgesPerPulse;
            distanceValue = pulseValue * distancePerPulse;
            offEdgeValue = edgeValue + offsetEdges;
            offPulseValue = offEdgeValue / edgesPerPulse;
            offDistanceValue = offPulseValue * distancePerPulse;

            //SmartDashboard.putNumber("sense encoder edges", edgeValue);
            //SmartDashboard.putNumber("sense encoder offsetEdges", offsetEdges);
            //SmartDashboard.putNumber("sense encoder offEdgeVal", offEdgeValue);

            long now = currentTimeMillis();
            if (lastUpdate == 0) {
                ms = 0;
            } else {
                ms = now - lastUpdate;
            }
            lastUpdate = now;

            if (ms == 0) {
                pulseRate = 0;
            } else {
                pulseRate = (pulseValue - lastPulseValue) * 1000 / ms;
            }
            speed = pulseRate * distancePerPulse;

            for (CBIndexEntry i : indexEntries) {
                if (i.trigger.get() == i.activeState) {
                    setDistance(i.distance);
                }
            }
        }

        @Override
        public void controlUpdate() {
        }
    };
}
