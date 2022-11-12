package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;

public class Dimensions {
    public final int width;
    public final int height;

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean contains(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < width && coordinate.getY() >= 0 && coordinate.getY() < height;
    }
}
