/*
 * ImageScaler.java
 *
 * Created on October 5, 2006, 7:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package carpreview;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author sky
 */
public final class ImageScaler {
    public static Image scaleImage(Image image, int w, int h) {
        int iw = image.getWidth(null);
        int ih = image.getHeight(null);
        if (iw > 0 && ih > 0) {
            float aspectRatio = (float)iw /(float)ih;
            int targetWidth;
            int targetHeight;
            if (iw > ih) {
                targetWidth = w;
                targetHeight = (int)(targetWidth / aspectRatio);
                if (targetHeight > h) {
                    targetHeight = h;
                    targetWidth = (int)(aspectRatio * targetHeight);
                }
            } else {
                targetHeight = h;
                targetWidth = (int)(aspectRatio * targetHeight);
                if (targetWidth > w) {
                    targetWidth = w;
                    targetHeight = (int)(targetWidth / aspectRatio);
                }
            }
            if (targetWidth != iw || targetHeight != iw) {
                int type;
                if (image instanceof BufferedImage) {
                    type = ((BufferedImage)image).getType();
                } else {
                    type = BufferedImage.TYPE_INT_ARGB;
                }
                BufferedImage scaledImage = new BufferedImage(
                        targetWidth, targetHeight, type);
                Graphics2D imageG = scaledImage.createGraphics();
//                imageG.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                imageG.drawImage(image, 0, 0, targetWidth, targetHeight,
                        0, 0, iw, ih, null);
                imageG.dispose();
                return scaledImage;
            }
        }
        return image;
    }

    public static void main(String[] args) {
        int matchedFiles = 0;
        for (File file : new File(System.getProperty("user.dir")).listFiles()) {
            String fileName = file.getName();
            int dotIndex = fileName.indexOf('.');
            if (dotIndex != -1) {
                String extension = fileName.substring(dotIndex + 1).toLowerCase();
                if (extension.equals("jpg")) {
                    System.err.println("processing " + fileName);
                    try {
                        BufferedImage image = ImageIO.read(file);
                        BufferedImage scaledImage = (BufferedImage)scaleImage(image, 800, 600);
                        ImageIO.write(scaledImage, "jpg", 
                                new File(file.getParentFile(), Integer.toString(matchedFiles++) + ".jpg"));
                        if (matchedFiles > 25) {
                            break;
                        }
                        System.err.println("\tprocessed");
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }
}
