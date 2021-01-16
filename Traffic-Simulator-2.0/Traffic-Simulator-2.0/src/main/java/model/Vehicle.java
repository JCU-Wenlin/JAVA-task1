package main.java.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static main.java.model.Road.Orientation.*;
import static main.java.model.TrafficLight.TrafficLightStatus.*;
import static main.java.model.Vehicle.Direction.*;

public abstract class Vehicle {

    public enum Direction {
        EAST, WEST, SOUTH, NORTH
    }
    private static final int STOPPED = 0;
    protected String id;        // unique identifier
    protected int length;       // number of segments occupied
    protected int breadth;      // breadth of a car is half its length
    protected int[] tailPosition; // tail position on current road
    protected int[] headPosition; // head position on current road
    protected int speed;        // segments moved per turn
    private Direction heading;  // heading direction
    private Road currentRoad;   // current Model.Road object
    protected Color colour;     // colour of the car

    // Constructor
    public Vehicle(String id, Road currentRoad, Direction direction) {
        this.id = id;
        // add this vehicle to the road its on
        this.currentRoad = currentRoad;
        this.currentRoad.getVehiclesOnRoad().add(this);
        // set initial speed to road speed limit
        this.speed = this.currentRoad.getSpeedLimit();
        // heading direction
        this.heading = direction;
        // random colour
        this.colour = randomColour();
    }

    // Initial position on road
    protected void setInitPosition(){
        if (currentRoad.getOrientation() == HORIZONTAL && this.heading == EAST){
            // heading east on a horizontal road on the left lane
            this.tailPosition = new int[]{currentRoad.getStartLocation()[0], currentRoad.getStartLocation()[1]};
            this.headPosition = new int[]{this.tailPosition[0] + this.length, this.tailPosition[1]};
        }
        else if (currentRoad.getOrientation() == HORIZONTAL && this.heading == WEST) {
            // heading west on a horizontal road on the left lane
            this.tailPosition = new int[]{currentRoad.getEndLocation()[0], currentRoad.getEndLocation()[1]
                    + currentRoad.getWidth() / 2};
            this.headPosition = new int[]{this.tailPosition[0] - this.length, this.tailPosition[1]};
        }
        else if (currentRoad.getOrientation() == VERTICAL && this.heading == NORTH) {
            // heading north on a vertical road on the left lane
            this.tailPosition = new int[]{currentRoad.getEndLocation()[0], currentRoad.getEndLocation()[1]};
            this.headPosition = new int[]{this.tailPosition[0], this.tailPosition[1] - this.length};

        }
        else if (currentRoad.getOrientation() == VERTICAL && this.heading == SOUTH) {
            // heading south on a vertical road on the left lane
            this.tailPosition = new int[]{currentRoad.getStartLocation()[0] + currentRoad.getWidth() / 2,
                    currentRoad.getStartLocation()[1]};
            this.headPosition = new int[]{this.tailPosition[0], this.tailPosition[1] + this.length};
        }
        else{
            System.err.println("Vehicle init error.");
            System.exit(0);
        }
    }

    // The Move method
    public void move() {
        // Calculate the next head position based on current speed
        HashMap<String, int[]> hmNextPosition = calcNextPosition();
        int[] nextHeadPosition = hmNextPosition.get("nextHeadPosition");
        int[] nextTailPosition = hmNextPosition.get("nextTailPosition");
        // Collision check: if there is a safe distance to the front vehicle
        if (collisionCheck(nextHeadPosition)){
            System.out.println("Collision risk detected!");
            // If there is a vehicle in front of this vehicle and there is no enough room to move forward
            // this vehicle would stop
            // TODO: make the vehicle slowing down rather than fully stopped
            this.speed = STOPPED;
        }
        // Red light check
        else if (redLightCheck(nextHeadPosition)){
            System.out.println("Red light detected!");
            this.speed = STOPPED;
        }
        else {
            // otherwise, this vehicle continue travelling at the current speed limit
            speed = this.currentRoad.getSpeedLimit();
        }
        // Reaching the end of current road
        if (roadEndCheck(nextHeadPosition)) {
            System.out.println("End of road detected!");
            if(!this.currentRoad.getConnectedRoads().isEmpty()) {
                // remove this vehicle from the current road
                this.currentRoad.getVehiclesOnRoad().remove(this);
                // move this vehicle to a random connected road
                ArrayList<Road> connectedRoads = currentRoad.getConnectedRoads();
                int nextRoadIndex = new Random().nextInt(connectedRoads.size());
                this.currentRoad = connectedRoads.get(nextRoadIndex);
                this.currentRoad.getVehiclesOnRoad().add(this);
                this.tailPosition = currentRoad.getStartLocation();
            }
        }
        else {
            // System.out.println("Moving on!");
            // or if still traveling on the current road
            this.headPosition = nextHeadPosition;
            this.tailPosition = nextTailPosition;
        }
    }

