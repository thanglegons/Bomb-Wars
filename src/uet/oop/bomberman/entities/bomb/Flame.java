package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    protected Board _board;
    protected int _direction;
    protected int xOrigin, yOrigin;
    protected FlameSegment[] _flameSegments = new FlameSegment[0];
    private int _radius;
    private int permittedLength = -1;
    private int[] dx = new int[]{0, 1, 0, -1};
    private int[] dy = new int[]{-1, 0, 1, 0};
    private Bomber bomber;
    private boolean _water = false;

    /**
     * @param x         hoành độ bắt đầu của Flame
     * @param y         tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius    độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius, Board board,Bomber _bomber) {
        xOrigin = x;
        yOrigin = y;
        _x = x;
        _y = y;
        _direction = direction;
        _radius = radius;
        _board = board;
        bomber = _bomber;
        createFlameSegments();
    }
    public Flame(int x, int y, int direction, int radius, Board board, Bomber _bomber, boolean water) {
        xOrigin = x;
        yOrigin = y;
        _x = x;
        _y = y;
        _direction = direction;
        _radius = radius;
        _board = board;
        _water = water;
        bomber = _bomber;
        createFlameSegments();
    }
    /**
     * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        _flameSegments = new FlameSegment[calculatePermitedDistance()];

        /**
         * biến last dùng để đánh dấu cho segment cuối cùng
         */
        boolean last;
        // TODO: tạo các segment dưới đây
        for (int i = 0; i < calculatePermitedDistance(); i++) {
            if (!_water) {
                //FlameSegment flameSegment = _board.getFlameSegmentAt(xOrigin + i * dx[_direction],
                //        yOrigin + i * dy[_direction]);
                //if (flameSegment == null)
                    _flameSegments[i] = new FlameSegment(
                            xOrigin + i * dx[_direction],
                            yOrigin + i * dy[_direction],
                            _direction,
                            (i == calculatePermitedDistance())
                    );
                //else
                //   _flameSegments[i] = flameSegment;
                //Destroy Entity
                Entity temp = _board.getEntityAt(xOrigin + i * dx[_direction], yOrigin + i * dy[_direction]);
                _flameSegments[i].collide(temp);
                for (Character character : _board._characters) {
                    _flameSegments[i].collide(character);
                }
                for (Bomb bomb : _board.getBombs()) {
                    _flameSegments[i].collide(bomb);
                }
            } else{
                _flameSegments[i] = new WaterSegment(
                        xOrigin + i * dx[_direction],
                        yOrigin + i * dy[_direction],
                        _direction,
                        (i == calculatePermitedDistance())
                );
                for (Character character : _board._characters) {
                    _flameSegments[i].collide(character);
                }
                FlameSegment flameSegment = _board.getFlameSegmentAt(xOrigin + i * dx[_direction],yOrigin + i * dy[_direction]);
<<<<<<< HEAD
                if (_flameSegments!= null) {
                    for (int j = 1; j <= 1; j++) {
                        _flameSegments[i].collide(flameSegment);
                        flameSegment = _board.getFlameSegmentAt(xOrigin + i * dx[_direction], yOrigin + i * dy[_direction]);
                    }
                }
=======
                if (flameSegment!= null)
                _flameSegments[i].collide(flameSegment);
>>>>>>> 38449e41b549ad12619837da8530164981830952
            }
        }
    }

    /**
     * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
     *
     * @return
     */
    private int calculatePermitedDistance() {
        // TODO: thực hiện tính toán độ dài của Flame
        if (permittedLength != -1) return permittedLength;
        for (int i = 0; i < _radius; i++) {
            int x = xOrigin + i * dx[_direction];
            int y = yOrigin + i * dy[_direction];
            Bomb bomb = this._board.getBombAt(x,y);
            Entity entity = this._board.getEntityAt(xOrigin + i * dx[_direction], yOrigin + i * dy[_direction]);
            if (!bomber.isSuperbomb()) {
                if (entity.getSprite() == Sprite.wall ||
<<<<<<< HEAD
                        (entity instanceof LayeredEntity && ((LayeredEntity) entity).getTopEntity().getSprite() == Sprite.brick)
                || (bomb != null && bomb instanceof WaterBomb)) {
                    if (!(entity.getSprite() == Sprite.wall) && !(bomb != null && bomb instanceof WaterBomb)) {
=======
                        (entity instanceof LayeredEntity && ((LayeredEntity) entity).getTopEntity().getSprite() == Sprite.brick)) {
                    if (!(entity.getSprite() == Sprite.wall)) {
>>>>>>> 38449e41b549ad12619837da8530164981830952
                        if (!_water)
                        ((LayeredEntity) entity).getTopEntity().collide(entity);
                    }
                    permittedLength = i;
                    return i;
                }
            }
            else{
                FlameSegment flameSegment = _board.getFlameSegmentAt(xOrigin + i * dx[_direction],
                        yOrigin + i * dy[_direction]);
                if (flameSegment != null) flameSegment = null;
                if (entity instanceof Wall && ((Wall) entity).isBorder()) {
                    permittedLength = i;
                    return i;
                }
            }
        }
        permittedLength = _radius;
        return _radius;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (int i = 0; i < _flameSegments.length; i++) {
            if (_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
                return _flameSegments[i];
        }
        return null;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
        for (int i = 0; i < _flameSegments.length; i++) {
            _flameSegments[i].render(screen);
        }
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
        for (int i = 0; i < calculatePermitedDistance(); i++)
            if (_flameSegments[i].collide(e))
                return true;
        return true;
    }
}
