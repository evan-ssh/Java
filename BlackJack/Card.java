package BlackJack;
import java.util.ArrayList;
import java.util.Collections;

public class Card{
    private String rank;
    private String suit;

    public Card(String rank, String suit){
        this.rank =  rank;
        this.suit = suit;

    public String returnRank(){
        return rank;
    }
    public String returnSuit(){
        return suit;
    }    

    }
}
