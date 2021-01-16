Reference: https://www.atlassian.com/agile/project-management/user-stories

=== User Stories ===

User Story - 1
As a user, I want to be able create new city maps in the GUI, so that I can run simulations on the maps that I created.

User Story - 2
As a user, I want to be able to save the city maps that I created so that I can use it later. Moreover, when I click the
Save button, it should pop up a window box to allow me select a location to save the file.

User Story - 3
As a user, I want to be able to load the city maps that I created so that I can run simulation on them or modify the
layouts. This would be done via a pop up window from which I can locate the saved city maps.

User Story - 4
As a user, I want to use a 'drag-and-drop' style to select roads in the GUI, define the length, orientation, and speed
limit of each road, and then place them anywhere within the canvas.

User Story - 5
As a user, I want to use a 'drag-and-drop' style to select traffic lights in the GUI and put them at the start or end 
of the roads as I wish.

User Story - 6
As a user, I want to be able to input the number of vehicles of each type to spawn in the GUI, so that I can run the
simulation with different number of vehicles running on the road.

User Story - 7
As a user, I want to be able to update vehicle spawn rate before and during the simulation, so that I can control how
many vehicles are on the roads at the same time.

User Story - 8
As a user, I want to be able to generate a random map with a number of roads and traffic lights, so that I don't have
to spend time in doing it myself.

User Story - 9
As a user, I want to be able to run, pause and stop the simulation at any time, so that I can have a better control of
the simulation process.

User Story - 10
As a user, I want to be able to download the simulation report once a simulation has ended, which would include the
logs and the summary statistics, so that I can store and analyse the simulation results.

User Story - 11
As a user, I want to be able to decide where the vehicles are spawn on the map. For example, they can be random spawn
on the roads, or enter the map from four corners etc. 

User Story - 12
As a user, I want to be able to add 3-way or 4-way intersections at the end of a road, and then continue adding more
roads to those intersections. Also, I want to be able to rotate the 3-way intersections, so that I can have a more
realistic road network.


=== Developer User Stories ===

Developer User Story - 1
As a developer, I want to add proper constraints to the road layout that the user trying to build. For example, the
system should not allow user to have overlapped roads, and traffic lights can only be put at the start or end of roads.

Developer User Story - 2
As a developer, I want to add proper constraints to user numerical inputs dynamically. For example, the length of the
road cannot exceed the size of canvas, the total number of vehicles cannot exceed the overall capacity of the road
network, etc.

Developer User Story - 3
As a developer, I want to make sure no collision would happen in the simulation and the vehicles can slow down if there
is another vehicle in front of it and there is no enough safe room.

Developer User Story - 4
As a developer, I want to ensure a smooth user experience during the simulations, by minimising errors and writing
efficient code or parallelism.

Developer User Story - 5
As a developer, I want to log the status of simulation at each move, which includes the number of vehicles and their
positions, the status of traffic lights, and the summary statistics.

Developer User Story - 6
As a developer, I want to show the statics about the number of vehicles and traffic lights, and the average speed of
the vehicles in the GUI in real-time, and the average statistics of the last 3/5/10 minutes to the user, so that the
user can have a better understanding of the simulation.




