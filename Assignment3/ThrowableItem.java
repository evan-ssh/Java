package assignment3;

public abstract class ThrowableItem extends Item {

    public ThrowableItem(String name) {
        super(name);
    }

    @Override
    public abstract Action getAction();
}
