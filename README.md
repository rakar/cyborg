# cyborg
Yet Another FRC Framework

The cyborg project is an attempt to streamline the development of the human/robot interface, specifically as it applies to the First Robotics Challenge. 

The intent is to provide a core framework to control the overall process, along with a set of extendable, customizable, replaceable plug-in modules that each perform simple well defined functions. In combination, the framework and these modules, along with custom user modules should be able to perform any of the basic to complex functions required by modern competitive robots. 

### Quick Notes:
- This repo is the framework project itself. Users who want to work on cyborg development should continue delving into this repo. Users who simply want to use the framework should probably look at the cyborg wiki and the [CBFRCRobot2019](https://github.com/rakar/CBFRCRobot2019) repo.
- If you do clone this repo, be aware that it contains submodules so you must execute **git submodule update --init** in the root directory of the cloned project in order to pull the submodule source.

An overview chart can be found [here](https://drive.google.com/open?id=1_jMmhirzTuN9DtRAc1PL9qTd5cR360GLOtq_dHbUayQ).

At the center of the system is a group of "behaviors" in the logic layer that act on high-level "request" inputs and produce high-level "control" outputs. These behaviors form the main logic layer for the system. For example, in this layer a high-level movement request would be analyzed and converted into high-level drivetrain control instructions. This would be done without needing to know if the movement request came from a tank drive joystick setup or an arcade drive joystick setup or possibly not even from a driver at all if the robot were driving autonomously. Furthermore, the drive train instructions would also be high level and completely independent of the number of motors being controlled on any given chassis and to some degree even independent of the type of chassis. The robot can run any number of behaviors so that independent functionality can be broken into separate modules (i.e. drivetrain and manipulator.) Cyborg behaviors define the core functionality of the robot and run all the time (even in autonomous mode.) These behaviors control the functionality of the robot based on requests whether or the request come from a driver/operator or autonomous routines. Additionally, there is a set of autonomous behaviors that only run during autonomous mode (prior to the standard behaviors) and are meant to read sensor data saved in the custom request data store (if necessary) and load the request data stores in place of driver/operator control. 

On the input side "mappers" convert raw low-level input data into the high-level requests. This is done in a manner that abstracts the "information" from the data so following the example above, tank drive joystick controls, arcade drive joystick controls, Xbox controllers and even button boxes could produce the same high-level request. This could also be used to setup various control schemes for left or right handers. The mappers map the hardware messages from the defined devices into informational messages for the logic layer. There are two groups of mappers, TeleOp mappers, which only run in TeleOp mode and Sensor mappers, which always run. This will prevent the TeleOp mappers from sending "dead stick" data which might conflict with Autonomous control data.   

On the output side "controllers" convert the high-level output data from the logic layer into hardware specific control signals. For example, a movement message requesting forward movement with a turn to the left could be translated into left and right power values which in turn would each be sent to two left and two right side motor controllers for a common differential (tank drive) chassis. While they might be translated into four coordinated power levels for a classic mecanum drive chassis or a different set of values for an asymmetric mecanum drive chassis. 

By isolating the logic layer, the input mappers, and the output controllers the entire process is broken down into clear discrete components that will hopefully allow for efficient implementation with a high degree of code reuse and a high degree of flexibility. 

Connecting the mappers and the behaviors and similarly the behaviors and the controllers are a number of data stores. These data stores act as the output of one "zone" and the input to another. Specifically, the mappers read from the devices defined in the hardware adapter and output the results to a data store representing the high-level requests. The behaviors then read from these same data store, determine what should be done and then output the desired control request to the control data store, which are in turn read by the controllers which execute the control requests by sending outputs full circle to the appropriate devices in the hardware adapter. The request data a specific property for the drivetrain data. The controller data does as well. This is done to facilitate using standard pre-built data stores and standard pre-built mappers, behaviors and controllers to control the drive train. These are often standard enough that they can be used "off the shelf" saving significant time in getting a robot up and running. Of course custom components can be used for any piece or all of the drivetrain control if desired. It is our hope that the tools provided in the framework will cover most (hopefully virtually all) drivetrains. On the other hand, manipulator control is almost impossible to predict, so additional properties will be added to custom extensions of the data stores to house this control data. These can hold whatever data is required by the mappers, behaviors and controllers you create to manage the rest of your robot. An additional data store is provided for the logic layer to maintain any additional data that would be required within the logic components. The autonomous behaviors are special in that they write to the Request data store.  

Additionally, the structured framework should allow for the creation of a rich toolset that will further promote rapid reliable code development. 

Cyborg uses the naming convention that other than the Cyborg itself, all of the classes begin with CB. it recommended that all classes for a given implementation have a similar class prefix. For example for StrongHold you might use "SH" as the prefix. Assuming that the robot class would be called SHRobot.java and the Robot class in the .robot package would extend SHRobot. You might also have SHD... for StrongHold Development and SHP... for StrongHold Production.

Project Source Structure:
A robot project should contain a package named org.usfirst.frc.team###.robot. This package should contain a single Robot.java class that should do nothing but extend the "true" implementation of the robot from a different package. This structure will allow for the implementation of multiple "robots" in multiple packages either for different years or different implementations. These can be swapped very easily by changing the class that Robot extends.

Additional packages should contain: 
A class representing the robot itself, which extends Cyborg. (Ex. SHRobot.java)
Data classes for any custom data required. (Ex. SHCustomRequestData.java extending CBRequestData, SHCustomControlData.java extending CBControlData, and SHCustomLogicData.java extending CBLogicData)
Mapper classes to collect data from the driver and sensors. (Ex. SHOperatorMapper.java extending CBTeleOpMapper and SHSensorMapper.java extending CBCustomMapper)
Behavior classes for custom/autonomous behaviors. (Ex. SHCustomerBehavior.java extending CBBehavior or SHAutonomous extending CBAutonomous)
Controller classes for any custom device control. (Ex. SHCustomController.java extending CBRobotController)

Custom data, mapper, behavior, and controller classes are only needed if standard ones can't be used. 

 





Implementation Directives:

Functional Isolation
Any component should only operate within a single zone of the system. For example mappers should only connect devices inputs and the request data stores. They should never access the control data stores or device outputs. Likewise, behaviors should never look at the devices directly, but instead should only access the request and control data stores. Controllers should only access the control data stores and devices.

Device Catalog
Devices are defined and added to the HardwareAdapter which manages them. From then on a DeviceId is used to reference the device in other Cyborg classes. 

InitHeavy/RunLight
Functionality in any periodic code should be as thin or "light" as possible, even if this requires that the initialization and setup routines are "heavy."

Currying
Currying or method linking is used whenever possible. 



