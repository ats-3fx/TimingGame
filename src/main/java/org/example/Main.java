package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var game = new Game();
        var input = new Input(game);

        var f = new JFrame("test");
        f.setVisible(true);
        var pane = f.getContentPane();
        f.addMouseListener(input);
        var view = new View(game);
        f.addMouseMotionListener(input);

        pane.add(view);

        f.setSize(View.Width, View.Height);
        f.setResizable(false);

        var loop = new Timer(10, (ActionEvent e) -> {
            game.update();
            view.update();
            view.repaint();
        });
        loop.start();
    }
}