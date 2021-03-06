package info.demmonic.hdrs.input.model;

import info.demmonic.hdrs.input.Mouse;

public enum Area {

    CHAT(17, 357, 479, 96),
    TAB(553, 205, 190, 261),
    VIEWPORT(4, 4, 512, 334),;

    public int x;
    public int y;
    public int width;
    public int height;

    Area(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

    public boolean containsMouse() {
        return contains(Mouse.lastX, Mouse.lastY);
    }

}
