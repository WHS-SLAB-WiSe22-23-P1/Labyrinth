package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;

import java.util.Map;

public class Graph {
    public final Dimensions size;

    private Map<Coordinate, Boolean> edges;

    public Graph(Dimensions size) {
        this.size = size;
    }

    public void addEdge(Coordinate a, Coordinate b) {

    }
}
