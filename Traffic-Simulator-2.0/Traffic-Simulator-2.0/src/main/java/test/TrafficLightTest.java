package main.java.test;

import main.java.model.Road;
import main.java.model.TrafficLight;
import org.junit.jupiter.api.Test;

import static main.java.model.TrafficLight.TrafficLightStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficLightTest {
    Road vRoad = new Road("0", 1, 5, new int[]{0, 0}, Road.Orientation.VERTICAL);
    Road hRoad = new Road("1", 1, 5, new int[]{10, 10}, Road.Orientation.HORIZONTAL);
    TrafficLight light1 = new TrafficLight("0", vRoad, "end");
    TrafficLight light2 = new TrafficLight("0", hRoad, "start");

    @Test
    void testRoadAttachedTo() {
        assertEquals(vRoad, light1.getRoadAttachedTo());
        assertEquals(hRoad, light2.getRoadAttachedTo());
    }

    @Test
    void testLightPosition() {
        assertEquals(vRoad.getEndLocation(), light1.getPosition());
        assertEquals(hRoad.getStartLocation(), light2.getPosition());
    }

    @Test
    void testInitialState() {
        assertEquals(RED, light1.getState());
        assertEquals(RED, light2.getState());
    }

    @Test
    void testLightOperation() {
        light1.operate(3515);
        assertEquals(GREEN, light1.getState());
    }

}