import java.util.*;

public class Hand {

    public enum Result {
        Draw("Draw"), 
        Win("Win"),
        Loss("Loss");

        public final String label;

        private Result(String label) {
            this.label = label;
        }
    }
    private int cardNum = 0;
    private Result result;
    private boolean busted = false;

    private List<Card> cardHand;

    public Hand() {
        cardHand = new ArrayList<>();
    }

    public void addCard(Card card) {
        cardHand.add(card);
    }

    public boolean getBusted() {
        return busted;
    }

    public void setBusted (boolean state) {
        busted = state;
    }

    public List<Card> getHand() {
        return this.cardHand;
    } 

    public Result determineIfWinner(Hand hand) {
        if(busted) return Result.Loss;
        if(findHandScore(this) > findHandScore(hand) || findHandScore(hand) >= 22) return Result.Win;
        if(findHandScore(this) == findHandScore(hand)) return Result.Draw;
        return Result.Loss;
    }

    //(50)blackjack, (4 -> 21)number, (22+)bust
    public static int findHandScore(Hand hand) {
        int score = 0;
        for(Card c : hand.getHand()) {
            if(c.getValue() == 11 && score+11 >21) {
                    score+= 1;
            }
            else {
                score+= c.getValue();
            }
        }
        if(score > 21) return score;
        if(score == 21 && hand.getHand().size() == 2) {
            for(Card c : hand.getHand()) {
                if(c.isPicture()) return 50;
            }
            return 21;
        }
        return score;
    }


    public boolean ifBust(Hand hand) {
        if(findHandScore(hand) >= 22) return true;
        return false;
    }

    public Card getFirstCard() {
        return cardHand.get(0);
    }

    public Card getSecondCard() {
        return cardHand.get(1);
    }

    public Optional<Card> getNextCard() {
        if(cardNum >= cardHand.size()) {
            cardNum = 0;
            return Optional.empty();
        }
        Card returnCard = cardHand.get(cardNum);
        cardNum++;
        return Optional.of(returnCard);
    }
}
