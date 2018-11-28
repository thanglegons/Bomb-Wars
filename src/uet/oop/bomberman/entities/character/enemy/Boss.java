package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

public class Boss extends FlameMonster{
    public Boss(int x, int y, Board board) {
        super(x, y, board);
        ghost = true;
        boss = true;
        life = 5;
        _speed = 1.0;
        _sprite = Sprite.boss_left1;
        _deadSprite = Sprite.boss_dead;
    }
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.boss_right1, Sprite.boss_right2, Sprite.boss_right3, _animate, 60);
                else
                    _sprite = Sprite.boss_left1;
                break;
            case 2:
            case 3:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.boss_left1, Sprite.boss_left2, Sprite.boss_left3, _animate, 60);
                else
                    _sprite = Sprite.boss_left1;
                break;
        }
    }
    public int getLife(){
        return life;
    }
}
