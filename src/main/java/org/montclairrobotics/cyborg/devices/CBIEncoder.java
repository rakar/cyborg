package org.montclairrobotics.cyborg.devices;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public interface CBIEncoder {

    int getRaw();

    void reset();

}
