package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Graph {
    public final Dimensions size;
    private final Map<EdgeCoordinate, Boolean> edges;

    public Graph(Dimensions size) {
        this.size = size;
        edges = new HashMap<>();
    }

    public void addEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        setEdge(a, b, true);
    }

    public void removeEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        setEdge(a, b, false);
    }

    public void setEdge(Coordinate a, Coordinate b, boolean value) throws IllegalArgumentException {
        edges.put(new EdgeCoordinate(a, b), value);
    }

    public boolean hasEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        return edges.getOrDefault(new EdgeCoordinate(a, b), false);
    }

    public Node nodeAt(Coordinate position) {
        if (!size.contains(position)) {
            throw new IllegalArgumentException("Coordinate out of bounds");
        }

        return new Node(size.contains(position.left()) && hasEdge(position, position.left()), size.contains(position.top()) && hasEdge(position, position.top()), size.contains(position.right()) && hasEdge(position, position.right()), size.contains(position.bottom()) && hasEdge(position, position.bottom()));
    }

    public Node[][] grid() {
        final Node[][] nodes = new Node[size.width][size.height];

        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                nodes[x][y] = nodeAt(new Coordinate(x, y));
            }
        }

        return nodes;
    }

    public boolean[][] getBlocks() {
        final boolean[][] blocks = fillUpField(size.width * 2 + 1, size.height * 2 + 1);

        edges.forEach((coordinate, hasEdge) -> blocks[coordinate.a.getY() + coordinate.b.getY() + 1][coordinate.a.getX() + coordinate.b.getX() + 1] = hasEdge);

        return blocks;
    }

    private boolean[][] fillUpField(int width, int height) {
        boolean[][] field = new boolean[height][width];

        for (final boolean[] row : field)
            Arrays.fill(row, false);

        for (int i = 1; i < field.length; i += 2) {
            for (int j = 1; j < field[i].length; j += 2) {
                field[i][j] = true;
            }
        }

        return field;
    }

    private class EdgeCoordinate {
        public final Coordinate a;
        public final Coordinate b;

        private EdgeCoordinate(Coordinate a, Coordinate b) throws IllegalArgumentException{
            if (!a.isNextTo(b)) {
                throw new IllegalArgumentException(String.format("Coordinate %s is not next to %s", a, b));
            }
            if (a.hashCode() > b.hashCode()) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EdgeCoordinate)) return false;
            EdgeCoordinate that = (EdgeCoordinate) o;
            return a.equals(that.a) && b.equals(that.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }
}
