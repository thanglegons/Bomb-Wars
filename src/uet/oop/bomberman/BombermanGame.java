package uet.oop.bomberman;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.WaterBomb;
import uet.oop.bomberman.gui.Frame;

import java.util.ArrayList;

public class BombermanGame {
	
	public static void main(String[] args) {
		Game.setGodMode(true);
		Game.setShield(true);
		Game.setMaxTypeOfBomb(3);
		new Frame();
	}
}
