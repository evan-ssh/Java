


public class QuestionPool {
    private Question[] questions;
    private int count;

    public QuestionPool(int capacity) {
        questions = new Question[capacity];
        count = 0;
        loadDefaultQuestions();
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count >= questions.length;
    }

    public void addQuestion(Question q) {
        if (q != null && !isFull()) {
            questions[count] = q;
            count++;
        }
    }


    public Question[] getRandomQuestions(int maxQuestions) {
        if (count == 0) {
            return new Question[0];
        }

        Question[] copy = new Question[count];
        for (int i = 0; i < count; i++) {
            copy[i] = questions[i];
        }


        for (int i = count - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1)); 

            Question temp = copy[i];
            copy[i] = copy[j];
            copy[j] = temp;
        }

        int limit = maxQuestions;
        if (limit > count) {
            limit = count;
        }

        Question[] result = new Question[limit];
        for (int i = 0; i < limit; i++) {
            result[i] = copy[i];
        }

        return result;
    }


    private void loadDefaultQuestions() {
        String[][] data = {
                {
                        "Which Alaskan town had a cat as its mayor for almost 20 years?",
                        "Talkeetna, Alaska",
                        "Juneau, Alaska",
                        "Fairbanks, Alaska",
                        "Anchorage, Alaska"
                },
                {
                        "Roughly how many pet cats live in the United States?",
                        "About 88 million",
                        "23 million",
                        "4 million",
                        "85 million"
                },
                {
                        "When was the first known cat video recorded?",
                        "1894",
                        "1915",
                        "1942",
                        "1888"
                },
                {
                        "Usually, how many kittens are usually born in a single litter?",
                        "3 to 4 kittens",
                        "Exactly 1",
                        "6 to 8 kittens",
                        "10 or more kittens"
                },
                {
                        "Which of these words can refer to a group of kittens?",
                        "A kindle",
                        "A pack",
                        "A flock",
                        "A school"
                },
                {
                        "What color eyes are all kittens born with?",
                        "Blue",
                        "Green",
                        "Yellow",
                        "Brown"
                },
                {
                        "What was the name of the first cat sent into space?",
                        "Félicette",
                        "Suki",
                        "Sage",
                        "Tobi"
                },
                {
                        "What is the main purpose a cat's tail serves?",
                        "Helping with balance and communication",
                        "Storing extra food",
                        "Keeping their ears warm",
                        "Help clean themselves"
                },
                {
                        "Which cat breed is traditionally given to newlyweds for good luck?",
                        "Korat",
                        "Siamese",
                        "Burmese",
                        "Bengal"
                },
                {
                        "Which smell do many cats dislike so much that it keeps them away?",
                        "Oranges",
                        "Chicken Nuggets",
                        "Lavender",
                        "Cheese"
                },
                {
                        "Which part of a cat's body is as unique as a human fingerprint?",
                        "Its nose",
                        "Its paws",
                        "Its whiskers",
                        "Its ears"
                },
                {
                        "Which cats are more likely to be left-pawed?",
                        "Male cats",
                        "Female cats",
                        "Both are just as likely",
                        "Only new born kittens"
                },
                {
                        "Which taste can cats NOT detect?",
                        "Sweet",
                        "Salty",
                        "Sour",
                        "Bitter"
                },
                {
                        "Which cat breed is often called \"the gentle giant\"?",
                        "Maine Coon",
                        "Sphynx",
                        "Tiger",
                        "Jaguar"
                },
                {
                        "What is the name for an extreme fear of cats?",
                        "Ailurophobia",
                        "Arachnophobia",
                        "Acrophobia",
                        "Felinophobia"
                }
        };

        for (int i = 0; i < data.length && !isFull(); i++) {
            String text = data[i][0];
            String correct = data[i][1];
            String wrong1 = data[i][2];
            String wrong2 = data[i][3];
            String wrong3 = data[i][4];

            Question q = new Question(text, correct, wrong1, wrong2, wrong3, 0);
            addQuestion(q);
        }
    }
}
