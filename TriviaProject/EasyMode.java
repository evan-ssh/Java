public class EasyMode implements GameMode {

    @Override
    public void initPlayer(Player player) {
        player.setLives(0);
    }

    @Override
    public int questionLimit() {
        return 10;
    }

    @Override
    public boolean canContinue(Player player, int questionsAsked, int totalQuestions) {
        return questionsAsked < totalQuestions;
    }
}
