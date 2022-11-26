package de.whs.slab.wise2223.project.labyrinth.level.generator.model;

import de.whs.slab.wise2223.project.labyrinth.model.Directions;

import java.util.Arrays;

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

    public boolean hasEvery(){
        return hasLeft() && hasRight() && hasBottom() && hasTop();
    }

    public boolean hasAny(){
        return hasLeft() || hasRight() || hasBottom() || hasTop();
    }

    public Directions[] getOpenDirections(){
        final Directions[] directions = new Directions[4];

        byte i = 0;

        if(hasTop()){
            directions[i++] = Directions.UP;
        }
        if(hasBottom()){
            directions[i++] = Directions.DOWN;
        }
        if(hasRight()){
            directions[i++] = Directions.RIGHT;
        }
        if(hasLeft()){
            directions[i++] = Directions.LEFT;
        }

        return Arrays.copyOf(directions, i);

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
