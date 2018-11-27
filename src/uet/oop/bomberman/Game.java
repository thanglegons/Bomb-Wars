package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

	public static final int TILES_SIZE = 16,
			WIDTH = TILES_SIZE * (31 / 2),
			HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;

	public static final String TITLE = "ShitGame";

	protected static final int BOMBRATE = 2;
	protected static final int BOMBRADIUS = 1;
	protected static final double BOMBERSPEED = 1.0;

	public static final int TIME = 200;
	public static final int POINTS = 0;

	protected static int SCREENDELAY = 6;

	protected static int numberOfPlayer;

	protected static int bombMax = BOMBRATE;
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double bomberSpeed = BOMBERSPEED;
	protected static double bomberSpeedV2 = 0;

	protected static int maxTypeOfBomb = 2;
	protected static int typeOfBomb = 0;

	protected static boolean godMode = false;

	protected static boolean superbomb = false;
	protected static boolean shield = false;
	protected static int wallpassDuration = 0;

	protected int _screenDelay = SCREENDELAY;

	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	private boolean _restarting = false;

	private static Board _board;
	private Screen screen;
	private Frame _frame;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	public static Clip themeSound, playerSound, bombGoesOff;

	public Game(Frame frame) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		_frame = frame;
		_frame.setTitle(TITLE);

		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();

		_board = new Board(this, _input, screen);
		addKeyListener(_input);
		prepareSound();
	}

	private void prepareSound() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
<<<<<<< HEAD
        //Sound
		String themeSoundName = "/soundtrack/theme.wav";
=======
        String themeSoundName = "/soundtrack/theme.wav";
>>>>>>> 38449e41b549ad12619837da8530164981830952
        AudioInputStream audioInputStreamTheme = AudioSystem.getAudioInputStream(new File(String.valueOf(getClass().getResource(themeSoundName).getFile())));
        themeSound = AudioSystem.getClip();
        themeSound.open(audioInputStreamTheme);
        String playerSoundName = "/soundtrack/player.wav";
        AudioInputStream audioInputStreamPlayer = AudioSystem.getAudioInputStream(new File(String.valueOf(getClass().getResource(playerSoundName).getFile())));
        playerSound = AudioSystem.getClip();
        playerSound.open(audioInputStreamPlayer);
        String bombGoesOffSoundName = "/soundtrack/bombGoesOff.wav";
        AudioInputStream audioInputStreamBombGoesOff = AudioSystem.getAudioInputStream(new File(String.valueOf(getClass().getResource(bombGoesOffSoundName).getFile())));
        bombGoesOff = AudioSystem.getClip();
        bombGoesOff.open(audioInputStreamBombGoesOff);
    }

    public static void playSound(String nameEntity){
	    if(nameEntity.equals("BombGoesOff")){
	        Clip newAudio = bombGoesOff;
	        newAudio.setFramePosition(0);
	        newAudio.start();
        }
    }

	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		_board.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);

		g.dispose();
		bs.show();
	}

	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		Graphics g = bs.getDrawGraphics();

		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}

	public void restart(){
	    _restarting = true;
        _board = new Board(this, _input, screen);
//        start();
    }

    public void resume(){
	    _restarting = false;
    }

	public void start() {
		_running = true;

		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
<<<<<<< HEAD
		//Sound
=======

>>>>>>> 38449e41b549ad12619837da8530164981830952
		themeSound.loop(Clip.LOOP_CONTINUOUSLY);
        FloatControl gainControl =
                (FloatControl) themeSound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15.0f); // Reduce volume by 10 decibels.
        themeSound.start();
		while(_running) {
		    if(_restarting){
                lastTime = System.nanoTime();
                timer = System.currentTimeMillis();
                delta = 0;
                frames = 0;
                updates = 0;
                continue;
            }
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}

			if(_paused) {
				if(_screenDelay <= 0) {
					_board.setShow(-1);
					_paused = false;
				}

				renderScreen();
			} else {
				renderGame();
			}

			if (numberOfPlayer > 1)
				_frame.changeMode(true);
			frames++;
				for (int i=0;i<_board.getBombers().size();i++)
			_frame.setShield(i, _board.getBombers().get(i).isShield());
			if(System.currentTimeMillis() - timer > 1000) {
				if (_board.getBomber() != null) {
					for (int i=0;i<_board.getBombers().size();i++) {
						_frame.setWallpass(i, _board.getBombers().get(i).getWallpassDuration() / 100);
						_frame.changeTypeOfBomb(i,_board.getBombers().get(i).getTypeOfBomb());
					}
				}
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;

				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
		System.out.println("END");
	}

	public static double getBomberSpeed() {
		return bomberSpeed;
	}

	/*public static int getBombRate() {
		return bombRate;
	}

	public static int getBombRadius() {
		return bombRadius;
	}

	public static void setBombRate(int bombRate) {
		Game.bombRate = bombRate;
	}

	public static void addBomberSpeed(double i) {
		bomberSpeed += i;
	}

	public static void addBombRadius(int i) {
		bombRadius += i;
	}

	public static void addBombRate(int i) {
		bombRate += i;
	}*/

	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public static Board getBoard() {
		return _board;
	}

	public boolean isPaused() {
		return _paused;
	}

	public void pause() {
		_paused = true;
	}

	/*public static double getBomberSpeedV2() {
		return bomberSpeedV2;
	}

	public static void setBomberSpeedV2(double bomberSpeedV2) {
		Game.bomberSpeedV2 = bomberSpeedV2;
	}

	public static boolean isShield() {
		return shield;
	}

	public static void setShield(boolean shield) {
		Game.shield = shield;
	}

	public static int getWallpassDuration() {
		return wallpassDuration;
	}
	public static void decreaseWallpassDuration(){
		if (wallpassDuration>0)
		wallpassDuration--;
	}
	public static void setWallpassDuration(int wallpassDuration) {
		Game.wallpassDuration = wallpassDuration;
	}
	//Lmao
	public static boolean isGodMode() {
		return godMode;
	}

	public static void setGodMode(boolean godMode) {
		if (godMode) {
			Game.godMode = godMode;
			setBombMax(100);
			bombRate = 100;
			bombRadius = 100;
			bomberSpeedV2 = 1.0;
			wallpassDuration = 999999999;
		}

	}

	public static int getBombMax() {
		return bombMax;
	}

	public static void setBombMax(int bombMax) {
		Game.bombMax = bombMax;
	}

	public static boolean isSuperbomb() {
		return superbomb;
	}

	public static void setSuperbomb(boolean superbomb) {
		Game.superbomb = superbomb;
	}

	public static int getMaxTypeOfBomb() {
		return maxTypeOfBomb;
	}

	public static void setMaxTypeOfBomb(int maxTypeOfBomb) {
		Game.maxTypeOfBomb = maxTypeOfBomb;
	}

	public static int getTypeOfBomb() {
		return typeOfBomb;
	}

	public static void setTypeOfBomb(int typeOfBomb) {
		Game.typeOfBomb = typeOfBomb;
	}
	public static void changeTypeOfBomb(){
		Game.typeOfBomb = (Game.typeOfBomb + 1) % Game.maxTypeOfBomb;
	}*/

	public static int getNumberOfPlayer() {
		return numberOfPlayer;
	}

	public static void setNumberOfPlayer(int numberOfPlayer) {
		Game.numberOfPlayer = numberOfPlayer;
	}

	public static int getBOMBRATE() {
		return BOMBRATE;
	}

	public static int getBOMBRADIUS() {
		return BOMBRADIUS;
	}
}