package counterex;

public class Counter {
    private int count;
    private static int totalCreated;
    public Counter(){
        totalCreated += 1;
    }


    public void increment(){
        this.count += 1;
    }

    public int getCount(){
        return  this.count;
    }

    public static int getTotalCreated(){
        return totalCreated;
    }
}
