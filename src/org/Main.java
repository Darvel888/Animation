package org;

import java.awt.*;
import javax.swing.*;
public class Main {
    public static void main(String []args){
        Main b = new Main();
        b.run();
    }
    private Sprite sprite;
    private Animation a;
    private ScreenManager s;
    public double sk = 0;

    private static final DisplayMode modes1[] = {
            new DisplayMode(200, 50, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
    };

    public void loadImages() {
        Image face1 = new ImageIcon("C:\\dog.png").getImage();
        Image face2 = new ImageIcon("C:\\dog.jpg").getImage();

        a = new Animation();
        a.addScene(face1, 50);
        a.addScene(face2, 50);

        sprite = new Sprite(a);
        sprite.setVelocityX(0.35f);
        sprite.setVelocityY(0.35f);

    }

    public void run() {
        s = new ScreenManager();
        try {
            DisplayMode dm = s.findFirstCompatibleMode(modes1);
            s.setFullScreen(dm);
            loadImages();
            movieLoop();
        }finally {
            s.restoreScreen();
        }
    }

    public void movieLoop() {
        long startingTime = System.currentTimeMillis();
        long cumTime = startingTime;

        while(cumTime - startingTime < 5000) {
            long timePassed = System.currentTimeMillis() - cumTime;
            cumTime += timePassed;
            update(timePassed);

            Graphics2D g = s.getGraphics();
            draw(g);
            g.dispose();
            s.update();

            try{
                Thread.sleep(20);
            }catch(Exception ex) {
                System.err.println("Error: " + ex);
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
        if(sk != 0){
            g.clearRect(Math.round(sprite.getoX()),Math.round(sprite.getoY()),Math.round(sprite.getWidth()),Math.round(sprite.getHeight()));
        }else{
            sk = 0;
        }

    }

    public void update(long timePassed) {
        if(sprite.getX() < 0) {
            sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
        } else if (sprite.getX() + sprite.getWidth() >= s.getWidth()) {
            sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
        }

        if(sprite.getY() < 0) {
            sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
        } else if (sprite.getY() + sprite.getHeight() >= s.getHeight()) {
            sprite.setVelocityY(-Math.abs(sprite.getVelocityY()));
        }

        sprite.oldX();
        sprite.oldY();
        sprite.update(timePassed);
    }


}
