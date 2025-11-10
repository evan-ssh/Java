package Assignment3;

public class SpecialAttack implements Action {
    @Override
    public void perform(Actor src, Actor trg) {
        if (src.stamina < 10) {
            System.out.println(src.name + " is too tired to perform a special attack!");
            return;
        }

        int damage = 20 - trg.defense;
        if (damage < 1) damage = 1;

        trg.health -= damage;
        src.stamina -= 10;

        System.out.println(src.name + " unleashes a special attack on " + trg.name + " for " + damage + " damage!");
    }
}