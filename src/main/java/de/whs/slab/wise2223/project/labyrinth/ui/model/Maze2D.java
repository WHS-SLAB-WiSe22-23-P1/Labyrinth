package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;
import java.util.ArrayList;

public class Maze2D implements IMaze{
    private Main parentProcessing;
    private Coordinate start;
    private Coordinate end;
    private ArrayList<ArrayList<Integer>> grid;

    public Maze2D(Main parentProcessing) {
        this.parentProcessing = parentProcessing;
    }

    public void fillMaze(Level level) {
        grid = new ArrayList<ArrayList<Integer>>();

        //Fill random, so we still have something to show
        start = new Coordinate((int)(Math.random()*(10)), (int)(Math.random()*(10)));
        end = new Coordinate((int)(Math.random()*(10)), (int)(Math.random()*(10)));
        for (int y = 0; y < 10; y++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int x = 0; x < 10; x++) {
                row.add((int)(Math.random()*(2)));
            }
            grid.add(row);
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

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.fill(255);
                    parentProcessing.rect(x * sizeOfBox, y * sizeOfBox, sizeOfBox, sizeOfBox);
                }
                else {
                    parentProcessing.fill(153);
                    parentProcessing.rect(x * sizeOfBox, y * sizeOfBox, sizeOfBox, sizeOfBox);
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
