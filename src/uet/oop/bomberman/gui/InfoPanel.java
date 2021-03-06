package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {
	
	private JLabel timeLabel;
	private JLabel pointsLabel;
	public JButton testButton, multiButton;
	/*private JLabel shieldLabel;
	private JLabel wallpassLabel;*/

	public InfoPanel(Game game) {
		setLayout(new GridLayout());
		
		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

		testButton = new JButton("one-player");
		multiButton = new JButton("multi-player");


		/*shieldLabel = new JLabel("Shield: " + Game.isShield());
		shieldLabel.setForeground(Color.red);
		shieldLabel.setHorizontalAlignment(JLabel.CENTER);

		wallpassLabel = new JLabel("Wallpass: " + Game.getWallpassDuration()/1000);
		wallpassLabel.setForeground(Color.green);
		wallpassLabel.setHorizontalAlignment(JLabel.CENTER);*/

		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);

		add(testButton);
		add(multiButton);
		add(timeLabel);
		add(pointsLabel);
		/*add(shieldLabel);
		add(wallpassLabel);*/
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(0, 40));
	}
	
	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}

	public void setPoints(int t) {
		pointsLabel.setText("Score: " + t);
	}

	/*public void setShield(boolean b) {
		if (b) shieldLabel.setForeground(Color.cyan);
		else shieldLabel.setForeground(Color.red);
		shieldLabel.setText("Shield :" + b);
	}
	public void setWallpassLabel(int t){
		wallpassLabel.setText("Wallpass: " + t);
	}*/
}
