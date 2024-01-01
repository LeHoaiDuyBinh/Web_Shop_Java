package com.example.web_shop_ptit.admin.security;

import cn.apiclub.captcha.backgrounds.BackgroundProducer;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomBackgroundProducer implements BackgroundProducer {
    private final FlatColorBackgroundProducer flatColorBackgroundProducer = new FlatColorBackgroundProducer();

    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Sử dụng nhiều màu sắc ngẫu nhiên từ danh sách các màu an toàn
        Color[] safeColors = {
                new Color(0, 102, 204), // Blue
                new Color(0, 153, 76),  // Green
                new Color(204, 0, 0),   // Red
                // Thêm các màu khác nếu cần
        };

        // Tạo hình nền
        BufferedImage background = flatColorBackgroundProducer.getBackground(width, height);
        Graphics2D graphics = background.createGraphics();

        // Áp dụng gradient hoặc họa tiết
        GradientPaint gradient = new GradientPaint(0, 0, safeColors[0], width, height, safeColors[1]);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);

        // Thêm nhiễu vào hình nền
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int noise = (int) (Math.random() * 60);
                int rgb = background.getRGB(i, j);
                int r = Math.max(0, Math.min(255, ((rgb >> 16) & 0xFF) + noise));
                int g = Math.max(0, Math.min(255, ((rgb >> 8) & 0xFF) + noise));
                int b = Math.max(0, Math.min(255, (rgb & 0xFF) + noise));
                background.setRGB(i, j, (r << 16) | (g << 8) | b);
            }
        }

        graphics.dispose();

        // Kết hợp hình nền với ảnh gốc
        Graphics2D combined = image.createGraphics();
        combined.drawImage(background, 0, 0, null);
        combined.dispose();

        return image;
    }

    @Override
    public BufferedImage getBackground(int width, int height) {
        // Sử dụng nhiều màu sắc ngẫu nhiên từ danh sách các màu an toàn
        Color[] safeColors = {
                new Color(0, 102, 204), // Blue
                new Color(0, 153, 76),  // Green
                new Color(204, 0, 0),   // Red
                // Thêm các màu khác nếu cần
        };

        BufferedImage background = flatColorBackgroundProducer.getBackground(width, height);
        Graphics2D graphics = background.createGraphics();

        // Áp dụng gradient hoặc họa tiết
        GradientPaint gradient = new GradientPaint(0, 0, safeColors[0], width, height, safeColors[1]);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);

        // Thêm nhiễu vào hình nền
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int noise = (int) (Math.random() * 60);
                int rgb = background.getRGB(i, j);
                int r = Math.max(0, Math.min(255, ((rgb >> 16) & 0xFF) + noise));
                int g = Math.max(0, Math.min(255, ((rgb >> 8) & 0xFF) + noise));
                int b = Math.max(0, Math.min(255, (rgb & 0xFF) + noise));
                background.setRGB(i, j, (r << 16) | (g << 8) | b);
            }
        }

        graphics.dispose();
        return background;
    }
}
