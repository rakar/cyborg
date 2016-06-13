package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.SpeedController;

public class CBSpeedControllerWrapper implements CBDevice, CBSpeedController{

	private SpeedController controller; 
	
	public CBSpeedControllerWrapper(SpeedController controller) {
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#pidWrite(double)
	 */
	@Override
	public CBSpeedController pidWrite(double output) {
		controller.pidWrite(output);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#get()
	 */
	@Override
	public double get() {
		return controller.get();
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#set(double)
	 */
	@Override
	public CBSpeedController set(double speed) {
		controller.set(speed);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#setInverted(boolean)
	 */
	@Override
	public CBSpeedControllerWrapper setInverted(boolean isInverted) {
		controller.setInverted(isInverted);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#getInverted()
	 */
	@Override
	public boolean getInverted() {
		return controller.getInverted();
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#disable()
	 */
	@Override
	public CBSpeedController disable() {
		controller.disable();
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#stopMotor()
	 */
	@Override
	public CBSpeedController stopMotor() {
		controller.stopMotor();
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#senseUpdate()
	 */
	@Override
	public void senseUpdate() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#controlUpdate()
	 */
	@Override
	public void controlUpdate() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.devices.CBSpeedController#configure()
	 */
	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
	
}
