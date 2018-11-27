<<<<<<< HEAD
package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPanel extends JPanel {
    public SelectionPanel(Frame frame){
        JButton jButton = new JButton("Start Game");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  frame.startGame();
            }
        });
        add(jButton,JLabel.CENTER);
        setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, (Game.HEIGHT * Game.SCALE)/2));
    }
}
=======
package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPanel extends JPanel {
    public SelectionPanel(Frame frame){
        JButton jButton = new JButton("Start Game");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  frame.startGame();
            }
        });
        add(jButton,JLabel.CENTER);
        setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, (Game.HEIGHT * Game.SCALE)/2));
    }
}
>>>>>>> 38449e41b549ad12619837da8530164981830952
