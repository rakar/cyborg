package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

public class CBEncoder extends Encoder implements CBDevice {

	public CBEncoder(int aChannel, int bChannel) {
		super(aChannel, bChannel);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource) {
		super(aSource, bSource);
	}

	public CBEncoder(int aChannel, int bChannel, boolean reverseDirection) {
		super(aChannel, bChannel, reverseDirection);
	}

	public CBEncoder(int aChannel, int bChannel, int indexChannel) {
		super(aChannel, bChannel, indexChannel);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection) {
		super(aSource, bSource, reverseDirection);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource, DigitalSource indexSource) {
		super(aSource, bSource, indexSource);
	}

	public CBEncoder(int aChannel, int bChannel, boolean reverseDirection, EncodingType encodingType) {
		super(aChannel, bChannel, reverseDirection, encodingType);
	}

	public CBEncoder(int aChannel, int bChannel, int indexChannel, boolean reverseDirection) {
		super(aChannel, bChannel, indexChannel, reverseDirection);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection,
			EncodingType encodingType) {
		super(aSource, bSource, reverseDirection, encodingType);
	}

	public CBEncoder(DigitalSource aSource, DigitalSource bSource, DigitalSource indexSource,
			boolean reverseDirection) {
		super(aSource, bSource, indexSource, reverseDirection);
	}

	@Override
	public void senseUpdate() {
		
	}

	@Override
	public void controlUpdate() {
		
	}
}
