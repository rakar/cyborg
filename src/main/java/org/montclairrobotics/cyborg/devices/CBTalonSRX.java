package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.montclairrobotics.cyborg.Cyborg;

public class CBTalonSRX extends CBSpeedController implements CBDevice {
	TalonSRX talon;
	int channel;

	public CBTalonSRX(int channel) {
		this.talon = new TalonSRX(channel);
		this.channel = channel;
	}

	@Override
	public CBSpeedController pidWrite(double output) {
		talon.set(ControlMode.PercentOutput, output);
		return this;
	}

	@Override
	public double get() {
		return talon.getMotorOutputPercent();
	}

	@Override
	public CBSpeedController set(double speed) {
		talon.set(ControlMode.PercentOutput, speed);
		return this;
	}

	@Override
	public CBSpeedController setInverted(boolean isInverted) {
		talon.setInverted(isInverted);
		return this;
	}

	@Override
	public boolean getInverted() {
		return talon.getInverted();
	}

	@Override
	public CBSpeedController disable() {
		talon.neutralOutput();
		return this;
	}

	@Override
	public CBSpeedController stopMotor() {
		talon.neutralOutput();
		return null;
	}

	@Override
	public void senseUpdate() {

	}

	@Override
	public void controlUpdate() {

	}

	@Override
	public void configure() {

	}

	public ErrorCode setStatusFramePeriod(StatusFrameEnhanced frame, int periodMs, int timeoutMs) {
		return talon.setStatusFramePeriod(frame, periodMs, timeoutMs);
	}

	public int getStatusFramePeriod(StatusFrameEnhanced frame, int timeoutMs) {
		return talon.getStatusFramePeriod(frame, timeoutMs);
	}

	public ErrorCode configVelocityMeasurementPeriod(VelocityMeasPeriod period, int timeoutMs) {
		return talon.configVelocityMeasurementPeriod(period, timeoutMs);
	}

	public ErrorCode configVelocityMeasurementWindow(int windowSize, int timeoutMs) {
		return talon.configVelocityMeasurementWindow(windowSize, timeoutMs);
	}

