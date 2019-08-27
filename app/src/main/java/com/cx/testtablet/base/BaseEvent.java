package com.cx.testtablet.base;

public class BaseEvent {
    private int type;
    private int tag;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public BaseEvent(int type, int tag) {
        this.type = type;
        this.tag = tag;
    }
}
