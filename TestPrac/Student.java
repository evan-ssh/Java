package TestPrac;

public class Student extends Person {
    private String program;

    public Student(String program, String name, int age) {

        super(name,age);
        this.program = program;
    }
    @Override
    public String describe(){
        return "Name: " + getName() + "," + "Age: " + getAge() + "Program: " + this.program; 
    }
    

}
