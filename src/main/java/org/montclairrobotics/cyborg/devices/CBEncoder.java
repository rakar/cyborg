package org.montclairrobotics.cyborg.devices;

import java.util.ArrayList;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBSource;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.tables.ITable;

public class CBEncoder implements CBDevice, CBSource{
	private Encoder encoder;
	private int edgesPerPulse;
	private double distancePerPulse;
	private int offsetPulses;
	private int offsetEdges;
	private double offsetDistance;
	private ArrayList<CBIndexEntry> indexEntries = new ArrayList<>();
	
	public class CBIndexEntry {
		CBDigitalInput trigger;
		CBDeviceID triggerId;
		boolean activeState;
		double distance;
		
		public CBIndexEntry(CBDeviceID triggerId, boolean activeState, double distance) {
			this.triggerId = triggerId;
			this.activeState = activeState;
			this.distance = distance;
			this.trigger = Cyborg.hardwareAdapter.getDigitalInput(triggerId);
		}
	}

	public CBEncoder(int aChannel, int bChannel, EncodingType encodingType, boolean reversed, double distancePerPulse) {
		encoder = new Encoder(aChannel, bChannel, reversed, encodingType);
		setTickConversion(encodingType);
		setDistancePerPulse(distancePerPulse);
	}
	
	public CBEncoder(DigitalSource aSource, DigitalSource bSource, EncodingType encodingType, boolean reversed, double distancePerPulse) {
		encoder = new Encoder(aSource, bSource, reversed, encodingType);
		setTickConversion(encodingType);
		setDistancePerPulse(distancePerPulse);
	}
	
	private void setTickConversion(EncodingType encodingType) {
		switch(encodingType) {
		case k1X:
			edgesPerPulse = 1;
			break;
		case k2X:
			edgesPerPulse = 2;
			break;
		case k4X:
			edgesPerPulse = 4;
			break;
		}
	}
	
	public CBEncoder setDistance(double distance){
		reset();
		offsetDistance = distance;
		offsetPulses = (int)(distance/distancePerPulse);
		offsetEdges = offsetPulses*edgesPerPulse;
		return this;
	}

	public CBEncoder setPulses(int pulses){
		reset();
		offsetDistance = distancePerPulse*pulses;
		offsetPulses = pulses;
		offsetEdges = offsetPulses*edgesPerPulse;
		return this;
	}

	public CBEncoder setEdges(int edges){
		reset();
		offsetEdges = edges;
		offsetPulses = edgesPerPulse*edges;
		offsetDistance = distancePerPulse*offsetPulses;
		return this;
	}

	public CBEncoder setReverseDirection(boolean reverseDirection) {
		encoder.setReverseDirection(reverseDirection);
		return this;
	}

	// hijack indexing to allow for multiple indexes with non-zero distances
	//public CBEncoder setIndexChannel(DigitalSource source) {
	//	encoder.setIndexSource(source);
	//	return this;
	//}

	//public CBEncoder setIndexChannel(int indexChannel) {
	//	encoder.setIndexSource(indexChannel);
	//	return this;
	//}

	//public CBEncoder setIndexChannel(DigitalSource source, IndexingType indexingType) {
	//	encoder.setIndexSource(source, indexingType);
	//	return this;
	//}

	//public CBEncoder setIndexChannel(int indexChannel, IndexingType indexingType) {
	//	encoder.setIndexSource(indexChannel, indexingType);
	//	return this;
	//}
	
	public CBEncoder addIndexEntry(CBIndexEntry indexEntry) {
		removeIndexEntry(indexEntry.triggerId);
		indexEntries.add(indexEntry);
		return this;
	}
	
	public CBEncoder removeIndexEntry(CBDeviceID triggerId) {
		int trg = -1;
		for(int i=0;i<indexEntries.size();i++) {
			if(indexEntries.get(i).triggerId==triggerId) {
				trg=i;
				break;
			}
		}
		if(trg>-1) {
			indexEntries.remove(trg);
		}
		return this;
	}
	
	public CBEncoder setDistancePerPulse(double distancePerPulse) {
		if (distancePerPulse==0) distancePerPulse=1;
		this.distancePerPulse = distancePerPulse;
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
	
	public CBEncoder updateTable() {
		updateTable();
		return this;
	}
	
	public void free() {
		encoder.free();
	}
	
	public double get() {
		return encoder.get()+offsetPulses;
	}
	
	public boolean getDirection() {
		return encoder.getDirection();
	}
	
	public double getDistance() {
		return encoder.getDistance()+offsetDistance;
	}
	
	public int getEncodingScale() {
		return encoder.getEncodingScale();
	}
	
	public int getFPGAIndex() {
		return encoder.getFPGAIndex();
	}
	
	public PIDSourceType getPIDSourceType() {
		return encoder.getPIDSourceType();
	}
	
	public double getRate() {
		return encoder.getRate();
	}
	
	public int getEdges() {
		return encoder.getRaw()+offsetEdges;
	}
	
	public int getRaw() {
		return encoder.getRaw();
	}
	
	public int getSamplesToAverage() {
		return encoder.getSamplesToAverage();
	}

	
	public boolean getStopped() {
		return encoder.getStopped();
	}
	
	// have to hijack this since our offset system 
	// prevents using the encoder's pidGet
	public double pidGet() {
		switch(encoder.getPIDSourceType()) {
		case kDisplacement:
			return getDistance();
		case kRate:
			return getRate();
		default:
			return 0;
		}
	}
	
	public CBEncoder reset() {
		encoder.reset();
		offsetEdges = 0;
		offsetPulses = 0;
		offsetDistance = 0;
		return this;
	}
	
	@Override
	public void senseUpdate() {
		for(CBIndexEntry i:indexEntries) {
			if(i.trigger.get()==i.activeState) {
				setDistance(i.distance);
			}
		}
	}

	@Override
	public void controlUpdate() {
		
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}
