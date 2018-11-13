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
    private JLabel typeOfBombLabel;

    private void typeOfBomb(){

    }

    public ItemPanel() {
        setLayout(new GridLayout());

        shieldLabel = new JLabel("Shield: " + Game.isShield());
        shieldLabel.setForeground(Color.red);
        shieldLabel.setHorizontalAlignment(JLabel.CENTER);

        typeOfBombLabel = new JLabel("");
        typeOfBombLabel.setForeground(Color.ORANGE);
        typeOfBombLabel.setHorizontalAlignment(JLabel.CENTER);
        changeTypeOfBombLabel();

        wallpassLabel = new JLabel("Wallpass: " + Game.getWallpassDuration()/1000);
        wallpassLabel.setForeground(Color.green);
        wallpassLabel.setHorizontalAlignment(JLabel.CENTER);

        add(typeOfBombLabel);
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

    public void changeTypeOfBombLabel(){
        switch (Game.getTypeOfBomb()){
            case 0: {
                typeOfBombLabel.setText("Bomb: " + "Normal");
                typeOfBombLabel.setForeground(Color.ORANGE);
                break;
            }
            case 1: {
                typeOfBombLabel.setText("Bomb: " + "Water");
                typeOfBombLabel.setForeground(Color.CYAN);
                break;
            }
            case 2: {
                typeOfBombLabel.setText("Bomb: " + "BFS");
                typeOfBombLabel.setForeground(Color.RED);
                break;
            }
        }
    }
}
