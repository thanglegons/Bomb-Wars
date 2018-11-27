package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

	public BombItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		((Bomber)e).addBombRate(1);
		((Bomber)e).setBombMax(((Bomber)e).getBombMax()+1);
		destroy();
		return false;
	}
	


}
