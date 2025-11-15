package assignment3;

public class Consume implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        src.restoreHealth(30);
        System.out.println(src.getName() + " drinks a potion and recovers 30 health!");
    }
}