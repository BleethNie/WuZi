package com.bleeth.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


/**
 * @author Sakura
 * @version 1.0
 * @date 2021-06-17 22:02
 **/
public class ImageUtil {

    public static final String RESOURCE_PATH = System.getProperty("user.dir") + "/resource";

    public static Image getImage(String filename) {
        Image image = new Image(Display.getCurrent(), filename);
        return image;
    }

    public static Color getColor(int red,int green,int blue){
        Color color = new Color (Display.getCurrent(), red, green, blue);
        return color;
    }

}
