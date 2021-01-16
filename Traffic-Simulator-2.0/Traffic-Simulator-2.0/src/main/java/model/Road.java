package main.java.model;

import java.awt.*;
import java.util.ArrayList;

public class Road {

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private String id;
    private Orientation orientation;
    private int speedLimit;
    private int length;
    private int width;
    private int[] startLocation;    // the X, Y of the start of the road
    private int[] endLocation;      // the X, Y of the end of the road
    private ArrayList<Vehicle> vehiclesOnRoad = new ArrayList<>();
    private ArrayList<TrafficLight> lightsOnRoad = new ArrayList<>();
    private ArrayList<Road> connectedRoads = new ArrayList<>();

    // Constructor
    public Road(String id, int speedLimit, int length, int[] startLocation, Orientation orientation) {
        this.id = "road_" + id;
        this.speedLimit = speedLimit;
        this.length = length;
        // assuming all roads have two lanes, and each lane can fit the widest vehicle i.e. bus
        this.width = Car.CAR_LENGTH * 3;
        this.startLocation = startLocation;
        this.orientation = orientation;
        setEndLocation();
    }

    private void setEndLocation() {
        if (orientation == Orientation.HORIZONTAL) {
            // increment on X only
            this.endLocation = new int[]{this.length + this.startLocation[0], this.startLocation[1]};
        } else if (orientation == Orientation.VERTICAL) {
            // increment on Y only
            this.endLocation = new int[]{this.startLocation[0], this.length + this.startLocation[1]};
            // this.endLocation = new int[]{this.startLocation[1], this.length + this.startLocation[0]};
        }
    }

    public String getId() {
        return id;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int[] getEndLocation() {
        return endLocation;
    }

    public int[] getStartLocation() {
        return this.startLocation;
    }

    public ArrayList<Vehicle> getVehiclesOnRoad() {
        return vehiclesOnRoad;
    }

    public ArrayList<TrafficLight> getLightsOnRoad() {
        return lightsOnRoad;
    }

    public ArrayList<Road> getConnectedRoads() {
        return connectedRoads;
    }

    public void addConnectedRoad(Road road){
        this.connectedRoads.add(road);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void draw(Graphics g, int scale) {
        if (orientation == Orientation.HORIZONTAL) {
            int[] startLocation = this.startLocation;
            int x = startLocation[0] * scale;
            int y = startLocation[1] * scale;
            int width = length * scale;
            int height = this.width * scale;
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            //Center Lines
            g.setColor(Color.white);
            g.fillRect(x, y + (height / 2) - scale / 6, width, scale / 6);
            g.fillRect(x, y + (height / 2) + scale / 6, width, scale / 6);
        } else if (orientation == Orientation.VERTICAL) {
            int[] startLocation = this.startLocation;
            int x = startLocation[0] * scale;
            int y = startLocation[1] * scale;
            int width = this.width * scale;
            int height = length * scale;
            g.setColor(Color.darkGray);
            g.fillRect(x, y, width, height);
            //Center Lines
            g.setColor(Color.white);
            g.fillRect(x + (width / 2) - scale / 6, y, scale / 6, height);
            g.fillRect(x + (width / 2) + scale / 6, y, scale / 6, height);
        }
    }
}
