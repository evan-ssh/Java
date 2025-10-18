package TestClass;

public class Person {
    public String name;
    public int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;

    }

    public String getName(){
        return this.name;
    }

    public void birthday(){
        this.age ++;
    }

    public String printPerson(){
        return this.age + " " + this.name;
    }

    public static void main(String[] args) {
        Person John = new Person("John",20);
        System.out.println(John.getName());
        System.out.println(John.printPerson());
        John.birthday();
        System.out.println("Happy birthday " + John.getName());
        System.out.println(John.printPerson());

    }
   
}
