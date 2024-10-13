import java.awt.Graphics;

public interface ContentInterface {
    public void draw(Graphics g, int x, int y, int w, int h);

    public void isHover();

    public void notHover();

    public int getBet();

    public boolean isClickable();

    public String getText();
}


