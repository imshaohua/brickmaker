package org.jocoba.lego;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author isaac.hu
 * @date 2017/7/27
 */
public class BrickMaker {
    public final static String PNG = "png";
    public final static String JPG = "jpg";


    private static IndexColorModel create8BitsColorModel() {
        Color[] colors = {Color.white, Color.darkGray, Color.lightGray,
            Color.black, Color.yellow, Color.red, Color.green,
            Color.blue, Color.CYAN, Color.PINK, Color.orange};
        int length = colors.length;
        byte[] reds = new byte[length];
        byte[] greens = new byte[length];
        byte[] blues = new byte[length];
        for (int i = 0; i < colors.length; i++) {
            reds[i] = (byte)colors[i].getRed();
            greens[i] = (byte)colors[i].getGreen();
            blues[i] = (byte)colors[i].getBlue();
        }
        IndexColorModel cm = new IndexColorModel(8, length, reds, greens, blues);
        return cm;
    }

    public static final IndexColorModel createGrayscaleModel() {
        int SIZE = 256;
        byte[] r = new byte[SIZE];
        byte[] g = new byte[SIZE];
        byte[] b = new byte[SIZE];
        for (int i = 0; i < SIZE; i++) {
            r[i] = g[i] = b[i] = (byte)i;
        }
        return new IndexColorModel(8, SIZE, r, g, b);
    }

    public static BufferedImage dye(BufferedImage image, Color color) throws IOException {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.SrcOver);
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();

        return dyed;
    }

    public static void saveImage(BufferedImage image, String filePath) throws IOException {

        if(image == null || filePath == null){
            return ;
        }

        int index = filePath.lastIndexOf(".");
        String format = filePath.substring(index + 1);

        Long startTime = System.currentTimeMillis();

        if(StringUtils.equalsIgnoreCase(format, JPG)){
            //if using jpg to save, decrease the quantity, in order to save time;
            Iterator iter = ImageIO
                .getImageWritersByFormatName("jpeg");

            ImageWriter imageWriter = (ImageWriter)iter.next();
            ImageWriteParam iwp = imageWriter.getDefaultWriteParam();

            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(0.8F);
        }

        ImageIO.write(image, format, new File(filePath));

        Long endTime = System.currentTimeMillis();
        System.out.println("Brick saves successfully, it takes time=" + (endTime - startTime));
    }

    private static BufferedImage createBaseImage(BufferedImage srcImage,IndexColorModel colorModel, float scale){
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int width = (int)(w * scale);
        int heigth = (int)(h * scale);

        BufferedImage baseImage = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = baseImage.createGraphics();
        g.drawImage(srcImage, 0, 0, width, heigth, null);
        g.dispose();

        return baseImage;
    }

    private static BufferedImage drawBrickImage(BufferedImage baseImage, BrickDrawer brickDrawer){

        int width = baseImage.getWidth();
        int heigth = baseImage.getHeight();

        int size = brickDrawer.getHeight();

        BufferedImage destImage = new BufferedImage(width * size, heigth * size, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = destImage.createGraphics();

        int total = width * heigth;

        g2d.setComposite(AlphaComposite.SrcOver);

        for (int index = 0; index < total; index++) {
            int i = index % width;
            int j = index / width;
            //图片像素点为一个矩阵，需用1个for循环操作像素
            Object data = baseImage.getRaster().getDataElements(i, j, null);//获取像素点
            //ColorModel是一个用来将图片某点的rgb值分别取出的类，包括取出alpha值
            int red = baseImage.getColorModel().getRed(data);
            int green = baseImage.getColorModel().getGreen(data);
            int blue = baseImage.getColorModel().getBlue(data);

            int x = i * size;
            int y = j * size;

            Color color = new Color(red, green, blue, 128);
            brickDrawer.drawBrick(g2d, x, y, color);
        }
        g2d.dispose();

        return destImage;
    }

    public static BufferedImage drawAllBrick(BufferedImage srcImage, IndexColorModel colorModel, float scale){
        BrickDrawer brickDrawer = new BrickDrawer();
        BufferedImage baseImage = createBaseImage(srcImage, colorModel, scale);

        BufferedImage destImage = drawBrickImage(baseImage, brickDrawer);

        return destImage;
    }

}
