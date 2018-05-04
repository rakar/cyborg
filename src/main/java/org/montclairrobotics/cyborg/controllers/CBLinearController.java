package org.montclairrobotics.cyborg.controllers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBDigitalInput;
import org.montclairrobotics.cyborg.devices.CBEncoder;
import org.montclairrobotics.cyborg.utils.CBStateMachine;
import org.montclairrobotics.cyborg.utils.CBTarget1D;

public class CBLinearController extends CBRobotController {


    boolean goUp;
    boolean goDown;
    boolean topLimit;
    boolean bottomLimit;
    boolean encoderClean;


    // These input devices might be a violation of the isolation rules!
    // Ponder that.
    // I'm thinking we never want to lie about this stuff. So yes controllers
    // can speak to hardware.
    CBDigitalInput topLimitSwitch;
    CBDigitalInput bottomLimitSwitch;
    CBEncoder encoder;


    CBLinearStateMachine sm;
    CBLinearControlData cd;


    public class CBLinearControlData {
        public boolean RequestUp;
        public boolean RequestDown;
        public CBTarget1D Target;
        public CBTarget1D TopMargin;
        public CBTarget1D BottomMargin;
        public CBTarget1D TopEncoderLimit;
        public CBTarget1D BottomEncoderLimit;
    }
    enum CBLinearControlStates {Start, Idle, AtTop, AtBottom, DownSlow, UpSlow, DownNorm, UpNorm};
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
                        nextState=CBLinearControlStates.AtTop;
                    } else if(bottomLimit) {
                        nextState = CBLinearControlStates.AtBottom;
                    } else if (goDown && !topLimit) {
                        nextState = CBLinearControlStates.DownSlow;
                    } else if (goUp && !bottomLimit) {
                        nextState = CBLinearControlStates.UpSlow;
                    }
                    break;
                case AtTop:
                    if(goDown) nextState = CBLinearControlStates.DownSlow;
                    break;
                case AtBottom:
                    if(goUp) nextState=CBLinearControlStates.UpSlow;
                    break;
                case DownSlow:
                    if(!goDown) nextState = CBLinearControlStates.Idle;
                    else if(goDown
                            // there is no bottom margin, so just do it normally
                            && (!cd.BottomMargin.isActive()
                                // or (there is one
                                // and the encoder is clean
                                // and we are above the margin
                                || (!cd.BottomMargin.isActive()
                                    && encoderClean
                                    && encoder.getDistance()>cd.BottomMargin.getXPosition()))) {
                        nextState = CBLinearControlStates.DownNorm;
                    }
                    break;
                case UpSlow:
                    if(!goUp) nextState = CBLinearControlStates.Idle;
                    else if(goUp
                            // there is no top margin, so just do it normally
                            && (!cd.TopMargin.isActive()
                                // or (there is one
                                // and the encoder is clean
                                // and we are below the margin
                                || (!cd.TopMargin.isActive()
                                    && encoderClean
                                    && encoder.getDistance()<cd.TopMargin.getXPosition()))) {
                        nextState = CBLinearControlStates.UpNorm;
                    }
                    break;
                case DownNorm:
                    if(!goDown) nextState = CBLinearControlStates.Idle;
                    else if(goDown
                            // (there is bottom margin
                            // and the encoder is clean
                            // and we are above the margin
                            && (cd.BottomMargin.isActive()
                            && encoder.getDistance()<cd.BottomMargin.getXPosition())) {
                        nextState = CBLinearControlStates.DownNorm;
                    }
                    break;
                case UpNorm:
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
                    break;
                case AtTop:
                    if(!encoderClean && encoder!=null && cd.TopEncoderLimit.isActive() ) {
                        encoder.setDistance(cd.TopEncoderLimit.getXPosition());
                        encoderClean = true;
                    }
                    break;
                case AtBottom:
                    if(!encoderClean && encoder!=null && cd.BottomEncoderLimit.isActive() ) {
                        encoder.setDistance(cd.BottomEncoderLimit.getXPosition());
                        encoderClean = true;
                    }
                    break;
                case DownSlow:
                    break;
                case UpSlow:
                    break;
                case DownNorm:
                    break;
                case UpNorm:
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

    public void update() {
        topLimit = topLimitSwitch.get() || (cd.TopEncoderLimit.isActive() && encoderClean && encoder.getDistance()>=cd.TopEncoderLimit.getXPosition());
        bottomLimit = bottomLimitSwitch.get() || (cd.BottomEncoderLimit.isActive() && encoderClean && encoder.getDistance()<=cd.BottomEncoderLimit.getXPosition());
        goUp = !topLimit && (cd.RequestUp || (encoderClean && cd.Target.isBelowTarget(encoder.getDistance())));
        goDown = !bottomLimit && (cd.RequestDown || (encoderClean && cd.Target.isAboveTarget(encoder.getDistance())));

        sm.update();
    }
}
