package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;

	protected Board _board;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	protected int[] dx = new int[]{0, 1, 0, -1};
	protected int[] dy = new int[]{-1, 0, 1, 0};

	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}

	@Override
	public void update() {
		if(_timeToExplode > 0)
			_timeToExplode--;
		else {
			if(!_exploded)
				explode();
			else
				updateFlames();

			if(_timeAfter > 0)
				_timeAfter--;
			else
				remove();
		}

		animate();
	}

	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

		int xt = (int)_x << 4;
		int yt = (int)_y << 4;

		screen.renderEntity(xt, yt , this);
	}

	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}

	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

	/**
	 * Xử lý Bomb nổ
	 */
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
		for (int i=0;i<4;i++){
			_flames[i] = new Flame((int)_x + dx[i],(int)_y + dy[i],i, Game.getBombRadius(),this._board);
		}
	}

	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;

		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}

		return null;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
		if(e instanceof Character){
			if(_allowedToPassThru){
				int curTileBomberX = Coordinates.pixelToTile(e.getX() + Game.TILES_SIZE / 2 - 1);
				int curTileBomberY = Coordinates.pixelToTile(e.getY() - Game.TILES_SIZE / 2 - 1);
				//System.out.println(curTileBomberX + " " + curTileBomberY);
				//System.out.println(this._x + " " + this._y);
				if((int)this._x != curTileBomberX || (int)this._y != curTileBomberY){
					_allowedToPassThru = false;
				}
				return false;
			}
			//System.out.println("wtf");
			return true;
		}
		// TODO: xử lý va chạm với Flame của Bomb khác
		return false;
	}
}