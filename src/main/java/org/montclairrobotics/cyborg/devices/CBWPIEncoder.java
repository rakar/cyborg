package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

public class CBWPIEncoder implements CBIEncoder {
    Encoder encoder;
    public CBWPIEncoder(final int channelA, final int channelB, boolean reverseDirection,
                        final CounterBase.EncodingType encodingType) {
        encoder = new Encoder(channelA, channelB, reverseDirection, encodingType);
    }

    public CBWPIEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection,
                   final CounterBase.EncodingType encodingType) {
        encoder = new Encoder(sourceA, sourceB, reverseDirection, encodingType);
    }

    @Override
    public double getRaw() {
        return encoder.getRaw();
    }

    @Override
    public void reset() {
        encoder.reset();
    }
}
