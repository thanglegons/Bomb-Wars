package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class BombermanGame {


	private static JButton onePlayer, multiPlayer;

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Frame();
	}

}
