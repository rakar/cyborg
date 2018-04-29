package org.montclairrobotics.cyborg.devices;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.tables.ITable;

public class CBCANTalon implements CBSpeedController {
	TalonSRX talon;
	int channel;
	
	public CBCANTalon(int channel) {
		this.talon = new TalonSRX(channel);
		this.channel = channel;
	}

	@Override
	public CBCANTalon pidWrite(double output) {
		talon.pidWrite(output);
		return this;
	}

	@Override
	public double get() {
		return talon.get();
	}

	@Override
	public CBCANTalon set(double speed) {
		talon.set(speed);
		return this;
	}

	@Override
	public CBCANTalon setInverted(boolean isInverted) {
		talon.setInverted(isInverted);
		return this;
	}

	@Override
	public boolean getInverted() {
		return talon.getInverted();
	}

	@Override
	public CBCANTalon disable() {
		talon.disable();
		return this;
	}

	/**
	 * {@link edu.wpi.first.wpilibj.TalonSRX#changeControlMode}
	 */
	public CBCANTalon changeControlMode(TalonControlMode controlMode) {
		talon.changeControlMode(controlMode);
		return this;
	}
	
	/**
	 * {@link edu.wpi.first.wpilibj.TalonSRX#changeMotionControlFramePeriod}
	 */
	public CBCANTalon changeMotionControlFramePeriod(int periodMs) {
		talon.changeMotionControlFramePeriod(periodMs);
		return this;
	}
	

	public CBCANTalon clearIAccum() {
		talon.clearIAccum();
		return this;
	}

	
	public CBCANTalon clearMotionProfileHasUnderrun() {
		talon.clearMotionProfileHasUnderrun();
		return this;
	}

	public CBCANTalon learMotionProfileTrajectories() {
		talon.clearMotionProfileTrajectories();
		return this;
	}

	
	public CBCANTalon clearStickyFaults() {
		talon.clearStickyFaults();
		return this;
	}
	
	public CBCANTalon configEncoderCodesPerRev(int countsPerRev) {
		talon.configEncoderCodesPerRev(countsPerRev);
		return this;
	}

	public CBCANTalon ConfigFwdLimitSwitchNormallyOpen(boolean normallyOpen) {
		talon.ConfigFwdLimitSwitchNormallyOpen(normallyOpen);
		return this;
	}
	
	public CBCANTalon configMaxOutputVoltage(double voltage) {
		talon.configMaxOutputVoltage(voltage);
		return this;
	}
	
	public CBCANTalon configNominalOutputVoltage(double forwardVoltage, double reverseVoltage) {
		talon.configNominalOutputVoltage(forwardVoltage, reverseVoltage);
		return this;
	}
	
	public CBCANTalon configPeakOutputVoltage(double forwardVoltage, double reverseVoltage) {
		talon.configPeakOutputVoltage(forwardVoltage, reverseVoltage);
		return this;
	}
	
	public CBCANTalon configPotentiometerTurns(int turns) {
		talon.configPotentiometerTurns(turns);
		return this;
	}

	public CBCANTalon configRevLimitSwitchNormallyOpen(boolean normallyOpen) {
		talon.ConfigRevLimitSwitchNormallyOpen(normallyOpen);
		return this;
	}
	
	public CBCANTalon delete() {
		talon.delete();
		return this;
	}

	public CBCANTalon disableControl() {
		talon.disableControl();
		return this;
	}

	public CBCANTalon enableBrakeMode(boolean brake) {
		talon.enableBrakeMode(brake);		
		return this;
	}

	public CBCANTalon enableControl() {
		talon.enableControl();		
		return this;
	}

	public CBCANTalon enableForwardSoftLimit(boolean enable) {
		talon.enableForwardSoftLimit(enable);		
		return this;
	}

	public CBCANTalon enableLimitSwitch(boolean forward, boolean reverse) {
		talon.enableLimitSwitch(forward, reverse);		
		return this;
	}
	
	public CBCANTalon enableReverseSoftLimit(boolean enable) {
		talon.enableReverseSoftLimit(enable);
		return this;
	}
	
	public CBCANTalon enableZeroSensorPositionOnIndex(boolean enable, boolean risingEdge) {
		talon.enableZeroSensorPositionOnIndex(enable, risingEdge);		
		return this;
	}
	
	public int getAnalogInPosition() {
		return talon.getAnalogInPosition();		
	}
	
	public int getAnalogInRaw() {
		return talon.getAnalogInRaw();
	}

	public int getAnalogInVelocity() {
		return talon.getAnalogInVelocity();
	}
	
	public boolean getBrakeEnableDuringNeutral() {
		return talon.getBrakeEnableDuringNeutral();
	}
	
	public double getBusVoltage() {
		return talon.getBusVoltage();
	}
	
	public int getClosedLoopError() {
		return talon.getClosedLoopError();
	}
	
	public double getCloseLoopRampRate() {
		return talon.getCloseLoopRampRate();
	}
	
	public TalonControlMode getControlMode() {
		return talon.getControlMode();
	}
	
