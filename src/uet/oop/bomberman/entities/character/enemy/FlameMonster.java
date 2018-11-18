package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class FlameMonster extends Enemy {


    public FlameMonster(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);

        _sprite = Sprite.balloom_left1;

        _ai = new AILow();
        _direction = _ai.calculateDirection(_direction,false, this.getX(), this.getY());
    }

    public void update() {
        animate();
        checkCollision();
        if(!_alive) {
            afterKill();
            return;
        }

        if(_alive) {
            calculateMove();
            FlameSegment flameSegment = _board.getFlameSegmentAt(getTileX(),getTileY());
            if (flameSegment !=null)
                flameSegment.remove();
            flameSegment = new FlameSegment(getTileX(),getTileY(),_direction,false);
            flameSegment.setDuration(500);
            _board.addFreeFlameSegment(flameSegment);
        }
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
                break;
        }
    }
}
