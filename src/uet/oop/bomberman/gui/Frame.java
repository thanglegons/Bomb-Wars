package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {

    public GamePanel _gamepane;
    private JPanel _containerpane;
    private InfoPanel _infopanel;
    private ItemPanel _itempanel;

    Game _game;

    public Frame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        _itempanel = new ItemPanel();
        _containerpane = new JPanel(new BorderLayout());
        _gamepane = new GamePanel(this);
        _infopanel = new InfoPanel(_gamepane.getGame());
        _itempanel = new ItemPanel();

        _containerpane.add(_infopanel, BorderLayout.PAGE_START);
        _containerpane.add(_gamepane, BorderLayout.CENTER);
        _containerpane.add(_itempanel,BorderLayout.PAGE_END);

        _game = _gamepane.getGame();
        Frame x = this;
        _infopanel.testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               _game.restart();
               _game.resume();
            }
        });
        _infopanel.multiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _game.loadMulti();
                _game.resume();
            }
        });
        add(_containerpane);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        _game.start();
    }

    public void setBossLife(int life){
        _itempanel.setBossLife(life);
    }
    public void setTime(int time) {
        _infopanel.setTime(time);
    }

    public void setPoints(int points) {
        _infopanel.setPoints(points);
    }

    public void setShield(int player, boolean shield){
        _itempanel.setShield(player,shield);
    }

    public void setWallpass(int player, int t){
        _itempanel.setWallpassLabel(player,t);
    }

    public void changeTypeOfBomb(int player, int typeOfBomb){
        _itempanel.changeTypeOfBombLabel(player,typeOfBomb);
    }
    public void changeMode(boolean multi){
        _itempanel.switchMode(multi);
    }
    public void addBoss(boolean remove){
        if (!remove)
        _itempanel.addBoss();
        else _itempanel.removeBoss();
    }
}