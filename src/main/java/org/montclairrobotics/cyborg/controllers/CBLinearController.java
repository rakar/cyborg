package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTarget1D;

public class CBLinearController extends CBRobotController {


    // internal
    boolean goUp;
    boolean goDown;
    boolean topLimit;
    boolean bottomLimit;
    boolean encoderClean;
    CBLinearStateMachine sm;
    CBLinearControlData cd;

    // external
    CBErrorCorrection errorCorrection;
    // These input devices might be a violation of the isolation rules!
    // Ponder that.
    // I'm thinking we never want to lie about this stuff. So yes controllers
    // can speak to hardware.
    CBDigitalInput topLimitSwitch;
    CBDigitalInput bottomLimitSwitch;
    CBEncoder encoder;
    CBSpeedControllerArrayController speedControllerArray;


    public class CBLinearControlData {
        public boolean RequestUp;
        public boolean RequestDown;
        public CBTarget1D Target;
        public CBTarget1D TopMargin;
        public CBTarget1D BottomMargin;
        public CBTarget1D TopEncoderLimit;
        public CBTarget1D BottomEncoderLimit;
        public double slowUp;
        public double slowDown;
        public double normUp;
        public double normDown;
    }

    enum CBLinearControlStates {Start, Idle, AtTop, AtBottom, DownSlow, UpSlow, DownNorm, UpNorm}

    ;

    private class CBLinearStateMachine extends CBStateMachine<CBLinearControlStates> {
        int cycleCheck = 0;

        public CBLinearStateMachine() {
            super(CBLinearControlStates.Start);
            setLoopMode(CBStateMachineLoopMode.Looping);
        }

        @Override
        public void calcNextState() {

            cycleCheck++;

            switch (currentState) {
                case Start:
                    nextState = CBLinearControlStates.Idle;
                    break;
                case Idle:
                    if (topLimit) {
                        nextState = CBLinearControlStates.AtTop;
                    } else if (bottomLimit) {
                        nextState = CBLinearControlStates.AtBottom;
                    } else if (goDown) {
                        nextState = CBLinearControlStates.DownSlow;
                    } else if (goUp) {
                        nextState = CBLinearControlStates.UpSlow;
                    }
                    break;
                case AtTop:
                    if (goDown) nextState = CBLinearControlStates.DownSlow;
                    break;
                case AtBottom:
                    if (goUp) nextState = CBLinearControlStates.UpSlow;
                    break;
                case DownSlow:
                    if (!goDown) nextState = CBLinearControlStates.Idle;
                    else if (goDown
                            // there is no bottom margin, so just do it normally
                            && (!cd.BottomMargin.isActive()
                            // or (there is one
                            // and the encoder is clean
                            // and we are above the margin
                            || (cd.BottomMargin.isActive()
                            && encoderClean
                            && cd.BottomMargin.isAboveTarget(encoder.getDistance())))) {
                        nextState = CBLinearControlStates.DownNorm;
                    }
                    break;
                case UpSlow:
                    if (!goUp) nextState = CBLinearControlStates.Idle;
                    else if (goUp
                            // there is no top margin, so just do it normally
                            && (!cd.TopMargin.isActive()
                            // or (there is one
                            // and the encoder is clean
                            // and we are below the margin
                            || (cd.TopMargin.isActive()
                            && encoderClean
                            && cd.TopMargin.isBelowTarget(encoder.getDistance())))) {
                        nextState = CBLinearControlStates.UpNorm;
                    }
                    break;
                case DownNorm:
                    if (!goDown) nextState = CBLinearControlStates.Idle;
                    else if (goDown
                            // (there is bottom margin
                            // and the encoder is clean
                            // and we are below the margin
                            && (cd.BottomMargin.isActive()
                            && encoderClean
                            && cd.BottomMargin.isBelowTarget(encoder.getDistance()))) {
                        nextState = CBLinearControlStates.DownSlow;
                    }
                    break;
                case UpNorm:
                    if (!goUp) nextState = CBLinearControlStates.Idle;
                    else if (goUp
                            // (there is top margin
                            // and the encoder is clean
                            // and we are above the margin
                            && (cd.TopMargin.isActive()
                            && encoderClean
                            && cd.TopMargin.isAboveTarget(encoder.getDistance()))) {
                        nextState = CBLinearControlStates.DownSlow;
                    }
                    break;
            }
        }

        @Override
        public void doTransition() {
        }

