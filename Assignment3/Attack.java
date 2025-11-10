package Assignment3;

public class Attack implements Action {
    @Override
    public void perform(Actor src, Actor trg){
        int damage = 10;
        if(damage < 1) damage = 1;
        trg.health -= damage;
        System.out.println(src.name + " attacks " + trg.name + " for " + damage + " damage!");
    }
}
