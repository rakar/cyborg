package org.montclairrobotics.cyborg.simulation;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class CBWPIPDB extends PowerDistributionPanel implements CBIPDB {

    public CBWPIPDB() {
        super(0);
    }

    public CBWPIPDB(int module) {
        super(module);
    }
}
