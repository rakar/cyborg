package org.montclairrobotics.cyborg.utils;

import java.util.Date;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CBPIDErrorCorrection implements CBErrorCorrection {
	private double minIn,maxIn,minOut,maxOut;
	private double[] k;
	private double in,out;
	private double target;
	private double totalError, prevError, error;
	private long   lastUpdate, thisUpdate; 
	private double timeSpan;

	/**
	 * @param P the Proportional Constant
	 * @param I the Integral Constant
	 * @param D the Derivative Constant
	 */
	public CBPIDErrorCorrection()
	{
		//this.k = k;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setInputLimits(double, double)
	 */
	@Override
	public CBErrorCorrection setInputLimits(double minIn,double maxIn)
	{
		this.minIn = minIn;
		this.maxIn = maxIn;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setOutputLimits(double, double)
	 */
	@Override
	public CBErrorCorrection setOutputLimits(double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setConstants(double[])
	 */
	@Override
	public CBErrorCorrection setConstants(double[] k){
		this.k = k;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setTarget()
	 */
	//public CBPIDController copy()
	//{
	//	return new CBPIDController()
	//			.setConstants(k)
	//			.setInputLimits(minIn, maxIn)
	//			.setOutputLimits(minOut, maxOut);
	//}
	
	
	@Override
	public CBErrorCorrection setTarget()
	{
		return setTarget(0.0,false);
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setTarget(double)
	 */
	@Override
	public CBErrorCorrection setTarget(double t)
	{
		return setTarget(t,false);
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#setTarget(double, boolean)
	 */
	@Override
	public CBErrorCorrection setTarget(double t, boolean reset)
	{
		target=t;
		if(reset)
		{
			this.reset();
		}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#reset()
	 */
	@Override
	public CBErrorCorrection reset() {
		error=0.0;
		prevError=0.0;
		totalError=0.0;
		lastUpdate = 0;
		return this;
	}
		
	public CBErrorCorrection resetIAccum() {
		totalError=0.0;	
		return this;
	}
		
	protected double calculate(double actual)
	{
		double P = k[0];
		double I = k[1];
		double D = k[2];
		
		error=(target-actual) * timeSpan;
		
		// If circular wrap to shortest error
		if(minIn!=0&&maxIn!=0)
		{
			double diff=maxIn-minIn;
			error=((error-minIn)%diff+diff)%diff+minIn;
		}
		
		// Accumulate total error for I term
		// and clamp result 
		// (not sure why were clamping the partial result)
		totalError+=error;
		if (I != 0) 
		{
			double potentialIGain = (error+totalError) * I;
			if (potentialIGain < maxOut) {
				if (potentialIGain > minOut) {
					totalError += error;
				} else {
					totalError = minOut / I;
				}
			} else {
				totalError = maxOut / I;
			}
		}
	
		// calculate correction
		double correction = P * error + I * totalError +
	             D * (error - prevError); //+ calculateFeedForward();

		// moved into update to make calculate "update free"
		// prevError = error;
		
		// Clamp output
		if(minOut!=0 || maxOut!=0)
		{
			if (correction > maxOut) correction=maxOut;
			else if (correction < minOut) correction=minOut;
		}
		
		return correction;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#update(double)
	 */
	@Override
	public double update(double source)
	{
		prevError=error;
		if (lastUpdate == 0) {
			lastUpdate = new Date().getTime();
			out=0;
			//SmartDashboard.putNumber("LastUpdate::", lastUpdate);
		} else {
			thisUpdate = new Date().getTime();
			timeSpan =  (thisUpdate-lastUpdate)/1000.0;
			//SmartDashboard.putNumber("TimeSpan::", timeSpan);
			in=source;
			out = calculate(source);
			lastUpdate = thisUpdate;
		}
		return out;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#getIn()
	 */
	@Override
	public double getIn()
	{
		return in;
	}
	
	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#getError()
	 */
	@Override
	public double getError(){
		return error;
	}

	/* (non-Javadoc)
	 * @see org.montclairrobotics.cyborg.utils.CBErrorCorrection#getOut()
	 */
	@Override
	public double getOut()
	{
		return out;
	}
}
