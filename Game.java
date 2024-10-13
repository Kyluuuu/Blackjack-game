import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.awt.*;
import java.awt.Taskbar.State;
import java.awt.font.TextLayout;
import java.awt.geom.Path2D;



public class Game {

    User player = new User();
    User dealer = new User();
    Deck deck = new Deck();
    int dealingFirst = 0;
    List<ContentBox> playerCards;
    List<ContentBox> dealerCards;

    long time = 0;

    //stage buttons
    List<ContentBox> StagingButtons;
    List<ContentBox> BettingButtons;
    List<ContentBox> PlayingButtons;
    List<ContentBox> AfterPlayButtons;
    List<ContentBox> finishButtons;
    List<ContentBox> curButtons;

    enum State {Staging, Betting, Playing, AfterPlay, Finish}
    State currentState = State.Staging;

    Global globals = Global.getGlobal();
    int screenWidth = globals.getWidth();
    int screenHeight = globals.getHeight();
    int cardWidth = globals.getCardWidth();
    int cardHeight = globals.getCardHeight();
    int playerMoney = (int) globals.getPlayerMoney();
    int buttonWidth = globals.getButtonWidth();
    int buttonHeight = globals.getButtonHeight();

    public Game() {
        makeButtonsAndElements();
        changeState(State.Staging);
    }

    //makes the preset buttons for each stage of the game
    private void makeButtonsAndElements() {

        //staging buttons
        StagingButtons = new ArrayList<>();
        ContentBox playButton = new ContentBox(screenWidth/2 - buttonWidth/2, screenHeight*15/20, buttonWidth, buttonHeight, new Button("Play"));
        StagingButtons.add(playButton);

        //Betting buttons
        BettingButtons = new ArrayList<>();
        ContentBox betButtonAll = new ContentBox(screenWidth/9 - buttonWidth/2, screenHeight*15/20 - buttonHeight*5/4, buttonWidth, buttonHeight, new Button((int)player.getMoney()+"", (int)player.getMoney()));
        BettingButtons.add(betButtonAll);
        ContentBox betButtonBy2 = new ContentBox(screenWidth/9 - buttonWidth/2, screenHeight*15/20 - 2*buttonHeight*5/4, buttonWidth, buttonHeight, new Button((int)player.getMoney()/2+"", (int)player.getMoney()/2));
        BettingButtons.add(betButtonBy2);
        ContentBox betButtonBy5 = new ContentBox(screenWidth/9 - buttonWidth/2, screenHeight*15/20 - 3*buttonHeight*5/4, buttonWidth, buttonHeight, new Button((int)player.getMoney()/5+"", (int)player.getMoney()/5));
        BettingButtons.add(betButtonBy5);
        ContentBox betButtonBy10 = new ContentBox(screenWidth/9 - buttonWidth/2, screenHeight*15/20 - 4*buttonHeight*5/4, buttonWidth, buttonHeight, new Button((int)player.getMoney()/10+"", (int)player.getMoney()/10));
        BettingButtons.add(betButtonBy10);

        //Playing buttons
        PlayingButtons = new ArrayList<>();
        ContentBox hitButton = new ContentBox(Global.getGlobal().getWidth()/20, Global.getGlobal().getHeight()*7/10, buttonWidth, buttonHeight, new Button("Hit"));
        PlayingButtons.add(hitButton);
        ContentBox standButton = new ContentBox(Global.getGlobal().getWidth()/5, Global.getGlobal().getHeight()*7/10, buttonWidth, buttonHeight, new Button("Stand"));
        PlayingButtons.add(standButton);

        //AfterPlay buttons
        AfterPlayButtons = new ArrayList<>();

        //Finished buttons
        finishButtons = new ArrayList<>();
        ContentBox finishButton = new ContentBox(screenWidth/2 - buttonWidth/2, screenHeight*15/20, buttonWidth, buttonHeight, new Button("Finish Round"));
        finishButtons.add(finishButton);
    }

