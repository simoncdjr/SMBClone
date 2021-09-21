package graphics;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

    private ArrayList frames;
    private long currFrameIndex;
    private long animTime;
    private long totalDuration;

    public Animation() { this(new ArrayList(), 0); }
    private Animation(ArrayList frames, long totalDuration) {
        this.frames = frames;
        this.totalDuration = totalDuration;
        start();
    }

    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
