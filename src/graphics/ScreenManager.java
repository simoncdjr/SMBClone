package graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

public class ScreenManager {

    private GraphicsDevice device;

    public ScreenManager() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    public DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
        DisplayMode goodModes[] = device.getDisplayModes();

        for (int i=0; i<modes.length; i++) {
            for (int j=0; j<goodModes.length; j++) {
                if (displayModesMatch(modes[i], goodModes[j])) {
                    return modes[i];
                }
            }
        }

        return null;
    }

   public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {

        if (mode1.getWidth() != mode2.getWidth() ||
            mode1.getHeight() != mode2.getHeight())
        {
            return false;
        }

        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
            mode1.getBitDepth() != mode2.getBitDepth())
        {
            return false;
        }

        if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
            mode1.getRefreshRate() != mode2.getRefreshRate())
        {
            return false;
        }

        return true;
   }

    public void setScreen(DisplayMode displayMode, boolean toggle) {
        if (toggle) {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);
            frame.setIgnoreRepaint(true);
            frame.setResizable(false);

            device.setFullScreenWindow(frame);
            if (displayMode != null && device.isDisplayChangeSupported()) {
                try {
                    device.setDisplayMode(displayMode);
                }
                catch (IllegalArgumentException ex) {}
            }
            try {
                EventQueue.invokeAndWait(new Runnable() {
                    @Override
                    public void run() { frame.createBufferStrategy(2);}
                });
            }
            catch (InterruptedException ex) {}
            catch (InvocationTargetException ex) {}
        }
    }

    public Graphics2D getGraphics() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D) strategy.getDrawGraphics();
        }
        else { return null; }
    }

    public void update() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            BufferStrategy strategy = window.getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }
    }

    public JFrame getFullScreenWindow() { return (JFrame)device.getFullScreenWindow(); }

    public void restoreScreen() {
        Window window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }
}
