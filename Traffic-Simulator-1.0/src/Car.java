public class Car {
    private static final int STOPPED = 0; //car speed is 0m/s
    private static final int NEXT_ROAD_INDEX = 0;
    private static final int START_POSITION = 1;
    String id; // unique identifier
    static float length; // number of segments occupied, 1 for ease of prototype.
    private static float breadth;
    private int speed; //segments moved per turn
    private int position; // position on current road
    private Road currentRoad; // current Road object


    public Car(String id, Road currentRoad) {
        this.id = "car_" + id;
        this.currentRoad = currentRoad;
        length = 1f; // cars made 1m long for prototype.
        breadth = length * 0.5f;
        speed = 0;
        position = 1;
        this.currentRoad.getCarsOnRoad().add(this); //add this car to the road its on.
    }

    public Car() {
        id = "000";
        length = 1f;
        breadth = length * 0.5f;
        speed = 0;
        position = 1;
    }

    public void move() {
        //set speed limit to that of currentRoad
        this.speed = this.currentRoad.getSpeedLimit();
        // check red light and if front road space is occupied
        if ((!this.currentRoad.getLightsOnRoad().isEmpty() &&
                this.position == this.currentRoad.getLightsOnRoad().get(0).getPosition() &&
                this.currentRoad.getLightsOnRoad().get(0).getState().equals("red")) | checkRoadSpace()) {
            this.speed = STOPPED;
        }
        else {
            this.speed = this.currentRoad.getSpeedLimit();
            if (this.currentRoad.getLength() == this.getPosition() && !this.currentRoad.getConnectedRoads().isEmpty()) {
                this.currentRoad.getCarsOnRoad().remove(this);
                this.currentRoad = this.currentRoad.getConnectedRoads().get(NEXT_ROAD_INDEX);
                this.currentRoad.getCarsOnRoad().add(this);
                this.position = START_POSITION;
            } else if (this.currentRoad.getLength() > this.getPosition()) {
                this.position = (this.position + this.speed);
            } else {
                this.speed = STOPPED;
            }
        }
    }

    // Check if the front road space is occupied
    private boolean checkRoadSpace(){
        boolean occupied = false;
        if (this.currentRoad.getCarsOnRoad().size() > 1) {
            // check my next position
            int nextPosition = (int)(this.getPosition() + this.getSpeed());
            for (Car otherCar : this.currentRoad.getCarsOnRoad()) {
                if(otherCar.getId() != this.getId()){
                    if (nextPosition == otherCar.getPosition()) {
                        occupied = true;
                    }
                }
            }
        }
        return occupied;
    }

    public void printCarStatus() {
        System.out.printf("%s going:%dm/s on %s at position:%s%n", this.getId(), this.getSpeed(), this.getCurrentRoad().
                getId(), this.getPosition());
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        Car.length = length;
    }

    public float getBreadth() {
        return breadth;
    }

    public void setBreadth(float breadth) {
        Car.breadth = breadth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Road getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentRoad(Road currentRoad) {
        this.currentRoad = currentRoad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

