package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {
	private static double CHANGE_RATE = 0.03;

	@Override
	public int calculateDirection(int currentDirection, boolean rateApplied) {
		int newDirection = currentDirection;
		if (random.nextDouble()<CHANGE_RATE || !rateApplied)
		newDirection = random.nextInt(4);
		// TODO: cài đặt thuật toán tìm đường đi
		return newDirection;
	}

}
