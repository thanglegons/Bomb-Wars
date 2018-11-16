package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[1020]; //120 is enough to this game
	public boolean up, down, left, right, space, r , w, a ,s ,d, enter, plus;
	public boolean k,u,f,c;
	
	public void update() {
		/*up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];*/
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
		r = keys[KeyEvent.VK_R];

		w = keys[KeyEvent.VK_W];
		s =  keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];

		f = keys[KeyEvent.VK_F];
		k = keys[KeyEvent.VK_K];
		c = keys[KeyEvent.VK_C];
		u = keys[KeyEvent.VK_U];

		enter = keys[KeyEvent.VK_ENTER];
		plus = keys[KeyEvent.VK_NUMPAD0];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

}
