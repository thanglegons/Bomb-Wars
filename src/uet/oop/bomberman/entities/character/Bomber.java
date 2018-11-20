package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.*;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    protected Keyboard _input;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    private boolean[] cheatCode = new boolean[5];
    protected int _timeBetweenPutBombs = 0;
    private List<Bomb> _bombs = new ArrayList<>();
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{-1, 0, 1, 0};
    private int invulnerableTime = 0;
    private int changeBombCoolDown = 0;
    private int playerNumber = 1;

    private int bombRate = Game.getBOMBRATE();
    private int bombMax = Game.getBOMBRATE();
    private int bombRadius = Game.getBOMBRADIUS();
    private double speedv2 = 0;
    private boolean godMode = false;
    private boolean shield = false;
    private int wallpassDuration = 0;
    private int typeOfBomb = 0;
    private boolean superbomb = false;
    private int maxTypeOfBomb = 3;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        //_bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right[playerNumber-1];
    }

    public void passParameter(Bomber bomber){
        this.bombRate = bomber.getBombMax();
        this.bombMax = bomber.getBombMax();
        this.bombRadius = bomber.getBombRadius();
        this.typeOfBomb = bomber.getTypeOfBomb();
        this.superbomb = bomber.isSuperbomb();
        this.godMode = bomber.isGodMode();
        this.shield = bomber.isShield();
        this.wallpassDuration = bomber.getWallpassDuration();
        this.speedv2 = bomber.getBomberSpeedV2();
    }

    public Bomber(int x,int y, Board board, int _playerNumber){
        super(x, y, board);
        //_bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right[playerNumber-1];
        playerNumber = _playerNumber;
        shield = true;
        godMode = false;
    }

    private void checkCollision() {
        Entity entity = this._board.getEntityAt(getTileX(), getTileY());
        if (entity instanceof LayeredEntity) {
            entity = ((LayeredEntity) entity).getTopEntity();
            if (entity instanceof Item)
                entity.collide(this);
        }
        if ((_board.getCharacterAtExcluding(getTileX(), getTileY(), this) instanceof Enemy))
            kill();
        FlameSegment flameSegment = _board.getFlameSegmentAt(getTileX(), getTileY());
        if (flameSegment != null) {
            flameSegment.collide(this);
        }
    }


    private void checkTypeOfBomb() {
        if ((playerNumber == 1 && _input.r) && changeBombCoolDown ==0){
            changeTypeOfBomb();
            changeBombCoolDown = 30;
        }
        if ((playerNumber == 2 && _input.plus) && changeBombCoolDown ==0){
            changeTypeOfBomb();
            changeBombCoolDown = 30;
        }
    }

    public void checkCheatcode(){
        if (playerNumber == 1 && !godMode) {
            if (_input.k) {
                cheatCode[0] = true;
                //System.out.println('k');
            }
            if (_input.f) {
                cheatCode[1] = true;
                //System.out.println('f');
            }
            if (_input.u) {
                cheatCode[2] = true;
                //System.out.println('u');
            }
            if (_input.c) {
                cheatCode[3] = true;
                //System.out.println('c');
            };
            cheatCode[4] = cheatCode[0] & cheatCode[1] & cheatCode[2] & cheatCode[3];
            if (cheatCode[4]) setGodMode(true);
        }
    }
    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }
        //System.out.println(this.getX() + " " + this.getY());
        /*if (_input.up) System.out.println("Up is pressed");
        /*if (_input.up) System.out.println("Up is pressed");
        if (_input.down) System.out.println("Down is pressed");
        if (_input.left) System.out.println("Left is pressed");
        if (_input.right) System.out.println("Right is pressed");*/

        //if (_timeBetweenPutBombs >= 7500) _timeBetweenPutBombs = 0;
        //else _timeBetweenPutBombs--;
        _timeBetweenPutBombs--;
        if (invulnerableTime > 0)
            invulnerableTime--;
        if (changeBombCoolDown > 0)
            changeBombCoolDown--;
        //System.out.println(_bombs.size());
        checkCheatcode();
        decreaseWallpassDuration();
        animate();

        calculateMove();

        checkTypeOfBomb();

        detectPlaceBomb();

        checkCollision();
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
        if (Game.getNumberOfPlayer()==1) {
            int xScroll = Screen.calculateXOffset(_board, this);
            int yScroll = Screen.calculateYOffset(_board, this);
            Screen.setOffset(xScroll, yScroll);
        } else
            Screen.setOffset(0,0);

    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */

    boolean checkBrick(Entity entity) {
        return ((entity instanceof LayeredEntity) &&
                ((LayeredEntity) entity).getTopEntity().getSprite() == Sprite.brick);

    }

    protected boolean pressBomb(){
        switch (playerNumber){
            case 1:
                return _input.space;
            case 2:
                return _input.enter;
        }
        return false;
    }
    private void detectPlaceBomb() {
        if (pressBomb()) {
            Entity entity = this._board.getEntityAt(getTileX(), getTileY());
            if (getBombRate() > 0 && _timeBetweenPutBombs < -10 && !checkBrick(entity) && !(entity instanceof Wall)) {

                //System.out.println(_timeBetweenPutBombs + "   " + Game.getBombRate());
                placeBomb(Coordinates.pixelToTile(this.getX() + Game.TILES_SIZE / 2 - 1), Coordinates.pixelToTile(this.getY() - Game.TILES_SIZE / 2 - 1));
                _timeBetweenPutBombs = 0;
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
        Bomb bomb = BombDecider.creatBomb(x,y,this._board,this);
        _board.addBomb(bomb);
        _bombs.add(bomb);
        addBombRate(-1);
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
                addBombRate(1);

            }
        }
    }

    @Override
    public void kill() {
        if (isGodMode())
            return;
        if (isShield()) {
            setShield(false);
            invulnerableTime = 50;
        }
        if (invulnerableTime > 0) return;
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.setLoser(playerNumber);
            _board.endGame();
        }
    }

    protected void calculateDirection(){
        switch (playerNumber){
            case 1:
                if (_input.w) this._direction = 0;
                if (_input.d) this._direction = 1;
                if (_input.s) this._direction = 2;
                if (_input.a) this._direction = 3;
                break;
            case 2:
                if (_input.up) this._direction = 0;
                if (_input.right) this._direction = 1;
                if (_input.down) this._direction = 2;
                if (_input.left) this._direction = 3;
        }
    }
    @Override
    protected void calculateMove() {
        int preDi = this._direction;
        this._direction = -1;
        calculateDirection();
        if (this._direction == -1) {
            this._moving = false;
            this._direction = preDi;
        } else {
            this._moving = true;
            //System.out.println(Game.getBomberSpeedV2());
            double nextX = this.getX() + dx[this._direction] * Game.getBomberSpeed();
            double nextY = this.getY() + dy[this._direction] * Game.getBomberSpeed();
            move(nextX, nextY);
            nextX = this.getX() + dx[this._direction] * getBomberSpeedV2();
            nextY = this.getY() + dy[this._direction] * getBomberSpeedV2();
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
                int curTileX = Coordinates.pixelToTile(x + i * (3.0) / (4.0) * (Game.TILES_SIZE - 1));
                int curTileY = Coordinates.pixelToTile((y + j * (Game.TILES_SIZE - 1)));
                //System.out.println("" + curTileX +" " + curTileY);
                Entity entity = this._board.getEntityAt(curTileX, curTileY);
                Entity preEntity = this._board.getEntityAt(getTileX(), getTileY());
                if (getWallpassDuration() == 0 && !(checkBrick(preEntity))) {
                    if (entity.getSprite() == Sprite.brick ||
                            entity.getSprite() == Sprite.wall || checkBrick(entity)
                    )
                        return false;
                    Bomb thisBomb = this._board.getBombAt(curTileX, curTileY);
                    if (thisBomb != null && thisBomb.collide(this) == true)
                        return false;
                } else if (entity instanceof Wall && ((Wall) entity).isBorder())
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
        //System.out.println(getTileX() + " " + getTileY());
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy

        return true;
    }

    private void chooseSprite() {
        if (!isShield() && invulnerableTime % 10 < 5) {
            switch (_direction) {
                case 0:
                    _sprite = Sprite.player_up[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_up_1[playerNumber-1], Sprite.player_up_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 1:
                    _sprite = Sprite.player_right[playerNumber-1];;
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_right_1[playerNumber-1], Sprite.player_right_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 2:
                    _sprite = Sprite.player_down[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_down_1[playerNumber-1], Sprite.player_down_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 3:
                    _sprite = Sprite.player_left[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_left_1[playerNumber-1], Sprite.player_left_2[playerNumber-1], _animate, 20);
                    }
                    break;
                default:
                    _sprite = Sprite.player_right[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_right_1[playerNumber-1], Sprite.player_right_2[playerNumber-1], _animate, 20);
                    }
                    break;
            }
        } else {
            switch (_direction) {
                case 0:
                    _sprite = Sprite.player_shield_up[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_shield_up_1[playerNumber-1], Sprite.player_shield_up_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 1:
                    _sprite = Sprite.player_shield_right[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_shield_right_1[playerNumber-1], Sprite.player_shield_right_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 2:
                    _sprite = Sprite.player_shield_down[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_shield_down_1[playerNumber-1], Sprite.player_shield_down_2[playerNumber-1], _animate, 20);
                    }
                    break;
                case 3:
                    _sprite = Sprite.player_shield_left[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_shield_left_1[playerNumber-1], Sprite.player_shield_left_2[playerNumber-1], _animate, 20);
                    }
                    break;
                default:
                    _sprite = Sprite.player_shield_right[playerNumber-1];
                    if (_moving) {
                        _sprite = Sprite.movingSprite(Sprite.player_shield_right_1[playerNumber-1], Sprite.player_shield_right_2[playerNumber-1], _animate, 20);
                    }
                    break;
            }
        }
    }

    public double getBomberSpeedV2() {
        return speedv2;
    }

    public void setBomberSpeedV2(double bomberSpeedV2) {
        speedv2 = bomberSpeedV2;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean _shield) {
        shield = _shield;
    }

    public int getWallpassDuration() {
        return wallpassDuration;
    }
    public void decreaseWallpassDuration(){
        if (wallpassDuration>0)
            wallpassDuration--;
    }
    public void setWallpassDuration(int _wallpassDuration) {
        wallpassDuration = _wallpassDuration;
    }
    //Lmao
    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean _godMode) {
        if (_godMode) {
            godMode = _godMode;
            setBombMax(100);
            bombRate = 100;
            bombRadius = 100;
            speedv2 = 1.0;
            wallpassDuration = 999999999;
        }

    }

    public int getBombRate() {
        return this.bombRate;
    }

    public void addBombRate(int t) {
        this.bombRate += t;
    }

    public void setBombRate(int bombRate) {
        this.bombRate = bombRate;
    }

    public int getBombRadius() {
        return this.bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public void addBombRadius(int bombRadius) {
        this.bombRadius += bombRadius;
    }

    public int getBombMax() {
        return bombMax;
    }

    public void setBombMax(int _bombMax) {
        bombMax = _bombMax;
    }

    public boolean isSuperbomb() {
        return superbomb;
    }

    public void setSuperbomb(boolean _superbomb) {
        superbomb = _superbomb;
    }

    public int getTypeOfBomb() {
        return typeOfBomb;
    }

    public void setTypeOfBomb(int _typeOfBomb) {
        typeOfBomb = _typeOfBomb;
    }
    public void changeTypeOfBomb(){
        typeOfBomb = (typeOfBomb + 1) % maxTypeOfBomb;
    }

    public List<Bomb> get_bombs() {
        return _bombs;
    }
}