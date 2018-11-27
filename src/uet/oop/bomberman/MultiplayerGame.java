package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class MultiplayerGame extends Game{

    private int[] bombrate_multi = new int[]{0,BOMBRATE,BOMBRATE};
    private int[] bombradius_multi = new int[]{0,BOMBRADIUS,BOMBRADIUS};
    private int[] bomberSpeedV2_multi = new int[]{0,0,0};


    public MultiplayerGame(Frame frame) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(frame);
    }
}
