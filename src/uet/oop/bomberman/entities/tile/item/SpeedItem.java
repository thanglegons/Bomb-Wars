package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
	boolean used = false;
	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		/*if (used) return false;
		used = true;
		Game.setBomberSpeedV2(Game.getBomberSpeedV2()+0.25);
		destroy();*/
		Game.setShield(true);
		destroy();
		return false;
	}
}
