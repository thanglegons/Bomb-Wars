package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
<<<<<<< HEAD
import uet.oop.bomberman.entities.tile.Wall;
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;

	private static double CHANGE_RATE = 0.03;

	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	private int[][] map;
	private int[] canGo;
	private int height, width;
	private int[] dx = new int[]{0, 1, 0, -1};
	private int[] dy = new int[]{-1, 0, 1, 0};
	private double[] gx = new double[]{Game.TILES_SIZE / 2 - 1, Game.TILES_SIZE - 1, Game.TILES_SIZE / 2 - 1, 0};
	private double[] gy = new double[]{0, Game.TILES_SIZE / 2 - 1, Game.TILES_SIZE - 1, Game.TILES_SIZE / 2 - 1};

	private class Pair{
		private int x;
		private int y;
		public Pair(int x, int y){
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Pair pair = (Pair) o;
			return x == pair.x &&
					y == pair.y;
		}
	}

	private Pair bomber;

	private int shortestPath(int x, int y){
		Queue<Pair> q = new LinkedList<>();
		q.add(new Pair(x, y));
		int[][] dist = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++) dist[i][j] = 100000;
		}
		dist[x][y] = 0;
		while(q.size() > 0){
			Pair cur = q.remove();
			int curX = cur.getX();
			int curY = cur.getY();
			if(cur == bomber){
				return dist[curX][curY];
			}
			if(dist[curX][curY] > 5) continue;
			for(int dir = 0; dir < 4; dir++){
				int nextX = curX + dx[dir];
				int nextY = curY + dy[dir];
                if(nextX < 0 || nextX >= width) continue;
                if(nextY < 0 || nextY >= height) continue;
				if(map[nextX][nextY] == 0){
					if(dist[nextX][nextY] > dist[curX][curY] + 1){
						dist[nextX][nextY] = dist[curX][curY] + 1;
						q.add(new Pair(nextX, nextY));
					}
				}
			}
		}
		return dist[bomber.getX()][bomber.getY()];
	}

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
		double bomberX = board.getBomber().getX() + Game.TILES_SIZE / 2;
		double bomberY = board.getBomber().getY() - Game.TILES_SIZE / 2;
		int tileX = Coordinates.pixelToTile(bomberX);
		int tileY = Coordinates.pixelToTile(bomberY);
		bomber = new Pair(tileX, tileY);
		// Re-create map
		map = new int[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Entity curEntity = board.getEntity(i, j, null);
				if(curEntity instanceof Bomb) map[i][j] = 2;
				else if(curEntity instanceof FlameSegment) map[i][j] = 2;
<<<<<<< HEAD
				else {
					if (!ghost) {
						if (curEntity instanceof LayeredEntity) {
							if (((LayeredEntity) curEntity).getTopEntity().getSprite() == Sprite.brick) {
								map[i][j] = 1;
							}
						} else if (curEntity != null && curEntity.getSprite() == Sprite.wall) {
							map[i][j] = 1;
						}
					} else{
						if (curEntity instanceof Wall && ((Wall) curEntity).isBorder()) {
							//System.out.println("??" + i +" " + j);
							map[i][j] = 1;
						}
					}
=======
				else if(curEntity instanceof LayeredEntity) {
					if(((LayeredEntity) curEntity).getTopEntity().getSprite() == Sprite.brick){
						map[i][j] = 1;
					}
				} else if(curEntity != null && curEntity.getSprite() == Sprite.wall){
					map[i][j] = 1;
>>>>>>> 38449e41b549ad12619837da8530164981830952
				}
			}
		}
//		System.out.println(curX + " " + curY);
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
					int curTileX = Coordinates.pixelToTile(curX + dx[dir] * _speed + i * (Game.TILES_SIZE - 1));
					int curTileY = Coordinates.pixelToTile(curY + dy[dir] * _speed + j * (Game.TILES_SIZE - 1));
//					if(dir == 3){
//					    System.out.println("??" + curTileX + " " + curTileY);
//                    }
<<<<<<< HEAD
					//System.out.println(curTileX+ " " + curTileY);
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
					if(map[curTileX][curTileY] >= 1) canGo[dir] = 1;
				}
			}
		}
		if(canGo[0] + canGo[1] + canGo[2] + canGo[3] == 4){
			if (random.nextDouble() < CHANGE_RATE || !rateApplied)
				newDirection = random.nextInt(4);
		} else{
<<<<<<< HEAD
			//System.out.println(canGo[0] + " " + canGo[1] + " " + canGo[2] + " " + canGo[3]);
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
//			System.out.println(tileEX + " " + tileEY);
			int minSoFar = 100000;
			for(int i = 0; i < 4; i++){
			    if(canGo[i] == 1) continue;
//			    System.out.println(i + "-");
                int nextEX = Coordinates.pixelToTile(curX + dx[i] * _speed + gx[i]);
                int nextEY = Coordinates.pixelToTile(curY + dy[i] * _speed + gy[i]);
<<<<<<< HEAD
			    if (map[nextEX][nextEY] != 0) continue;
			    int curPath = shortestPath(nextEX, nextEY);
			    //System.out.println(minSoFar);
=======
			    if(map[nextEX][nextEY] != 0) continue;
			    int curPath = shortestPath(nextEX, nextEY);
>>>>>>> 38449e41b549ad12619837da8530164981830952
			    if(curPath < minSoFar){
			        minSoFar = curPath;
			        newDirection = i;
                }
<<<<<<< HEAD
				//System.out.println(nextEX + " " +  nextEY +" " + curPath+" " + i);
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
            }
            if(minSoFar == 100000){
                if(canGo[currentDirection] != 0){
                    ArrayList<Integer> validMove = new ArrayList<>();
                    for(int i = 0; i < 4; i++) if(canGo[i] == 0) validMove.add(i);
                    newDirection = validMove.get(random.nextInt(validMove.size()));
                } else{
                    if (random.nextDouble() < CHANGE_RATE || !rateApplied)
                        newDirection = random.nextInt(4);
                    else{
                        newDirection = currentDirection;
                    }
                }
            }
		}
<<<<<<< HEAD
		System.out.println(newDirection);
=======
>>>>>>> 38449e41b549ad12619837da8530164981830952
		return newDirection;
	}

}
