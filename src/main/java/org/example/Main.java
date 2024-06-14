package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        var f = new JFrame("test");
        f.setVisible(true);
        var pane = f.getContentPane();
        var view = new View();

        pane.add(view);

        f.setSize(View.Width, View.Height);
        f.setResizable(false);

        var loop = new Timer(10, (ActionEvent e) -> {
            view.repaint();
        });
        loop.start();
    }
}