# Working Document of the Traffic Simulator

# 1. Problem Specification
The problem is to simulate simplified city traffic conditions which involves only three elements: roads, traffic lights 
and vehicles. The program is consist of a GUI Java Programming and backend application logic programming, and will be
used by users via the front-end. User would be able to create road layouts and input other parameters via the front-end 
interface, and then run and observe the simulation and summary statistics on-the-fly. The backend Java code would execute 
the logic and rules as defined in the requirement.

# 2. Problem Decomposition
The problem can be conceptually decomposed into three main classes: vehicles, road and traffic lights, as the UML shown. 
These three elements are interdependent, as vehicles run on roads and traffic lights sit on the start or end of roads, 
and traffic lights decide if the vehicles can move forward. Vehicle will be an abstract class, and car, bus and motorbike 
will be inherited from this abstract class.

As to the front-end, it has a main window which loads the city editor panel, the simulation panel, menu bars and menu
items. By selecting and clicking corresponding menu items, the user would be able to set up a city road map and run
simulations.

# 3. Class & Method Design
# model.Road
As described in the requirement, the road has two lines and following the left-hand traffic rules. It has a length limit
which may be better implemented in the front-end. 

Attributes;
- *id* - a unique identifier that will differentiate each road
- *speedLimit* – the maximum speed that vehicles on that road may travel at.
- *length* – the number of segments the road is comprised of and the physical space it occupies.
- *width* – the minimum width of the road, each lane is at least as wide as the breadth of the bus.
- *startLocation* – the (x,y) coordinate that represents where the road begins.
- *endLocation* – the (x,y) coordinate that represents where the road ends.
- *connectedRoadss* – all of the roads that this road is physically connected to.
- *lightsOnRoad* – all the traffic lights that are on the ends this road.
- *vehiclesOnRoad* – all of the vehicles that are currently traveling on this road.
- *orientation* - The direction the road is facing graphically.

Some key methods including:
- setEndLocation(): set the end location of the road.
- getVehiclesOnRoad(): count number of vehicles on the road (both lanes).
- getConnectedRoads(): get a list of connected roads.
- addConnectedRoad(): add a connected road to this road.
and getter and setter methods.
  
# model.RoadIntersection (TODO)
As described in the requirement, ideally introducing another class for intersection which includes of the 4-way and 
3-way road intersections. At the end of a road, the user has to connect it with either another road or one of these
two intersections. Due to time constraint, this class is not implemented.

# model.TrafficLight
A traffic light is associated with a road and can sit either the start or the end of road. As generally on two-lane
roads traffic lights are placed at both lanes at the intersection to stop traffic, I believe there is no need to
implement traffic lights on a single lane.

Attributes:
- *id* - a unique identifier that will differentiate each traffic light.
- *state* - the colour the ligth is displaying, either RED or GREEN.
- *position* - where the traffic light is located on the road.
- *roadAttachedToo* - the road that the light is attached to.

Key methods:
- operate(): randomly change the state of the traffic light

# model.Vehicle (abstract)
The vehicle class will be abstract which defines the common attributes and behaviours of all types of vehicles. And
then car, bus and motorbike classes will extend this class.

Attributes:
- *id* – a unique identifier
- *length* – the length of the vehicle.
- *breadth* – the width of the vehicle, which is half of its length.
- *speed* – how far the vehicle moves for each simulation turn.
- *headPosition* – the position of the head of the vehicle.
- *tailPosition* – the position of the tail of the vehicle.
- *currentRoad* – the road the vehicle is currently traveling on.
- *heading* – the direction the vehicle is heading. As the road has two lanes and two orientations, vehicles that 
              travelling on the road can have four heading directions: east, west, north and south.
- *colour* - a colour assigned to each vehicle for when it is represented graphically.

Key methods:
- move()： implements the logic that how a vehicle should move; it consists of several other methods;
- calcNextPosition(): calculate the next head and tail positions depends on the speed;
- laneCheck(): check if another vehicle is in the same lane as this vehicle;
- collisionCheck(): check if there is a risk of collision with front vehicle;
- redLightCheck(): check if there is a red light ahead;
- roadEndCheck(): check if reaching the end of road;

# model.Car
It extends the Vehicle class with a specific length of cars.

# model.Bus
It extends the Vehicle class with a specific length of buses (3 times of the length of cars).

# model.Motorbike
It extends the Vehicle class with a specific length of motorbike (half size of the length of cars).

# view.MainWindow
The main window / JFrame of the front-end interface. It loads the simulation panel, the editor panel, as well as
other components e.g. menu, buttons. Due to time constraint, the simulator is not fully functioning yet.

# view.SimulationPanel
- *vehiclesToSpawn* - total numbers of vehicles to be spawned in the sim,
- *vehiclesSpawned* - how many vehicles have been spawned so far.
- *vehiclesRemoved* - vehicles that have reached the end of the map and been removed.
- *numberOfCycles* - the number of simulation cycles between spawns.
- *scale* - the scale of objects drawn graphically.

This class will generate the graphical display of the simulation within the main frame. Loading the location of road
and traffic light objects from the editor. Vehicles will be created for the simulation, types of vehicle created
bing random, here and the update rate is also set here depending on user input form dialog boxes in main. The simulation
will be run within a timer.

# view.EditorPanel
- *scale* - the scale of objects drawn graphically.
  This class will generate the graphical display for the city editor within the main frame. Allowing users to click
  to place roads, letting them define orientation and connection with dialog boxes within the New menu option.

# util.SaveFile
This class will handel the saving of city maps.

# util.LoadFile
This class will load a saved city map.

# test.*
Those 3 scripts implemented unit tests for each of the three key classes. Due to time constraint, the tests of VehicleTest
are not exhaustive.

# RunSimulator
This class will have the main() method that will Call the simulation and editor panel. It will also Have the
menus used for user navigation. User input for simulation parameters will also be handled here; number of vehicles
spawned 
