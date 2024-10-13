import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.tools.Tool;

public class Global {
    private static Global instance = new Global();
    private static Color backgroundColour;
    private static Color outlineColour;
    private double playerMoney;
    private int screenWidth;
    private int screenHeight;
    private int buttonWidth = 250;
    private int buttonHeight = 150;


    private Global() {
        this.playerMoney = 10000;
        backgroundColour = new Color(9, 50, 32);
        outlineColour = new Color(220, 220, 220);
    }



    public static Global getGlobal() { return instance; }

    public int getWidth() { return screenWidth; }
    public int getHeight() { return screenHeight; }
    public int getCardWidth() { return 130 ; }
    public int getCardHeight() { return 130 * 3/2; }
    public Color getBackgroundColour() { return backgroundColour; }
    public Color getoutlineColour() { return outlineColour; }
    public double getPlayerMoney() { return playerMoney; }
    public int getButtonWidth() { return buttonWidth; }
    public int getButtonHeight() { return buttonHeight; }

    public void setScreenDim(double x, double y) {
        this.screenWidth = (int) x;
        this.screenHeight = (int) y;
    }




}
