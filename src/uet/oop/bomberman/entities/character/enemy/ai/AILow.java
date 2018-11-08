package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
		int newDirection = random.nextInt(4);
		// TODO: cài đặt thuật toán tìm đường đi
		return newDirection;
	}

}
