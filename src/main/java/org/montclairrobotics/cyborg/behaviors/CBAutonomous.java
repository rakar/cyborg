package org.montclairrobotics.cyborg.behaviors;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.utils.CBModule;

/**
 * Base class for all Autonomous behaviors
 */
public abstract class CBAutonomous extends CBModule {

    public CBAutonomous(Cyborg robot) {
        super(robot);
    }

    public void init() {
    }

    public void update() {
    }
}
