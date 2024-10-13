import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;

public class Button implements ContentInterface{

    private String text;
    private Color colour;
    private int betMoney;
    private boolean isClickable = true;

    public Button(String text) {
        this.text = text;
        colour = Global.getGlobal().getoutlineColour();
        this.betMoney = 0;
    }

    public Button(String text, int bet) {
        this.text = text;
        colour = Global.getGlobal().getoutlineColour();
        this.betMoney = bet;
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(colour);
        g2.fillRect(x, y, w, h);

        g.setColor(Color.black);
        g2.setStroke(new BasicStroke(w / 50));
        g2.setFont(new Font("TimesRoman", Font.BOLD, w/8)); 
        g2.drawRect(x, y, w, h);
        int stringLength = g.getFontMetrics().stringWidth(text);
        int charHeight = g.getFontMetrics().getHeight();
        g2.drawString(text, x + w/2 - stringLength/2, y + h/2 +charHeight/4);
    }

    @Override
    public void isHover() {
        colour = Color.gray;
    }

    @Override
    public void notHover() {
        colour = Global.getGlobal().getoutlineColour();
    }

    @Override
    public int getBet() {
        return betMoney;
    }

    @Override
    public boolean isClickable() {
        return this.isClickable;
    }

    public String getText() {
        return this.text;
    }
}
