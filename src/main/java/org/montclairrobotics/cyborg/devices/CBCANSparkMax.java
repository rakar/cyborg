package org.montclairrobotics.cyborg.devices;

import com.revrobotics.*;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBEnums;

import static com.revrobotics.ControlType.*;

public class CBCANSparkMax extends CBSmartSpeedController {
    CANSparkMax controller;
    private CANPIDController pidController;
    CANError canError;
    ControlType localControlMode;

    public CBCANSparkMax(int channel, CANSparkMaxLowLevel.MotorType motorType) {
        controller = new CANSparkMax(channel, motorType);
        pidController = controller.getPIDController();
        pidController.setOutputRange(-1,1);
        setLocalControlMode();
    }

    @Override
    public CBCANSparkMax pidWrite(double output) {
        controller.pidWrite(output);
        return this;
    }

    @Override
    public double get() {
        return controller.get();
    }

    private void setLocalControlMode() {
        com.revrobotics.ControlType ct = null;
        switch (controlMode) {
            case NONE:
            case DUTYCYCLE:
                ct = kDutyCycle;
                break;
            case VOLTAGE:
                ct = kVoltage;
                break;
            case POSITION:
                ct = kPosition;
                break;
            case VELOCITY:
                ct = kVelocity;
                break;
            case PERCENTAGEOUTPUT:
            case CURRENT:
            case DISABLED:
            case FOLLOWER:
            case MOTIONMAGIC:
            case MOTIONPROFILE:
            case MOTIONPROFILEARC:
                break;
        }
        localControlMode = ct;
    }

    @Override
    public CBCANSparkMax set(double speed) {
        pidController.setReference(speed, localControlMode);
        return this;
    }

    public CBCANSparkMax set(double speed, CBEnums.CBMotorControlMode ctrl) {
        setControlMode(ctrl);
        set(speed);
        return this;
    }

    public CBCANSparkMax setControlMode(CBEnums.CBMotorControlMode ctrl) {
        controlMode = ctrl;
        setLocalControlMode();
        return this;
    }

    @Override
    public CBCANSparkMax setInverted(boolean isInverted) {
        controller.setInverted(isInverted);
        return this;
    }

    @Override
    public boolean getInverted() {
        return controller.getInverted();
    }

    @Override
    public CBCANSparkMax disable() {
        controller.disable();
        return this;
    }

    @Override
    public CBCANSparkMax stopMotor() {
        controller.stopMotor();
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
        }

