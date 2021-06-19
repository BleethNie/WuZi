package com.bleeth.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import sun.audio.AudioPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * @author Sakura
 * @version 1.0
 * @date 2021-06-17 22:02
 **/
public class ResourceUtil {

    public static final String RESOURCE_PATH = System.getProperty("user.dir") + "/resource";

    private static final String SOUND_PATH = RESOURCE_PATH + "/sound/sound_2.wav";

    public static Image getImage(String filename) {
        Image image = new Image(Display.getCurrent(), filename);
        return image;
    }

    public static Color getColor(int red, int green, int blue) {
        Color color = new Color(Display.getCurrent(), red, green, blue);
        return color;
    }

    public static void playSound() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(SOUND_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(inputStream);
    }

}
