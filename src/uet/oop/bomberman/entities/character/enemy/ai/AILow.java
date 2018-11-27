package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.util.ArrayList;

public class AILow extends AI {
	private static double CHANGE_RATE = 0.03;

	private int[][] map;
	private int[] canGo;
	private int height, width;
	private int[] dx = new int[]{0, 1, 0, -1};
	private int[] dy = new int[]{-1, 0, 1, 0};
	private double[] gx = new double[]{Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2, 0};
	private double[] gy = new double[]{0, Game.TILES_SIZE / 2, Game.TILES_SIZE, Game.TILES_SIZE / 2};

	@Override
<<<<<<< HEAD
	public int calculateDirection(int currentDirection, boolean rateApplied, double curX, double curY, double _speed, boolean ghost) {
=======
	public int calculateDirection(int currentDirection, boolean rateApplied, double curX, double curY, double _speed) {
>>>>>>> 38449e41b549ad12619837da8530164981830952
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
//		if(map[tileX][tileY] != 0){
//			System.out.println("Something wrong!!");
//		}
		for(int dir = 0; dir < 4; dir++){
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if(dir == 0){
						if(!((i == 0 && j == 0) || (i == 1 && j == 0))) continue;
					}
					if(dir == 1){
						if(!((i == 1 && j == 1) || (i == 1 && j == 0))) continue;
					}
					if(dir == 2){
						if(!((i == 1 && j == 1) || (i == 0 && j == 1))) continue;
					}
					if(dir == 3){
						if(!((i == 0 && j == 0) || (i == 0 && j == 1))) continue;
					}
<<<<<<< HEAD
					double additionx= dx[dir] * _speed;
					double additiony =dy[dir] * _speed;
					int curTileX = Coordinates.pixelToTile(curX + additionx + i * (Game.TILES_SIZE - 1));
					int curTileY = Coordinates.pixelToTile(curY + additiony + j * (Game.TILES_SIZE - 1));
=======
					int curTileX = Coordinates.pixelToTile(curX + dx[dir] * _speed + i * (Game.TILES_SIZE - 1));
					int curTileY = Coordinates.pixelToTile((curY + dy[dir] * _speed + j * (Game.TILES_SIZE - 1)));
>>>>>>> 38449e41b549ad12619837da8530164981830952
					if(map[curTileX][curTileY] >= 1) canGo[dir] = 1;
				}
			}
		}
		if(canGo[0] + canGo[1] + canGo[2] + canGo[3] == 4){
			if (random.nextDouble() < CHANGE_RATE || !rateApplied)
				newDirection = random.nextInt(4);
		} else{
			if(canGo[newDirection] != 0){
				ArrayList<Integer> validMove = new ArrayList<>();
				for(int i = 0; i < 4; i++) if(canGo[i] == 0) validMove.add(i);
				newDirection = validMove.get(random.nextInt(validMove.size()));
//				System.out.println("haha");
//				for(Integer x : validMove) System.out.print(x);
//				System.out.println("??" + newDirection + "?");
			} else{
				if (random.nextDouble() < CHANGE_RATE || !rateApplied)
					newDirection = random.nextInt(4);
			}
		}
		return newDirection;
	}

}