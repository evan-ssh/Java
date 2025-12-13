package vehicleex;

public class Vehicle {
    protected int speed;

    public Vehicle(int speed){
        if(speed < 0){
            this.speed = 0;
        }else{
            this.speed = speed;
        }
    }

    public void move(){
        System.out.println("Vehicle is driving at "+ this.speed);
    }
}
