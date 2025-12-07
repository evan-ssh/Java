class Animal{
    protected String name;
    protected int age;

    public Animal(String name, int age){
        this.name = name;
        this.age = age;
    }
}


class Dog extends Animal{
    String breed;

    public Dog(String name, int age, String breed){
        super(name, age);
        this.breed = breed;
        
    }
}