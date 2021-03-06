package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.Boss;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.level.LevelLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Board implements IRender {
	protected Boss boss = null;
	protected LevelLoader _levelLoader;
	protected Game _game;
	protected Keyboard _input;
	protected Screen _screen;

	protected int loser = -1;
	
	public Entity[] _entities;
	public List<Character> _characters = new ArrayList<>();
	protected List<Bomb> _bombs = new ArrayList<>();
	private List<FlameSegment> _freeFlameSegment = new ArrayList<>();
	private List<Bomber> _bombers= new ArrayList<>();
	private List<Message> _messages = new ArrayList<>();
	
	private int _screenToShow = 4; //1:endgame, 2:changelevel, 3:paused

	private Portal portal;
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	
	public Board(Game game, Keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		
		loadLevel(1); //start in level 1
	}

    public Board(Game game, Keyboard input, Screen screen, boolean isMulti) {
        _game = game;
        _input = input;
        _screen = screen;

        loadMulti(1); //start in level 1
    }
	
	@Override
	public void update() {
		if( _game.isPaused() ) return;
		_input.update();
		//System.out.println(_bombers.size());
		updateEntities();
		updateCharacters();
		updateBombs();
		updateFreeFlameSegment();
		updateMessages();
		detectEndGame();

		if (_input.space)
			_screenToShow =-1;
		
		for (int i = 0; i < _characters.size(); i++) {
			Character a = _characters.get(i);
			if(a.isRemoved()) _characters.remove(i);
		}
		for (int i=0;i<_freeFlameSegment.size();i++){
			FlameSegment flameSegment = _freeFlameSegment.get(i);
			if (flameSegment.isRemoved())
				_freeFlameSegment.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;
		
		//only render the visible part of screen
		int x0 = Screen.xOffset >> 4; //tile precision, -> left X
		int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight() + Game.TILES_SIZE) / Game.TILES_SIZE; //render one tile plus to fix black margins
		if (y1>this.getHeight()) y1--;
		if (x1>this.getWidth()) x1--;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x + y * _levelLoader.getWidth() >= _entities.length)
					System.out.println("ok");
				_entities[x + y * _levelLoader.getWidth()].render(screen);
			}
		}

		renderFreeFlameSegment(screen);
		renderBombs(screen);
		renderCharacter(screen);
	}
	
	public void nextLevel() {
		loadLevel(_levelLoader.getLevel() + 1);
	}
	
	public void loadLevel(int level) {
		if (level == 2)
			System.out.println("a");
		boss = null;
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_freeFlameSegment.clear();
		_bombs.clear();
		_messages.clear();
		try {
			_levelLoader = new FileLevelLoader(this, level);
			_entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth()];
			
			_levelLoader.createEntities();
			_bombers.clear();
			for (Character character: _characters){
				if (character instanceof Bomber){
					_bombers.add((Bomber)character);
					((Bomber) character).setBombRate(((Bomber) character).getBombMax());
				}
			}
		} catch (LoadLevelException e) {
			endGame();
		}
	}

	public void loadMulti(int level){
		if (level == 2)
			System.out.println("a");
		boss = null;
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_freeFlameSegment.clear();
		_bombs.clear();
		_messages.clear();
		try {
			_levelLoader = new FileLevelLoader(this, level, true);
			_entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth()];
			_levelLoader.createEntities();
			_bombers.clear();
			for (Character character: _characters){
				if (character instanceof Bomber){
					_bombers.add((Bomber)character);
					((Bomber) character).setBombRate(((Bomber) character).getBombMax());
				}
			}
		} catch (LoadLevelException e) {
			endGame();
		}
	}
	
	protected void detectEndGame() {
		if(_time <= 0)
			_time = 10000;
		/*if (detectNoEnemies())
			System.out.println("lamao");*/
		//System.out.println(Game.getWallpassDuration());
		//System.out.println(portal.getX()+" " +portal.getY() +" " +getBomber().getTileX() + " "+ getBomber().getTileY());
		if (Game.numberOfPlayer == 1&& detectNoEnemies() && getBomber().getTileX() == portal.getX()
			&& getBomber().getTileY() == portal.getY()) {
			if (_levelLoader.getLevel() < 4)
				nextLevel(); else
					endGame();
		}
	}
	
	public void endGame() {
		if (Game.numberOfPlayer == 1) {
			_screenToShow = 1;
			_game.resetScreenDelay();
			_game.pause();
		} else{
			_screenToShow = 4;
			_game.resetScreenDelay();
			_game.pause();
		}
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _characters.size(); i++) {
			if(_characters.get(i) instanceof Bomber == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points);
				break;
			case 2:
				_screen.drawChangeLevel(g, _levelLoader.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
			/*case 4:
				_screen.drawMainMenu(g);
				break;*/
			case 4:
				_screen.drawEndMultiGame(g,1 + (1-(loser-1)));
				break;
		}
	}
	
	public Entity getEntity(double x, double y, Character m) {
		
		Entity res = null;
		
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return _bombs;
	}
	
	public Bomb getBombAt(double x, double y) {

		for (Bomber bomber: _bombers){
			Iterator<Bomb> itr = bomber.get_bombs().iterator();
			while(itr.hasNext()){
				Bomb temp = itr.next();
				if(temp.getX() == (int)x && temp.getY() == (int)y)
					return temp;
			}
		}
		return null;
	}

	public Bomber getBomber() {
		if (_bombers.isEmpty()) return null;
		return _bombers.get(0);
	}

	public List<Bomber> getBombers(){
		return _bombers;
	}
	
	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = _characters.iterator();
		
		Character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public FlameSegment getFlameSegmentAt(int x, int y) {
		for (Bomber bomber: _bombers) {
			Iterator<Bomb> bs = bomber.get_bombs().iterator();
			Bomb b;
			while (bs.hasNext()) {
				b = bs.next();

				FlameSegment e = b.flameAt(x, y);
				if (e != null) {
					return e;
				}
			}
		}
		for (FlameSegment flameSegment : _freeFlameSegment) {
			if (flameSegment.getX() == x && flameSegment.getY() == y)
				return flameSegment;
		}
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return _entities[(int)x + (int)y * _levelLoader.getWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addCharacter(Character e) {
		_characters.add(e);
	}
	
	public void addBomb(Bomb e) {
		_bombs.add(e);
	}

	public void addFreeFlameSegment(FlameSegment a) {
		_freeFlameSegment.add(a);
	}
	
	public void addMessage(Message e) {
		_messages.add(e);
	}

	protected void renderCharacter(Screen screen) {
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		/*Iterator<Bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
		*/
		for (Bomber bomber: _bombers){
			Iterator<Bomb> itr = bomber.get_bombs().iterator();
			while(itr.hasNext())
				itr.next().render(screen);
		}
	}

	protected void renderFreeFlameSegment(Screen screen){
		for (FlameSegment flameSegment : _freeFlameSegment){
			if (flameSegment!=null)
			flameSegment.render(screen);
		}
	}
	
	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * Game.SCALE, (int)m.getY());
		}
	}
	
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}
	
	protected void updateCharacters() {
		if( _game.isPaused() ) return;
		Iterator<Character> itr = _characters.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;
		/*Iterator<Bomb> itr = _bombs.iterator();
		
		*/
		for (Bomber bomber: _bombers){
			Iterator<Bomb> itr = bomber.get_bombs().iterator();
			while(itr.hasNext())
				itr.next().update();
		}
	}
	protected void updateFreeFlameSegment(){
		for (FlameSegment flameSegment: _freeFlameSegment){
			if (flameSegment!=null)
			flameSegment.update();
		}
	}
	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		int left;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				_messages.remove(i);
		}
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public Keyboard getInput() {
		return _input;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}

	public LevelLoader getLevel() {
		return _levelLoader;
	}

	public Game getGame() {
		return _game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getTime() {
		return _time;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}
	
	public int getWidth() {
		return _levelLoader.getWidth();
	}

	public int getHeight() {
		return _levelLoader.getHeight();
	}

	public int getLoser() {
		return loser;
	}

	public void setLoser(int loser) {
		this.loser = loser;
	}

	public Boss getBoss() {
		return boss;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
	}
}
