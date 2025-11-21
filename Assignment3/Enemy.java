package assignment3;

public class Enemy extends Actor {
    private int level;
    
    public Enemy(String name, int health, int defense, int stamina, int experience) {
        super(name, health, defense, stamina, experience); // Enemies start with 0 experience
        
    }
    
    public int awardXp(Actor player) {
        return player.experience += this.experience;
    }
    
    // Override methods to customize enemy behavior
    @Override
    public void gainExperience(int exp) {
        // Enemies don't gain experience - do nothing
    }
    
    @Override
    public void regenerateStamina(int amount) {
        stamina += amount; // Note: stamina is private in Actor, you'll need to fix this
        // No message for enemy stamina - they regenerate silently
    }
    
    
    // Simple AI for enemy actions
    public Action chooseAction() {
        if (stamina >= 10 && Math.random() < 0.3) {
            return new SpecialAttack(); // 30% chance for special attack
        } else if (health <= 20 && Math.random() < 0.4) {
            return new Guard(); // 40% chance to guard when low health
        } else {
            return new Attack(); // Default to normal attack
        }
    }
}