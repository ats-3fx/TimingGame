package org.example;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener {
    public int x = 0;
    public int y = 0;
    public boolean clicked = false;



    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //System.out.println("mouse");
        //System.out.println(mouseEvent.getX());
        //System.out.println(mouseEvent.getY());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        //System.out.println("test");
        x = mouseEvent.getX();
        y = mouseEvent.getY();
    }
}
