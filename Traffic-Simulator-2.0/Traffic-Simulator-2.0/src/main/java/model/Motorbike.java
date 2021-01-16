package main.java.model;

public class Motorbike extends Vehicle {

    public Motorbike(String id, Road currentRoad, Direction direction) {
        super(id, currentRoad, direction);
        this.id = ("motorbike_" + id);
        setLength(Car.CAR_LENGTH / 2);
        setBreadth();
        setInitPosition();
    }

}
