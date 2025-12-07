

public class Question {
    private String question;          
    private String[] possibleAnswers; 
    private int answerIndex;          

    public Question(String question,
                    String questionAnswer,
                    String falseAnswer1,
                    String falseAnswer2,
                    String falseAnswer3,
                    int answerIndex) {

        this.question = question;
        this.possibleAnswers = new String[4];
        this.possibleAnswers[0] = questionAnswer;
        this.possibleAnswers[1] = falseAnswer1;
        this.possibleAnswers[2] = falseAnswer2;
        this.possibleAnswers[3] = falseAnswer3;

        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public boolean isCorrect(int chosenIndex) {
        return chosenIndex == answerIndex;
    }
}
