package TestPrac;

public class Main {
    public static void main(String[] args) {
        Animal[] arr = { new Dog(), new Cat(), new Cow() };
        makeThemTalk(arr);
    }

    public static void makeThemTalk(Animal[] animals) {
        for (Animal a : animals) {
            System.out.println(a.speak());
        }
    }
}

class Animal {
    public String speak() {
        return "...";
    }
}

class Dog extends Animal {
    @Override
    public String speak() { return "Woof"; }
}

class Cat extends Animal {
    @Override
    public String speak() { return "Meow"; }
}

class Cow extends Animal {
    @Override
    public String speak() { return "Moo"; }
}
