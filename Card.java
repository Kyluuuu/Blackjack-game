import java.awt.*;


public class Card implements ContentInterface{
    private String house;
    private String houseSymbol;
    private String disText;
    private Color cardColour;
    private boolean picture;
    private int value;
    private String name;
    private boolean isClickable = false;
    private boolean faceDown = false;

    public Card(String house, int value) {
        picture = false;
        this.value = value;

        this.house = house;
        switch(house) {
            case "Club" : houseSymbol = "♣"; cardColour = Color.black; break; 
            case "Spade" : houseSymbol = "♠"; cardColour = Color.black; break; 
            case "Diamond" : houseSymbol = "♦"; cardColour = Color.red; break; 
            case "Heart" : houseSymbol = "♥"; cardColour = Color.red; break; 
        }

        disText = String.valueOf(this.value);
        this.name = disText + " of " + house + "s";
        switch(value) {
            case 1 : disText = "A"; this.name = "Ace of " + house + "s"; this.value = 11; break; 
            case 11 : disText = "J"; this.name = "Jack of " + house + "s"; this.value = 10; picture = true; break; 
            case 12 : disText = "Q"; this.name = "Queen of " + house + "s"; this.value = 10; picture = true; break; 
            case 0 : disText = "K"; this.name = "King of " + house + "s"; this.value = 10; picture = true; break; 
        }
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(faceDown) {
            //drawing the rectangular box around it
            g2.setColor(Color.red);
            g2.fillRect(x, y, w, h);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(w / 40));
            g2.drawRect(x, y, w, h);
        }
        else 
        {
            //drawing the rectangular box around it
            g2.setColor(Color.white);
            g2.fillRect(x, y, w, h);
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(w / 40));
            g2.drawRect(x, y, w, h);

            //drawing the middle card symbol
            g2.setColor(cardColour);
            g2.setFont(new Font("TimesRoman", Font.BOLD, w/2)); 
            g2.drawString(houseSymbol, x + w/3, y + h/2 + w/5);

            //drawing the top left number
            g2.setFont(new Font("TimesRoman", Font.BOLD, w/5)); 
            g2.drawString(disText, x + w/30, y + w/5);
            int DoubleDigitsX = x + w - w/7;
            if(disText.equals("10")) DoubleDigitsX = x + w - w/4;
            if(picture || value == 11) DoubleDigitsX = x + w - w/6;
            //drawing the bottom right number
            g2.drawString(disText, DoubleDigitsX, y + h - w/30);
        }
    }

    public String getHouse() {
        return house;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean isPicture() {
        return picture;
    }

    public void setFaceDown() {
        if(this.faceDown) this.faceDown = false;
        else if(this.faceDown != true) this.faceDown = true;
    }

    @Override
    public boolean isClickable() {
        return this.isClickable;
    }

    @Override
    public void isHover() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isHover'");
    }

    @Override
    public void notHover() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notHover'");
    }

    @Override
    public int getBet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBet'");
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getText'");
    }
}
