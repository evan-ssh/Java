public class Medal {

    public String getMedalName(int correctCount) {
        if (correctCount >= 8) {
            return "Gold";
        } else if (correctCount > 3 && correctCount <= 7) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }
}