    //function to change the state of game
    public void changeState(State state) {
        switch(state) {
            case State.Staging:
                player = new User();
                dealer = new User();
                deck = new Deck();
                currentState = State.Staging;
                curButtons = StagingButtons;
                dealingFirst = 0;
            break;
            case State.Betting:
                currentState = State.Betting;
                curButtons = BettingButtons;
            break;
            case State.Playing:

                currentState = State.Playing;
                curButtons = PlayingButtons;    

                playerCards = new ArrayList<>();
                dealerCards = new ArrayList<>();
                //getting the cards for the player and dealer
                //throw away first card
                deck.takeCard();
                player.getHand().addCard(deck.takeCard());
                dealer.getHand().addCard(deck.takeCard());
                player.getHand().addCard(deck.takeCard());
                Card dc2 = deck.takeCard();
                dc2.setFaceDown();
                dealer.getHand().addCard(dc2);


                // draw cards
                // player cards
                int playerCardX = screenWidth/2 - cardWidth/2;
                int playerCardY = 700;
                for(Card c : player.getHand().getHand()) {
                    playerCards.add(new ContentBox(playerCardX, playerCardY, 0, 0, c));
                    playerCardX += 80;
                    playerCardY -= 50;
                    
                }
                //dealer cards
                int dealerCardX = screenWidth/2 - cardWidth/2;
                int dealerCardY = 70;
                for (Card c : dealer.getHand().getHand()) {
                    dealerCards.add(new ContentBox(dealerCardX, dealerCardY, 0, 0, c));
                    dealerCardX -= 80;
                    dealerCardY += 50;
                }
            break;
            case State.AfterPlay:
                currentState = State.AfterPlay;
                curButtons = AfterPlayButtons;
                dealerCards.remove(1);
                dealerCardX = screenWidth/2 - cardWidth/2 - 80;
                dealerCardY = 70 + 50;
                Card c = dealer.getHand().getHand().get(1);
                c.setFaceDown();
                dealerCards.add(new ContentBox(dealerCardX, dealerCardY, 0, 0, c));
            break;
            case State.Finish:
                currentState = State.Finish;
                curButtons = finishButtons;
        }
    }


    public void paint(Graphics g, Point mouseLoc) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //paints background green
        g2.setColor(Global.getGlobal().getBackgroundColour());
        g2.fillRect(0, 0, Global.getGlobal().getWidth(), Global.getGlobal().getHeight());
        //paints table background (box lines and blackjack rule text)

