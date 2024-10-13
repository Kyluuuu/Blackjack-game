import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Display extends JFrame{

    public Display() {
        int w = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int h = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int wMin = 1200;
        int hMin = 800;
        Canvas canvas = new Canvas(w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(canvas);
        this.setPreferredSize(new Dimension(wMin, hMin));
        this.setLocation(w/2 - wMin/2, h/2 - hMin/2);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        long time = System.currentTimeMillis();
        long timeInterval = 1000/30;
        //30 fps
        while(true) {
            if(System.currentTimeMillis() - time >= timeInterval) {
                time = System.currentTimeMillis();
                repaint();
                Global.getGlobal().setScreenDim(this.getContentPane().getWidth(), this.getContentPane().getHeight());
            }
        }
    }

    class Canvas extends JPanel implements MouseListener {
        Game game;

        public Canvas(int w, int h) {
            Global.getGlobal().setScreenDim(w, h);
            game = new Game();
            this.addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            game.paint(g, getMousePosition());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            game.mouseClicked(getMousePosition());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}


