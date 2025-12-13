package shapeex;

public class Circle extends Shape{
    public double  radius;

    public Circle(double radius){
        if(radius < 0){
            this.radius = 0;
        }else{
            this.radius = radius;
        }
    }

    @Override
    public double area(){
        return Math.PI * radius * radius;
    }
    

}
