package com.example.web_shop_ptit.admin.security;


import cn.apiclub.captcha.text.producer.TextProducer;

public class CustomTextProducer implements TextProducer {

    private final String[] characters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public String getText() {
        // Tạo văn bản ngẫu nhiên có thể là chữ hoa, chữ thường, hoặc kết hợp
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * characters.length);
            sb.append(characters[randomIndex]);
        }
        return sb.toString();
    }
}