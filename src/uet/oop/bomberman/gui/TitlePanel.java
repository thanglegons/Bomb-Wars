package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel{
    private JLabel title;

    public TitlePanel(){
        setLayout(new GridLayout());
        title = new JLabel("Shitgame");
        title.setBackground(Color.white);
        title.setForeground(Color.RED);
        Font labelFont = title.getFont();
        title.setFont(new Font(labelFont.getName(),Font.PLAIN,100));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);
        setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, (Game.HEIGHT * Game.SCALE)/2));
    }
}
