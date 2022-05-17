package com.xiedapao.netty.base;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * netty传输协议
 */
public class Message {

    private int length;
    private byte[] content;

    public Message(String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            this.content = bytes;
            this.length = bytes.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "length=" + length +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
