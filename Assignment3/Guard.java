package assignment3;

public class Guard implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        src.setGuarding(true);
        System.out.println(src.getName() + " braces for impact and guards!");
    }
}
