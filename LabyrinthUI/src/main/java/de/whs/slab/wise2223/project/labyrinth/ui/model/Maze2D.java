package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import processing.core.PApplet;

import java.util.ArrayList;

public class Maze2D implements IMaze {
    private PApplet parentProcessing;
    protected Coordinate start;
    protected Coordinate end;
    protected int sizeHeight;
    protected int sizeWidth;
    protected float cellSize;
    protected ArrayList<ArrayList<Integer>> grid;

    public Maze2D(PApplet parentProcessing) {
        this.parentProcessing = parentProcessing;

        cellSize = 25f;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public float getCellSize() {
        return cellSize;
    }

    public Integer getCell(Coordinate cords) {
        if (cords.getX() < 0 || cords.getY() < 0) return 1;
        if (grid.size() > cords.getY()) {
            ArrayList<Integer> row = grid.get(cords.getY());
            if (row.size() > cords.getX()) return row.get(cords.getX());
        }
        return 1;
    }

    public void fillMaze(Level level) {
        grid = new ArrayList<ArrayList<Integer>>();

        if (level != null) {
            start = level.getStart();
            end = level.getEnd();
            sizeHeight = level.getHeight();
            sizeWidth = level.getWidth();

            for (int y = 0; y < level.getHeight(); y++) {
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (int x = 0; x < level.getWidth(); x++) {
                    row.add(level.getFieldAt(new Coordinate(x, y)) ? 1 : 0);
                }
                grid.add(row);
            }
        }
    }

    public void drawMaze() {
        int offSetX = 0;
        int offSetY = 0;

        parentProcessing.background(153);

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.noStroke();
                if (grid.get(y).get(x) == 1) {
                    parentProcessing.fill(255);
                    parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                } else {
                    parentProcessing.fill(153);
                    parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                }
            }
        }

        if (start != null) {
            parentProcessing.fill(253, 102, 0);
            parentProcessing.rect(start.getX() * cellSize, start.getY() * cellSize, cellSize, cellSize);
        }

        if (end != null) {
            parentProcessing.fill(0, 120, 255);
            parentProcessing.rect(end.getX() * cellSize, end.getY() * cellSize, cellSize, cellSize);
        }
    }
}
