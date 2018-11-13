package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

public class BombDecider {
    public static Bomb creatBomb(int x, int y, Board board){
        switch (Game.getTypeOfBomb()){
            case 0: return new NormalBomb(x,y,board);
            case 1: return new WaterBomb(x,y,board);
            case 2: return new BFSBomb(x,y,board);
        }
        return null;
    }
}
