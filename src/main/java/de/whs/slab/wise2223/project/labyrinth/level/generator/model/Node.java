package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

public class Node {
    private final boolean left;
    private final boolean top;
    private final boolean right;
    private final boolean bottom;

    public Node(boolean left, boolean top, boolean right, boolean bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean hasLeft() {
        return left;
    }

    public boolean hasTop() {
        return top;
    }

    public boolean hasRight() {
        return right;
    }

    public boolean hasBottom() {
        return bottom;
    }
}