	public ErrorCode configForwardLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int timeoutMs) {
		return talon.configForwardLimitSwitchSource(type, normalOpenOrClose, timeoutMs);
	}

	public ErrorCode configReverseLimitSwitchSource(LimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int timeoutMs) {
		return talon.configReverseLimitSwitchSource(type, normalOpenOrClose, timeoutMs);
	}

	/**
	 * Configures the peak current limit of the motor controller.
	 *
	 * @param amps
	 *            Peak current limit (in amps).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configPeakCurrentLimit(int amps, int timeoutMs) {
		return talon.configPeakCurrentLimit(amps, timeoutMs);
	}

	/**
	 * Configures the maximum time allowed at peak current limit of the motor
	 * controller.
	 *
	 * @param milliseconds
	 *            Maximum time allowed at peak current limit (in milliseconds).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configPeakCurrentDuration(int milliseconds, int timeoutMs) {
		return talon.configPeakCurrentDuration(milliseconds, timeoutMs);
	}

	/**
	 * Configures the continuous current limit.
	 *
	 * @param amps
	 *            Continuous Current Limit.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configContinuousCurrentLimit(int amps, int timeoutMs) {
		return talon.configContinuousCurrentLimit(amps, timeoutMs);
	}

	/**
	 * Enables the current limit feature.
	 *
	 * @param enable
	 *            Enable state of current limit.
	 **/
	public void enableCurrentLimit(boolean enable) {
		talon.enableCurrentLimit(enable);
	}

	public long getHandle() {
		return talon.getHandle();
	}

	/**
	 * Returns the Device ID
	 *
	 * @return Device number.
	 */
	public int getDeviceID() {
		return talon.getDeviceID();
	}

	/**
	 * Sets the appropriate output on the talon, depending on the mode.
	 *
	 * In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as
	 * stopped. In Voltage mode, output value is in volts. In Current mode,
	 * output value is in amperes. In Speed mode, output value is in position
	 * change / 100ms. In Position mode, output value is in encoder ticks or an
	 * analog value, depending on the sensor. In Follower mode, the output value
	 * is the integer device ID of the talon to duplicate.
	 *
	 *
	 * @param mode
	 * @param outputValue
	 *            The setpoint value, as described above.
	 * @see #SelectProfileSlot to choose between the two sets of gains.
	 */
	public void set(ControlMode mode, double outputValue) {
		talon.set(mode, outputValue);
	}

	public void set(ControlMode mode, double demand0, double demand1) {
		talon.set(mode, demand0, demand1);
	}

	/**
	 * Neutral the motor output by setting control mode to disabled.
	 */
	public void neutralOutput() {
		talon.neutralOutput();
	}

	/**
	 * Sets the mode of operation during neutral throttle output.
	 *
	 * @param neutralMode
	 *            The desired mode of operation when the Controller output
	 *            throttle is neutral (ie brake/coast)
	 **/
	public void setNeutralMode(NeutralMode neutralMode) {
		talon.setNeutralMode(neutralMode);
	}

	public void enableHeadingHold(boolean enable) {
		talon.enableHeadingHold(enable);
	}

	public void selectDemandType(boolean value) {
		talon.selectDemandType(value);
	}

	/**
	 * Sets the phase of the sensor. Use when controller forward/reverse output
	 * doesn't correlate to appropriate forward/reverse reading of sensor.
	 *
	 * @param PhaseSensor
	 *            Indicates whether to invert the phase of the sensor.
	 **/
	public void setSensorPhase(boolean PhaseSensor) {
		talon.setSensorPhase(PhaseSensor);
	}

	/**
	 * Configures the open-loop ramp rate of throttle output.
	 *
	 * @param secondsFromNeutralToFull
	 *            Minimum desired time to go from neutral to full throttle. A
	 *            value of '0' will disable the ramp.
	 * @param timeoutMs
	 *            Timeout value in ms. Function will generate error if config is
	 *            not successful within timeout.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configOpenloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
		return talon.configOpenloopRamp(secondsFromNeutralToFull, timeoutMs);
	}

	/**
	 * Configures the closed-loop ramp rate of throttle output.
	 *
	 * @param secondsFromNeutralToFull
	 *            Minimum desired time to go from neutral to full throttle. A
	 *            value of '0' will disable the ramp.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configClosedloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
		return talon.configClosedloopRamp(secondsFromNeutralToFull, timeoutMs);
	}

	/**
	 * Configures the forward peak output percentage.
	 *
	 * @param percentOut
	 *            Desired peak output percentage.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configPeakOutputForward(double percentOut, int timeoutMs) {
		return talon.configPeakOutputForward(percentOut, timeoutMs);
	}

	/**
	 * Configures the reverse peak output percentage.
	 *
	 * @param percentOut
	 *            Desired peak output percentage.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configPeakOutputReverse(double percentOut, int timeoutMs) {
		return talon.configPeakOutputReverse(percentOut, timeoutMs);
	}

	/**
	 * Configures the forward nominal output percentage.
	 *
	 * @param percentOut
	 *            Nominal (minimum) percent output.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configNominalOutputForward(double percentOut, int timeoutMs) {
		return talon.configNominalOutputForward(percentOut, timeoutMs);
	}

	/**
	 * Configures the reverse nominal output percentage.
	 *
	 * @param percentOut
	 *            Nominal (minimum) percent output.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configNominalOutputReverse(double percentOut, int timeoutMs) {
		return talon.configNominalOutputReverse(percentOut, timeoutMs);
	}

	/**
	 * Configures the output deadband percentage.
	 *
	 * @param percentDeadband
	 *            Desired deadband percentage. Minimum is 0.1%, Maximum is 25%.
	 *            Pass 0.04 for 4%.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configNeutralDeadband(double percentDeadband, int timeoutMs) {
		return talon.configNeutralDeadband(percentDeadband, timeoutMs);
	}

	/**
	 * Configures the Voltage Compensation saturation voltage.
	 *
	 * @param voltage
	 *            TO-DO: Comment me!
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configVoltageCompSaturation(double voltage, int timeoutMs) {
		return talon.configVoltageCompSaturation(voltage, timeoutMs);
	}

	/**
	 * Configures the voltage measurement filter.
	 *
	 * @param filterWindowSamples
	 *            Number of samples in the rolling average of voltage
	 *            measurement.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configVoltageMeasurementFilter(int filterWindowSamples, int timeoutMs) {
		return talon.configVoltageMeasurementFilter(filterWindowSamples, timeoutMs);
	}

	/**
	 * Enables voltage compensation. If enabled, voltage compensation works in
	 * all control modes.
	 *
	 * @param enable
	 *            Enable state of voltage compensation.
	 **/
	public void enableVoltageCompensation(boolean enable) {
		talon.enableVoltageCompensation(enable);
	}

	/**
	 * Gets the bus voltage seen by the motor controller.
	 *
	 * @return The bus voltage value (in volts).
	 */
	public double getBusVoltage() {
		return talon.getBusVoltage();
	}

	/**
	 * Gets the output percentage of the motor controller.
	 *
	 * @return Output of the motor controller (in percent).
	 */
	public double getMotorOutputPercent() {
		return talon.getMotorOutputPercent();
	}

	/**
	 * @return applied voltage to motor
	 */
	public double getMotorOutputVoltage() {
		return talon.getMotorOutputVoltage();
	}

	/**
	 * Gets the output current of the motor controller.
	 *
	 * @return The output current (in amps).
	 */
	public double getOutputCurrent() {
		return talon.getOutputCurrent();
	}

	/**
	 * Gets the temperature of the motor controller.
	 *
	 * @return Temperature of the motor controller (in 'C)
	 */
	public double getTemperature() {
		return talon.getTemperature();
	}

	/**
	 * Select the remote feedback device for the motor controller.
	 *
	 * @param feedbackDevice
	 *            Remote Feedback Device to select.
	 * @param pidIdx
	 *            0 for Primary closed-loop. 1 for cascaded closed-loop.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configSelectedFeedbackSensor(RemoteFeedbackDevice feedbackDevice, int pidIdx, int timeoutMs) {
		return talon.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}

	/**
	 * Select the feedback device for the motor controller.
	 *
	 * @param feedbackDevice
	 *            Feedback Device to select.
	 * @param pidIdx
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configSelectedFeedbackSensor(FeedbackDevice feedbackDevice, int pidIdx, int timeoutMs) {
		return talon.configSelectedFeedbackSensor(feedbackDevice, pidIdx, timeoutMs);
	}

	public ErrorCode configRemoteFeedbackFilter(int deviceID, RemoteSensorSource remoteSensorSource, int remoteOrdinal, int timeoutMs) {
		return talon.configRemoteFeedbackFilter(deviceID, remoteSensorSource, remoteOrdinal, timeoutMs);
	}

	public ErrorCode configSensorTerm(SensorTerm sensorTerm, FeedbackDevice feedbackDevice, int timeoutMs) {
		return talon.configSensorTerm(sensorTerm, feedbackDevice, timeoutMs);
	}

	/**
	 * Get the selected sensor position.
	 *
	 * @return Position of selected sensor (in Raw Sensor Units).
	 * @param pidIdx
	 */
	public int getSelectedSensorPosition(int pidIdx) {
		return talon.getSelectedSensorPosition(pidIdx);
	}

	/**
	 * Get the selected sensor velocity.
	 *
	 * @return Velocity of selected sensor (in Raw Sensor Units per 100 ms).
	 * @param pidIdx
	 */
	public int getSelectedSensorVelocity(int pidIdx) {
		return talon.getSelectedSensorVelocity(pidIdx);
	}

	/**
	 * Sets the sensor position to the given value.
	 *
	 * @param sensorPos
	 *            Position to set for the selected sensor (in Raw Sensor Units).
	 * @param pidIdx
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setSelectedSensorPosition(int sensorPos, int pidIdx, int timeoutMs) {
		return talon.setSelectedSensorPosition(sensorPos, pidIdx, timeoutMs);
	}

	/**
	 * Sets the period of the given control frame.
	 *
	 * @param frame
	 *            Frame whose period is to be changed.
	 * @param periodMs
	 *            Period in ms for the given frame.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setControlFramePeriod(ControlFrame frame, int periodMs) {
		return talon.setControlFramePeriod(frame, periodMs);
	}

	/**
	 * Sets the period of the given control frame.
	 *
	 * @param frame
	 *            Frame whose period is to be changed.
	 * @param periodMs
	 *            Period in ms for the given frame.
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setControlFramePeriod(int frame, int periodMs) {
		return talon.setControlFramePeriod(frame, periodMs);
	}

	/**
	 * Sets the period of the given status frame.
	 *
	 *
	 * @param frameValue
	 * @param periodMs
	 *            Period in ms for the given frame.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setStatusFramePeriod(int frameValue, int periodMs, int timeoutMs) {
		return talon.setStatusFramePeriod(frameValue, periodMs, timeoutMs);
	}

	/**
	 * Sets the period of the given status frame.
	 *
	 * @param frame
	 *            Frame whose period is to be changed.
	 * @param periodMs
	 *            Period in ms for the given frame.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setStatusFramePeriod(StatusFrame frame, int periodMs, int timeoutMs) {
		return talon.setStatusFramePeriod(frame, periodMs, timeoutMs);
	}

	/**
	 * Gets the period of the given status frame.
	 *
	 * @param frame
	 *            Frame to get the period of.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Period of the given status frame.
	 */
	public int getStatusFramePeriod(int frame, int timeoutMs) {
		return talon.getStatusFramePeriod(frame, timeoutMs);
	}

	/**
	 * Gets the period of the given status frame.
	 *
	 * @param frame
	 *            Frame to get the period of.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Period of the given status frame.
	 */
	public int getStatusFramePeriod(StatusFrame frame, int timeoutMs) {
		return talon.getStatusFramePeriod(frame, timeoutMs);
	}

	/**
	 * Configures the forward limit switch for a remote source.
	 *
	 * @param type
	 *            Remote limit switch source. @see #LimitSwitchSource
	 * @param normalOpenOrClose
	 *            Setting for normally open or normally closed.
	 * @param deviceID
	 *            Device ID of remote source.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configForwardLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int deviceID, int timeoutMs) {
		return talon.configForwardLimitSwitchSource(type, normalOpenOrClose, deviceID, timeoutMs);
	}

	/**
	 * Configures the reverse limit switch for a remote source.
	 *
	 * @param type
	 *            Remote limit switch source. @see #LimitSwitchSource
	 * @param normalOpenOrClose
	 *            Setting for normally open or normally closed.
	 * @param deviceID
	 *            Device ID of remote source.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configReverseLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose, int deviceID, int timeoutMs) {
		return talon.configReverseLimitSwitchSource(type, normalOpenOrClose, deviceID, timeoutMs);
	}

	/**
	 * Sets the enable state for limit switches.
	 *
	 * @param enable
	 *            Enable state for limit switches.
	 **/
	public void overrideLimitSwitchesEnable(boolean enable) {
		talon.overrideLimitSwitchesEnable(enable);
	}

	/**
	 * Configures the forward soft limit threhold.
	 *
	 * @param forwardSensorLimit
	 *            Forward Sensor Position Limit (in Raw Sensor Units).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configForwardSoftLimitThreshold(int forwardSensorLimit, int timeoutMs) {
		return talon.configForwardSoftLimitThreshold(forwardSensorLimit, timeoutMs);
	}

	/**
	 * Configures the reverse soft limit threshold.
	 *
	 * @param reverseSensorLimit
	 *            Reverse Sensor Position Limit (in Raw Sensor Units).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configReverseSoftLimitThreshold(int reverseSensorLimit, int timeoutMs) {
		return talon.configReverseSoftLimitThreshold(reverseSensorLimit, timeoutMs);
	}

	/**
	 * Configures the forward soft limit enable.
	 *
	 * @param enable
	 *            Forward Sensor Position Limit Enable.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configForwardSoftLimitEnable(boolean enable, int timeoutMs) {
		return talon.configForwardSoftLimitEnable(enable, timeoutMs);
	}

	/**
	 * Configures the reverse soft limit enable.
	 *
	 * @param enable
	 *            Reverse Sensor Position Limit Enable.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configReverseSoftLimitEnable(boolean enable, int timeoutMs) {
		return talon.configReverseSoftLimitEnable(enable, timeoutMs);
	}

	/**
	 * Sets the enable state for soft limit switches.
	 *
	 * @param enable
	 *            Enable state for soft limit switches.
	 **/
	public void overrideSoftLimitsEnable(boolean enable) {
		talon.overrideSoftLimitsEnable(enable);
	}

	/**
	 * Sets the 'P' constant in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param value
	 *            Value of the P constant.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode config_kP(int slotIdx, double value, int timeoutMs) {
		return talon.config_kP(slotIdx, value, timeoutMs);
	}

	/**
	 * Sets the 'I' constant in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param value
	 *            Value of the I constant.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode config_kI(int slotIdx, double value, int timeoutMs) {
		return talon.config_kI(slotIdx, value, timeoutMs);
	}

	/**
	 * Sets the 'D' constant in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param value
	 *            Value of the D constant.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode config_kD(int slotIdx, double value, int timeoutMs) {
		return talon.config_kD(slotIdx, value, timeoutMs);
	}

	/**
	 * Sets the 'F' constant in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param value
	 *            Value of the F constant.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode config_kF(int slotIdx, double value, int timeoutMs) {
		return talon.config_kF(slotIdx, value, timeoutMs);
	}

	/**
	 * Sets the Integral Zone constant in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param izone
	 *            Value of the Integral Zone constant.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode config_IntegralZone(int slotIdx, int izone, int timeoutMs) {
		return talon.config_IntegralZone(slotIdx, izone, timeoutMs);
	}

	/**
	 * Sets the allowable closed-loop error in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param allowableClosedLoopError
	 *            Value of the allowable closed-loop error.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configAllowableClosedloopError(int slotIdx, int allowableClosedLoopError, int timeoutMs) {
		return talon.configAllowableClosedloopError(slotIdx, allowableClosedLoopError, timeoutMs);
	}

	/**
	 * Sets the maximum integral accumulator in the given parameter slot.
	 *
	 * @param slotIdx
	 *            Parameter slot for the constant.
	 * @param iaccum
	 *            Value of the maximum integral accumulator.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configMaxIntegralAccumulator(int slotIdx, double iaccum, int timeoutMs) {
		return talon.configMaxIntegralAccumulator(slotIdx, iaccum, timeoutMs);
	}

	/**
	 * Sets the integral accumulator.
	 *
	 * @param iaccum
	 *            Value to set for the integral accumulator.
	 * @param pidIdx
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode setIntegralAccumulator(double iaccum, int pidIdx, int timeoutMs) {
		return talon.setIntegralAccumulator(iaccum, pidIdx, timeoutMs);
	}

	/**
	 * Gets the closed-loop error.
	 *
	 *
	 * @param pidIdx@return Closed-loop error value.
	 */
	public int getClosedLoopError(int pidIdx) {
		return talon.getClosedLoopError(pidIdx);
	}

	/**
	 * Gets the iaccum value.
	 *
	 * @return Integral accumulator value.
	 * @param pidIdx
	 */
	public double getIntegralAccumulator(int pidIdx) {
		return talon.getIntegralAccumulator(pidIdx);
	}

	/**
	 * Gets the derivative of the closed-loop error.
	 *
	 *
	 * @param pidIdx@return The error derivative value.
	 */
	public double getErrorDerivative(int pidIdx) {
		return talon.getErrorDerivative(pidIdx);
	}

	/**
	 * Selects which profile slot to use for closed-loop control.
	 *
	 * @param slotIdx
	 *            Profile slot to select.
	 * @param pidIdx
	 **/
	public void selectProfileSlot(int slotIdx, int pidIdx) {
		talon.selectProfileSlot(slotIdx, pidIdx);
	}

	public int getActiveTrajectoryPosition() {
		return talon.getActiveTrajectoryPosition();
	}

	public int getActiveTrajectoryVelocity() {
		return talon.getActiveTrajectoryVelocity();
	}

	public double getActiveTrajectoryHeading() {
		return talon.getActiveTrajectoryHeading();
	}

	/**
	 * Sets the Motion Magic Cruise Velocity.
	 *
	 * @param sensorUnitsPer100ms
	 *            Motion Magic Cruise Velocity (in Raw Sensor Units per 100 ms).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configMotionCruiseVelocity(int sensorUnitsPer100ms, int timeoutMs) {
		return talon.configMotionCruiseVelocity(sensorUnitsPer100ms, timeoutMs);
	}

	/**
	 * Sets the Motion Magic Acceleration.
	 *
	 * @param sensorUnitsPer100msPerSec
	 *            Motion Magic Acceleration (in Raw Sensor Units per 100 ms per
	 *            second).
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configMotionAcceleration(int sensorUnitsPer100msPerSec, int timeoutMs) {
		return talon.configMotionAcceleration(sensorUnitsPer100msPerSec, timeoutMs);
	}

	public ErrorCode clearMotionProfileTrajectories() {
		return talon.clearMotionProfileTrajectories();
	}

	public int getMotionProfileTopLevelBufferCount() {
		return talon.getMotionProfileTopLevelBufferCount();
	}

	public ErrorCode pushMotionProfileTrajectory(TrajectoryPoint trajPt) {
		return talon.pushMotionProfileTrajectory(trajPt);
	}

	public boolean isMotionProfileTopLevelBufferFull() {
		return talon.isMotionProfileTopLevelBufferFull();
	}

	public void processMotionProfileBuffer() {
		talon.processMotionProfileBuffer();
	}

	public ErrorCode getMotionProfileStatus(MotionProfileStatus statusToFill) {
		return talon.getMotionProfileStatus(statusToFill);
	}

	public ErrorCode clearMotionProfileHasUnderrun(int timeoutMs) {
		return talon.clearMotionProfileHasUnderrun(timeoutMs);
	}

	public ErrorCode changeMotionControlFramePeriod(int periodMs) {
		return talon.changeMotionControlFramePeriod(periodMs);
	}

	/**
	 * Gets the last error generated by this object.
	 *
	 * @return Last Error Code generated by a function.
	 */
	public ErrorCode getLastError() {
		return talon.getLastError();
	}

	public ErrorCode getFaults(Faults toFill) {
		return talon.getFaults(toFill);
	}

	public ErrorCode getStickyFaults(StickyFaults toFill) {
		return talon.getStickyFaults(toFill);
	}

	public ErrorCode clearStickyFaults(int timeoutMs) {
		return talon.clearStickyFaults(timeoutMs);
	}

	/**
	 * Gets the firmware version of the device.
	 *
	 * @return Firmware version of device.
	 */
	public int getFirmwareVersion() {
		return talon.getFirmwareVersion();
	}

	/**
	 * Returns true if the device has reset.
	 *
	 * @return Has a Device Reset Occurred?
	 */
	public boolean hasResetOccurred() {
		return talon.hasResetOccurred();
	}

	/**
	 * Sets the value of a custom parameter.
	 *
	 * @param newValue
	 *            Value for custom parameter.
	 * @param paramIndex
	 *            Index of custom parameter.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configSetCustomParam(int newValue, int paramIndex, int timeoutMs) {
		return talon.configSetCustomParam(newValue, paramIndex, timeoutMs);
	}

	/**
	 * Gets the value of a custom parameter.
	 *
	 * @param paramIndex
	 *            Index of custom parameter.
	 * @param timoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Value of the custom param.
	 */
	public int configGetCustomParam(int paramIndex, int timoutMs) {
		return talon.configGetCustomParam(paramIndex, timoutMs);
	}

	/**
	 * Sets a parameter.
	 *
	 * @param param
	 *            Parameter enumeration.
	 * @param value
	 *            Value of parameter.
	 * @param subValue
	 *            Subvalue for parameter. Maximum value of 255.
	 * @param ordinal
	 *            Ordinal of parameter.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configSetParameter(ParamEnum param, double value, int subValue, int ordinal, int timeoutMs) {
		return talon.configSetParameter(param, value, subValue, ordinal, timeoutMs);
	}

	/**
	 * Sets a parameter.
	 *
	 * @param param
	 *            Parameter enumeration.
	 * @param value
	 *            Value of parameter.
	 * @param subValue
	 *            Subvalue for parameter. Maximum value of 255.
	 * @param ordinal
	 *            Ordinal of parameter.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Error Code generated by function. 0 indicates no error.
	 */
	public ErrorCode configSetParameter(int param, double value, int subValue, int ordinal, int timeoutMs) {
		return talon.configSetParameter(param, value, subValue, ordinal, timeoutMs);
	}

	/**
	 * Gets a parameter.
	 *
	 * @param param
	 *            Parameter enumeration.
	 * @param ordinal
	 *            Ordinal of parameter.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Value of parameter.
	 */
	public double configGetParameter(ParamEnum param, int ordinal, int timeoutMs) {
		return talon.configGetParameter(param, ordinal, timeoutMs);
	}

	/**
	 * Gets a parameter.
	 *
	 * @param param
	 *            Parameter enumeration.
	 * @param ordinal
	 *            Ordinal of parameter.
	 * @param timeoutMs
	 *            Timeout value in ms. @see #ConfigOpenLoopRamp
	 * @return Value of parameter.
	 */
	public double configGetParameter(int param, int ordinal, int timeoutMs) {
		return talon.configGetParameter(param, ordinal, timeoutMs);
	}

	public int getBaseID() {
		return talon.getBaseID();
	}

	public void follow(IMotorController masterToFollow) {
		talon.follow(masterToFollow);
	}

	public void valueUpdated() {
		talon.valueUpdated();
	}

	/**
	 * @retrieve object that can get/set individual RAW sensor values.
	 */
	public SensorCollection getSensorCollection() {
		return talon.getSensorCollection();
	}

	/**
	 * @retrieve control mode of motor controller
	 */
	public ControlMode getControlMode() {
		return talon.getControlMode();
	}
}
