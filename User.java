public class User {
    private Hand hand;
    private double money;
    private int curBet;

    public User() {
        this.money = Global.getGlobal().getPlayerMoney();
        hand = new Hand();
    }

    public Hand getHand() {
        return this.hand;
    }

    public double getMoney() {
        return money;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void adjustMoney(double money) {
        this.money+= money;
    }

    public void setBet(int bet) {
        this.curBet = bet;
        money-= curBet;
    }

    public int getBet() {
        return this.curBet;
    }
}
