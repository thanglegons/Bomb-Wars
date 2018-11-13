package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.FlameMonster;
import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Sprite;

public class WaterSegment extends FlameSegment {
    public WaterSegment(int x, int y, int direction, boolean last) {
        super(x,y,direction,last);
        switch (direction) {
            case 0:
                if(!last) {
                    _sprite = Sprite.water_explosion_vertical2;
                } else {
                    _sprite = Sprite.water_explosion_vertical_top_last2;
                }
                break;
            case 1:
                if(!last) {
                    _sprite = Sprite.water_explosion_horizontal2;
                } else {
                    _sprite = Sprite.water_explosion_horizontal_right_last2;
                }
                break;
            case 2:
                if(!last) {
                    _sprite = Sprite.water_explosion_vertical2;
                } else {
                    _sprite = Sprite.water_explosion_vertical_down_last2;
                }
                break;
            case 3:
                if(!last) {
                    _sprite = Sprite.water_explosion_horizontal2;
                } else {
                    _sprite = Sprite.water_explosion_horizontal_left_last2;
                }
                break;
        }
    }

    public boolean collide(Entity e) {
        // TODO: xử lý khi FlameSegment va chạm với Character
        if (e instanceof FlameMonster){
            if (((Character) e).getTileX() == _x && ((Character) e).getTileY() == _y) {
                ((Character) e).kill();
                return true;
            }
        }
        if (e instanceof FlameSegment){
            e.remove();
            return true;
        }
        return false;
    }
}
