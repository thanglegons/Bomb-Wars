package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class ItemPanel extends JPanel {

    private JLabel[] shieldLabel = new JLabel[2];
    private JLabel[] wallpassLabel = new JLabel[2];
    private JLabel[] typeOfBombLabel = new JLabel[2];
    private JLabel divider;
    private JLabel boss;

    private void typeOfBomb(){

    }

    public ItemPanel() {
        setLayout(new GridLayout());
        shieldLabel[0] = new JLabel("Shield: " + "");
        shieldLabel[0].setForeground(Color.red);
        shieldLabel[0].setHorizontalAlignment(JLabel.CENTER);

        typeOfBombLabel[0] = new JLabel("");
        typeOfBombLabel[0].setForeground(Color.ORANGE);
        typeOfBombLabel[0].setHorizontalAlignment(JLabel.CENTER);

        wallpassLabel[0] = new JLabel("Wallpass: " + "");
        wallpassLabel[0].setForeground(Color.green);
        wallpassLabel[0].setHorizontalAlignment(JLabel.CENTER);

        add(typeOfBombLabel[0]);
        add(shieldLabel[0]);
        add(wallpassLabel[0]);

        setBackground(Color.darkGray);
        setPreferredSize(new Dimension(0, 40));
    }

    public void setShield(int player, boolean b) {
        if (b) shieldLabel[player].setForeground(Color.cyan);
        else shieldLabel[player].setForeground(Color.red);
        shieldLabel[player].setText("Shield :" + b);
    }
    public void setWallpassLabel(int player, int t){
        wallpassLabel[player].setText("Wallpass: " + t);
    }

    public void setBossLife(int life){
        if (life<3){
            boss.setForeground(Color.red);
        }
        boss.setText("Boss lifes: " + life);
    }

    public void changeTypeOfBombLabel(int player,int typeOfBomb){
        switch (typeOfBomb){
            case 0: {
                typeOfBombLabel[player].setText("Bomb: " + "Normal");
                typeOfBombLabel[player].setForeground(Color.ORANGE);
                break;
            }
            case 1: {
                typeOfBombLabel[player].setText("Bomb: " + "Water");
                typeOfBombLabel[player].setForeground(Color.CYAN);
                break;
            }
            case 2: {
                typeOfBombLabel[player].setText("Bomb: " + "BFS");
                typeOfBombLabel[player].setForeground(Color.RED);
                break;
            }
        }
    }

    public void switchMode(boolean multi){
        if (multi){

            divider = new JLabel("|");
            divider.setForeground(Color.white);
            divider.setHorizontalAlignment(JLabel.CENTER);

            add(divider);

            shieldLabel[1] = new JLabel("Shield: " + "");
            shieldLabel[1].setForeground(Color.red);
            shieldLabel[1].setHorizontalAlignment(JLabel.CENTER);

            typeOfBombLabel[1] = new JLabel("");
            typeOfBombLabel[1].setForeground(Color.ORANGE);
            typeOfBombLabel[1].setHorizontalAlignment(JLabel.CENTER);

            wallpassLabel[1] = new JLabel("Wallpass: " + "");
            wallpassLabel[1].setForeground(Color.green);
            wallpassLabel[1].setHorizontalAlignment(JLabel.CENTER);

            add(typeOfBombLabel[1]);
            add(shieldLabel[1]);
            add(wallpassLabel[1]);
        } else {
            this.remove(divider);
            this.remove(shieldLabel[1]);
            this.remove(typeOfBombLabel[1]);
            this.remove(wallpassLabel[1]);
        }
    }
    public void addBoss(){
        divider = new JLabel("|");
        divider.setForeground(Color.white);
        divider.setHorizontalAlignment(JLabel.CENTER);

        add(divider);

        boss = new JLabel("Boss lifes: " + "");
        boss.setForeground(Color.cyan);
        boss.setHorizontalAlignment(JLabel.CENTER);
        add(boss);
    }

    public void removeBoss(){
        remove(divider);
        remove(boss);
    }
}
