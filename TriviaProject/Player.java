public class Player {
    private String name;
    private int correctAnsCount;
    private int lives;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Player";
        } else {
            this.name = name.trim();
        }
        this.correctAnsCount = 0;
        this.lives = 0;
    }

    public String getName() {
        return name;
    }

    public int getCorrectCount() {
        return correctAnsCount;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public void decLives(){
        if(isAlive()){
            lives--;
        }
    }

    public void incScore() {
        correctAnsCount++;
    }

}
