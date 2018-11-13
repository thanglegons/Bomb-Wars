package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class WaterBomb extends Bomb{
    public WaterBomb(int x, int y, Board board) {
        super(x, y, board);
    }
    public void render(Screen screen) {
        if(_exploded) {
            _sprite =  Sprite.water_bomb_exploded2;
            renderFlames(screen);
        } else
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

        int xt = (int)_x << 4;
        int yt = (int)_y << 4;

        screen.renderEntity(xt, yt , this);
    }
    protected void explode() {
        if (_exploded) return;
        _exploded = true;
        _flames = new Flame[4];
        //Entity entity_test = this._board.getEntityAt(0,0);
        // TODO: xử lý khi Character đứng tại vị trí Bomb
        Bomber bomber = _board.getBomber();
        if ((bomber).getTileX() == _x && (bomber).getTileY() == _y) {
            (bomber).kill();
        }
        // TODO: tạo các Flame
        for (int i = 0; i < 4; i++) {
            _flames[i] = new Flame((int) _x + dx[i], (int) _y + dy[i], i, Game.getBombRadius(), this._board, true);
        }
    }
}
