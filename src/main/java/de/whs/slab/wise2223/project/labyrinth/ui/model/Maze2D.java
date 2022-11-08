package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;
import java.util.ArrayList;

public class Maze2D implements IMaze{
    private Main mainP;
    private int height;
    private int width;
    private ArrayList<ArrayList<Integer>> grid;

    public Maze2D(Main newMain) {
        this.mainP = newMain;
        fillMaze();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private void fillMaze() {
        grid = new ArrayList<ArrayList<Integer>>();

        for (int y = 0; y < 10; y++) {
            ArrayList<Integer> row = new ArrayList<Integer>();
            for (int x = 0; x < 10; x++) {
                row.add(1);
                row.add(0);
            }
            grid.add(row);
        }
    }

    public void drawMaze() {
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if(grid.get(y).get(x) == 0) {
                    mainP.fill(153);
                    mainP.rect(x * 25, y * 25, 25, 25);
                }
                else {
                    mainP.fill(204, 102, 1);
                    mainP.rect(x * 25, y * 25, 25, 25);
                }
            }
        }
    }
}
