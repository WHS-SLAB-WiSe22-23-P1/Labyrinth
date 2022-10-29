package de.whs.slab.wise2223.project.labyrinth.model;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toJSONString() {
        return String.format("{\"x\": \"%d\", \"y\": \"%d\"}", x, y);
    }
}
