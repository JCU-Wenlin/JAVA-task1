//import java.util.ArrayList;
//import java.util.List;
//
//public class testCode {
//
//    public static final int USERINPUT = 2;
//    //creates multiple car objects:                   *************
//    public static void main(String[] args) {
//        List<Car> cars = new ArrayList<>();
//        for (int i = 0; i < USERINPUT; i++){
//           cars.add(new Car("car_" + (i +1)));
//        }
//        cars.get(0).setSpeed(1);
//        for (int object = 0; object < USERINPUT; object++) {
//            System.out.printf("%s going:%dseg/s at location:%s%n", cars.get(object).getId(), cars.get(object).getSpeed(), cars.get(object).getPosition());
//        }
//
//
//    }
//}

////Simulation loop: that gets user input for each turn
//        System.out.println("Start Simulation?(y)");
//                String answer = simController.nextLine();
//                answer = answer.toLowerCase();
//                if (answer.equals("y")) {
//                do {
//                turn = turn + 1;
//                for (int i = 0; i < carSpawns; i++) {
//        Car currentCar = cars.get(i);
//        TrafficLight currentLight = lights.get(i);
//        currentLight.operate();
//        currentLight.printLightStatus();
//        currentCar.move();
//        currentCar.printCarStatus();
//        }
//
//
//        System.out.println("Sim turn = " + turn + "\nContinue Sim?(y)");
//        answer = simController.nextLine();
//        answer = answer.toLowerCase();
//        } while (answer.equals("y"));
//        }