	public double getD() {
		return talon.getD();
	}
	
	public String getDescription() {
		return talon.getDescription();
	}
	
	public int getDeviceID() {
		return talon.getDeviceID();
	}
	
	public int getEncPosition() {
		return talon.getEncPosition();
	}
	
	public int getEncVelocity() {
		return talon.getEncVelocity();
	}
	
	public double getError() {
		return talon.getError();
	}
	
	public double getExpiration() {
		return talon.getExpiration();
	}
	
	public double getF() {
		return talon.getF();
	}
	
	public int getFaultForLim() {
		return talon.getFaultForLim();
	}
	
	public int getFaultForSoftLim() {
		return talon.getFaultForSoftLim();
	}
	
	public int getFaultHardwareFailure() {
		return talon.getFaultHardwareFailure();
	}
	
	public int getFaultOverTemp() {
		return talon.getFaultOverTemp();
	}
	
	public int getFaultRevLim() {
		return talon.getFaultRevLim();
	}
	
	public int getFaultRevSoftLim() {
		return talon.getFaultRevSoftLim();
	}
	
	public int getFaultUnderVoltage() {
		return talon.getFaultUnderVoltage();
	}
	
	public long getFirmwareVersion() {
		return talon.GetFirmwareVersion();
	}
	
	public int getForwardSoftLimit() {
		return talon.getForwardSoftLimit();
	}
	
	public double getI() {
		return talon.getI();
	}
	
	public double getIZone() {
		return talon.getIZone();
	}
	
	public CBCANTalon getMotionProfileStatus(MotionProfileStatus motionProfileStatus) {
		talon.getMotionProfileStatus(motionProfileStatus);
		return this;
	}
	
	public int getMotionProfileTopLevelBufferCount() {
		return talon.getMotionProfileTopLevelBufferCount();
	}
	
	public int getNumberOfQuadIdxRises() {
		return talon.getNumberOfQuadIdxRises();
	}
	
	public double getOutputCurrent() {
		return talon.getOutputCurrent();
	}
	
	public double getOutputVoltage() {
		return talon.getOutputVoltage();
	}
	
	public double getP() {
		return talon.getP();
	}
	
	//public double getParameter(param_t paramEnum) {
	//	return talon.getParameter(paramEnum);
	//}
	
	public PIDSourceType getPIDSourceType() {
		return talon.getPIDSourceType();
	}
	
	public int getPinStateQuadA() {
		return talon.getPinStateQuadA();
	}
	
	public int getPinStateQuadB() {
		return talon.getPinStateQuadB();
	}
	
	//public CBCANTalon  {
	//	return talon.getPinStateQuadIdx();
	//}
	
	public double getPosition() {
		return talon.getPosition();
	}
	
	public int getPulseWidthPosition() {
		return talon.getPulseWidthPosition();
	}
	
	public int getPulseWidthRiseToFallUs() {
		return talon.getPulseWidthRiseToFallUs();
	}
	
	public int getPulseWidthRiseToRiseUs() {
		return talon.getPulseWidthRiseToRiseUs();
	}
	
	public int getPulseWidthVelocity() {
		return talon.getPulseWidthVelocity();
	}
	
	public int getReverseSoftLimit() {
		return talon.getReverseSoftLimit();
	}
	
	//public CBCANTalon  {
	//	return talon.getSetpoint();
	//}
	
	public String getSmartDashboardType() {
		return talon.getSmartDashboardType();
	}
	
	public double getSpeed() {
		return talon.getSpeed();
	}
	
	public int getStickyFaultForLim() {
		return talon.getStickyFaultForLim();
	}
	
	public int getStickyFaultForSoftLim() {
		return talon.getStickyFaultForSoftLim();
	}
	
	public int getStickyFaultOverTemp() {
		return talon.getStickyFaultOverTemp();
	}
	
	public int getStickyFaultRevLim() {
		return talon.getStickyFaultRevLim();
	}
	
	public int getStickyFaultRevSoftLim() {
		return talon.getStickyFaultRevSoftLim();
	}
	
	public int getStickyFaultUnderVoltage() {
		return talon.getStickyFaultUnderVoltage();
	}
	
	public ITable getTable() {
		return talon.getTable();
	}
	
	public double getTemperature() {
		return talon.getTemperature();
	}
	
	public CBCANTalon initTable(ITable subtable) {
		talon.initTable(subtable);
		return this;
	}
	
	public boolean isAlive() {
		return talon.isAlive();
	}
	
	public boolean isControlEnabled() {
		return talon.isControlEnabled();
	}
	
	public boolean isEnabled() {
		return talon.isEnabled();
	}
	
	public boolean isForwardSoftLimitEnabled() {
		return talon.isForwardSoftLimitEnabled();
	}
	
	public boolean isFwdLimitSwitchClosed() {
		return talon.isFwdLimitSwitchClosed();
	}
	
	public boolean isMotionProfileTopLevelBufferFull() {
		return talon.isMotionProfileTopLevelBufferFull();
	}
	
