package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var f = new JFrame("test");
        f.setVisible(true);
        var pane = f.getContentPane();
        var input = new Input();
        f.addMouseListener(input);
        var view = new View(input);
        f.addMouseMotionListener(input);

        pane.add(view);

        f.setSize(View.Width, View.Height);
        f.setResizable(false);

        var loop = new Timer(10, (ActionEvent e) -> {
            view.repaint();
        });
        loop.start();
    }
}