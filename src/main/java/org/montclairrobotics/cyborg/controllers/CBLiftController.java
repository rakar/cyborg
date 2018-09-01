package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.assemblies.CBSpeedControllerArrayController;
import org.montclairrobotics.cyborg.data.CBLiftControlData;
import org.montclairrobotics.cyborg.devices.CBDeviceID;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.utils.CBErrorCorrection;
import org.montclairrobotics.cyborg.utils.CBStateMachine;

/**
 * Implements a Lift or "Back and Forth" controller which
 * would typically be linear, but could also be rotational.
 * For example one or two lifts might be used to control a set
 * of jaws. The Lift travels between a conceptual "bottom" and "top".
 *
 * If an encoder exists, up is assumed to have higher distance values.
 *
 * Limit switches can be present at the bottom and/or top.
 *
 * Encoder Limits may also be set. If one or more of these are set
 * they will act as soft limits and will be used as encoder reset values
 * when either limit switch is activated.
 *
 * Margins or slow zones may also be set if an encoder is configured.
 * If the lift is below the bottom margin height and moving downward,
 * it will revert to the "slow down" speed or power. Likewise if the
 * lift is above the top margin and moving upward, it will revert to the
 * "slow up" speed or power.
 *
 * When an encoder is used, it's value is assumed to be incorrect
 * until the bottom limit switch is triggered or the top limit
 * switch has been triggered and there is a top encoder limit to
 * provide a top limit encoder value.
 * When the bottom limit switch is triggered the encoder is reset to 0
 * or if a bottom encoder limit is active, the encoder is set to that
 * value and then the encoder is considered valid.
 *
 * All limits, margins, and targets use the encoder
 * getDistance values.
 *
 * The lift controller must be attached to a CBLiftControlData object
 * using the setControlData method. This data object contains all of the soft
 * limits, margins, and targets, as well as the normal and slow speed values,
 * and the up/down request values. These up and down request values
 * are used to control the lift.
 *
 * Additionally, if the target is set active
 * the lift will move to the desired height,
 * but will be overridden by the
 * up/down requests.
 */
public class CBLiftController extends CBRobotController {


    // internal
    boolean goUp;
    boolean goDown;
    boolean topLimit;
    boolean bottomLimit;
    boolean encoderClean;
    CBLiftStateMachine sm;
    CBLiftControlData cd;

    // external
    CBErrorCorrection errorCorrection;
    CBDigitalInput topLimitSwitch;
    CBDigitalInput bottomLimitSwitch;
    CBEncoder encoder;
    CBSpeedControllerArrayController speedControllerArray;

    enum CBLiftControlStates {Start, Idle, AtTop, AtBottom, DownSlow, UpSlow, DownNorm, UpNorm};

    private class CBLiftStateMachine extends CBStateMachine<CBLiftControlStates> {
        int cycleCheck = 0;

        public CBLiftStateMachine() {
            super(CBLiftControlStates.Start);
            setLoopMode(CBStateMachineLoopMode.Looping);
        }

        @Override
        public void calcNextState() {
            //SmartDashboard.putString("calc Next State:", currentState.name());

            cycleCheck++;

            switch (currentState) {
                case Start:
                    nextState = CBLiftControlStates.Idle;
                    break;
                case Idle:
                    if (topLimit) {
                        nextState = CBLiftControlStates.AtTop;
                    } else if (bottomLimit) {
                        nextState = CBLiftControlStates.AtBottom;
                    } else if (goDown) {
                        nextState = CBLiftControlStates.DownSlow;
                    } else if (goUp) {
                        nextState = CBLiftControlStates.UpSlow;
                    }
                    break;
                case AtTop:
                    if (goDown) nextState = CBLiftControlStates.DownSlow;
                    break;
                case AtBottom:
                    if (goUp) nextState = CBLiftControlStates.UpSlow;
                    break;
                case DownSlow:
                    if (!goDown) nextState = CBLiftControlStates.Idle;
                    else if (goDown
                            // there is no bottom margin, so just do it normally
                            && (!cd.bottomMargin.isActive()
                            // or (there is one
                            // and the encoder is clean
                            // and we are above the margin)
                            || (cd.bottomMargin.isActive()
                            && encoderClean
                            && cd.bottomMargin.isAboveTarget()))) {
                        nextState = CBLiftControlStates.DownNorm;
                    }
                    break;
                case UpSlow:
                    if (!goUp) nextState = CBLiftControlStates.Idle;
                    else if (goUp
                            // there is no top margin, so just do it normally
                            && (!cd.topMargin.isActive()
                            // or (there is one
                            // and the encoder is clean
                            // and we are below the margin
                            || (cd.topMargin.isActive()
                            && encoderClean
                            && cd.topMargin.isBelowTarget()))) {
                        nextState = CBLiftControlStates.UpNorm;
                    }
                    break;
                case DownNorm:
                    if (!goDown) nextState = CBLiftControlStates.Idle;
                    else if (goDown
                            // (there is bottom margin
                            // and the encoder is clean
                            // and we are below the margin
                            && (cd.bottomMargin.isActive()
                            && encoderClean
                            && cd.bottomMargin.isBelowTarget())) {
                        nextState = CBLiftControlStates.DownSlow;
                    }
                    break;
                case UpNorm:
                    if (!goUp) nextState = CBLiftControlStates.Idle;
                    else if (goUp
                            // (there is top margin
                            // and the encoder is clean
                            // and we are above the margin
                            && (cd.topMargin.isActive()
                            && encoderClean
                            && cd.topMargin.isAboveTarget())) {
                        nextState = CBLiftControlStates.DownSlow;
                    }
                    break;
            }
        }