	public boolean isReverseSoftLimitEnabled() {
		return talon.isReverseSoftLimitEnabled();
	}
	
	public boolean isRevLimitSwitchClosed() {
		return talon.isRevLimitSwitchClosed();
	}
	
	public boolean isSafetyEnabled() {
		return talon.isSafetyEnabled();
	}
	
	public FeedbackDeviceStatus isSensorPresent(FeedbackDevice feedbackDevice) {
		return talon.isSensorPresent(feedbackDevice);
	}
	
	public double pidGet() {
		return talon.pidGet();
	}
	
	public CBCANTalon processMotionProfileBuffer() {
		talon.processMotionProfileBuffer();
		return this;
	}
	
	public boolean pushMotionProfileTrajectory(TrajectoryPoint trajPt) {
		return talon.pushMotionProfileTrajectory(trajPt);
	}
	
	public CBCANTalon reset() {
		talon.reset();
		return this;
	}
	
	public CBCANTalon reverseOutput(boolean flip) {
		talon.reverseOutput(flip);
		return this;
	}
	
	public CBCANTalon reverseSensor(boolean flip) {
		talon.reverseSensor(flip);
		return this;
	}
	
	public CBCANTalon setAllowableClosedLoopErr(int allowableCloseLoopError) {
		talon.setAllowableClosedLoopErr(allowableCloseLoopError);
		return this;
	}
	
	public CBCANTalon setAnalogPosition(int newPosition) {
		talon.setAnalogPosition(newPosition);
		return this;
	}
	
	public CBCANTalon setCloseLoopRampRate(double rampRate) {
		talon.setCloseLoopRampRate(rampRate);
		return this;
	}
	
	public CBCANTalon setControlMode(int mode) {
		talon.setControlMode(mode);
		return this;
	}
	
	public CBCANTalon setD(double d) {
		talon.setD(d);
		return this;
	}
	
	public CBCANTalon setEncPosition(int newPosition) {
		talon.setEncPosition(newPosition);
		return this;
	}
	
	public CBCANTalon setExpiration(double timeout) {
		talon.setExpiration(timeout);
		return this;
	}
	
	public CBCANTalon setF(double f) {
		talon.setF(f);
		return this;
	}
	
	public CBCANTalon setFeedbackDevice(FeedbackDevice device) {
		talon.setFeedbackDevice(device);
		return this;
	}
	
	public CBCANTalon setForwardSoftLimit(double forwardLimit) {
		talon.setForwardSoftLimit(forwardLimit);
		return this;
	}
	
	public CBCANTalon setI(double i) {
		talon.setI(i);
		return this;
	}
	
	public CBCANTalon setIZone(int izone) {
		talon.setIZone(izone);
		return this;
	}
	
	public CBCANTalon setP(double p) {
		talon.setP(p);
		return this;
	}
	
	//public CBCANTalon setParameter(ParamEnum paramEnum, double value) {
	//	talon.setParameter(paramEnum, value);
	//	return this;
	//}
	
	public CBCANTalon setPID(double p, double i, double d) {
		talon.setPID(p, i, d);
		return this;
	}
	
	public CBCANTalon setPID(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile) {
		talon.setPID(p, i, d, f, izone, closeLoopRampRate, profile);
		return this;
	}
	
	public CBCANTalon setPIDSourceType(PIDSourceType pidSource) {
		talon.setPIDSourceType(pidSource);
		return this;
	}
	
	public CBCANTalon setPosition(double pos) {
		talon.setPosition(pos);
		return this;
	}
	
	public CBCANTalon setProfile(int profile) {
		talon.setProfile(profile);
		return this;
	}
	
	public CBCANTalon setPulseWidthPosition(int newPosition) {
		talon.setPulseWidthPosition(newPosition);
		return this;
	}
	
	public CBCANTalon setReverseSoftLimit(double reverseLimit) {
		talon.setReverseSoftLimit(reverseLimit);
		return this;
	}
	
	public CBCANTalon setSafetyEnabled(boolean enabled) {
		talon.setSafetyEnabled(enabled);
		return this;
	}
	
	public CBCANTalon setStatusFrameRateMs(StatusFrameRate stateFrame, int periodMs) {
		talon.setStatusFrameRateMs(stateFrame, periodMs);
		return this;
	}
	
	public CBCANTalon setVoltageCompensationRampRate(double rampRate) {
		talon.setVoltageCompensationRampRate(rampRate);
		return this;
	}
	
	public CBCANTalon setVoltageRampRate(double rampRate) {
		talon.setVoltageRampRate(rampRate);
		return this;
	}
	
	public CBCANTalon startLiveWindowMode() {
		talon.startLiveWindowMode();
		return this;
	}
	
	public CBCANTalon stopLiveWindowMode() {
		talon.stopLiveWindowMode();
		return this;
	}
	
	public String toString()  {
		return talon.toString();
	}
	
	public CBCANTalon updateTable() {
		talon.updateTable();
		return this;
	}
	
	@Override
	public CBCANTalon stopMotor() {
		talon.disableControl();
		return this;
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
}
