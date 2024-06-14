package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class View extends JPanel {
    final static int Width = 1500;
    final static int Height = 900;

    public int x = 0;
    public int y = 0;

    private List<Integer> xs;
    private List<Integer> ys;

    record Point(double x, double y){
        Point rotateFromOrigin(double angle){
            var X = Math.cos(angle)*x - Math.sin(angle)*y;
            var Y = Math.sin(angle)*x + Math.cos(angle)*y;

            return new Point(X, Y);
        }
        Point move(double moveX, double moveY){
            return new Point(x + moveX, y + moveY);
        }
    }

    public View(){
        super();
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
    }

    @Override
        public void paintComponent(Graphics g){
        super.paintComponent(g);



        //楕円形
        //g.setColor(Color.ORANGE);
        //g.drawOval(200, 100, 200, 100);
        //g.fillOval(200 , 100, 200, 100);

        //正方形
        //g.setColor(Color.BLACK);
        //g.drawRect(x, 100, 100, 100);
        //g.drawRect(100, 100, 100, 100);

        //正三角形の作成
        //g.drawPolygon(new int[]{900, 600, 1200}, new int[]{300, 600, 600},3);

        //星型の作成
        //g.drawLine(200+x, 200+y, 160+x, 300+y);
        //g.drawLine(200+x, 200+y, 240+x, 300+y);
        //g.drawLine(160+x, 300+y, 50+x, 300+y);
        //g.drawLine(240+x, 300+y, 350+x, 300+y);
        //g.drawLine(50+x, 300+y, 140+x, 365+y);
        //g.drawLine(350+x, 300+y, 260+x, 365+y);
        //g.drawLine(140+x, 365+y, 100+x, 480+y);
        //g.drawLine(260+x, 365+y, 300+x, 480+y);
        //g.drawLine(100+x, 480+y, 200+x, 410+y);
        //g.drawLine(300+x, 480+y, 200+x, 410+y);

        //三角形
        var p = new Polygon();
        //p.addPoint(200, 200);
        //p.addPoint(142, 300);
        //p.addPoint(258, 300);
        //g.drawPolygon(p);

        //星型の作成 ポリゴン
        p.addPoint(40, 40);
        p.addPoint(32, 60);

        p.addPoint(40, 40);
        p.addPoint(48, 60);

        p.addPoint(32, 60);
        p.addPoint(10, 60);

        p.addPoint(48, 60);
        p.addPoint(70, 60);

        p.addPoint(10, 60);
        p.addPoint(28, 73);

        p.addPoint(70, 60);
        p.addPoint(52, 73);

        p.addPoint(28, 73);
        p.addPoint(20, 96);

        p.addPoint(52, 73);
        p.addPoint(60, 96);

        p.addPoint(20, 96);
        p.addPoint(40, 82);

        p.addPoint(60, 96);
        p.addPoint(40, 82);
        g.drawPolygon(p);


        //for (int i = 0; i < 11; i++) {
            //var x = xs.get(i);
            //var y = ys.get(i);

            //g.drawLine(200+x, 200+y, 160+x, 300+y);
            //g.drawLine(200+x, 200+y, 240+x, 300+y);
            //g.drawLine(160+x, 300+y, 50+x, 300+y);
            //g.drawLine(240+x, 300+y, 350+x, 300+y);
            //g.drawLine(50+x, 300+y, 140+x, 365+y);
            //g.drawLine(350+x, 300+y, 260+x, 365+y);
            //g.drawLine(140+x, 365+y, 100+x, 480+y);
            //g.drawLine(260+x, 365+y, 300+x, 480+y);
            //g.drawLine(100+x, 480+y, 200+x, 410+y);
            //g.drawLine(300+x, 480+y, 200+x, 410+y);
        //}
        var origin = 0;
        var p1 = new Point(200, 100);
        var p2 = new Point(400, 200);
        var movedp2 = p2.move(-p1.x, -p1.y);
        var notMovedp2 = movedp2.rotateFromOrigin(Math.PI*5/6);
        var p3 = notMovedp2.move(p1.x, p1.y);
        var newP = p1.rotateFromOrigin(Math.PI/4);

        g.drawLine(0,0, (int) p2.x, (int) p2.y);
        g.drawLine((int) p1.x, (int) p1.y, (int)p3.x, (int) p3.y);

    }
}
