package shapeex;

public class Square extends Shape{
    public double side;

    public Square(double side){
        if(side < 0){
            this.side = 0;
        }else{
            this.side = side;
        }
    }

    @Override
    public double area(){
        return side * side;
    }
}