    // Calculate the next head position if travelling on current speed
    public HashMap<String, int[]> calcNextPosition(){
        int[] nextHeadPosition = new int[]{0, 0};
        int[] nextTailPosition = new int[]{0, 0};
        switch (this.currentRoad.getOrientation()){
            case HORIZONTAL:
                if (this.heading == EAST){
                    nextHeadPosition = new int[]{this.headPosition[0] + this.speed, this.headPosition[1]};
                    nextTailPosition = new int[]{this.tailPosition[0] + this.speed, this.tailPosition[1]};
                }
                else {
                    nextHeadPosition = new int[]{this.headPosition[0] - this.speed, this.headPosition[1]};
                    nextTailPosition = new int[]{this.tailPosition[0] - this.speed, this.tailPosition[1]};
                }
                break;
            case VERTICAL:
                if (this.heading == NORTH){
                    nextHeadPosition = new int[]{this.headPosition[0], this.headPosition[1] - this.speed};
                    nextTailPosition = new int[]{this.tailPosition[0], this.tailPosition[1] - this.speed};
                }
                else {
                    nextHeadPosition = new int[]{this.headPosition[0], this.headPosition[1] + this.speed};
                    nextTailPosition = new int[]{this.tailPosition[0], this.tailPosition[1] + this.speed};
                }
                break;
        }
        HashMap<String, int[]> map = new HashMap<>();
        map.put("nextHeadPosition", nextHeadPosition);
        map.put("nextTailPosition", nextTailPosition);
        return map;
    }

    // Check if another vehicle is on the same lane of this vehicle
    public boolean laneCheck(Vehicle v){
        boolean sameLane = false;
        switch (this.currentRoad.getOrientation()){
            case HORIZONTAL:
                // same Y
                if (this.headPosition[1] == v.tailPosition[1]){
                    sameLane = true;
                }
                break;
            case VERTICAL:
                // same X
                if (this.headPosition[0] == v.tailPosition[0]){
                    sameLane = true;
                }
                break;
        }
        return sameLane;
    }

    // Check if there is a risk of collision with front vehicle
    public boolean collisionCheck(int[] nextHeadPosition){
        boolean riskFlag = false;
        // As the order in which vehicles are updated on a lane is from the end of the lane backwards,
        // the front vehicles would have been moved before this step.
        for (Vehicle nextVehicle : this.currentRoad.getVehiclesOnRoad()) {
            // check if on the same lane
            if (laneCheck(nextVehicle)) {
                switch (this.currentRoad.getOrientation()){
                    case HORIZONTAL:
                        // same Y, check X
                        if (this.heading == EAST && nextVehicle.tailPosition[0] > this.headPosition[0]
                                && nextVehicle.tailPosition[0] <= nextHeadPosition[0]){
                            riskFlag = true;
                            break;
                        }
                        else if (this.heading == WEST && nextVehicle.tailPosition[0] < this.headPosition[0]
                                && nextVehicle.tailPosition[0] >= nextHeadPosition[0]){
                            riskFlag = true;
                            break;
                        }
                        break;
                    case VERTICAL:
                        // same X, check Y
                        if (this.heading == NORTH && nextVehicle.tailPosition[1] < this.headPosition[1]
                                && nextVehicle.tailPosition[1] >= nextHeadPosition[1]){
                            riskFlag = true;
                            break;
                        }
                        else if (this.heading == SOUTH && nextVehicle.tailPosition[1] > this.headPosition[1]
                                && nextVehicle.tailPosition[1] <= nextHeadPosition[1]){
                            riskFlag = true;
                            break;
                        }
                        break;
                }
            }
        }
        return riskFlag;
    }

