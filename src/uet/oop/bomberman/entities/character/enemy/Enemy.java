package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
<<<<<<< HEAD
import uet.oop.bomberman.entities.tile.Wall;
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;
import java.util.Random;

public abstract class Enemy extends Character {

<<<<<<< HEAD
    protected final double MAX_STEPS;
    protected final double rest;
    protected int _points;
    protected boolean ghost = false;
    protected boolean boss = false;
    protected double _speed = 1.0;
    protected double _speedRate = 1.0;
    protected double bombrate = 0.025;
    protected AI _ai;
    protected double _steps;
    protected int life = 1;
    protected int invulnerableTime = 0;
    protected int bombRadius = 3;

    protected int _finalAnimation = 30;
    protected Sprite _deadSprite;

    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{-1, 0, 1, 0};
    private double[] gx = new double[]{Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2, 0};
    private double[] gy = new double[]{0, Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2};

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        _points = points;
        _speedRate = speed;

        MAX_STEPS = Game.TILES_SIZE / _speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;

        _timeAfter = 20;
        _deadSprite = dead;
    }

    protected void checkCollision() {
        FlameSegment flameSegment = _board.getFlameSegmentAt(getTileX(), getTileY());
        if (flameSegment != null) {
            flameSegment.collide(this);
        }
    }

    @Override
    public void update() {
        animate();
        checkCollision();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_alive) {
            Random random1 = new Random();

            if (random1.nextDouble() <= _speedRate)
                calculateMove();
        }
    }

    @Override
    public void render(Screen screen) {

        if (_alive)
            chooseSprite();
        else {
            if (_timeAfter > 0) {
                _sprite = _deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }

        }

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        if (this instanceof Enemy) {
            _direction = _ai.calculateDirection(_direction, true, this.getX(), this.getY(), _speed, ghost);
            this._moving = true;
            double nextX = this.getX() + dx[this._direction] * _speed;
            double nextY = this.getY() + dy[this._direction] * _speed;
            if (canMove(nextX, nextY))
                move(nextX, nextY);
            else {
                double xa = nextX;
                double ya = nextY;
                _direction = _ai.calculateDirection(_direction, false, this.getX(), this.getY(), _speed, ghost);
=======
	protected int _points;
	
	protected double _speed;
	protected AI _ai;
	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;

	private int[] dx = new int[]{0, 1, 0, -1};
	private int[] dy = new int[]{-1, 0, 1, 0};
    private double[] gx = new double[]{Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2, 0};
    private double[] gy = new double[]{0, Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2};
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}

	protected void checkCollision() {
		FlameSegment flameSegment = _board.getFlameSegmentAt(getTileX(), getTileY());
		if (flameSegment != null) {
			flameSegment.collide(this);
		}
	}

	@Override
	public void update() {
		animate();
		checkCollision();
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		if (this instanceof Enemy) {
			_direction = _ai.calculateDirection(_direction,true, this.getX(), this.getY(), _speed);
			this._moving = true;
			double nextX = this.getX() + dx[this._direction] * _speed;
			double nextY = this.getY() + dy[this._direction] * _speed;
			if (canMove(nextX, nextY))
				move(nextX, nextY);
			else {
			    double xa = nextX;
			    double ya = nextY;
                _direction = _ai.calculateDirection(_direction, false, this.getX(), this.getY(), _speed);
>>>>>>> 38449e41b549ad12619837da8530164981830952
                int nextDir = -1;
                for (int len = 0; len < Game.TILES_SIZE / 5; len++) {
                    if (nextDir != -1) break;
                    for (int dir = 0; dir < 4; dir++) {
                        if (dir == this._direction || Math.abs(dir - this._direction) == 2) continue;
                        double tnextX = xa + dx[dir] * len + dx[_direction] * _speed;
                        double tnextY = ya + dy[dir] * len + dy[_direction] * _speed;
                        if (canMove(tnextX, tnextY)) {
                            nextDir = dir;
                            this._x = xa + dx[dir] * len + dx[_direction] * _speed;
                            this._y = ya + dy[dir] * len + dy[_direction] * _speed;
                            break;
                        }
                    }
                }
                if (nextDir == -1) return;
<<<<<<< HEAD
            }

        }
    }

    @Override
    public void move(double xa, double ya) {
        if (!_alive) return;
        _y = ya;
        _x = xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        y -= Game.TILES_SIZE;

        //System.out.println("" + x +" " + y);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (this._direction == 0) {
                    if (!((i == 0 && j == 0) || (i == 1 && j == 0))) continue;
                }
                if (this._direction == 1) {
                    if (!((i == 1 && j == 1) || (i == 1 && j == 0))) continue;
                }
                if (this._direction == 2) {
                    if (!((i == 1 && j == 1) || (i == 0 && j == 1))) continue;
                }
                if (this._direction == 3) {
                    if (!((i == 0 && j == 0) || (i == 0 && j == 1))) continue;
                }
                int curTileX = Coordinates.pixelToTile(x + i * (Game.TILES_SIZE - 1));
                int curTileY = Coordinates.pixelToTile((y + j * (Game.TILES_SIZE - 1)));
                //System.out.println("" + curTileX +" " + curTileY);
                Entity entity = this._board.getEntityAt(curTileX, curTileY);

                if (!ghost && (entity.getSprite() == Sprite.brick ||
                        entity.getSprite() == Sprite.wall ||
                        ((entity instanceof LayeredEntity) &&
                                ((LayeredEntity) entity).getTopEntity().getSprite() == Sprite.brick)))
                    return false;
                else if (entity instanceof Wall && ((Wall) entity).isBorder())
                    return false;
                Bomb thisBomb = this._board.getBombAt(curTileX, curTileY);
                if (thisBomb != null && thisBomb.collide(this) == true)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber
        return true;
    }

    @Override
    public void kill() {
        if (!_alive) return;
        if (invulnerableTime>0) return;
        life--;
        if (life > 0) {
            if (life <3){
                bombRadius +=2;
                bombrate*=2;
                _speed+=0.5;
            }
            invulnerableTime = 15;
            return;
        }
        _alive = false;

        _board.addPoints(_points);

        Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
        _board.addMessage(msg);
    }


    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if (_finalAnimation > 0) --_finalAnimation;
            else
                remove();
        }
    }

    protected abstract void chooseSprite();
