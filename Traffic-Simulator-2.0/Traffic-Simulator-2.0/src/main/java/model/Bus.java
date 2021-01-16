package main.java.model;

public class Bus extends Vehicle {
    public Bus(String id, Road currentRoad, Direction direction) {
        super(id, currentRoad, direction);
        this.id = ("bus_" + id);
        setLength(Car.CAR_LENGTH * 3);
        setBreadth();
        setInitPosition();
    }

}
