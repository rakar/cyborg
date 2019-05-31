package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.core.utils.CBEnums;

public class CBTalonSRX extends CBSmartSpeedController {
    TalonSRX controller;
    int canChannel;
    ErrorCode errorCode;
    ControlMode localControlMode;


    public CBTalonSRX(int canChannel) {
        this.controller = new TalonSRX(canChannel);
        this.canChannel = canChannel;
        setLocalControlMode();
    }

    private void setLocalControlMode() {
        com.ctre.phoenix.motorcontrol.ControlMode ct = null;
        switch (controlMode) {
            case DUTYCYCLE:
            case VOLTAGE:
                break;
            case POSITION:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.Position;
                break;
            case VELOCITY:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.Velocity;
                break;
            case NONE:
            case PERCENTAGEOUTPUT:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;
                break;
            case CURRENT:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.Current;
                break;
            case DISABLED:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.Disabled;
                break;
            case FOLLOWER:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.Follower;
                break;
            case MOTIONMAGIC:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.MotionMagic;
                break;
            case MOTIONPROFILE:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.MotionProfile;
                break;
            case MOTIONPROFILEARC:
                ct = com.ctre.phoenix.motorcontrol.ControlMode.MotionProfileArc;
                break;
        }
        localControlMode = ct;
    }


    @Override
    public CBTalonSRX pidWrite(double output) {
        controller.set(localControlMode, output);
        return this;
    }

    @Override
    public double get() {
        return controller.getMotorOutputPercent();
    }

    @Override
    public CBTalonSRX set(double speed) {
        controller.set(localControlMode, speed);
        if(debug) {
            Cyborg.hardwareAdapter.robot.logMessage("CBTalonSRX: set: "+Double.toString(speed));
        }
        return this;
    }

    /**
     * Sets the appropriate output on the controller, depending on the mode.
     * <p>
     * In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as
     * stopped. In Voltage mode, output value is in volts. In Current mode,
     * output value is in amperes. In Speed mode, output value is in position
     * change / 100ms. In Position mode, output value is in encoder ticks or an
     * analog value, depending on the sensor. In Follower mode, the output value
     * is the integer device ID of the controller to duplicate.
     *
     * @param speed The setpoint value, as described above.
     * @param ctrl
     *                    //@see #SelectProfileSlot to choose between the two sets of gains.
     */
    public CBTalonSRX set(double speed, CBEnums.CBMotorControlMode ctrl) {
        setControlMode(ctrl);
        set(speed);
        return this;
    }

    public CBTalonSRX setControlMode(CBEnums.CBMotorControlMode ctrl) {
        controlMode = ctrl;
        setLocalControlMode();
        return this;
    }

    @Override
    public CBTalonSRX setInverted(boolean isInverted) {
        controller.setInverted(isInverted);
        return this;
    }

    @Override
    public boolean getInverted() {
        return controller.getInverted();
    }

    @Override
    public CBTalonSRX disable() {
        controller.neutralOutput();
        return this;
    }

    @Override
    public CBTalonSRX stopMotor() {
        controller.neutralOutput();
        return this;
    }

    public double getOutputVoltage() {
        return controller.getMotorOutputVoltage();
    }

    public double getOutputCurrent() {
        return controller.getOutputCurrent();
    }

    public double getTemperature() {
        return controller.getTemperature();
    }

    public CBTalonSRX follow(CBDeviceID master) {
        controller.follow(Cyborg.hardwareAdapter.getTalonSRX(master).controller);
        return this;
    }

    public CBTalonSRX follow(CBDeviceID master, boolean inverted) {
        controller.follow(Cyborg.hardwareAdapter.getTalonSRX(master).controller);
        if(!inverted)
            controller.setInverted(InvertType.FollowMaster);
        else
            controller.setInverted(InvertType.OpposeMaster);
        return this;
    }





    public CBTalonSRX setDeviceName(String name) {
        setName(name);
        return this;
    }

    public CBTalonSRX setDeviceName(String subsystem, String name) {
        setName(subsystem, name);
        return this;
    }

    @Override
    public String getName() {
        String name = super.getName();
        if (name == "") {
            return "CAN:" + Integer.toString(canChannel) + " PDB:" + Integer.toString(pdbChannel);
        } else {
            return name;
        }
    }