        @Override
        protected void doCurrentState() {
            switch (currentState) {
                case Start:
                    // be really explicit
                    encoderClean = false;
                    break;
                case Idle:
                    speedControllerArray.update(0); // full stop
                    break;
                case AtTop:
                    if (!encoderClean && encoder != null && cd.TopEncoderLimit.isActive()) {
                        encoder.setDistance(cd.TopEncoderLimit.getXPosition());
                        encoderClean = true;
                    }
                    speedControllerArray.update(0); // full stop
                    break;
                case AtBottom:
                    if (!encoderClean && encoder != null && cd.BottomEncoderLimit.isActive()) {
                        encoder.setDistance(cd.BottomEncoderLimit.getXPosition());
                        encoderClean = true;
                    }
                    speedControllerArray.update(0); // full stop
                    break;
                case DownSlow: {
                    double speed = 0;
                    if (cd.RequestDown) {
                        speed = cd.slowDown;
                    } else if (cd.Target.isActive()) {
                        speed = errorCorrection.getOut();
                        if (Math.abs(speed) > Math.abs(cd.slowDown)) {
                            speed = cd.slowDown;
                        }
                    }
                    speedControllerArray.update(speed);
                }
                break;
                case UpSlow: {
                    double speed = 0;
                    if (cd.RequestUp) {
                        speed = cd.slowUp;
                    } else if (cd.Target.isActive()) {
                        speed = errorCorrection.getOut();
                        if (Math.abs(speed) > Math.abs(cd.slowUp)) {
                            speed = cd.slowUp;
                        }
                    }
                    speedControllerArray.update(speed);
                }
                break;
                case DownNorm: {
                    double speed = 0;
                    if (cd.RequestDown) {
                        speed = cd.normDown;
                    } else if (cd.Target.isActive()) {
                        speed = errorCorrection.getOut();
                    }
                    speedControllerArray.update(speed);
                }
                break;
                case UpNorm: {
                    double speed = 0;
                    if (cd.RequestUp) {
                        speed = cd.normUp;
                    } else if (cd.Target.isActive()) {
                        speed = errorCorrection.getOut();
                    }
                    speedControllerArray.update(speed);
                }
                break;
            }
        }
    }

    public CBLinearController(Cyborg robot) {
        super(robot);
        sm = new CBLinearStateMachine();
    }

    public CBLinearController setData(CBLinearControlData data) {
        cd = data;
        return this;
    }

    public CBLinearController setSpeedControllerArray(CBSpeedControllerArrayController array) {
        speedControllerArray = array;
        return this;
    }

    public CBLinearController setTopLimit(CBDeviceID limitID) {
        this.topLimitSwitch = Cyborg.hardwareAdapter.getDigitalInput(limitID);
        return this;
    }

    public CBLinearController setTopLimit(CBDigitalInput limit) {
        this.topLimitSwitch = limit;
        return this;
    }

    public CBLinearController setBottomLimit(CBDeviceID limitID) {
        this.bottomLimitSwitch = Cyborg.hardwareAdapter.getDigitalInput(limitID);
        return this;
    }

    public CBLinearController setBottomLimit(CBDigitalInput limit) {
        this.bottomLimitSwitch = limit;
        return this;
    }

    public CBLinearController setEncoder(CBDeviceID deviceID) {
        this.encoder = Cyborg.hardwareAdapter.getEncoder(deviceID);
        return this;
    }

    public CBLinearController setEncoder(CBEncoder device) {
        this.encoder = device;
        return this;
    }

    public CBLinearController setErrorCorrection(CBErrorCorrection errorCorrection) {
        this.errorCorrection = errorCorrection;
        return this;
    }

    public void update() {
        topLimit = (topLimitSwitch != null && topLimitSwitch.get()) || (cd.TopEncoderLimit.isActive() && encoderClean && cd.TopEncoderLimit.isAboveTarget(encoder.getDistance()));
        bottomLimit = (bottomLimitSwitch != null && bottomLimitSwitch.get()) || (cd.BottomEncoderLimit.isActive() && encoderClean && encoder.getDistance() <= cd.BottomEncoderLimit.getXPosition());
        goUp = !topLimit && (cd.RequestUp || (encoderClean && cd.Target.isBelowTarget(encoder.getDistance())));
        goDown = !bottomLimit && (cd.RequestDown || (encoderClean && cd.Target.isAboveTarget(encoder.getDistance())));
        if (errorCorrection != null) {
            if (cd.Target.isActive()) {
                errorCorrection.setTarget(cd.Target.getXPosition());
                errorCorrection.update(encoder.getDistance());
            }
        }
        sm.update();
    }
}
