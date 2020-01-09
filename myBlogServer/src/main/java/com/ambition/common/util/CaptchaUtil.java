package com.ambition.common.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Classname CaptchaUtil
 * @Description 生成验证码工具类
 * @Author Created by Lihaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-22 08:04
 * @Version 1.0
 */
@UtilityClass
public class CaptchaUtil {


    private int width = 200;
    private int height = 50;


    public BufferedImage createImage(){
        //生成对应宽高的初始图片
        return  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public static String drawRandomText(BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        //数字和字母的组合
        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuilder sBuffer = new StringBuilder();
        //旋转原点的 x 坐标
        int x = 10;
        String ch = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            //角度小于30度
            int degree = random.nextInt() % 30;
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }

        //画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }
        //添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 1);
        }
        return sBuffer.toString();
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));

    }

    /**
     * 不重复的验证码
     *
     * @param i
     * @return
     */
    public String codeGen(int i) {
        char[] codeSequence = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I',
                'O', 'P', 'L', 'K', 'J', 'H', 'G', 'F', 'D',
                'S', 'A', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        while (true) {
            // 随机产生一个下标，通过下标取出字符数组中对应的字符
            char c = codeSequence[random.nextInt(codeSequence.length)];
            // 假设取出来的字符在动态字符中不存在，代表没有重复的
            if (stringBuilder.indexOf(c + "") == -1) {
                stringBuilder.append(c);
                count++;
                //控制随机生成的个数
                if (count == i) {
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }
}
