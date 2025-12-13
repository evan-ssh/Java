package rectangleex;

public class Rectangle {
    public double width;
    public double height;

    public Rectangle(double width, double height){
        if(width < 0){
            width = 0;
        }

        if(height < 0){
            height = 0;
        }
        this.width = width;
        this.height = height;
    }

    public Rectangle(double side){
        if(side < 0){
            side = 0;
        }
        this.width = side;
        this.height = side;
    }

    public double area(){
        return width * height;
    }
}