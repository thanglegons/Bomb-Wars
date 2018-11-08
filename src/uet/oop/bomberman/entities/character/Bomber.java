package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    protected Keyboard _input;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    private List<Bomb> _bombs;
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{-1, 0, 1, 0};

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }
        //System.out.println(this.getX() + " " + this.getY());
        if (_input.up) System.out.println("Up is pressed");
        if (_input.down) System.out.println("Down is pressed");
        if (_input.left) System.out.println("Left is pressed");
        if (_input.right) System.out.println("Right is pressed");

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */

    private void detectPlaceBomb() {
        if (_input.space) {
            System.out.println(_board.getBombs().size() + "   "  +Game.getBombRate());
            if (Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

                placeBomb(Coordinates.pixelToTile(this.getX() + Game.TILES_SIZE / 2 - 1), Coordinates.pixelToTile(this.getY() - Game.TILES_SIZE / 2 - 1));
                _timeBetweenPutBombs = 0;
                Game.addBombRate(-1);
            }
        }

        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
    }

    protected void placeBomb(int x, int y) {
        System.out.println("Bomb");
        //Entity entity_test = this._board.getEntityAt(0,0);
        Bomb bomb = new Bomb(x, y, this._board);
        _board.addBomb(bomb);
//        _board.addEntity(x +  y * _board.getWidth(), bomb);
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        int preDi = this._direction;
        this._direction = -1;
        if (_input.up) this._direction = 0;
        if (_input.right) this._direction = 1;
        if (_input.down) this._direction = 2;
        if (_input.left) this._direction = 3;
        if (this._direction == -1) {
            this._moving = false;
            this._direction = preDi;
        } else {
            this._moving = true;
            double nextX = this.getX() + dx[this._direction] * Game.getBomberSpeed();
            double nextY = this.getY() + dy[this._direction] * Game.getBomberSpeed();
            move(nextX, nextY);
        }
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
    }

    @Override
    public boolean canMove(double x, double y) {
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
                int curTileX = Coordinates.pixelToTile(x + i * (3.0) / (4.0) * (Game.TILES_SIZE - 1));
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
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
        if (canMove(xa, ya)) {
            this._x = xa;
            this._y = ya;
        } else {
            int nextDir = -1;
            for (int len = 1; len < Game.TILES_SIZE / 3 * 2; len++) {
                if (nextDir != -1) break;
                for (int dir = 0; dir < 4; dir++) {
                    if (dir == this._direction || Math.abs(dir - this._direction) == 2) continue;
                    double nextX = xa + dx[dir] * Game.getBomberSpeed() * len;
                    double nextY = ya + dy[dir] * Game.getBomberSpeed() * len;
                    if (canMove(nextX, nextY)) {
                        nextDir = dir;
                        break;
                    }
                }
            }
            if (nextDir == -1) return;
            this._x = xa + dx[nextDir] * Game.getBomberSpeed();
            this._y = ya + dy[nextDir] * Game.getBomberSpeed();
        }
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy

        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}