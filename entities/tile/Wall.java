package uet.oop.bomberman.entities.tile;


import uet.oop.bomberman.graphics.Sprite;

public class Wall extends Tile {
    private boolean border = false;

    public Wall(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public Wall(int x, int y, Sprite sprite, boolean border) {
        super(x, y, sprite);
        this.border = border;
    }

    public boolean isBorder() {
        return border;
    }
}
