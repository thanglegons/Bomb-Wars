package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class ItemPanel extends JPanel {

    private JLabel shieldLabel;
    private JLabel wallpassLabel;

    public ItemPanel() {
        setLayout(new GridLayout());

        shieldLabel = new JLabel("Shield: " + Game.isShield());
        shieldLabel.setForeground(Color.red);
        shieldLabel.setHorizontalAlignment(JLabel.CENTER);

        wallpassLabel = new JLabel("Wallpass: " + Game.getWallpassDuration()/1000);
        wallpassLabel.setForeground(Color.green);
        wallpassLabel.setHorizontalAlignment(JLabel.CENTER);

        add(shieldLabel);
        add(wallpassLabel);

        setBackground(Color.darkGray);
        setPreferredSize(new Dimension(0, 40));
    }

    public void setShield(boolean b) {
        if (b) shieldLabel.setForeground(Color.cyan);
        else shieldLabel.setForeground(Color.red);
        shieldLabel.setText("Shield :" + b);
    }
    public void setWallpassLabel(int t){
        wallpassLabel.setText("Wallpass: " + t);
    }
}
