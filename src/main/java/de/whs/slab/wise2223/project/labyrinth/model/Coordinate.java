package de.whs.slab.wise2223.project.labyrinth.model;

import org.json.simple.JSONObject;

import java.util.Objects;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(JSONObject json) {
        this((int) (long)json.get("x"), (int) (long)json.get("y"));
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

    public Coordinate left() {
        return new Coordinate(getX() - 1, getY());
    }

    public Coordinate top() {
        return new Coordinate(getX(), getY() - 1);
    }

    public Coordinate right() {
        return new Coordinate(getX() + 1, getY());
    }

    public Coordinate bottom() {
        return new Coordinate(getX(), getY() + 1);
    }

    public Coordinate nextTo(Directions direction){
        switch(direction){
            case RIGHT:
                return right();
            case LEFT:
                return left();
            case UP:
                return top();
            case DOWN:
                return bottom();
            default:
                return null;
        }
    }

    public boolean isNextTo(Coordinate other) {
        return Math.abs(getX() - other.getX()) + Math.abs(getY() - other.getY()) == 1;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        return new JSONObject() {{
            put("x", getX());
            put("y", getY());
        }};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