=======
			}

		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y = ya;
		_x = xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
		y -= Game.TILES_SIZE;

		//System.out.println("" + x +" " + y);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if(this._direction == 0){
					if(!((i == 0 && j == 0) || (i == 1 && j == 0))) continue;
				}
				if(this._direction == 1){
					if(!((i == 1 && j == 1) || (i == 1 && j == 0))) continue;
				}
				if(this._direction == 2){
					if(!((i == 1 && j == 1) || (i == 0 && j == 1))) continue;
				}
				if(this._direction == 3){
					if(!((i == 0 && j == 0) || (i == 0 && j == 1))) continue;
				}
				int curTileX = Coordinates.pixelToTile(x + i * (Game.TILES_SIZE - 1));
				int curTileY = Coordinates.pixelToTile((y + j * (Game.TILES_SIZE - 1)));
				//System.out.println("" + curTileX +" " + curTileY);
				Entity entity = this._board.getEntityAt(curTileX, curTileY);
				if (entity.getSprite() == Sprite.brick ||
						entity.getSprite() == Sprite.wall ||
						((entity instanceof LayeredEntity)&&
								((LayeredEntity) entity).getTopEntity().getSprite() == Sprite.brick))
					return false;
				Bomb thisBomb = this._board.getBombAt(curTileX, curTileY);
				if(thisBomb != null && thisBomb.collide(this) == true)
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Bomber
		return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
>>>>>>> 38449e41b549ad12619837da8530164981830952
}
