package org.montclairrobotics.cyborg.mappers;

import org.montclairrobotics.cyborg.Cyborg;
import org.montclairrobotics.cyborg.devices.CBSpeedController;
import org.montclairrobotics.cyborg.devices.CBSpeedControllerFault;

import java.util.ArrayList;

public class CBMotorMonitorMapper extends CBCustomMapper {
    ArrayList<CBSpeedController> controllers = new ArrayList();

    public CBMotorMonitorMapper(Cyborg robot) {
        super(robot);
    }

    public CBMotorMonitorMapper add(CBSpeedController controller) {
        controllers.add(controller);
        return this;
    }

    @Override
    public void update() {
        for(CBSpeedController controller: controllers) {
            CBSpeedControllerFault fault = controller.getSpeedControllerFault();
            controller.getActualCurrent();
        }
    }
}
