package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class AILow extends AI {
	private static double CHANGE_RATE = 0.03;

	private int[][] map;
	private int[] canGo;
	private int height, width;
	private int[] dx = new int[]{0, 1, 0, -1};
	private int[] dy = new int[]{-1, 0, 1, 0};

	@Override
	public int calculateDirection(int currentDirection, boolean rateApplied, double curX, double curY) {
		int newDirection = currentDirection;
		Board board = Game.getBoard();
		canGo = new int[4];
		height = board.getHeight();
		width = board.getWidth();
		curY -= Game.TILES_SIZE;
		int tileX = Coordinates.pixelToTile(curX);
		int tileY = Coordinates.pixelToTile(curY);
		// Re-create map
		map = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Entity curEntity = board.getEntity(i, j, null);
				if(curEntity instanceof Bomb) map[i][j] = 2;
				else if(curEntity instanceof FlameSegment) map[i][j] = 2;
				else if(curEntity instanceof LayeredEntity) {
					if(((LayeredEntity) curEntity).getTopEntity().getSprite() == Sprite.brick){
						map[i][j] = 1;
					}
				} else if(curEntity != null && curEntity.getSprite() == Sprite.wall){
					map[i][j] = 1;
				}
			}
		}
		for(int i = 0; i < 4; i++){
			int nextTileX = tileX + dx[i];
			int nextTileY = tileY + dy[i];
			if(map[nextTileX][nextTileY] == 2) canGo[i] = 1;
		}
		if(canGo[0] + canGo[1] + canGo[2] + canGo[3] == 4){
			if (random.nextDouble() < CHANGE_RATE || !rateApplied)
				newDirection = random.nextInt(4);
		} else{
			if(canGo[newDirection] != 0){
				int nextMove = random.nextInt(4);
				while(canGo[nextMove] == 1){
					nextMove = random.nextInt(4);
				}
				newDirection = nextMove;
			} else{
				if (random.nextDouble() < CHANGE_RATE || !rateApplied)
					newDirection = random.nextInt(4);
			}
		}
		return newDirection;
	}

}
