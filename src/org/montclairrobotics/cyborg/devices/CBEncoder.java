package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Encoder.IndexingType;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.tables.ITable;

public class CBEncoder implements CBDevice{
	Encoder encoder;

	public CBEncoder(int aChannel, int bChannel, EncodingType encodingType) {
		encoder = new Encoder(aChannel, bChannel, false, encodingType);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource, EncodingType encodingType) {
		encoder = new Encoder(aSource, bSource, false, encodingType);
	}

	public CBEncoder setReverseDirection(boolean reverseDirection) {
		encoder.setReverseDirection(reverseDirection);
		return this;
	}

	public CBEncoder setIndexChannel(DigitalSource source) {
		encoder.setIndexSource(source);
		return this;
	}

	public CBEncoder setIndexChannel(int indexChannel) {
		encoder.setIndexSource(indexChannel);
		return this;
	}

	public CBEncoder setIndexChannel(DigitalSource source, IndexingType indexingType) {
		encoder.setIndexSource(source, indexingType);
		return this;
	}

	public CBEncoder setIndexChannel(int indexChannel, IndexingType indexingType) {
		encoder.setIndexSource(indexChannel, indexingType);
		return this;
	}
	
	public CBEncoder setDistancePerPulse(double distancePerPulse) {
		encoder.setDistancePerPulse(distancePerPulse);
		return this;
	}

	public CBEncoder setMaxPeriod(double maxPeriod) {
		encoder.setMaxPeriod(maxPeriod);
		return this;
	}
	
	public CBEncoder setMinRate(double minRate) {
		encoder.setMinRate(minRate);
		return this;
	}
	
	public CBEncoder setPIDSourceType(PIDSourceType pidSource) {
		encoder.setPIDSourceType(pidSource);
		return this;
	}

	public CBEncoder setSamplesToAverage(int samplesToAverage) {
		encoder.setSamplesToAverage(samplesToAverage);
		return this;
	}
	
	public CBEncoder startLiveWindowMode() {
		encoder.startLiveWindowMode();
		return this;
	}
	
	public CBEncoder stopLiveWindowMode() {
		encoder.stopLiveWindowMode();
		return this;
	}
	
	public CBEncoder updateTable() {
		updateTable();
		return this;
	}
	
	public void free() {
		encoder.free();
	}
	
	public int get() {
		return encoder.get();
	}
	
	public boolean getDirection() {
		return getDirection();
	}
	
	public double getDistance() {
		return getDistance();
	}
	
	public int getEncodingScale() {
		return getEncodingScale();
	}
	
	public int getFPGAIndex() {
		return encoder.getFPGAIndex();
	}
	
	public PIDSourceType getPIDSourceType() {
		return encoder.getPIDSourceType();
	}
	
	public double getRate() {
		return getRate();
	}
	
	public int getRaw() {
		return encoder.getRaw();
	}
	
	public int getSamplesToAverage() {
		return encoder.getSamplesToAverage();
	}
	
	public String getSmartDashboardType() {
		return encoder.getSmartDashboardType();
	}
	
	public boolean getStopped() {
		return encoder.getStopped();
	}
	
	public ITable getTable() {
		return encoder.getTable();
	}
	
	public CBEncoder initTable(ITable subtable) {
		encoder.initTable(subtable);
		return this;
	}
	
	public double pidGet() {
		return encoder.pidGet();
	}
	
	public CBEncoder reset() {
		encoder.reset();
		return this;
	}
	
	@Override
	public void senseUpdate() {
		
	}

	@Override
	public void controlUpdate() {
		
	}

}
