package util;

import graphics.ScreenManager;

import javax.swing.*;
import java.awt.*;

public abstract class GameCore {

    protected static final int DEFAULT_FONT_SIZE = 24;

    private static final DisplayMode[] RES_MODES = {
            new DisplayMode(1024, 768, 16, 0),
            new DisplayMode(1024, 768, 32, 0)
    };

    private boolean isRunning;
    ScreenManager screen;
    boolean fullScreen = true;

    public void stop() { isRunning = false; }

    public void run() {
        try {
            init();
            gameLoop();
        }
        finally {
            if (fullScreen) {
                screen.restoreScreen();
            }
        }
    }

    public void init() {
        screen = new ScreenManager();
        DisplayMode displayMode = screen.findFirstCompatibleMode(RES_MODES);

        if (fullScreen) {
            screen.setScreen(displayMode, fullScreen);

            Window window = screen.getFullScreenWindow();
            window.setFont(new Font("Impact", Font.PLAIN, DEFAULT_FONT_SIZE));
            window.setBackground(Color.black);
            window.setForeground(Color.WHITE);
        }

        isRunning = true;
    }

    public Image loadImage(String fileName) { return new ImageIcon(fileName).getImage(); }

    public void gameLoop() {
        long startTime = System.currentTimeMillis();

        while (isRunning) {
            long elapsedTime =
                    System.currentTimeMillis() - startTime;
            startTime += elapsedTime;

            update(elapsedTime);

            Graphics2D g = screen.getGraphics();
            draw(g);
            g.dispose();
            screen.update();
        }
    }

    public void update(long elapsedTime) {}

    public abstract void draw(Graphics2D g);
}
