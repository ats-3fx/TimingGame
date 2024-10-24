package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class View extends JPanel {
    final static int Width = 1600;
    final static int Height = 1000;

    public int x = 0;
    public int y = 0;

    private List<Integer> xs;
    private List<Integer> ys;

    private Input input;
    private int barX = 100;
    private boolean goRight = true;
    private int count = 0;
    private BufferedImage image;
    private BufferedImage missImage;
    private BufferedImage hitImage;
    private BufferedImage criticalImage;
    private boolean preclicked = false;
    private boolean showMiss = false;
    private final long showMissInterval = 1000;
    private long MissLastTime = 0;
    private boolean showHit = false;
    private final long showHitInterval = 1000;
    private long HitLastTime = 0;
    private boolean showCritical = false;
    private final long showCriticalInterval = 1000;
    private long CriticalLastTime = 0;
    public long frameCount = 0;

    record Point(double x, double y) {
        Point rotateFromOrigin(double angle) {
            var X = Math.cos(angle) * x - Math.sin(angle) * y;
            var Y = Math.sin(angle) * x + Math.cos(angle) * y;

            return new Point(X, Y);
        }

        Point move(double moveX, double moveY) {
            return new Point(x + moveX, y + moveY);
        }

        Point set(double x, double y) {
            return new Point(x, y);
        }

        Point rotateFrom(Point origin, double angle) {
            return this.move(-origin.x, -origin.y)
                    .rotateFromOrigin(angle)
                    .move(origin.x, origin.y);
        }

        Point scale(double k) {
            return new Point(this.x * k, this.y * k);
        }

        Point scaleFrom(Point origin, double k) {
            return this.move(-origin.x, -origin.y)
                    .scale(k)
                    .move(origin.x, origin.y);
        }
    }

    public View(Input input) throws IOException {
        super();
        this.input = input;
        Random x1 = new Random();
        Random y1 = new Random();
        x = x1.nextInt(512);
        y = y1.nextInt(512);

        xs = new ArrayList<>();
        ys = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            xs.add(x1.nextInt(512));
            ys.add(x1.nextInt(512));
        }

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
        mouseInput(g);
        drawMiss(g);
        drawHit(g);
        drawCritical(g);
        frameCount ++;
    }

    private void bar(Graphics g){
        var green = new Color(0, 200, 0);
        var orange = new Color(255, 192, 0);
        var red = new  Color(255, 0, 0);
        var modFrameCount = frameCount % 14;
        if (showMiss){
            if (modFrameCount <= 7){
                g.setColor(green.darker());
            }else {
                g.setColor(green.brighter());
            }
        }else {
            g.setColor(green.darker().darker());
        }
        g.fillRect(100, 100, Width-200, 150);
        if (showHit){
            if (modFrameCount <= 7){
                g.setColor(orange.darker());
            }else {
                g.setColor(orange.brighter());
            }
        }else {
            g.setColor(orange.darker().darker());
        }
        g.fillRect(350, 100, Width-700, 150);
        if (showCritical){
            if (modFrameCount <= 7){
                g.setColor(red.darker());
            }else {
                g.setColor(red.brighter());
            }
        }else {
            g.setColor(red.darker().darker());
        }
        g.fillRect(775, 100, Width-1550, 150);
    }

    private void timingBar(Graphics g){
        if (goRight){
            barX += 8;
        }else {
            barX -= 8;
        }

        if (barX >= Width - 105){
            goRight = false;
        }else if (barX <= 100){
            goRight = true;
        }

        g.setColor(new Color(255,255,255));
        g.fillRect(barX, 100, 20, 150);
    }

    private void loadImage(Graphics g){
        g.drawImage(image, 768, 600, null);
        //g.drawImage(missImage,400, 800, null);
        //g.drawImage(hitImage,800, 800, null);
        //g.drawImage(criticalImage,1200, 800, null);
    }

    private void mouseInput(Graphics g){
        boolean criticalCheck = barX >= 775 && barX <= 825;
        boolean hitCheck = barX >= 350 && barX <= 1250 && !criticalCheck;
        boolean missCheck = barX >= 100 && barX <= 1500 &&  !hitCheck;
        if (input.clicked && !preclicked){
            if(criticalCheck) {
                showCritical = true;
                CriticalLastTime = System.currentTimeMillis();
            }else if(hitCheck){
                showHit = true;
                HitLastTime = System.currentTimeMillis();
            }else if(missCheck){
                showMiss = true;
                MissLastTime = System.currentTimeMillis();
            }
        }
        preclicked = input.clicked;
    }

    private void drawMiss(Graphics g){
        if (showMiss){
            Graphics2D g2D = (Graphics2D) g;
            var m = AffineTransform.getTranslateInstance(764.0, 500.0);
            m.scale(2.5, 2.5);
            g2D.setTransform(m);
            g2D.drawImage(missImage, 0, 0, null);
        }
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
        if (System.currentTimeMillis() - CriticalLastTime > showCriticalInterval) {
            showCritical = false;
        }
    }
}
