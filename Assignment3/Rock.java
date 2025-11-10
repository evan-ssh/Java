package assignment3;

public class Rock extends Throwable {
    public Rock(String name) {
        super(name);
    }

    @Override
    public Action getAction() {
        return new Throw(this);
    }
}