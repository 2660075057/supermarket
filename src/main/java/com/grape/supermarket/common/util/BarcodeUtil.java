package com.grape.supermarket.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;

/**
 * Created by LXP on 2017/3/14.
 * 条码工具
 */

public class BarcodeUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成条形码
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static BufferedImage encodeToOneBarcode(String str, int width, int height) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height);
        return toBufferedImage(matrix);
    }


    /**
     * 生成二维码
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static BufferedImage createTwoCode(String str, int width, int height,boolean deleteWhite) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, width, height);
        if (deleteWhite) {
            matrix = deleteWhite(matrix);
        }

        return toBufferedImage(matrix);
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix){
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
