package main.java.model;

import java.awt.*;
import java.util.Random;

import static main.java.model.TrafficLight.TrafficLightStatus.*;

public class TrafficLight {
    private static final double CHANGE = 0.4; // more often red
    public enum TrafficLightStatus {
        GREEN, RED
    }
    private String id;
    private TrafficLightStatus state;
    private Road roadAttachedTo;
    private int[] position;

    // Constructor
    public TrafficLight(String id, Road road, String loc) {
        this.id = "light_" + id;
        // initialise as a RED light
        this.state = RED;
        // adds this light to the road it belongs to
        this.roadAttachedTo = road;
        this.roadAttachedTo.getLightsOnRoad().add(this);
        // As usually traffic lights sitting at both lanes at the road intersection,
        // therefore there is no need to set traffic lights on single lane
        if (loc == "start") {
            this.position = this.roadAttachedTo.getStartLocation();
        }
        else{
            this.position = this.roadAttachedTo.getEndLocation();
        }
    }

    public void operate(int seed) {
        Random random = new Random(seed);
        double probability = random.nextDouble();
        // only changes if vehicles are present:
        if (probability > CHANGE && !this.roadAttachedTo.getVehiclesOnRoad().isEmpty()) {
            setState(RED);
        } else {
            setState(GREEN);
        }
    }

    public void printLightStatus() {
        System.out.printf("%s is:%s on %s at position:%s%n", getId(), getState(), this.getRoadAttachedTo().getId(),
                this.getPosition());
    }

    public String getId() {
        return this.id;
    }

    public TrafficLightStatus getState() {
        return this.state;
    }

    private void setState(TrafficLightStatus state) {
        this.state = state;
    }

    public Road getRoadAttachedTo() {
        return this.roadAttachedTo;
    }

    public int[] getPosition() {
        return this.position;
    }

    public void draw(Graphics g, int scale) {
        if (roadAttachedTo.getOrientation() == Road.Orientation.HORIZONTAL) {
            switch (state) {
                case RED:
                    g.setColor(Color.red);
                    break;
                case GREEN:
                    g.setColor(Color.green);
            }
            int[] startLocation = getRoadAttachedTo().getStartLocation();
            int x = (getPosition()[0] + startLocation[0]) * scale;
            int y = startLocation[1] * scale;
            int height = (getRoadAttachedTo().getWidth() / 2) * scale;
            g.fillRect(x, y, scale, height);
        }
        if (roadAttachedTo.getOrientation() == Road.Orientation.VERTICAL) {
            switch (state) {
                case RED:
                    g.setColor(Color.red);
                    break;
                case GREEN:
                    g.setColor(Color.green);
            }
            int[] startLocation = getRoadAttachedTo().getStartLocation();
            int x = (startLocation[0] + (getRoadAttachedTo().getWidth() / 2)) * scale;
            int y = (getPosition()[1] + startLocation[1]) * scale;
            int width = (getRoadAttachedTo().getWidth() / 2) * scale;
            g.fillRect(x, y, width, scale);
        }
    }
}
