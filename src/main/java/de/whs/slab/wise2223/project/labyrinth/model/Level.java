package de.whs.slab.wise2223.project.labyrinth.model;

import org.json.simple.JSONObject;

public interface Level {
    int getWidth();
    int getHeight();

    Coordinate getStart();
    Coordinate getEnd();

    boolean getFieldAt(Coordinate coordinate) throws IllegalArgumentException;

    JSONObject toJSON();
}
