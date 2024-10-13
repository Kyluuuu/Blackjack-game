import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.ArrayList;

public class ContentBox{
    private int posX;
    private int posY;
    private int w;
    private int h;
    private Rectangle rect;
    private ContentInterface content;
    
    public ContentBox(int x, int y, int w, int h, ContentInterface content) {
        this.w = w;
        this.h = h;
        if(content instanceof Card){
            this.w = Global.getGlobal().getCardWidth();
            this.h = Global.getGlobal().getCardHeight();
        }
        posX = x;
        posY = y;
        this.content = content;
        //if null, does nothing, however it will never cuase a null error
        if(content == null) {
            this.content = new ContentInterface() {
                @Override
                public void draw(Graphics g, int x, int y, int w, int h) {}

                @Override
                public void isHover() {}

                @Override
                public void notHover() {} 

                @Override
                public int getBet() {return 0;}

                @Override
                public boolean isClickable() { return false; }

                @Override
                public String getText() { return ""; }
            };
        }
        rect = new Rectangle(posX, posY, w, h);
    }

    public void draw(Graphics g) {
        content.draw(g, posX, posY, w, h);
    }

    public ContentInterface getContent() {
        return content;
    }

    public boolean contains(int x, int y) {
        if(rect.contains(new Point(x, y))) return true;
        return false;
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public boolean isClickable() {
        return content.isClickable();
    }
}

