package BlackJack;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<String> cardDeck;
    
    public Deck(){
     cardDeck = new ArrayList<>();
     String[] suits = {"Hearts","Diamonds","Clubs","Spades"};
        String[] ranks = {"2","3","4","5","6","7","8","9","Jack","Queen","King","Ace"};

        for(String suit : suits){
            for(String rank : ranks){
                cardDeck.add(rank + " of " + suit);
            }
        }
    }
}
