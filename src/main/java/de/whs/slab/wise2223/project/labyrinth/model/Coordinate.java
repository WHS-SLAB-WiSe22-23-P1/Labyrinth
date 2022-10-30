package de.whs.slab.wise2223.project.labyrinth.model;

import org.json.simple.JSONObject;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(JSONObject json) {
        this((int) json.get("x"), (int) json.get("y"));
    }

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

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        return new JSONObject() {{
            put("x", getX());
            put("y", getY());
        }};
    }
}
