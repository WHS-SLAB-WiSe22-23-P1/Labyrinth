package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;
import java.util.ArrayList;

public class Maze2D implements IMaze{
    private Main parentProcessing;
    private Coordinate start;
    private Coordinate end;
    private int sizeHeight;
    private int sizeWidth;
    private float cellSize;
    private ArrayList<ArrayList<Integer>> grid;

    public Maze2D(Main parentProcessing) {
        this.parentProcessing = parentProcessing;
    }

    public Coordinate getStart() {
        return start;
    }

    public float getCellSize() {return cellSize;}

    public Integer getCell(Coordinate cords) {
        if (cords.getX() < 0 || cords.getY() < 0)
            return 1;
        if (grid.size() > cords.getY()) {
            ArrayList<Integer> row = grid.get(cords.getY());
            if (row.size() > cords.getX())
                return row.get(cords.getX());
        }
        return 1;
    }

    public void fillMaze(Level level) {
        grid = new ArrayList<ArrayList<Integer>>();
        sizeHeight = 15;
        sizeWidth = 20;
        cellSize = 25f;

        for (int y = 0; y < sizeHeight; y++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int x = 0; x < sizeWidth; x++) {
                row.add(1);
            }
            grid.add(row);
        }

        //Fill random, so we still have something to show
        start = new Coordinate((int)(Math.random()*(sizeWidth)), (int)(Math.random()*(sizeHeight)));
        end = new Coordinate((int)(Math.random()*(sizeWidth)), (int)(Math.random()*(sizeHeight)));
        for (int y = 1; y < sizeHeight - 1; y++) {
            ArrayList<Integer> row = grid.get(y);
            for (int x = 1; x < sizeWidth - 1; x++) {
                row.set(x, (int)(Math.random()*(2)));
            }
        }

        grid.get(start.getY()).set(start.getX(), 0);
        grid.get(end.getY()).set(end.getX(), 0);

        //Read from Level
        /*
        start = level.getStart();
        end = level.getEnd();
        for (int y = 0; y < level.getHeight(); y++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int x = 0; x < level.getWidth(); x++) {
                row.add(level.getFieldAt(new Coordinate(x, y)) ? 1 : 0);
            }
            grid.add(row);
        }*/
    }

    public void drawMaze() {
        int offSetX = 0;
        int offSetY = 0;

        parentProcessing.background(153);

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.noStroke();
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.fill(255);
                    parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                }
                else {
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
