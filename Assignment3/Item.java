package assignment3;

public abstract class Item {
    String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract Action getAction();
}
