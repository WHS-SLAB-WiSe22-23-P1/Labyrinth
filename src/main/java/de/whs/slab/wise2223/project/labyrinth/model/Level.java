package de.whs.slab.wise2223.project.labyrinth.model;

import com.sun.istack.internal.NotNull;
import org.json.simple.JSONObject;

public interface Level {
    @NotNull int getWidth();
    @NotNull int getHeight();

    Coordinate getStart();
    Coordinate getEnd();

    boolean getFieldAt(Coordinate coordinate) throws IllegalArgumentException;

    JSONObject toJSON();
}
