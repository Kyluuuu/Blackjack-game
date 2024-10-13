import java.util.*;
import java.util.Random;

public class Deck {

    private static final int deckSize = 52;

    private List<Card> deck;

    //when making a new deck object, it automatically creates the 
    public Deck() {
        deck = shuffleDeck(newDeck());
        //deck = newDeck();
    }

    //creates a deck
    public List<Card> newDeck() {
        deck = new ArrayList<>();
        for(int i = 1; i <= deckSize; i++) {
            if(i < 14) deck.add(new Card("Club", i % 13));
            else if(i < 27) deck.add(new Card("Spade", i % 13));
            else if(i < 40) deck.add(new Card("Diamond", i % 13));
            else deck.add(new Card("Heart", i % 13));
        }
        return deck;
    }

    //shuffles a deck
    public List<Card> shuffleDeck(List<Card> deck) {
        Random rand = new Random();
        List<Card> shuffledList = new ArrayList<>();
        while(!deck.isEmpty()) {
            shuffledList.add(deck.remove(rand.nextInt(deck.size())));
        }
        return shuffledList;
    }

    //returns the deck
    public List<Card> getDeck() {
        return deck;
    }

    public Card takeCard() {
        return deck.remove(0);
    }

    @Override
    public String toString() {
        String returnString = "";
        for(Card c : deck) {
            returnString += c.getName();
        }
        return returnString;
    }
}
