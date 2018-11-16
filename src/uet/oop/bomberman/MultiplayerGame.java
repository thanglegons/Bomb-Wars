package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class MultiplayerGame extends Game{

    private int[] bombrate_multi = new int[]{0,BOMBRATE,BOMBRATE};
    private int[] bombradius_multi = new int[]{0,BOMBRADIUS,BOMBRADIUS};
    private int[] bomberSpeedV2_multi = new int[]{0,0,0};


    public MultiplayerGame(Frame frame) {
        super(frame);
    }
}
