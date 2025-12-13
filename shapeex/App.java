package shapeex;

public class App {
    public static void main(String[] args) {
        Shape[] shapes ={
            new Circle(2),
            new Square(5),
            new Circle(-3)
        };

        for(Shape s : shapes){
            System.out.println(s.area());
        }
    }
}
