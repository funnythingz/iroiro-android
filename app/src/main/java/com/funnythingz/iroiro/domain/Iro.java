package com.funnythingz.iroiro.domain;

public class Iro {
    protected int id;
    protected Color color;
    protected String content;

    public Iro(int id, Color color, String content) {
        this.id = id;
        this.color = color;
        this.content = content;
    }
}