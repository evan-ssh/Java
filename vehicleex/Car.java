package vehicleex;

public class Car extends Vehicle{
    public Car(int speed){
        super(speed);
    }

    @Override
    public void move(){
        System.out.println("Car is currently driving at "+ this.speed);
    }

}
