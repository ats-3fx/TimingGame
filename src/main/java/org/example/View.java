package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class View extends JPanel {
    private final Game game;

    final static int Width = 800;
    final static int Height = 600;


    public final int MaxRight = Width - 105;
    public final int MaxLeft = 100;

    private final BufferedImage image;
    private final BufferedImage missImage;
    private final BufferedImage hitImage;
    private final BufferedImage criticalImage;

    private boolean showMiss = false;
    private long MissLastTime = 0;
    private boolean showHit = false;
    private long HitLastTime = 0;
    private boolean showCritical = false;
    private long CriticalLastTime = 0;
    public long frameCount = 0;
    

    public View(Game game) throws IOException {
        super();
        this.game = game;


        var path = "src/main/resources/image/cat.png";
        var file = new File(path);
        image = ImageIO.read(file);

        var path1 = "src/main/resources/image/MISS.png";
        var miss = new File(path1);
        missImage = ImageIO.read(miss);

        var path2 = "src/main/resources/image/HIT.png";
        var hit = new File(path2);
        hitImage = ImageIO.read(hit);

        var path3 = "src/main/resources/image/CRITICAL.png";
        var critical = new File(path3);
        criticalImage = ImageIO.read(critical);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0));
        g.clearRect(0, 0, Width, Height);
        g.fillRect(0, 0, Width, Height);

        bar(g);
        timingBar(g);
        loadImage(g);
        drawMiss(g);
        drawHit(g);
        drawCritical(g);
    }

    public void update(){
        long showMissInterval = 1000;
        long showHitInterval = 1000;
        long showCriticalInterval = 1000;
        long now = System.currentTimeMillis();
        switch (this.game.getState()){
            case Hit -> {
                if(now - HitLastTime > showHitInterval){
                    showHit = true;
                    HitLastTime = now;
                }
            }
            case Miss -> {
                if(now - MissLastTime > showMissInterval){
                    showMiss = true;
                    MissLastTime = now;
                }
            }
            case Critical -> {
                if(now - CriticalLastTime > showCriticalInterval){
                    showCritical = true;
                    CriticalLastTime = now;
                }
            }
        }
        frameCount++;
    }



    private void bar(Graphics g){
        var green = new Color(0, 200, 0);
        var orange = new Color(255, 192, 0);
        var red = new  Color(255, 0, 0);
        var modFrameCount = frameCount % 14;
        var y = 100;
        var height = 150;

        var hitLeft = gameXToViewX(game.getHitLeft());
        var hitRight = gameXToViewX(game.getHitRight());
        var hitWidth =  hitRight - hitLeft;
        var criticalLeft = gameXToViewX(game.getCriticalLeft());
        var criticalWidth = gameXToViewX(game.getCriticalRight()) - gameXToViewX(game.getCriticalLeft());

        if (showMiss){
            if (modFrameCount <= 7){
                g.setColor(green.darker());
            }else {
                g.setColor(green.brighter());
            }
        }else {
            g.setColor(green.darker().darker());
        }
        g.fillRect(MaxLeft , y, MaxRight - MaxLeft, height);

        if (showHit){
            if (modFrameCount <= 7){
                g.setColor(orange.darker());
            }else {
                g.setColor(orange.brighter());
            }
        }else {
            g.setColor(orange.darker().darker());
        }

        g.fillRect(hitLeft, y, hitWidth, height);
        if (showCritical){
            if (modFrameCount <= 7){
                g.setColor(red.darker());
            }else {
                g.setColor(red.brighter());
            }
        }else {
            g.setColor(red.darker().darker());
        }
        g.fillRect(criticalLeft, y, criticalWidth, height);
    }

    private int gameXToViewX(double x){

        return MaxLeft + (int)(x * (MaxRight - MaxLeft));
    }

    private void timingBar(Graphics g){
        var barX = gameXToViewX(game.getBarPos());
        g.setColor(new Color(255,255,255));
        g.fillRect(barX, 100, 20, 150);
    }

    private void loadImage(Graphics g){
        g.drawImage(image, 768, 600, null);
    }


    private void drawMiss(Graphics g){
        if (showMiss){
            Graphics2D g2D = (Graphics2D) g;
            var m = AffineTransform.getTranslateInstance(764.0, 500.0);
            m.scale(2.5, 2.5);
            g2D.setTransform(m);
            g2D.drawImage(missImage, 0, 0, null);
        }
        long showMissInterval = 1000;
        if (System.currentTimeMillis() - MissLastTime > showMissInterval){
            showMiss = false;
        }
    }
    private void drawHit(Graphics g) {
        if (showHit) {
            Graphics2D g2D = (Graphics2D) g;
            var m = AffineTransform.getTranslateInstance(750.0, 500.0);
            m.scale(3.0, 3.0);
            g2D.setTransform(m);
            g2D.drawImage(hitImage, 0, 0, null);
        }
        long showHitInterval = 1000;
        if (System.currentTimeMillis() - HitLastTime > showHitInterval) {
            showHit = false;
        }
    }
    private void drawCritical(Graphics g) {
        if (showCritical) {
            Graphics2D g2D = (Graphics2D) g;
            var m = AffineTransform.getTranslateInstance(680.0, 500.0);
            m.scale(4.0, 4.0);
            g2D.setTransform(m);
            g2D.drawImage(criticalImage, 0, 0, null);
        }
        long showCriticalInterval = 1000;
        if (System.currentTimeMillis() - CriticalLastTime > showCriticalInterval) {
            showCritical = false;
        }
    }
}