        //current bet text
        g2.setColor(Global.getGlobal().getoutlineColour());
        g2.setFont(new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/50)); 
        String playerMoneyString = "Current Bet : $"+(int)player.getBet()+"";

        //paints elements on background
        paintBackground(g);

        //stage dependent elements
        if(currentState == State.Betting) {
            g2.drawString("Place your bet", screenWidth/9 - buttonWidth/2, screenHeight*16/20);
        }
        if(currentState == State.Playing || currentState == State.AfterPlay) {
            //draws players money
            g2.drawString(playerMoneyString, Global.getGlobal().getWidth() - (g.getFontMetrics().stringWidth(playerMoneyString) + g.getFontMetrics().getHeight()/2),
            g.getFontMetrics().getHeight() + 3 * g.getFontMetrics().getHeight());

            //printing dealers score
            int tempDealerScore = 0;
            String dealerScoreString = "";

            //printing players score
            int tempScore = Hand.findHandScore(player.getHand());
            String playerCurScore = tempScore + "";
            if(tempScore == 50) playerCurScore = "Blackjack";

            if(dealingFirst >= 0) {
                int timeInt = 500;
                long curTime = System.currentTimeMillis();
                if(time == 0) {
                    time = curTime;
                }
                if(curTime >= time + timeInt) {
                    dealingFirst++;
                    time = curTime + timeInt;
                }
                switch(dealingFirst) {
                    case 0:
                    playerCards.get(0).draw(g2);
                    tempScore = player.getHand().getFirstCard().getValue();
                    break;
                    case 1:
                    playerCards.get(0).draw(g2);
                    dealerCards.get(0).draw(g2);
                    tempScore = player.getHand().getFirstCard().getValue();
                    tempDealerScore = dealer.getHand().getFirstCard().getValue();
                    dealerScoreString = tempDealerScore + "";
                    break;
                    case 2:
                    playerCards.get(0).draw(g2);
                    dealerCards.get(0).draw(g2);
                    playerCards.get(1).draw(g2);
                    tempScore = player.getHand().getFirstCard().getValue() + player.getHand().getSecondCard().getValue();
                    tempDealerScore = dealer.getHand().getFirstCard().getValue();
                    dealerScoreString = tempDealerScore + "";
                    break;
                    case 3:
                    playerCards.get(0).draw(g2);
                    dealerCards.get(0).draw(g2);
                    playerCards.get(1).draw(g2);
                    dealerCards.get(1).draw(g2);
                    tempScore = player.getHand().getFirstCard().getValue() + player.getHand().getSecondCard().getValue();
                    tempDealerScore = dealer.getHand().getFirstCard().getValue();
                    dealerScoreString = tempDealerScore + "";
                    time = 0;
                    dealingFirst = -1;
                    break;
                }

                g2.setColor(Global.getGlobal().getoutlineColour());
                g2.setFont(new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/50)); 

                playerCurScore = tempScore + "";
                g2.drawString(dealerScoreString, 
                screenWidth/2 + 300,
                250);
                g2.drawString(playerCurScore, 
                screenWidth/2 + 300,
                screenHeight - 250);
            }
            else {
                g2.setColor(Global.getGlobal().getoutlineColour());
                g2.setFont(new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/50)); 

                dealerScoreString = dealer.getHand().getFirstCard().getValue() + "";
                if(currentState == State.AfterPlay) {
                    dealerScoreString = Hand.findHandScore(dealer.getHand()) + "";
                }
                g2.drawString(dealerScoreString, 
                screenWidth/2 + 300,
                250);

                g2.drawString(playerCurScore, 
                screenWidth/2 + 300,
                screenHeight - 250);

                for(ContentBox element : playerCards) {
                    element.draw(g);
                }
                for(ContentBox element : dealerCards) {
                    element.draw(g);
                }
            }
        }
        if(currentState == State.AfterPlay) {
            if(player.getHand().getBusted()) {
                g2.setColor(Global.getGlobal().getoutlineColour());
                g2.setFont(new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/40));
                g2.drawString("You busted!", 
                    screenWidth/2 - 850,
                    200);
                int timeInt = 2000;
                long curTime = System.currentTimeMillis();
                if(time == 0) {
                    time = curTime;
                }
                if(curTime >= time + timeInt) {
                    time = 0;
                    changeState(State.Finish);
                }
            }
            else {
                int timeInt = 1000;
                long curTime = System.currentTimeMillis();

                if(dealingFirst == -1) {
                    if(time == 0) {
                        time = curTime;
                    }
                    if(curTime >= time + timeInt) {
                        time = curTime + timeInt;
                        if(Hand.findHandScore(dealer.getHand()) > 16) {
                            time = 0;
                            dealingFirst = -2;
                        }
                        else {
                            Card addedCard = deck.takeCard();
                            int dealerCardX = screenWidth/2 - cardWidth/2;
                            int dealerCardY = 70;
                            int handSize = dealer.getHand().getHand().size();
                            dealer.getHand().addCard(addedCard);
                            dealerCards.add(new ContentBox(dealerCardX - (handSize) * 80, dealerCardY  + (handSize) * 50, 0, 0, addedCard));
                        }
                    }
                }
                else if(dealingFirst == -2) {
                    if(time == 0) {
                        time = curTime;
                    }
                    if(curTime >= time + timeInt) {
                        time = 0;
                        changeState(State.Finish);
                    }
                }
            }
        }
        if(currentState == State.Finish) {
            //drawing win or lose
            String winString = player.getHand().determineIfWinner(dealer.getHand()).name();
            Font f = new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/10);

            TextLayout tl = new TextLayout(winString, f, g2.getFontRenderContext());

            Shape s = tl.getOutline(null);
            g2.translate(screenWidth/2 - s.getBounds2D().getWidth()/2, screenHeight/2);
            
            g2.setColor(Color.white);
            g2.fill(s);

            g2.setColor(Color.black);
            g2.draw(s);

            g2.translate(-(screenWidth/2 - s.getBounds2D().getWidth()/2), -screenHeight/2);
        }

        //checks if mouse has enterd any buttons
        if(mouseLoc != null) mouseEntered(mouseLoc);

        //displays buttons
        for(ContentBox element : curButtons) {
            element.draw(g);
        }
    }



    public void paintBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //drawing money text top right
        g2.setColor(Global.getGlobal().getoutlineColour());
        g2.setFont(new Font("Arial", Font.BOLD, Global.getGlobal().getWidth()/30)); 
        String playerMoneyString = "$"+(int)player.getMoney()+"";
        g2.drawString(playerMoneyString, Global.getGlobal().getWidth() - (g.getFontMetrics().stringWidth(playerMoneyString) + g.getFontMetrics().getHeight()/2),
        g.getFontMetrics().getHeight() + g.getFontMetrics().getHeight()/2);

        int d = 1500;
        int r = d/2;
        int x = Global.getGlobal().getWidth()/2 - r;
        int y = -1100;

        ContentBox blackjacktext = new ContentBox(x, y, d, d, new curvedText(new Color(180, 0, 0), Global.getGlobal().getWidth()/35, "Blackjack pays 3 to 2"));
        blackjacktext.draw(g2);

        ContentBox dealerText = new ContentBox(x, y + 50, d, d, new curvedText(null, Global.getGlobal().getWidth()/50, "Dealer must draw to 16, and stand on all 17's"));
        dealerText.draw(g2);

        ContentBox insuranceText = new ContentBox(x, y + 110, d, d, new curvedText(new Color(210, 210, 0), Global.getGlobal().getWidth()/50, "Pays 2 to 1       INSURANCE       Pays 2 to 1"));
        insuranceText.draw(g2);

        g2.setColor(Color.white);
        int yL = 320;
        int mid = Global.getGlobal().getWidth()/2;
        int lineWidth = Global.getGlobal().getWidth() - 1000;
        Path2D.Double pTop = new Path2D.Double();
        pTop.moveTo(mid - lineWidth/2, yL); 
        pTop.curveTo(mid - 150 ,yL + 200, mid + 150, yL + 200, mid - lineWidth/2 + lineWidth, yL);
        g2.draw(pTop);

        Path2D.Double pBot = new Path2D.Double();
        pBot.moveTo(mid - lineWidth/2 - 100, yL); 
        pBot.curveTo(mid - 200 ,yL + 280, mid + 200, yL + 280, mid + lineWidth/2 + 100, yL);
        g2.draw(pBot);

        g2.drawLine(mid - lineWidth/2, yL, mid - lineWidth/2 - 100, yL);
        g2.drawLine(mid + lineWidth/2, yL, mid + lineWidth/2 + 100, yL);

        //user rect
        int recW = 130;
        int recH = recW * 3/2;
        g2.drawRect(mid - recW/2, 700, recW, recH);

        //dealer rect
        recW = 130;
        recH = recW * 3/2;
        g2.drawRect(mid - recW/2, 70, recW, recH);

    }

    //needs to be changed for buttons only :))))
    public void mouseClicked(Point mouseLoc) {
        if(mouseLoc != null) {
            for(ContentBox c : curButtons) {
                if(c.isClickable()) {
                    if(c.contains((int) mouseLoc.getX(), (int) mouseLoc.getY())) {

                        if(currentState == State.Staging && player.getMoney() >= 1000) changeState(State.Betting);

                        if(currentState == State.Betting) {
                            if(c.getContent().getBet() != 0 && player.getMoney() >= c.getContent().getBet()) {
                                player.setBet(c.getContent().getBet());
                                changeState(State.Playing);
                            }
                        }

                        if(currentState == State.Playing && dealingFirst == -1) {
                            if(c.getContent().getText().equals("Stand")) {
                                changeState(State.AfterPlay);
                            }
                            else if(c.getContent().getText().equals("Hit")) {
                                if(!player.getHand().getBusted()) {
                                    Card addedCard = deck.takeCard();
                                    int playerCardX = screenWidth/2 - cardWidth/2;
                                    int playerCardY = 700;
                                    int handSize = player.getHand().getHand().size();
                                    player.getHand().addCard(addedCard);
                                    playerCards.add(new ContentBox(playerCardX + (handSize) * 80, playerCardY  - (handSize) * 50, 0, 0, addedCard));
                                    if(player.getHand().ifBust(player.getHand())) {
                                        player.getHand().setBusted(true);
                                        changeState(State.AfterPlay);
                                    }
                                }
                            }
                        }
                        if(currentState == State.Finish) {
                            if(c.getContent().getText().equals("Finish Round")) {
                                changeState(State.Staging);
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseEntered(Point mouseLoc) {
        for(ContentBox c : curButtons) {
            if(c.isClickable()) {
                if(c.contains((int) mouseLoc.getX(), (int) mouseLoc.getY())) c.getContent().isHover();
                else c.getContent().notHover();
            }
        }
    }
}