        @Override
        public void controlUpdate() {
        }
    };

    public CANSparkMax getController() {
        return controller;
    }

    @Override
    public double getOutputVoltage() {
        return controller.getBusVoltage();
    }

    @Override
    public double getOutputCurrent() {
        return controller.getOutputCurrent();
    }

    public double getMotorTemperature() {
        return controller.getMotorTemperature();
    }

    @Override
    public CBCANSparkMax follow(CBDeviceID master) { //IMotorController masterToFollow) {
        controller.follow(Cyborg.hardwareAdapter.getCBCANSparkMax(master).controller);
        return this;
    }

    @Override
    public CBCANSparkMax follow(CBDeviceID master, boolean invert) {
        canError = controller.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax,
                Cyborg.hardwareAdapter.getCBCANSparkMax(master).controller.getDeviceId(),
                invert);
        return this;
    }


    public boolean isFollower() {
        return controller.isFollower();
    }

    public CBCANSparkMax setOutputRange(double min, double max) {
        pidController.setOutputRange(min,max);
        return this;
    }

    public CBCANSparkMax setPIDConstants(double p, double i, double d) {
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);
        return this;
    }

    public CBCANSparkMax setPIDConstants(double p, double i, double d, double f) {
        setPIDConstants(p,i,d);
        pidController.setFF(f);
        return this;
    }

    public CBCANSparkMax close() {
        return this;
    }

    // return special items.
    public CANEncoder getEncoder() {
        return new CANEncoder(controller);
    }

    public CANPIDController getPIDController() {
        return new CANPIDController(controller);
    }

    public CANDigitalInput getForwardLimitSwitch(CANDigitalInput.LimitSwitchPolarity polarity) {
        return new CANDigitalInput(controller, CANDigitalInput.LimitSwitch.kForward, polarity);
    }

    public CANDigitalInput getReverseLimitSwitch(CANDigitalInput.LimitSwitchPolarity polarity) {
        return new CANDigitalInput(controller, CANDigitalInput.LimitSwitch.kReverse, polarity);
    }


    public CBCANSparkMax setSmartCurrentLimit(int limit) {
        canError = controller.setSmartCurrentLimit(limit, 0, 20000);
        return this;
    }

    public CBCANSparkMax setSmartCurrentLimit(int stallLimit, int freeLimit) {
        canError = controller.setSmartCurrentLimit(stallLimit, freeLimit, 20000);
        return this;
    }

    public CBCANSparkMax setSmartCurrentLimit(int stallLimit, int freeLimit, int limitRPM) {
        canError = controller.setSmartCurrentLimit(stallLimit, freeLimit, limitRPM);
        return this;
    }

    public CBCANSparkMax setSecondaryCurrentLimit(double limit) {
        canError = controller.setSecondaryCurrentLimit(limit);
        return this;
    }

    public CBCANSparkMax setSecondaryCurrentLimit(double limit, int chopCycles) {
        canError = controller.setSecondaryCurrentLimit(limit,chopCycles);
        return this;
    }

    public CBCANSparkMax setIdleMode(CANSparkMax.IdleMode mode) {
        canError = controller.setIdleMode(mode);
        return this;
    }

    public CANSparkMax.IdleMode getIdleMode() {
        return controller.getIdleMode();
    }

    public CBCANSparkMax setRampRate(double rate) {
        canError = controller.setRampRate(rate);
        return this;
    }

    public double getRampRate() {
        return controller.getRampRate();
    }


    public CBCANSparkMax follow(CANSparkMax.ExternalFollower leader, int deviceID) {
        canError = controller.follow(leader, deviceID, false);
        return this;
    }

    public CBCANSparkMax follow(CANSparkMax.ExternalFollower leader, int deviceID, boolean invert) {
        canError = controller.follow(leader, deviceID, invert);
        return this;
    }

    public short getFaults() {
        return controller.getFaults();
    }

    public short getStickyFaults() {
        return controller.getStickyFaults();
    }

    public boolean getFault(CANSparkMax.FaultID faultID) {
        return controller.getFault(faultID);
    }

    public boolean getStickyFault(CANSparkMax.FaultID faultID) {
        return controller.getStickyFault(faultID);
    }

    public double getBusVoltage() {
        return controller.getBusVoltage();
    }

    public double getAppliedOutput() {
        return controller.getAppliedOutput();
    }


    public CBCANSparkMax clearFaults() {
        canError = controller.clearFaults();
        return this;
    }

    public CBCANSparkMax burnFlash() {
        canError = controller.burnFlash();
        return this;
    }

    public CBCANSparkMax setCANTimeout(int milliseconds) {
        controller.setCANTimeout(milliseconds);
        return this;
    }

    public static class ExternalFollower {
        private final int arbId;
        private final int configId;
        public static final CANSparkMax.ExternalFollower kFollowerDisabled = new CANSparkMax.ExternalFollower(0, 0);
        public static final CANSparkMax.ExternalFollower kFollowerSparkMax = new CANSparkMax.ExternalFollower(33888256, 26);
        public static final CANSparkMax.ExternalFollower kFollowerPhoenix = new CANSparkMax.ExternalFollower(33821696, 27);

        public ExternalFollower(int arbId, int configId) {
            this.arbId = arbId;
            this.configId = configId;
        }
    }

    public static enum FaultID {
        kBrownout(0),
        kOvercurrent(1),
        kOvervoltage(2),
        kMotorFault(3),
        kSensorFault(4),
        kStall(5),
        kEEPROMCRC(6),
        kCANTX(7),
        kCANRX(8),
        kHasReset(9),
        kDRVFault(10),
        kOtherFault(11),
        kSoftLimitFwd(12),
        kSoftLimitRev(13),
        kHardLimitFwd(14),
        kHardLimitRev(15);

        public final int value;

        private FaultID(int value) {
            this.value = value;
        }
    }

    public static enum InputMode {
        kPWM(0),
        kCAN(1);

        public final int value;

        private InputMode(int value) {
            this.value = value;
        }

        public static CBCANSparkMax.InputMode fromId(int id) {
            return id == 1 ? kCAN : kPWM;
        }
    }

    public static enum IdleMode {
        kCoast(0),
        kBrake(1);

        public final int value;

        private IdleMode(int value) {
            this.value = value;
        }

        public static CBCANSparkMax.IdleMode fromId(int id) {
            return id == 1 ? kBrake : kCoast;
        }
    }

    public static enum SensorType {
        kNoSensor(0),
        kHallSensor(1),
        kEncoder(2);

        public final int value;

        private SensorType(int value) {
            this.value = value;
        }

        public static CBCANSparkMax.SensorType fromId(int id) {
            switch(id) {
                case 1:
                    return kHallSensor;
                case 2:
                    return kEncoder;
                default:
                    return kNoSensor;
            }
        }
    }
}
