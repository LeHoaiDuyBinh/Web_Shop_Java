package com.example.web_shop_ptit.admin.security;

import cn.apiclub.captcha.text.renderer.WordRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

public class CustomWordRenderer implements WordRenderer {

    @Override
    public void render(String s, BufferedImage bufferedImage) {
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

        Font font = new Font("Kristen ITC", Font.BOLD, 40); // Thay đổi font chữ và cỡ chữ tùy ý
        g2d.setFont(font);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(s);
        int stringHeight = fontMetrics.getAscent() + fontMetrics.getDescent();

        int x = (bufferedImage.getWidth() - stringWidth) / 2;
        int y = (bufferedImage.getHeight() - stringHeight) / 2 + fontMetrics.getAscent();

        g2d.setColor(Color.BLACK);
        g2d.drawString(s, x, y);
        g2d.dispose();
    }

}