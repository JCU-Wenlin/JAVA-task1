package main.java.model;

public class Car extends Vehicle {

    public static final int CAR_LENGTH = 2; // the default length of a car

    public Car(String id, Road currentRoad, Direction direction) {
        super(id, currentRoad, direction);
        this.id = "car_" + id;
        setLength(CAR_LENGTH);
        setBreadth();
        setInitPosition();
    }
}

