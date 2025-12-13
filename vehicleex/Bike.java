package vehicleex;

public class Bike extends Vehicle{

    public Bike(int speed){
        super(speed);
    }

    @Override
    public void move(){
        System.out.println("Bike is currently pedaling at "+ this.speed);
    }
}
