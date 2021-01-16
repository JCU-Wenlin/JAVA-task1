package main.java.test;

import main.java.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static main.java.model.Road.Orientation.*;
import static main.java.model.Vehicle.Direction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {
    // Initialise a horizontal road with two vehicles on each lane
    Road hRoad = new Road("0", 1, 10, new int[]{0, 0}, HORIZONTAL);
    Car v1 = new Car("1", hRoad, EAST);
    Bus v2 = new Bus("2", hRoad, EAST);
    Car v3 = new Car("3", hRoad, WEST);
    Motorbike v4 = new Motorbike("4", hRoad, WEST);

    // Initialise a vertical road with two vehicles on each lane
    Road vRoad = new Road("1", 2, 20, new int[]{10, 0}, VERTICAL);
    Car v5 = new Car("5", vRoad, NORTH);
    Bus v6 = new Bus("6", vRoad, NORTH);
    Car v7 = new Car("7", vRoad, SOUTH);
    Motorbike v8 = new Motorbike("8", vRoad, SOUTH);

    @Test
    void testId() {
        assertEquals("car_1", v1.getId());
        assertEquals("bus_2", v2.getId());
        assertEquals("car_3", v3.getId());
        assertEquals("motorbike_4", v4.getId());
        assertEquals("car_5", v5.getId());
        assertEquals("bus_6", v6.getId());
        assertEquals("car_7", v7.getId());
        assertEquals("motorbike_8", v8.getId());
    }

    @Test
    void testLength() {
        assertEquals(Car.CAR_LENGTH, v1.getLength());
        assertEquals(v1.getLength(), v3.getLength());
        assertEquals(v1.getLength(), v5.getLength());
        assertEquals(v1.getLength(), v7.getLength());
        assertEquals(v1.getLength() * 3, v2.getLength());
        assertEquals(v1.getLength() * 3, v6.getLength());
        assertEquals(v1.getLength() / 2, v4.getLength());
        assertEquals(v1.getLength() / 2, v8.getLength());
    }

    @Test
    void testBreadth() {
        assertEquals(Car.CAR_LENGTH / 2, v1.getBreadth());
        assertEquals(v1.getBreadth(), v3.getBreadth());
        assertEquals(v1.getBreadth(), v5.getBreadth());
        assertEquals(v1.getBreadth(), v7.getBreadth());
        assertEquals(v2.getLength() / 2, v2.getBreadth());
        assertEquals(v6.getLength() / 2, v6.getBreadth());
        assertEquals(v4.getLength() / 2, v4.getBreadth());
        assertEquals(v8.getLength() / 2, v8.getBreadth());
    }

    @Test
    void testRoad() {
        assertEquals(hRoad, v1.getCurrentRoad());
        assertEquals(EAST, v1.getHeadingDirection());
        assertEquals(HORIZONTAL, v1.getCurrentRoad().getOrientation());

        assertEquals(hRoad, v3.getCurrentRoad());
        assertEquals(WEST, v3.getHeadingDirection());
        assertEquals(HORIZONTAL, v3.getCurrentRoad().getOrientation());

        assertEquals(vRoad, v5.getCurrentRoad());
        assertEquals(NORTH, v5.getHeadingDirection());
        assertEquals(VERTICAL, v5.getCurrentRoad().getOrientation());

        assertEquals(vRoad, v7.getCurrentRoad());
        assertEquals(SOUTH, v7.getHeadingDirection());
        assertEquals(VERTICAL, v7.getCurrentRoad().getOrientation());
    }

    @Test
    void testInitialSpeed() {
        assertEquals(1, v1.getSpeed());
        assertEquals(1, v2.getSpeed());
        assertEquals(1, v3.getSpeed());
        assertEquals(1, v4.getSpeed());
        assertEquals(2, v5.getSpeed());
        assertEquals(2, v6.getSpeed());
        assertEquals(2, v7.getSpeed());
        assertEquals(2, v8.getSpeed());
    }

    @Test
    void testInitialPosition() {
        // v1: car, heading east, left lane
        assertEquals(0, v1.getTailPosition()[0]);
        assertEquals(0, v1.getTailPosition()[1]);
        assertEquals(0 + v1.getLength(), v1.getHeadPosition()[0]);
        assertEquals(0, v1.getHeadPosition()[1]);
        // v3: car, heading west, left lane
        assertEquals(10, v3.getTailPosition()[0]);
        assertEquals(v3.getCurrentRoad().getWidth() / 2, v3.getTailPosition()[1]);
        assertEquals(10 - v3.getLength(), v3.getHeadPosition()[0]);
        assertEquals(v3.getCurrentRoad().getWidth() / 2, v3.getHeadPosition()[1]);
        // v5: car, heading north, left lane
        assertEquals(10, v5.getTailPosition()[0]);
        assertEquals(20, v5.getTailPosition()[1]);
        assertEquals(10, v5.getHeadPosition()[0]);
        assertEquals(20 - v5.getLength(), v5.getHeadPosition()[1]);
        // v7: car, heading south, left lane
        assertEquals(10 + v7.getCurrentRoad().getWidth() / 2, v7.getTailPosition()[0]);
        assertEquals(0, v7.getTailPosition()[1]);
        assertEquals(10 + v7.getCurrentRoad().getWidth() / 2, v7.getHeadPosition()[0]);
        assertEquals(0 + v7.getLength(), v7.getHeadPosition()[1]);
    }

    @Test
    void testNextLocation() {
        // Move v1 - car to the east
        v1.move();
        assertEquals(1, v1.getSpeed());
        assertEquals(1, v1.getTailPosition()[0]);
        assertEquals(0, v1.getTailPosition()[1]);
        assertEquals(1 + v1.getLength(), v1.getHeadPosition()[0]);
        assertEquals(0, v1.getHeadPosition()[1]);
        // Move v4 - motorbike to the west
        v4.move();
        assertEquals(1, v4.getSpeed());
        assertEquals(9, v4.getTailPosition()[0]);
        assertEquals(v4.getCurrentRoad().getWidth() / 2, v4.getTailPosition()[1]);
        assertEquals(9 - v4.getLength(), v4.getHeadPosition()[0]);
        assertEquals(v4.getCurrentRoad().getWidth() / 2, v4.getHeadPosition()[1]);
        // Move v6 - bus to the north
        v6.move();
        assertEquals(2, v6.getSpeed());
        assertEquals(10, v6.getTailPosition()[0]);
        assertEquals(18, v6.getTailPosition()[1]);
        assertEquals(10, v6.getHeadPosition()[0]);
        assertEquals(18 - v6.getLength(), v6.getHeadPosition()[1]);
        // Move v8 - motorbike to the south
        v8.move();
        assertEquals(2, v8.getSpeed());
        assertEquals(10 + v8.getCurrentRoad().getWidth() / 2, v8.getTailPosition()[0]);
        assertEquals(2, v8.getTailPosition()[1]);
        assertEquals(10 + v8.getCurrentRoad().getWidth() / 2, v8.getHeadPosition()[0]);
        assertEquals(2 + v8.getLength(), v8.getHeadPosition()[1]);
    }

    @Test
    void testLaneCheck() {
        assertEquals(true, v1.laneCheck(v2));
        v1.move();
        assertEquals(true, v1.laneCheck(v2));
        assertEquals(true, v3.laneCheck(v4));
        assertEquals(true, v5.laneCheck(v6));
        assertEquals(true, v7.laneCheck(v8));
        assertEquals(false, v2.laneCheck(v3));
        assertEquals(false, v4.laneCheck(v5));
        assertEquals(false, v6.laneCheck(v7));
    }

    @Test
    void testCollisionCheck() {
        for (int i=0; i<7; i++){
            v1.move();
        }
        assertEquals(7, v1.getTailPosition()[0]);
        assertEquals(0, v1.getTailPosition()[1]);
        assertEquals(9, v1.getHeadPosition()[0]);
        assertEquals(0, v1.getHeadPosition()[1]);
        assertEquals(0, v2.getTailPosition()[0]);
        assertEquals(0, v2.getTailPosition()[1]);
        assertEquals(6, v2.getHeadPosition()[0]);
        assertEquals(0, v2.getHeadPosition()[1]);
        assertEquals(true, v2.collisionCheck(v2.calcNextPosition().get("nextHeadPosition")));
    }

    @Test
    void testRedLightCheck() {
        // TODO
    }

    @Test
    void testRoadEndCheck() {
        // TODO
    }
}