package Assignment3;

public class Guard implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        src.defense += 3;
        System.out.println(src.name + " guards and raises defense by 3!");
    }
}