    // Check if there is a red light ahead
    private boolean redLightCheck(int[] nextHeadPosition){
        boolean redLightAhead = false;
        // check if there is any traffic light at all
        if (!this.currentRoad.getLightsOnRoad().isEmpty()) {
            TrafficLight light = this.currentRoad.getLightsOnRoad().get(0);
            // if there is a traffic light ahead and it is red light
            switch (this.currentRoad.getOrientation()){
                case HORIZONTAL:
                    if (this.heading == EAST && nextHeadPosition[0] + 1 >= light.getPosition()[0]
                            && light.getState().equals(RED)) {
                        redLightAhead = true;
                    }
                    else if (this.heading == WEST && nextHeadPosition[0] - 1 <= light.getPosition()[0]
                            && light.getState().equals(RED)) {
                        redLightAhead = true;
                    }
                    break;
                case VERTICAL:
                    if (this.heading == NORTH && nextHeadPosition[1] - 1 <= light.getPosition()[1]
                            && light.getState().equals(RED)) {
                        redLightAhead = true;
                    }
                    else if (this.heading == SOUTH && nextHeadPosition[0] + 1 <= light.getPosition()[1]
                            && light.getState().equals(RED)) {
                        redLightAhead = true;
                    }
                    break;
            }
        }
        return redLightAhead;
    }

    // Check if reaching the end of road
    private boolean roadEndCheck(int[] nextHeadPosition){
        boolean roadEnd = false;
        switch (this.currentRoad.getOrientation()){
            case HORIZONTAL:
                if (this.heading == EAST && nextHeadPosition[0] > this.currentRoad.getEndLocation()[0]) {
                    roadEnd = true;
                }
                else if (this.heading == WEST && nextHeadPosition[0] < this.currentRoad.getStartLocation()[0]) {
                    roadEnd = true;
                }
                break;
            case VERTICAL:
                if (this.heading == NORTH && nextHeadPosition[1] < this.currentRoad.getStartLocation()[1]) {
                    roadEnd = true;
                }
                else if (this.heading == SOUTH && nextHeadPosition[1] > this.currentRoad.getEndLocation()[1]) {
                    roadEnd = true;
                }
                break;
        }
        return roadEnd;
    }

//
//    public void draw(Graphics g, int scale) {
//        int xValue = 0;
//        int yValue = 1;
//        if (currentRoad.getOrientation() == HORIZONTAL) {
//            int[] startLocation = getCurrentRoad().getStartLocation();
//            int width = getLength() * scale;
//            int height = getBreadth() * scale;
//            int x = (getTailPosition() + startLocation[xValue]) * scale;
//            int y = (startLocation[yValue] * scale) + scale;
//            g.setColor(colour);
//            g.fillRect(x, y, width, height);
//        } else if (currentRoad.getOrientation() == VERTICAL) {
//            int[] startLocation = getCurrentRoad().getStartLocation();
//            int width = getBreadth() * scale;
//            int height = getLength() * scale;
//            int x = (startLocation[xValue] * scale) + ((currentRoad.getWidth() * scale) - (width + scale));
//            int y = (getTailPosition() + startLocation[yValue]) * scale;
//            g.setColor(colour);
//            g.fillRect(x, y, width, height);
//        }
//    }

    private Color randomColour() {
        Random random = new Random();
        int r = random.nextInt(245 + 1) + 10;
        int g = random.nextInt(245 + 1) + 10;
        int b = random.nextInt(245 + 1) + 10;
        return new Color(r, g, b);
    }

    public void printStatus() {
        System.out.printf("%s going:%dm/s on %s at position:%s%n", this.getId(), this.getSpeed(), this.getCurrentRoad().
                getId(), this.getTailPosition());
    }

    // Getters and setters
    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return this.breadth;
    }

    public void setBreadth() {
        this.breadth = this.length / 2;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int[] getTailPosition() {
        return this.tailPosition;
    }

    public int[] getHeadPosition() {
        return this.headPosition;
    }

    public Road getCurrentRoad() {
        return this.currentRoad;
    }

    public String getId() {
        return this.id;
    }

    public Direction getHeadingDirection(){
        return this.heading;
    }
}