        @Override
        public void doTransition() {
            if(isTransitionFrom(CBLiftControlStates.Start)) {
                encoderClean = false;
                // if there are no limit switches, we need to assume
                // that the lift is in initial conditions
                if (topLimitSwitch==null && bottomLimitSwitch==null) {
                    encoder.reset();
                    encoderClean = true;
                }
            }
        }

        @Override
        protected void doCurrentState() {
            //SmartDashboard.putString("do Current State:", currentState.name());
            switch (currentState) {
                case Start:
                    break;
                case Idle:
                    speedControllerArray.update(0); // full stop
                    break;
                case AtTop:
                    if (encoder != null) {
                        if (cd.topEncoderLimit.isActive()) {
                            encoder.setDistance(cd.topEncoderLimit.getXPosition());
                            encoderClean = true;
                        }
                    }
                    speedControllerArray.update(0); // full stop
                    break;
                case AtBottom:
                    if(encoder!=null)  {
                        encoder.reset();
                        encoderClean = true;
                        if (cd.bottomEncoderLimit.isActive()) {
                            encoder.setDistance(cd.bottomEncoderLimit.getXPosition());
                        }
                    }
                    speedControllerArray.update(0); // full stop
                    break;
                case DownSlow: {
                    double speed = 0;
                    if (cd.requestDown) {
                        speed = cd.slowDown;
                    } else if (cd.target.isActive()) {
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
                    if (cd.requestUp) {
                        speed = cd.slowUp;
                    } else if (cd.target.isActive()) {
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
                    if (cd.requestDown) {
                        speed = cd.normDown;
                    } else if (cd.target.isActive()) {
                        speed = errorCorrection.getOut();
                    }
                    speedControllerArray.update(speed);
                }
                break;
                case UpNorm: {
                    double speed = 0;
                    if (cd.requestUp) {
                        speed = cd.normUp;
                    } else if (cd.target.isActive()) {
                        speed = errorCorrection.getOut();
                    }
                    speedControllerArray.update(speed);
                }
                break;
            }
        }
    }

    public CBLiftController(Cyborg robot) {
        super(robot);
        sm = new CBLiftStateMachine();
    }

    public CBLiftController setData(CBLiftControlData data) {
        cd = data;
        return this;
    }

    public CBLiftController setSpeedControllerArray(CBSpeedControllerArrayController array) {
        speedControllerArray = array;
        return this;
    }

    public CBLiftController setTopLimit(CBDeviceID limitID) {
        this.topLimitSwitch = Cyborg.hardwareAdapter.getDigitalInput(limitID);
        return this;
    }

    public CBLiftController setTopLimit(CBDigitalInput limit) {
        this.topLimitSwitch = limit;
        return this;
    }

    public CBLiftController setBottomLimit(CBDeviceID limitID) {
        this.bottomLimitSwitch = Cyborg.hardwareAdapter.getDigitalInput(limitID);
        return this;
    }

    public CBLiftController setBottomLimit(CBDigitalInput limit) {
        this.bottomLimitSwitch = limit;
        return this;
    }

    public CBLiftController setEncoder(CBDeviceID deviceID) {
        this.encoder = Cyborg.hardwareAdapter.getEncoder(deviceID);
        return this;
    }

    public CBLiftController setEncoder(CBEncoder device) {
        this.encoder = device;
        return this;
    }

    public CBLiftController setErrorCorrection(CBErrorCorrection errorCorrection) {
        this.errorCorrection = errorCorrection;
        return this;
    }

    @Override
    public void update() {
        //SmartDashboard.putString("LIft controller update", "Hi");
        if (cd!=null) {
            double liftHeight = encoder.getDistance();
            cd.topEncoderLimit.update(liftHeight);
            cd.topMargin.update(liftHeight);
            cd.bottomMargin.update(liftHeight);
            cd.bottomEncoderLimit.update(liftHeight);
            cd.target.update(liftHeight);

            //SmartDashboard.putString("LIft controller update", "not null");
            //if(bottomLimitSwitch==null) {
            //    SmartDashboard.putBoolean("encoderClean", encoderClean);
            //}

            topLimit = (topLimitSwitch != null && topLimitSwitch.get())
                    || (encoder != null && cd.topEncoderLimit.isActive() && encoderClean && cd.topEncoderLimit.isAboveTarget());
            bottomLimit = (bottomLimitSwitch != null && bottomLimitSwitch.get())
                    || (encoder != null && cd.bottomEncoderLimit.isActive() && encoderClean && cd.bottomEncoderLimit.isBelowTarget());
            goUp = !topLimit && (cd.requestUp || (encoder != null && encoderClean && cd.target.isBelowTarget()));
            goDown = !bottomLimit && (cd.requestDown || (encoder != null && encoderClean && cd.target.isAboveTarget()));
            if (errorCorrection != null) {
                if (cd.target.isActive()) {
                    errorCorrection.setTarget(cd.target.getXTarget());
                    errorCorrection.update(liftHeight);
                }
            }
            sm.update();
        } else {
            System.err.println("\nCBLiftController cd is null...");
        }
    }
}
