package org.example;

import javax.swing.*;
import java.awt.*;
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

    record Point(double x, double y){
        Point rotateFromOrigin(double angle){
            var X = Math.cos(angle)*x - Math.sin(angle)*y;
            var Y = Math.sin(angle)*x + Math.cos(angle)*y;

            return new Point(X, Y);
        }
        Point move(double moveX, double moveY){
            return new Point(x + moveX, y + moveY);
        }

        Point set(double x, double y){
            return new Point(x, y);
        }

        Point rotateFrom(Point origin, double angle){
            return this.move(-origin.x, -origin.y)
                    .rotateFromOrigin(angle)
                    .move(origin.x, origin.y);
        }

        Point scale(double k){
            return new Point(this.x * k, this.y * k);
        }

        Point scaleFrom(Point origin,double k){
            return this.move(-origin.x, -origin.y)
                    .scale(k)
                    .move(origin.x, origin.y);
        }
    }

    public View(Input input){
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
        //g.drawPolygon(p);


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

        //星型の作成2
        var origin = new Point(800, 100);
        var l = 50;
        var p1 = origin.move(0, l).rotateFrom(origin, Math.PI/10);
        var p2 = origin.rotateFrom(p1, -Math.PI*3/5);
        var p3 = p1.rotateFrom(p2, Math.PI/5);
        var p4 = p2.rotateFrom(p3, -Math.PI*3/5);
        var p5 = p3.rotateFrom(p4, Math.PI/5);

        g.drawLine((int) origin.x, (int) origin.y, (int) p1.x, (int) p1.y);
        g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        g.drawLine((int) p2.x, (int) p2.y, (int) p3.x, (int) p3.y);
        g.drawLine((int) p3.x, (int) p3.y, (int) p4.x, (int) p4.y);
        g.drawLine((int) p4.x, (int) p4.y, (int) p5.x, (int) p5.y);

        var _preP = origin;
        for(var _p : List.of(p1,p2,p3,p4)){
            var _pm = _p.move((origin.x- _p.x)*2, 0);
            g.drawLine((int) _preP.x, (int) _preP.y, (int) _pm.x, (int) _pm.y);
            _preP = _pm;
        }
        g.drawLine((int) p5.x, (int) p5.y, (int) _preP.x, (int) _preP.y);

        //イス型の作成
        var second_origin = new Point(200, 300);
        var m = 100;
        var m2 = 85;
        var cp1 = second_origin.move(0, m).rotateFrom(second_origin, 0);
        var cp2 = second_origin.rotateFrom(cp1, Math.PI*4/7);
        var cp3 = cp1.rotateFrom(cp2, Math.PI*3/7);
        var cp4 = cp2.rotateFrom(cp3, Math.PI*4/7);
        var cp5 = cp1.rotateFrom(cp2, Math.PI*13/12);
        var cp6 = cp5.rotateFrom(cp3, Math.PI*5/12);

        g.drawLine((int) second_origin.x, (int) second_origin.y, (int) cp1.x, (int) cp1.y);
        g.drawLine((int) cp1.x, (int) cp1.y, (int) cp2.x, (int) cp2.y);
        g.drawLine((int) cp2.x, (int) cp2.y, (int) cp3.x, (int) cp3.y);
        g.drawLine((int) cp3.x, (int) cp3.y, (int) cp4.x, (int) cp4.y);
        g.drawLine((int) cp2.x, (int) cp2.y, (int) cp5.x, (int) cp5.y);
        g.drawLine((int) cp5.x, (int) cp5.y, (int) cp6.x, (int) cp6.y);

        //遠近感のあるイスの作成
        var chair_origin = new Point(800, 500);
        //var chair_origin_moved = chair_origin.move(10, 10);
        var n = 100;
        var line1_point = chair_origin.move(0, n).rotateFrom(chair_origin, Math.PI*2/5);
        var line1 = line1_point.scaleFrom(chair_origin, 2);
        var line2_point = chair_origin.move(0, n).rotateFrom(chair_origin, Math.PI*5/9);
        var line2 = line2_point.scaleFrom(chair_origin, 1.95);
        var line3_point = chair_origin.move(0, n*0.5).rotateFrom(chair_origin, Math.PI*2/9);
        var line3 = line3_point.scaleFrom(chair_origin, 1.8);  //need to fix

        var vertical_line = line1_point.move(0, 48).rotateFrom(line1_point, Math.PI);
        var vertical_line2 = line2.move(0, -95).rotateFrom(line2, Math.PI);
        var horizontal_line = line1_point.move(-61, -7).rotateFrom(line1_point, Math.PI);
        var horizontal_line2 = line1.move(-130, -7).rotateFrom(line1, Math.PI);     //need to fix

        var chair_leg_point = new Point(624, 563);
        var chair_leg = chair_leg_point.move(0, 80);
        var chair_leg_point2 = new Point(700, 566);
        var chair_leg2 = chair_leg_point2.move(0, 25);
        var chair_leg_point3 = new Point(730, 569);
        var chair_leg3 = chair_leg_point3.move(0, 90);
        var chair_leg_point4 = new Point(758, 550);
        var chair_leg4 = chair_leg_point4.move(0, 39);

        g.drawLine((int) line1_point.x, (int) line1_point.y, (int) line1.x, (int) line1.y);
        g.drawLine((int) line2_point.x, (int) line2_point.y, (int) line2.x, (int) line2.y);
        g.drawLine((int) line3_point.x, (int) line3_point.y, (int) line3.x, (int) line3.y);
        g.drawLine((int) line1_point.x, (int) line1_point.y, (int) vertical_line.x, (int) vertical_line.y);
        g.drawLine((int) line2.x, (int) line2.y, (int) vertical_line2.x, (int) vertical_line2.y);
        g.drawLine((int) line1_point.x, (int) line1_point.y, (int) horizontal_line.x, (int) horizontal_line.y);
        g.drawLine((int) line1.x, (int) line1.y, (int) horizontal_line2.x, (int) horizontal_line2.y);

        g.drawLine((int) chair_leg_point.x, (int) chair_leg_point.y, (int) chair_leg.x, (int) chair_leg.y);
        g.drawLine((int) chair_leg_point2.x, (int) chair_leg_point2.y, (int) chair_leg2.x, (int) chair_leg2.y);
        g.drawLine((int) chair_leg_point3.x, (int) chair_leg_point3.y, (int) chair_leg3.x, (int) chair_leg3.y);
        g.drawLine((int) chair_leg_point4.x, (int) chair_leg_point4.y, (int) chair_leg4.x, (int) chair_leg4.y);

        var polygon = new Polygon();
        polygon.addPoint(200, 160);
        polygon.addPoint(100, 320);
        polygon.addPoint(300, 320);

        //g.drawPolygon(polygon);
        g.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);

        //星型をポリゴンを使って描く
        var ppoint = new Polygon();
        ppoint.addPoint(140, 40);
        ppoint.addPoint(132, 60);
        ppoint.addPoint(110, 60);
        ppoint.addPoint(128, 73);
        ppoint.addPoint(120, 96);
        ppoint.addPoint(140, 82);
        ppoint.addPoint(160, 96);
        ppoint.addPoint(152, 73);
        ppoint.addPoint(170, 60);
        ppoint.addPoint(148, 60);

        g.drawPolygon(ppoint);

        var c = new Color(0, 0, 0);

        ppoint.translate(300, 600);
        g.drawPolygon(ppoint);
        g.setColor(c);
        g.fillPolygon(ppoint);


        //お絵描き
        g.drawLine(0,0, input.x, input.y);
    }
}
