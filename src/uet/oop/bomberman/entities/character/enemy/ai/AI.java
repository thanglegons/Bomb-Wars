package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public abstract class AI {

	protected Random random = new Random();

	/**
	 * Thuật toán tìm đường đi
	 * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
	 */
<<<<<<< HEAD
	public abstract int calculateDirection(int currentDirection, boolean rateApplied, double x, double y, double _speed, boolean ghost);
=======
	public abstract int calculateDirection(int currentDirection, boolean rateApplied, double x, double y, double _speed);
>>>>>>> 38449e41b549ad12619837da8530164981830952
}