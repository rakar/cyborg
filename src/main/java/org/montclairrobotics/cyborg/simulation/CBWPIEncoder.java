package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Encoder;

public class CBWPIEncoder extends Encoder implements CBIEncoder {
    public CBWPIEncoder(final int channelA, final int channelB, boolean reverseDirection,
                        final EncodingType encodingType) {
        super(channelA, channelB, reverseDirection, encodingType);
    }

    public CBWPIEncoder(DigitalSource sourceA, DigitalSource sourceB, boolean reverseDirection,
                   final EncodingType encodingType) {
        super(sourceA, sourceB, reverseDirection, encodingType);

    }
}
