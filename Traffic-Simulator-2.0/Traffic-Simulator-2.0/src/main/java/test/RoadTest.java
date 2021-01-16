package main.java.test;
import main.java.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static main.java.model.Vehicle.Direction.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

class RoadTest {
    Road hRoad = new Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.HORIZONTAL);
    Road vRoad = new Road("1", 2, 10, new int[]{10, 10}, Road.Orientation.VERTICAL);

    @Test
    void testRoadLength() {
        assertEquals(5, hRoad.getLength());
        assertEquals(10, vRoad.getLength());
    }

    @Test
    void testRoadWidth() {
        assertEquals(Car.CAR_LENGTH * 3, hRoad.getWidth());
        assertEquals(hRoad.getWidth(), vRoad.getWidth());
    }

    @Test
    void testSpeedLimit() {
        assertEquals(1, hRoad.getSpeedLimit());
        assertEquals(2, vRoad.getSpeedLimit());
    }

    @Test
    void testStartLocation() {
        assertArrayEquals(new int[]{0, 0}, hRoad.getStartLocation());
        assertArrayEquals(new int[]{10, 10}, vRoad.getStartLocation());
    }

    @Test
    void testEndLocation() {
        assertArrayEquals(new int[]{0 + hRoad.getLength(), 0}, hRoad.getEndLocation());
        assertArrayEquals(new int[]{10, 10 + vRoad.getLength()}, vRoad.getEndLocation());
    }

    @Test
    void testVehiclesOnRoad() {
        assertEquals(new ArrayList<>(), hRoad.getVehiclesOnRoad());
        assertEquals(new ArrayList<>(), vRoad.getVehiclesOnRoad());
        Car v1 = new Car("000", hRoad, EAST);
        Motorbike v2 = new Motorbike("000", vRoad, NORTH);
        Bus v3 = new Bus("000", hRoad, WEST);
        ArrayList<Vehicle> l1 = new ArrayList<>();
        l1.add(v1);
        l1.add(v3);
        assertEquals(l1, hRoad.getVehiclesOnRoad());
        ArrayList<Vehicle> l2 = new ArrayList<>();
        l2.add(v2);
        assertEquals(l2, vRoad.getVehiclesOnRoad());
    }

    @Test
    void testTrafficLights() {
        assertEquals(new ArrayList<>(), hRoad.getLightsOnRoad());
        assertEquals(new ArrayList<>(), hRoad.getLightsOnRoad());
        TrafficLight l1 = new TrafficLight("000", hRoad, "start");
        ArrayList<TrafficLight> ll1 = new ArrayList<>();
        ll1.add(l1);
        assertEquals(ll1, hRoad.getLightsOnRoad());
        TrafficLight l2 = new TrafficLight("001", vRoad, "end");
        ArrayList<TrafficLight> ll2 = new ArrayList<>();
        ll2.add(l2);
        assertEquals(ll2, vRoad.getLightsOnRoad());

    }

    @Test
    void testConnectedRoads() {
        assertEquals(new ArrayList<>(), hRoad.getConnectedRoads());
        assertEquals(new ArrayList<>(), vRoad.getConnectedRoads());
        Road hRoad2 = new Road("2", 1, 5, hRoad.getEndLocation(), Road.Orientation.HORIZONTAL);
        hRoad.addConnectedRoad(hRoad2);
        ArrayList<Road> rl = new ArrayList<>();
        rl.add(hRoad2);
        assertEquals(rl, hRoad.getConnectedRoads());
    }
}