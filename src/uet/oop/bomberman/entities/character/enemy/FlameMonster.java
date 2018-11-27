<<<<<<< HEAD
package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class FlameMonster extends Enemy {

    protected int bombCoolDown = 0;
    protected int flameCoolDown = 0;

    public FlameMonster(int x, int y, Board board) {
        super(x, y, board, Sprite.flameMons_dead, Game.getBomberSpeed(), 100);

        _sprite = Sprite.flameMons_left1;
        // _board = Game.getBoard();
        _ai = new AIMedium(_board.getBomber(), this);
        _direction = 0;
        //_direction = _ai.calculateDirection(_direction,false, this.getX(), this.getY(), _speed);
    }

    public void update() {
        animate();
        checkCollision();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_alive) {
            calculateMove();

            if (flameCoolDown == 0) {
                FlameSegment flameSegment = _board.getFlameSegmentAt(getTileX(), getTileY());
                if (flameSegment != null)
                    flameSegment.remove();
                flameSegment = new FlameSegment(getTileX(), getTileY(), _direction, false);
                flameSegment.setDuration(500);
                _board.addFreeFlameSegment(flameSegment);
                flameCoolDown = 10;
            }
            Random random2 = new Random();
            //Place Bomb
            if (boss && random2.nextDouble() < bombrate && bombCoolDown == 0) {
                Bomber test = Game.getBoard().getBomber();
                System.out.println(test);
                Bomb bomb = new Bomb(getXTile(), getYTile(), Game.getBoard(), test);
                bomb.setRadius(bombRadius);
                Game.getBoard().getBomber().addBomb(bomb);
                bombCoolDown = 10;
            }
            if (bombCoolDown > 0)
                bombCoolDown--;
            if (invulnerableTime > 0)
                invulnerableTime--;
            if (flameCoolDown > 0)
                flameCoolDown--;
        }
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.flameMons_right1, Sprite.flameMons_right2, Sprite.flameMons_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.flameMons_left1, Sprite.flameMons_left2, Sprite.flameMons_left3, _animate, 60);
                break;
        }
    }
}
=======
package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class FlameMonster extends Enemy {


    public FlameMonster(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed(), 100);

        _sprite = Sprite.balloom_left1;
       // _board = Game.getBoard();
        _ai = new AIMedium(_board.getBomber(),this);
        _direction = 0;
        //_direction = _ai.calculateDirection(_direction,false, this.getX(), this.getY(), _speed);
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
>>>>>>> 38449e41b549ad12619837da8530164981830952
