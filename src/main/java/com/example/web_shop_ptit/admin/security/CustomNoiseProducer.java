package com.example.web_shop_ptit.admin.security;

import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.noise.NoiseProducer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CustomNoiseProducer implements NoiseProducer {

    private CurvedLineNoiseProducer curvedLineNoise = new CurvedLineNoiseProducer();

    @Override
    public void makeNoise(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Thêm đường cong nhiễu
        curvedLineNoise.makeNoise(image);

        // Thêm điểm nhiễu
        for (int i = 0; i < 30; i++) {
            int x = (int) (Math.random() * image.getWidth());
            int y = (int) (Math.random() * image.getHeight());
            int size = (int) (Math.random() * 3);
            graphics.setColor(Color.BLACK);
            graphics.fillOval(x, y, size, size);
        }

        graphics.dispose();
    }
}
