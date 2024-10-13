import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.Math;

public class curvedText implements ContentInterface{

    //draws text inside a circle code with centering
    private Color textColour;
    private int fontSize;
    private String message;
    private boolean isClickable = false;

    public curvedText(Color textColour, int fontSize, String message) {
        if(textColour == null) textColour = Global.getGlobal().getoutlineColour();
        this.textColour = textColour;
        this.fontSize = fontSize;
        this.message = message;
    }


    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform ogTransform = g2.getTransform();

        int r = w/2;
        
        g2.setColor(textColour);
        g2.setFont(new Font("Arial", Font.BOLD, fontSize)); 

        double stringSize = 0;
        for(int i = 1; i < message.length(); i++) {
            stringSize+= g.getFontMetrics().stringWidth(message.charAt(i-1)+"");
        }
        stringSize/= 2;
        double theta = Math.PI/2;
        double offset = -Math.PI/2 + stringSize/r;
        //g2.drawOval(x, y, w, h);

        //first letter
        double x1 = x + r - r*Math.sin(theta + offset);
        double y1 = y + r + r*Math.cos(theta + offset); 
        g2.rotate(theta + offset, (int) x1, (int) y1);
        g2.drawString(message.charAt(0)+"", (int) x1, (int) y1);


        //rest of the letters
        for(int i = 1; i<message.length(); i++) {
            double length = g.getFontMetrics().stringWidth(message.charAt(i-1)+"");
            double theta2 = length/r;
            
            g2.rotate(Math.PI*2 - theta, (int) x1, (int) y1);

            x1+= r - r*Math.sin(theta - theta2);
            y1+= r*Math.cos(theta - theta2);
            
            g2.rotate(theta - theta2, (int) x1, (int) y1);
            g2.drawString(message.charAt(i)+"", (int) x1, (int) y1);
        }
        //reset rotation of g
        g2.setTransform(ogTransform);
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
    public boolean isClickable() {
        return this.isClickable;
    }


    @Override
    public String getText() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getText'");
    }
}
