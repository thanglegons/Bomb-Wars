<<<<<<< HEAD
package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class WallpassItem extends Item {

    public WallpassItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý Bomber ăn Item
        ((Bomber)e).setWallpassDuration(1500);
        destroy();
        return false;
    }

}
=======
package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class WallpassItem extends Item {

    public WallpassItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý Bomber ăn Item
        ((Bomber)e).setWallpassDuration(1500);
        destroy();
        return false;
    }

}
>>>>>>> 38449e41b549ad12619837da8530164981830952
