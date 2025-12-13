package vehicleex;

public class App {
    public static void main(String[] args) {
        Vehicle newCar = new Car(32);
        Vehicle newBike = new Bike(34);
        newCar.move();
        newBike.move();
        
    }
}
