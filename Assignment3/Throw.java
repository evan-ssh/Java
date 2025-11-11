package assignment3;

public class Throw implements Action {
    private ThrowableItem item;

    public Throw(ThrowableItem item) {
        this.item = item;
    }

    @Override
    public void perform(Actor src, Actor trg) {
        System.out.println(src.getName() + " throws " + item.getName() + " at " + trg.getName());
        trg.takeDamage(15);
    }
}
