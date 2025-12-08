import java.util.Scanner;

public class Game {
    private QuestionPool questionPool;
    private Medal medals;
    private Scanner Sc;


    public static void main(String[] args) {
        Game triviaGame = new Game();
        triviaGame.start();
    }

    public Game() {
        questionPool = new QuestionPool(50); 
        medals = new Medal();
        Sc = new Scanner(System.in);
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println();
            System.out.println("🐾😸 Cat Trivia 😸🐾");
            System.out.println("Questions available: " + questionPool.getCount());
            System.out.println("1) Play Game");
            System.out.println("2) Add Question");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String menuChoice = Sc.nextLine().trim();
            switch (menuChoice) {
                case "1":
                    playGame();
                    break;
                case "2":
                    addQuestionFromUser();
                    break;
                case "3":
                    isRunning = false;
                    System.out.println("Goodbye! Thanks for playing.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        Sc.close();
    }

  
    private void playGame() {
        if (questionPool.isEmpty()) {
            System.out.println("Question Pool Is Empty Add Some Questions");
            return;
        }

        System.out.print("Enter your name: ");
        String playerName = Sc.nextLine();
        Player player = new Player(playerName);

        Question[] gameQuestions = questionPool.shuffleQuestions(10);
        int totalQuestions = gameQuestions.length;
        int questionsAsked = 0;

        while (questionsAsked < totalQuestions) {
            Question currentQuestion = gameQuestions[questionsAsked];
            boolean answeredCorrectly = askQuestion(currentQuestion, questionsAsked + 1);

            if (answeredCorrectly) {
                player.incScore();
            } 

            questionsAsked++;
        }

        showResults(player, questionsAsked);
    }

    private boolean askQuestion(Question question, int questionNumber) {
        System.out.println();
        System.out.println("Question " + questionNumber + ":");
        System.out.println(question.getQuestion());
    
        
        String[] originalAnswers = question.getPossibleAnswers();

        String[] answerOptions = new String[originalAnswers.length];
        for (int i = 0; i < originalAnswers.length; i++) {
            answerOptions[i] = originalAnswers[i];
        }
    
    
        for (int i = answerOptions.length - 1; i > 0; i--) {
            int j = (int)(Math.random() * (i + 1)); 
            String currentOption = answerOptions[i];
            answerOptions[i] = answerOptions[j];
            answerOptions[j] = currentOption;
        }
    
      
        for (int i = 0; i < answerOptions.length; i++) {
            System.out.println((i + 1) + ") " + answerOptions[i]);
        }
    
        
      
    
        int chosenAnswerIndex = -1;
        while (chosenAnswerIndex == -1){
            System.out.print("Enter your answer (1-4): ");
            String userAnswer = Sc.nextLine().trim();

            if (userAnswer.length() == 1) {
                char answerChar = userAnswer.charAt(0);
                if (answerChar >= '1' && answerChar <= '4') {
                    chosenAnswerIndex = answerChar - '1'; 
                }
            }
            if (chosenAnswerIndex == -1) {
                System.out.println("Please enter a valid number (1-4).");
            }
        }
    

      
    
        String correctAnswer = originalAnswers[question.getAnswerIndex()];
        String chosenAnswer = answerOptions[chosenAnswerIndex];
    
        if (chosenAnswer.equals(correctAnswer)) {
            System.out.println("You answered correctly!");
            return true;
        } else {
            System.out.println("Sorry you guessed incorrectly :(");
            System.out.println("The correct answer was: " + correctAnswer);
            return false;
        }
    }
    

    private void showResults(Player player, int questionsAsked) {
        int correctAnswers = player.getCorrectCount();

        System.out.println();
        System.out.println("🏆🏆 Results 🏆🏆");
        System.out.println("Player: " + player.getName());
        System.out.println("Questions answered: " + questionsAsked);
        System.out.println("You answered " + correctAnswers + "/" + questionsAsked +" questions correctly");
       


        System.out.println("You earned a " + medals.getMedalName(correctAnswers) + " medal!");
      
        
        
        System.out.print("Would you like to play again? y/n ");
        String choice = Sc.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
            playGame();
        }

    }

    private void addQuestionFromUser() {
        if (questionPool.isFull()) {
            System.out.println("Question pool is full. Cannot add more.");
            return;
        }

        System.out.println();
        System.out.println("✏️✏️ Add a New Question ✏️✏️");

        System.out.print("Enter question: ");
        String questionText = Sc.nextLine();

        System.out.print("Enter correct answer: ");
        String correctAnswer = Sc.nextLine();

        System.out.print("Enter false answer 1: ");
        String wrongAnswer1 = Sc.nextLine();

        System.out.print("False answer 2: ");
        String wrongAnswer2 = Sc.nextLine();

        System.out.print("False answer 3: ");
        String wrongAnswer3 = Sc.nextLine();

        Question newQuestion = new Question(
                questionText,
                correctAnswer,
                wrongAnswer1,
                wrongAnswer2,
                wrongAnswer3,
                0
        );

        questionPool.addQuestion(newQuestion);

        System.out.println("Succesfully added - Total questions: " + questionPool.getCount());
    }
}
