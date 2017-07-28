package org.jocoba.lego;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author isaac.hu
 * @date 2017/7/27
 */
@Setter
@Getter
public class BrickDrawer {

    private boolean isInit = false;

    private int height;

    private int width;

    private BufferedImage brickImage;

    private String DEFAULT_BRICK = "brick.png";

    public BrickDrawer() {
        super();

        try {
            this.init(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BrickDrawer(String path){
        super();

        try {
            this.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean init(String path) throws Exception {

        if(isInit){
            return true;
        }

        InputStream brickStream = ClassLoader.getSystemResourceAsStream(DEFAULT_BRICK);
        if(StringUtils.isNotBlank(path)) {
            File file = new File(path);
            if (file.exists()) {
                brickStream = new FileInputStream(path);
            }
        }

        brickImage = ImageIO.read(brickStream);
        height = brickImage.getHeight();
        width = brickImage.getWidth();

        if(height != width){
           isInit = false;
        }else{
            isInit = true;
        }

        return isInit;
    }

    public void drawBrick(Graphics2D g2d, int xPos, int yPos, Color color){
        Color brickColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);

        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.drawImage(brickImage, xPos,yPos, null);
        g2d.setColor(brickColor);
        g2d.fillRect(xPos, yPos, width, height);
    }

}
