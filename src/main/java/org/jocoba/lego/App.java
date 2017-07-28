package org.jocoba.lego;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {

        //Just for test, simple way to write
        if (args != null && args.length == 3) {
            String sourcePath = args[0];
            String fileName = args[1];
            String strScale = args[2];
            Float scale = Float.valueOf(strScale);

            int index = sourcePath.lastIndexOf(".");
            String format = sourcePath.substring(index + 1);
            if (StringUtils.equalsIgnoreCase(format, BrickMaker.JPG) || StringUtils.equalsIgnoreCase(format,
                BrickMaker.PNG)) {
                Long startTime = System.currentTimeMillis();
                BufferedImage srcImage = ImageIO.read(new FileInputStream(sourcePath));

                BufferedImage destImage = BrickMaker.drawAllBrick(srcImage, BrickMaker.createGrayscaleModel(), scale);

                BrickMaker.saveImage(destImage, fileName);
                Long endTime = System.currentTimeMillis();

                System.out.println("Brick Make successfully, it takes time=" + (endTime - startTime));
            }else{
                System.out.println("Brick Make error, only support jpg/png");
            }

        } else {
            System.out.println("Brick Make version 1.0.2 [2017/07/12]");
            System.out.println("Copyright (c) 1983-2017 by Isaac Hu and others");
            System.out.println("java -jar brickmarker.jar <sourceImage:string> <fileName:string> <scale:float>");
            System.out.println("          <sourceImage:string>, full path of the source image");
            System.out.println("          <fileName:string>, file name of target, recommend to use *.jpg");
            System.out.println("          <scale:float> scale of the source image");
        }
    }
}
