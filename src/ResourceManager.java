import javax.swing.*;
import java.awt.*;

public class ResourceManager {

    public Image loadImage(String name) {
        String filename = "images/mario.png";
        return new ImageIcon(filename).getImage();
    }
}