    public CBTalonSRX setStatusFramePeriod(StatusFrameEnhanced frame, int periodMs, int timeoutMs) {
        errorCode =  controller.setStatusFramePeriod(frame, periodMs, timeoutMs);
        return this;
    }

    public int getStatusFramePeriod(StatusFrameEnhanced frame, int timeoutMs) {
        return controller.getStatusFramePeriod(frame, timeoutMs);
    }

    public CBTalonSRX configVelocityMeasurementPeriod(VelocityMeasPeriod period, int timeoutMs) {
        errorCode =  controller.configVelocityMeasurementPeriod(period, timeoutMs);
        return this;
    }

    public CBTalonSRX configVelocityMeasurementWindow(int windowSize, int timeoutMs) {
        errorCode = controller.configVelocityMeasurementWindow(windowSize, timeoutMs);
        return this;
    }

    public CBTalonSRX configForwardLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int timeoutMs) {
        errorCode = controller.configForwardLimitSwitchSource(type, normalOpenOrClose, timeoutMs);
        return this;
    }

    public CBTalonSRX configReverseLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int timeoutMs) {
        errorCode = controller.configReverseLimitSwitchSource(type, normalOpenOrClose, timeoutMs);
        return this;
    }

    /**
     * Configures the peak current limit of the motor controller.
     *
     * @param amps      Peak current limit (in amps).
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configPeakCurrentLimit(int amps, int timeoutMs) {
        errorCode = controller.configPeakCurrentLimit(amps, timeoutMs);
        return this;
    }

    /**
     * Configures the maximum time allowed at peak current limit of the motor
     * controller.
     *
     * @param milliseconds Maximum time allowed at peak current limit (in milliseconds).
     * @param timeoutMs    Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configPeakCurrentDuration(int milliseconds, int timeoutMs) {
        errorCode = controller.configPeakCurrentDuration(milliseconds, timeoutMs);
        return this;
    }

    /**
     * Configures the continuous current limit.
     *
     * @param amps      Continuous Current Limit.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configContinuousCurrentLimit(int amps, int timeoutMs) {
        errorCode = controller.configContinuousCurrentLimit(amps, timeoutMs);
        return this;
    }

    /**
     * Enables the current limit feature.
     *
     * @param enable Enable state of current limit.
     **/
    public CBTalonSRX enableCurrentLimit(boolean enable) {
        controller.enableCurrentLimit(enable);
        return this;
    }

    public long getHandle() {
        return controller.getHandle();
    }

    /**
     * Returns the Device ID
     *
     * @return Device number.
     */
    public int getDeviceID() {
        return controller.getDeviceID();
    }

    /**
     * Sets the appropriate output on the controller, depending on the mode.
     * <p>
     * In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as
     * stopped. In Voltage mode, output value is in volts. In Current mode,
     * output value is in amperes. In Speed mode, output value is in position
     * change / 100ms. In Position mode, output value is in encoder ticks or an
     * analog value, depending on the sensor. In Follower mode, the output value
     * is the integer device ID of the controller to duplicate.
     *
     * @param mode
     * @param outputValue The setpoint value, as described above.
     *                    //@see #SelectProfileSlot to choose between the two sets of gains.
     */
    //public CBTalonSRX set(CBMotorControlMode mode, double outputValue) {
    //    controller.set(mode, outputValue);
    //    return this;
    //}

    //@Deprecated
    //public void set(CBMotorControlMode mode, double demand0, double demand1) {
    //    controller.set(mode, demand0, demand1);
    //}

    /**
     * Neutral the motor output by setting control mode to disabled.
     */
    public CBTalonSRX neutralOutput() {
        controller.neutralOutput();
        return this;
    }

    /**
     * Sets the mode of operation during neutral throttle output.
     *
     * @param neutralMode The desired mode of operation when the Controller output
     *                    throttle is neutral (ie brake/coast)
     **/
    public CBTalonSRX setNeutralMode(NeutralMode neutralMode) {
        controller.setNeutralMode(neutralMode);
        return this;
    }

    public CBTalonSRX enableHeadingHold(boolean enable) {
        controller.enableHeadingHold(enable);
        return this;
    }

    public CBTalonSRX selectDemandType(boolean value) {
        controller.selectDemandType(value);
        return this;
    }

    /**
     * Sets the phase of the sensor. Use when controller forward/reverse output
     * doesn't correlate to appropriate forward/reverse reading of sensor.
     *
     * @param PhaseSensor Indicates whether to invert the phase of the sensor.
     **/
    public CBTalonSRX setSensorPhase(boolean PhaseSensor) {
        controller.setSensorPhase(PhaseSensor);
        return this;
    }

    /**
     * Configures the open-loop ramp rate of throttle output.
     *
     * @param secondsFromNeutralToFull Minimum desired time to go from neutral to full throttle. A
     *                                 value of '0' will disable the ramp.
     * @param timeoutMs                Timeout value in ms. Function will generate error if config is
     *                                 not successful within timeout.
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configOpenloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
        errorCode = controller.configOpenloopRamp(secondsFromNeutralToFull, timeoutMs);
        return this;
    }

    /**
     * Configures the closed-loop ramp rate of throttle output.
     *
     * @param secondsFromNeutralToFull Minimum desired time to go from neutral to full throttle. A
     *                                 value of '0' will disable the ramp.
     * @param timeoutMs                Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configClosedloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
        errorCode = controller.configClosedloopRamp(secondsFromNeutralToFull, timeoutMs);
        return this;
    }

    /**
     * Configures the forward peak output percentage.
     *
     * @param percentOut Desired peak output percentage.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configPeakOutputForward(double percentOut, int timeoutMs) {
        errorCode = controller.configPeakOutputForward(percentOut, timeoutMs);
        return this;
    }

    /**
     * Configures the reverse peak output percentage.
     *
     * @param percentOut Desired peak output percentage.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configPeakOutputReverse(double percentOut, int timeoutMs) {
        errorCode = controller.configPeakOutputReverse(percentOut, timeoutMs);
        return this;
    }

    /**
     * Configures the forward nominal output percentage.
     *
     * @param percentOut Nominal (minimum) percent output.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configNominalOutputForward(double percentOut, int timeoutMs) {
        errorCode = controller.configNominalOutputForward(percentOut, timeoutMs);
        return this;
    }

    /**
     * Configures the reverse nominal output percentage.
     *
     * @param percentOut Nominal (minimum) percent output.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configNominalOutputReverse(double percentOut, int timeoutMs) {
        errorCode = controller.configNominalOutputReverse(percentOut, timeoutMs);
        return this;
    }

    /**
     * Configures the output deadband percentage.
     *
     * @param percentDeadband Desired deadband percentage. Minimum is 0.1%, Maximum is 25%.
     *                        Pass 0.04 for 4%.
     * @param timeoutMs       Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configNeutralDeadband(double percentDeadband, int timeoutMs) {
        errorCode = controller.configNeutralDeadband(percentDeadband, timeoutMs);
        return this;
    }

    /**
     * Configures the Voltage Compensation saturation voltage.
     *
     * @param voltage   TO-DO: Comment me!
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configVoltageCompSaturation(double voltage, int timeoutMs) {
        errorCode = controller.configVoltageCompSaturation(voltage, timeoutMs);
        return this;
    }

    /**
     * Configures the voltage measurement filter.
     *
     * @param filterWindowSamples Number of samples in the rolling average of voltage
     *                            measurement.
     * @param timeoutMs           Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configVoltageMeasurementFilter(int filterWindowSamples, int timeoutMs) {
        errorCode = controller.configVoltageMeasurementFilter(filterWindowSamples, timeoutMs);
        return this;
    }

    /**
     * Enables voltage compensation. If enabled, voltage compensation works in
     * all control modes.
     *
     * @param enable Enable state of voltage compensation.
     **/
    public CBTalonSRX enableVoltageCompensation(boolean enable) {
        controller.enableVoltageCompensation(enable);
        return this;
    }

    /**
     * Gets the bus voltage seen by the motor controller.
     *
     * @return The bus voltage value (in volts).
     */
    public double getBusVoltage() {
        return controller.getBusVoltage();
    }

    /**
     * Gets the output percentage of the motor controller.
     *
     * @return Output of the motor controller (in percent).
     */
    public double getMotorOutputPercent() {
        return controller.getMotorOutputPercent();
    }


    /**
     * Select the remote feedback device for the motor controller.
     *
     * @param feedbackDevice Remote Feedback Device to select.
     * @param pidIdx         0 for Primary closed-loop. 1 for cascaded closed-loop.
     * @param timeoutMs      Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configSelectedFeedbackSensor(RemoteFeedbackDevice feedbackDevice, int pidIdx, int timeoutMs) {
        errorCode = controller.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
        return this;
    }

    /**
     * Select the feedback device for the motor controller.
     *
     * @param feedbackDevice Feedback Device to select.
     * @param pidIdx
     * @param timeoutMs      Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configSelectedFeedbackSensor(FeedbackDevice feedbackDevice, int pidIdx, int timeoutMs) {
        errorCode = controller.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
        return this;
    }

    public CBTalonSRX configRemoteFeedbackFilter(int deviceID, RemoteSensorSource remoteSensorSource, int remoteOrdinal, int timeoutMs) {
        errorCode = controller.configRemoteFeedbackFilter(deviceID, remoteSensorSource, remoteOrdinal, timeoutMs);
        return this;
    }

    public CBTalonSRX configSensorTerm(SensorTerm sensorTerm, FeedbackDevice feedbackDevice, int timeoutMs) {
        errorCode = controller.configSensorTerm(sensorTerm, feedbackDevice, timeoutMs);
        return this;
    }

    /**
     * Get the selected sensor position.
     *
     * @param pidIdx
     * @return Position of selected sensor (in Raw Sensor Units).
     */
    public int getSelectedSensorPosition(int pidIdx) {
        return controller.getSelectedSensorPosition(pidIdx);
    }

    /**
     * Get the selected sensor velocity.
     *
     * @param pidIdx
     * @return Velocity of selected sensor (in Raw Sensor Units per 100 ms).
     */
    public int getSelectedSensorVelocity(int pidIdx) {
        return controller.getSelectedSensorVelocity(pidIdx);
    }

    /**
     * Sets the sensor position to the given value.
     *
     * @param sensorPos Position to set for the selected sensor (in Raw Sensor Units).
     * @param pidIdx
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setSelectedSensorPosition(int sensorPos, int pidIdx, int timeoutMs) {
        errorCode = controller.setSelectedSensorPosition(sensorPos, pidIdx, timeoutMs);
        return this;
    }

    /**
     * Sets the period of the given control frame.
     *
     * @param frame    Frame whose period is to be changed.
     * @param periodMs Period in ms for the given frame.
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setControlFramePeriod(ControlFrame frame, int periodMs) {
        errorCode = controller.setControlFramePeriod(frame, periodMs);
        return this;
    }

    /**
     * Sets the period of the given control frame.
     *
     * @param frame    Frame whose period is to be changed.
     * @param periodMs Period in ms for the given frame.
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setControlFramePeriod(int frame, int periodMs) {
        errorCode = controller.setControlFramePeriod(frame, periodMs);
        return this;
    }

    /**
     * Sets the period of the given status frame.
     *
     * @param frameValue
     * @param periodMs   Period in ms for the given frame.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setStatusFramePeriod(int frameValue, int periodMs, int timeoutMs) {
        errorCode = controller.setStatusFramePeriod(frameValue, periodMs, timeoutMs);
        return this;
    }

    /**
     * Sets the period of the given status frame.
     *
     * @param frame     Frame whose period is to be changed.
     * @param periodMs  Period in ms for the given frame.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setStatusFramePeriod(StatusFrame frame, int periodMs, int timeoutMs) {
        errorCode = controller.setStatusFramePeriod(frame, periodMs, timeoutMs);
        return this;
    }

    /**
     * Gets the period of the given status frame.
     *
     * @param frame     Frame to get the period of.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Period of the given status frame.
     */
    public int getStatusFramePeriod(int frame, int timeoutMs) {
        return controller.getStatusFramePeriod(frame, timeoutMs);
    }

    /**
     * Gets the period of the given status frame.
     *
     * @param frame     Frame to get the period of.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Period of the given status frame.
     */
    public int getStatusFramePeriod(StatusFrame frame, int timeoutMs) {
        return controller.getStatusFramePeriod(frame, timeoutMs);
    }

    /**
     * Configures the forward limit switch for a remote source.
     *
     * @param type              Remote limit switch source. @see #LimitSwitchSource
     * @param normalOpenOrClose Setting for normally open or normally closed.
     * @param deviceID          Device ID of remote source.
     * @param timeoutMs         Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configForwardLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int deviceID, int timeoutMs) {
        errorCode = controller.configForwardLimitSwitchSource(type, normalOpenOrClose, deviceID, timeoutMs);
        return this;
    }

    /**
     * Configures the reverse limit switch for a remote source.
     *
     * @param type              Remote limit switch source. @see #LimitSwitchSource
     * @param normalOpenOrClose Setting for normally open or normally closed.
     * @param deviceID          Device ID of remote source.
     * @param timeoutMs         Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configReverseLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int deviceID, int timeoutMs) {
        errorCode = controller.configReverseLimitSwitchSource(type, normalOpenOrClose, deviceID, timeoutMs);
        return this;
    }

    /**
     * Sets the enable state for limit switches.
     *
     * @param enable Enable state for limit switches.
     **/
    public CBTalonSRX overrideLimitSwitchesEnable(boolean enable) {
        controller.overrideLimitSwitchesEnable(enable);
        return this;
    }

    /**
     * Configures the forward soft limit threhold.
     *
     * @param forwardSensorLimit Forward Sensor Position Limit (in Raw Sensor Units).
     * @param timeoutMs          Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configForwardSoftLimitThreshold(int forwardSensorLimit, int timeoutMs) {
        errorCode = controller.configForwardSoftLimitThreshold(forwardSensorLimit, timeoutMs);
        return this;
    }

    /**
     * Configures the reverse soft limit threshold.
     *
     * @param reverseSensorLimit Reverse Sensor Position Limit (in Raw Sensor Units).
     * @param timeoutMs          Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configReverseSoftLimitThreshold(int reverseSensorLimit, int timeoutMs) {
        errorCode = controller.configReverseSoftLimitThreshold(reverseSensorLimit, timeoutMs);
        return this;
    }

    /**
     * Configures the forward soft limit enable.
     *
     * @param enable    Forward Sensor Position Limit Enable.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configForwardSoftLimitEnable(boolean enable, int timeoutMs) {
        errorCode = controller.configForwardSoftLimitEnable(enable, timeoutMs);
        return this;
    }

    /**
     * Configures the reverse soft limit enable.
     *
     * @param enable    Reverse Sensor Position Limit Enable.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configReverseSoftLimitEnable(boolean enable, int timeoutMs) {
        errorCode = controller.configReverseSoftLimitEnable(enable, timeoutMs);
        return this;
    }

    /**
     * Sets the enable state for soft limit switches.
     *
     * @param enable Enable state for soft limit switches.
     **/
    public CBTalonSRX overrideSoftLimitsEnable(boolean enable) {
        controller.overrideSoftLimitsEnable(enable);
        return this;
    }

    /**
     * Sets the 'P' constant in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param value     Value of the P constant.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX config_kP(int slotIdx, double value, int timeoutMs) {
        errorCode = controller.config_kP(slotIdx, value, timeoutMs);
        return this;
    }

    /**
     * Sets the 'I' constant in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param value     Value of the I constant.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX config_kI(int slotIdx, double value, int timeoutMs) {
        errorCode = controller.config_kI(slotIdx, value, timeoutMs);
        return this;
    }

    /**
     * Sets the 'D' constant in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param value     Value of the D constant.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX config_kD(int slotIdx, double value, int timeoutMs) {
        errorCode = controller.config_kD(slotIdx, value, timeoutMs);
        return this;
    }

    /**
     * Sets the 'F' constant in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param value     Value of the F constant.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX config_kF(int slotIdx, double value, int timeoutMs) {
        errorCode = controller.config_kF(slotIdx, value, timeoutMs);
        return this;
    }

    /**
     * Sets the Integral Zone constant in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param izone     Value of the Integral Zone constant.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX config_IntegralZone(int slotIdx, int izone, int timeoutMs) {
        errorCode = controller.config_IntegralZone(slotIdx, izone, timeoutMs);
        return this;
    }

    /**
     * Sets the allowable closed-loop error in the given parameter slot.
     *
     * @param slotIdx                  Parameter slot for the constant.
     * @param allowableClosedLoopError Value of the allowable closed-loop error.
     * @param timeoutMs                Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configAllowableClosedloopError(int slotIdx, int allowableClosedLoopError, int timeoutMs) {
        errorCode = controller.configAllowableClosedloopError(slotIdx, allowableClosedLoopError, timeoutMs);
        return this;
    }

    /**
     * Sets the maximum integral accumulator in the given parameter slot.
     *
     * @param slotIdx   Parameter slot for the constant.
     * @param iaccum    Value of the maximum integral accumulator.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configMaxIntegralAccumulator(int slotIdx, double iaccum, int timeoutMs) {
        errorCode = controller.configMaxIntegralAccumulator(slotIdx, iaccum, timeoutMs);
        return this;
    }

    /**
     * Sets the integral accumulator.
     *
     * @param iaccum    Value to set for the integral accumulator.
     * @param pidIdx
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX setIntegralAccumulator(double iaccum, int pidIdx, int timeoutMs) {
        errorCode = controller.setIntegralAccumulator(iaccum, pidIdx, timeoutMs);
        return this;
    }

    /**
     * Gets the closed-loop error.
     *
     * @param pidIdx@return Closed-loop error value.
     */
    public int getClosedLoopError(int pidIdx) {
        return controller.getClosedLoopError(pidIdx);
    }

    /**
     * Gets the iaccum value.
     *
     * @param pidIdx
     * @return Integral accumulator value.
     */
    public double getIntegralAccumulator(int pidIdx) {
        return controller.getIntegralAccumulator(pidIdx);
    }

    /**
     * Gets the derivative of the closed-loop error.
     *
     * @param pidIdx@return The error derivative value.
     */
    public double getErrorDerivative(int pidIdx) {
        return controller.getErrorDerivative(pidIdx);
    }

    /**
     * Selects which profile slot to use for closed-loop control.
     *
     * @param slotIdx Profile slot to select.
     * @param pidIdx
     **/
    public CBTalonSRX selectProfileSlot(int slotIdx, int pidIdx) {
        controller.selectProfileSlot(slotIdx, pidIdx);
        return this;
    }

    public int getActiveTrajectoryPosition() {
        return controller.getActiveTrajectoryPosition();
    }

    public int getActiveTrajectoryVelocity() {
        return controller.getActiveTrajectoryVelocity();
    }

    public double getActiveTrajectoryHeading() {
        return controller.getActiveTrajectoryHeading();
    }

    /**
     * Sets the Motion Magic Cruise Velocity.
     *
     * @param sensorUnitsPer100ms Motion Magic Cruise Velocity (in Raw Sensor Units per 100 ms).
     * @param timeoutMs           Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configMotionCruiseVelocity(int sensorUnitsPer100ms, int timeoutMs) {
        errorCode = controller.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs);
        return this;
    }

    /**
     * Sets the Motion Magic Acceleration.
     *
     * @param sensorUnitsPer100msPerSec Motion Magic Acceleration (in Raw Sensor Units per 100 ms per
     *                                  second).
     * @param timeoutMs                 Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configMotionAcceleration(int sensorUnitsPer100msPerSec, int timeoutMs) {
        errorCode = controller.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs);
        return this;
    }

    public CBTalonSRX clearMotionProfileTrajectories() {
        errorCode = controller.clearMotionProfileTrajectories();
        return this;
    }

    public int getMotionProfileTopLevelBufferCount() {
        return controller.getMotionProfileTopLevelBufferCount();
    }

    public CBTalonSRX pushMotionProfileTrajectory(TrajectoryPoint trajPt) {
        errorCode = controller.pushMotionProfileTrajectory(trajPt);
        return this;
    }

    public boolean isMotionProfileTopLevelBufferFull() {
        return controller.isMotionProfileTopLevelBufferFull();
    }

    public CBTalonSRX processMotionProfileBuffer() {
        controller.processMotionProfileBuffer();
        return this;
    }

    public CBTalonSRX getMotionProfileStatus(MotionProfileStatus statusToFill) {
        errorCode = controller.getMotionProfileStatus(statusToFill);
        return this;
    }

    public CBTalonSRX clearMotionProfileHasUnderrun(int timeoutMs) {
        errorCode = controller.clearMotionProfileHasUnderrun(timeoutMs);
        return this;
    }

    public CBTalonSRX changeMotionControlFramePeriod(int periodMs) {
        errorCode = controller.changeMotionControlFramePeriod(periodMs);
        return this;
    }

    /**
     * Gets the last error generated by this object.
     *
     * @return Last Error Code generated by a function.
     */
    public CBTalonSRX getLastError() {
        errorCode = controller.getLastError();
        return this;
    }

    public CBTalonSRX getFaults(Faults toFill) {
        errorCode = controller.getFaults(toFill);
        return this;
    }

    public CBTalonSRX getStickyFaults(StickyFaults toFill) {
        errorCode = controller.getStickyFaults(toFill);
        return this;
    }

    public CBTalonSRX clearStickyFaults(int timeoutMs) {
        errorCode = controller.clearStickyFaults(timeoutMs);
        return this;
    }

    /**
     * Gets the firmware version of the device.
     *
     * @return Firmware version of device.
     */
    public int getFirmwareVersion() {
        return controller.getFirmwareVersion();
    }

    /**
     * Returns true if the device has reset.
     *
     * @return Has a Device Reset Occurred?
     */
    public boolean hasResetOccurred() {
        return controller.hasResetOccurred();
    }

    /**
     * Sets the value of a custom parameter.
     *
     * @param newValue   Value for custom parameter.
     * @param paramIndex Index of custom parameter.
     * @param timeoutMs  Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configSetCustomParam(int newValue, int paramIndex, int timeoutMs) {
        errorCode = controller.configSetCustomParam(newValue, paramIndex, timeoutMs);
        return this;
    }

    /**
     * Gets the value of a custom parameter.
     *
     * @param paramIndex Index of custom parameter.
     * @param timoutMs   Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Value of the custom param.
     */
    public int configGetCustomParam(int paramIndex, int timoutMs) {
        return controller.configGetCustomParam(paramIndex, timoutMs);
    }

    /**
     * Sets a parameter.
     *
     * @param param     Parameter enumeration.
     * @param value     Value of parameter.
     * @param subValue  Subvalue for parameter. Maximum value of 255.
     * @param ordinal   Ordinal of parameter.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configSetParameter(ParamEnum param, double value, int subValue, int ordinal, int timeoutMs) {
        errorCode = controller.configSetParameter(param, value, subValue, ordinal, timeoutMs);
        return this;
    }

    /**
     * Sets a parameter.
     *
     * @param param     Parameter enumeration.
     * @param value     Value of parameter.
     * @param subValue  Subvalue for parameter. Maximum value of 255.
     * @param ordinal   Ordinal of parameter.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Error Code generated by function. 0 indicates no error.
     */
    public CBTalonSRX configSetParameter(int param, double value, int subValue, int ordinal, int timeoutMs) {
        errorCode = controller.configSetParameter(param, value, subValue, ordinal, timeoutMs);
        return this;
    }

    /**
     * Gets a parameter.
     *
     * @param param     Parameter enumeration.
     * @param ordinal   Ordinal of parameter.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Value of parameter.
     */
    public double configGetParameter(ParamEnum param, int ordinal, int timeoutMs) {
        return controller.configGetParameter(param, ordinal, timeoutMs);
    }

    /**
     * Gets a parameter.
     *
     * @param param     Parameter enumeration.
     * @param ordinal   Ordinal of parameter.
     * @param timeoutMs Timeout value in ms. @see #ConfigOpenLoopRamp
     * @return Value of parameter.
     */
    public double configGetParameter(int param, int ordinal, int timeoutMs) {
        return controller.configGetParameter(param, ordinal, timeoutMs);
    }

    public int getBaseID() {
        return controller.getBaseID();
    }


    public CBTalonSRX valueUpdated() {
        controller.valueUpdated();
        return this;
    }

    /**
     * @retrieve object that can get/set individual RAW sensor values.
     */
    public SensorCollection getSensorCollection() {
        return controller.getSensorCollection();
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
}
