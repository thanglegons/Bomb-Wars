package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;

public class NormalBomb extends Bomb {

    public NormalBomb(int x, int y, Board board, Bomber _bomber) {
        super(x, y, board, _bomber);
    }
}
