package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Ghost extends  Enemy{

    public Ghost(int x, int y, Board board) {
        super(x, y, board, Sprite.ghost_dead, Game.getBomberSpeed()/3 , 200);
        ghost = true;
        _sprite = Sprite.ghost_left1;

        _ai = new AIMedium(_board.getBomber(), this);
        _direction  = 0;
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.ghost_right1, Sprite.ghost_right2, Sprite.ghost_right3, _animate, 60);
                else
                    _sprite = Sprite.ghost_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.ghost_left1, Sprite.ghost_left2, Sprite.ghost_left3, _animate, 60);
                else
                    _sprite = Sprite.ghost_left1;
                break;
        }
    }
}
