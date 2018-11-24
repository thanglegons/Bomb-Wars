package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class MainMenu extends Frame {

    private JButton onePlayer, multiPlayer;

    public MainMenu() {
        System.out.println("Hi");
        JFrame frame = new JFrame("Choose mode");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(2, 1));
        onePlayer = new JButton("1-Player");
        multiPlayer = new JButton("multi-player");

        onePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Frame();
            }
        });

        firstPanel.add(onePlayer);
        firstPanel.add(multiPlayer);

        mainPanel.add(firstPanel);
//        mainPanel.setVisible(true);
        frame.add(mainPanel);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        new Frame();
//        justSomeTest();
    }

}
