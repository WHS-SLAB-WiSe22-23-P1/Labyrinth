package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    public final Dimensions size;

    private final Map<Coordinate, Boolean> edges;

    public Graph(Dimensions size) {
        this.size = size;
        edges = new HashMap<>();
    }

    private Coordinate edgeCoordinate(Coordinate a, Coordinate b) throws IllegalArgumentException {
        if (!a.isNextTo(b)) {
            throw new IllegalArgumentException(String.format("Coordinate %s is not next to %s", a, b));
        }

        if (!size.contains(a) || !size.contains(b)) {
            throw new IllegalArgumentException("Coordinates are out of bounds.");
        }

        final int deltaX = b.getX() - a.getX();
        final int deltaY = b.getY() - a.getY();

        if (deltaX == 0) {
            final int x = a.getX();
            return new Coordinate(x, a.getY() + Math.min(0, deltaY));
        } else {
            final int y = a.getY();
            return new Coordinate(a.getY() + Math.min(0, deltaX), y);
        }
    }

    public void addEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        setEdge(a, b, true);
    }

    public void removeEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        setEdge(a, b, false);
    }

    public void setEdge(Coordinate a, Coordinate b, boolean value) throws IllegalArgumentException {
        edges.put(edgeCoordinate(a, b), value);
    }

    public boolean hasEdge(Coordinate a, Coordinate b) throws IllegalArgumentException {
        return edges.getOrDefault(edgeCoordinate(a, b), false);
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
        final boolean[][] blocks = fillUpField(size.height * 2);

        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                Node node = nodeAt(coordinate);

                if (node.hasRight()) {
                    Coordinate edge = edgeCoordinate(coordinate, coordinate.right());
                    blocks[edge.getX() * 2][edge.getY() * 2] = true;
                }
                if (node.hasLeft()) {
                    Coordinate edge = edgeCoordinate(coordinate, coordinate.left());
                    blocks[edge.getX() * 2][edge.getY() * 2] = true;
                }
                if (node.hasTop()) {
                    Coordinate edge = edgeCoordinate(coordinate, coordinate.top());
                    blocks[edge.getX() * 2][edge.getY() * 2] = true;
                }
                if (node.hasBottom()) {
                    Coordinate edge = edgeCoordinate(coordinate, coordinate.bottom());
                    blocks[edge.getX() * 2][edge.getY() * 2] = true;
                }
            }
        }

        return blocks;
    }

    public boolean[][] fillUpField(int size) {
        boolean[][] field = new boolean[size][size];

        for (final boolean[] row : field)
            Arrays.fill(row, false);

        for (int i = 1; i < field.length - 1; i += 2) {
            for (int j = 1; j < field[i].length - 1; j += 2) {
                field[i][j] = true;
            }
        }

        return field;
    }
}
