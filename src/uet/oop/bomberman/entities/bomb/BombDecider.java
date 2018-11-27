package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;

public class BombDecider {
    public static Bomb creatBomb(int x, int y, Board board, Bomber bomber){
        switch (bomber.getTypeOfBomb()){
            case 0: return new NormalBomb(x,y,board,bomber);
            case 1: return new WaterBomb(x,y,board,bomber);
            case 2: return new BFSBomb(x,y,board,bomber);
        }
        return null;
    }
}
