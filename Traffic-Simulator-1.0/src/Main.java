import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Get info needed to start sim:
        Scanner simController = new Scanner(System.in);
        System.out.println("How many roads?");
        int roadSpawns = simController.nextInt();
        System.out.println("How many cars?");
        int carSpawns = simController.nextInt();
        System.out.println("How many traffic lights?");
        int lightSpawns = simController.nextInt();

        // Create objects:
        System.out.println("Object Creation:\n---------------------");
        System.out.println("Roads:");
        ArrayList<Road> roads = new ArrayList<>();
        for (int i = 0; i < roadSpawns; i++) {
            System.out.println("Please input parameters for road_" + i + "...");
            System.out.print("Length:");
            int lengthInput = simController.nextInt();
            System.out.println("Speed limit is set to 1.");
//            int speedLimitInput = simController.nextInt();
            int speedLimitInput = 1; // force speed limit to be 1 for prototype.
            roads.add(new Road(Integer.toString(i), speedLimitInput, lengthInput, new int[]{0, 0}));
        }
        System.out.println("\nRoads;");
        for (Road road : roads
        ) {
            road.printRoadInfo();
        }

        System.out.println("\nCars;");
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < carSpawns; i++) {
            cars.add(new Car(Integer.toString(i), roads.get(0))); // all created cars will begin on road_0.
            cars.get(i).printCarStatus();
        }

        System.out.println("\nTraffic Lights;");
        ArrayList<TrafficLight> lights = new ArrayList<>();
        for (int i = 0; i < lightSpawns; i++) {
            lights.add(new TrafficLight(Integer.toString(i), roads.get(0))); // all created lights will begin on road_0.
            lights.get(i).printLightStatus();
        }
        System.out.println();

        // set locations and connections:
        if (roads.size() > 1){
            System.out.println("Settings:");
            roads.get(0).printRoadInfo();
            for (int i=0; i<roads.size()-1; i++){
                Road thisRoad = roads.get(i);
                int thisRoadLength = thisRoad.getLength();
                System.out.println("thisRoadLength: " + thisRoadLength);
                Road nextRoad = roads.get(i+1);
                // place road_1 to a position at the end of road_0.
                nextRoad.setStartLocation(new int[]{thisRoadLength + 1, 0});
                nextRoad.printRoadInfo();
                thisRoad.getConnectedRoads().add(nextRoad); // connect road_0 to road_1
            }
            System.out.println();
        }

        //Simulation loop:
        System.out.println("Simulation:");
        Random random = new Random();
        int time = 0;
        System.out.print("Set time scale in milliseconds:");
        int speedOfSim = simController.nextInt();
        int carsFinished = 0;
        while (carsFinished < cars.size()) {
            for (TrafficLight light : lights) {
                light.operate(random.nextInt());
                light.printLightStatus();
            }
            for (Car car : cars) {
                car.move();
                car.printCarStatus();
                if (car.getCurrentRoad().getConnectedRoads().isEmpty() && (car.getSpeed() == 0)) {
                    carsFinished = carsFinished + 1;
                }
            }
            time = time + 1;
            System.out.println(time + " Seconds have passed.\n");
            try {
                Thread.sleep(speedOfSim); // set speed of simulation.
            } catch (InterruptedException sim) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
