public interface GameMode {
    void initPlayer(Player player);
    int questionLimit();
    boolean canContinue(Player player, int questionsAsked, int totalQuestions);
}
