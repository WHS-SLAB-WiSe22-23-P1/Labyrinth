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
    private ArrayList<ArrayList<Integer>> grid;

    public Maze2D(Main parentProcessing) {
        this.parentProcessing = parentProcessing;
    }

    public Coordinate getStart() {
        return start;
    }

    public void fillMaze(Level level) {
        grid = new ArrayList<ArrayList<Integer>>();
        sizeHeight = 15;
        sizeWidth = 20;

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
        int sizeOfBox = 25;
        int offSetX = 0;
        int offSetY = 0;

        parentProcessing.background(153);

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.noStroke();
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.fill(255);
                    parentProcessing.rect(offSetX + x * sizeOfBox, offSetY + y * sizeOfBox, sizeOfBox, sizeOfBox);
                }
                else {
                    parentProcessing.fill(153);
                    parentProcessing.rect(offSetX + x * sizeOfBox, offSetY + y * sizeOfBox, sizeOfBox, sizeOfBox);
                }
            }
        }

        if (start != null) {
            parentProcessing.fill(253, 102, 0);
            parentProcessing.rect(start.getX() * sizeOfBox, start.getY() * sizeOfBox, sizeOfBox, sizeOfBox);
        }

        if (end != null) {
            parentProcessing.fill(0, 120, 255);
            parentProcessing.rect(end.getX() * sizeOfBox, end.getY() * sizeOfBox, sizeOfBox, sizeOfBox);
        }
    }
}
