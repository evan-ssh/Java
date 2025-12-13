package quckex;

public class App {

    public static void acceptQuack(Quacks q){
        System.out.println(q.quack());
    }

    public static void main(String[] args) {
        acceptQuack(new Duck());
        acceptQuack(new Person());
    }

}
