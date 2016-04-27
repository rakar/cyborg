package org.montclairrobotics.cyborg.utils;


//TODO: add a delta time calculation to adjust for variable update periods.

public class CBPID {
	private double P,I,D,minIn,maxIn,minOut,maxOut;
	
	private double in,out;
	private double target;
	private double totalError, prevError, error;

	/**
	 * @param P the Propotional Constant
	 * @param I the Integral Constant
	 * @param D the Derivitive Constant
	 */
	public CBPID(double P,double I,double D)
	{
		this.P=P;
		this.I=I;
		this.D=D;
	}
	
	/**
	 * @param minIn the minimum input, or 0 to ignore. Use with maxIn to "wrap" the values, 
	 * eg. so the error between 5 degrees and 355 degrees is 10 degrees
	 * @param maxIn the maximum input, or 0 to ignore
	 */
	public CBPID setInputLimits(double minIn,double maxIn)
	{
		this.minIn = minIn;
		this.maxIn = maxIn;
		return this;
	}
	
	/**
	 * 
	 * @param minOut the minimum output to constrain to, or 0 to ignore
	 * @param maxOut the maximum output to constrain to, or 0 to ignore
	 */
	public CBPID setOutputLimits(double minOut, double maxOut)
	{
		this.minOut=minOut;
		this.maxOut=maxOut;
		return this;
	}

	public CBPID setPID(double P, double I, double D){
		this.P=P;
		this.I=I;
		this.D=D;
		return this;
	}
	
	/**
	 * Copy constructor so you can copy PID controllers
	 * @return a copy of this PID controller
	 */
	public CBPID copy()
	{
		return new CBPID(P,I,D)
				.setInputLimits(minIn, maxIn)
				.setOutputLimits(minOut, maxOut);
	}
	
	
	public CBPID setTarget()
	{
		return setTarget(0.0,true);
	}
	
	public CBPID setTarget(double t)
	{
		return setTarget(t,true);
	}
	
	/**
	 * Sets the setpoint
	 * @param t the target/setpoint
	 * @param reset true if the PID should reset, false otherwise
	 */
	public CBPID setTarget(double t, boolean reset)
	{
		target=t;
		if(reset)
		{
			this.reset();
		}
		return this;
	}
	
	public CBPID reset() {
		error=0.0;
		prevError=0.0;
		totalError=0.0;	
		return this;
	}
		
	public CBPID resetIAccum() {
		totalError=0.0;	
		return this;
	}
		
	private double calculate(double actual)
	{
		error=target-actual;
		
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
	
	public double update(double source)
	{
		prevError=error;
		in=source;
		out = calculate(source);
		return out;
	}
	
	public double getIn()
	{
		return in;
	}
	
	public double getError(){
		return error;
	}

	public double getOut()
	{
		return out;
	}
}
