package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;

import java.util.ArrayList;
import java.util.Map;

public class BFSBomb extends Bomb {
    private int trueRadius;
    public BFSBomb(int x, int y, Board board, Bomber _bomber) {
        super(x, y, board,_bomber);
        trueRadius = Math.min(5,bomber.getBombRadius());
    }
    protected void explode(){
        if (_exploded) return;
        _exploded = true;
        _flames = new Flame[4];
        //Bomber bomber = _board.getBomber();
        for (Bomber bomber: _board.getBombers()) {
            if ((bomber).getTileX() == _x && (bomber).getTileY() == _y) {
                (bomber).kill();
            }
        }
        // TODO: tạo các Flame
        boolean[][] mark = new boolean[_board.getWidth()][_board.getHeight()];
        ArrayList<Flame> flameList = new ArrayList<>();
        ArrayList<Integer> queueX = new ArrayList<>();
        ArrayList<Integer> queueY = new ArrayList<>();
        ArrayList<Integer> queueDepth = new ArrayList<>();
        queueX.add((int) _x);
        queueY.add((int) _y);
        mark[(int) _x][(int) _y]= true;
        queueDepth.add(0);
        int L=0, R=0;
        while (L<=R){
            int tempX = queueX.get(L);
            int tempY = queueY.get(L);
            int depth = queueDepth.get(L);
            L++;
            if (depth<trueRadius)
            for (int i=0;i<4;i++){
                int newX = tempX+dx[i];
                int newY = tempY+dy[i];
                if (mark[newX][newY]) continue;
                Entity entity = _board.getEntityAt(newX,newY);
                if (entity instanceof Wall) continue;
                if (entity instanceof LayeredEntity && ((LayeredEntity) entity).getTopEntity() instanceof Brick){
                    ((LayeredEntity) entity).getTopEntity().collide(entity);
                } else{
                    R++;
                    /*FlameSegment flameSegment = new FlameSegment(newX,newY,i,(depth+1 == trueRadius));
                    Entity temp = _board.getEntityAt(newX,newY);
                    flameSegment.collide(temp);
                    for (Character character: _board._characters){
                        flameSegment.collide(character);
                    }
                    for (Bomb bomb: _board.getBombs()){
                        flameSegment.collide(bomb);
                    }*/
                    flameList.add(new Flame(newX,newY,i,1,_board,bomber));
                    mark[newX][newY] = true;
                    queueX.add(newX);
                    queueY.add(newY);
                    queueDepth.add(depth+1);
                }
            }
        }
        _flames = new Flame[flameList.size()];
        for (int i=0;i<flameList.size();i++)
            _flames[i]=flameList.get(i);
    }
}
