# cyborg
Yet Another FRC Framework

The cyborg project is an attempt to streamline the development of the human/robot interface, specifically as it applies to the First Robotics Challenge. 

The intent is to provide a core framework to control the overall process, along with a set of extendable, customizable, replaceable plug-in modules that each perform simple well defined functions. In combination the framework and these modules along with custom user modules should be able to perform any of the basic to complex functions required by modern competitive robots. 

At the center of the system is a group of "processors" in the logic layer that act on high-level "request" inputs and produce high-level "control" outputs. These processors form the main logic layer for the system. For example, in this layer a high-level movement request would be analyzed and converted into high-level drivetrain control instructions. This would be done without needing to know if the movement request came from a tank drive joystick setup or an arcade drive joystick setup or possibly not even from a driver at all if the robot were driving autonomously. Furthermore, the drive train instructions would also be high level and completely independent of the number of motors being controlled on any given chassis and to some degree even the type of chassis.   

On the input side "mappers" convert raw low-level input data into the high-level requests. This is done in a manner that abstracts the "information" from the data so following the example above, tank drive joystick controls, arcade drive joystick controls, Xbox controllers and even button boxes could produce the same high-level request. This could also be used to setup various control schemes for left or right handers. The mappers map the hardware messages into informational messages for the logic layer.

On the output side "controllers" convert the high-level output data from the logic layer into hardware specific control signals. For example, a movement message requesting forward movement with a turn to the left could be translated into left and right power values which in turn would each be sent to two left and two right side motor controllers for a common differential (tank drive) chassis. While they might be translated into four coordinated power levels for a classic mecanum drive chassis or a different set of values for an asymmetric mecanum drive chassis. 

By isolating the logic layer, the input mappers, and the output controllers the entire process is broken down into clear discrete components that will hopefully allow for efficient implementation with a high degree of code reuse and a high degree of flexibility. 

Implementation Approaches:
Devices are defined and added to the HardwareAdapter which manages them. From then on a DeviceId is used to reference the device in other Cybor classes. 

Additionally, the structured framework should allow for the creation of a rich toolset that will further promote rapid reliable code development. 
