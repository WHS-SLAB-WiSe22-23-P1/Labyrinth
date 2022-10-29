package de.whs.slab.wise2223.project.labyrinth.model;

public interface Level {
    int getWidth();
    int getHeight();

    Coordinate getStart();
    Coordinate getEnd();

    byte getFieldAt(Coordinate coordinate) throws IllegalArgumentException;

    String toJSONString();
}
