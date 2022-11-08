package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {
    private Maze2D maze;
    public void settings() {
        size(600, 480);
        printArray(PFont.list());
    }

    public void draw() {
        maze.drawMaze();
    }

    public static void main(String[] args) {
        Main newMain = new Main();

        newMain.maze = new Maze2D(newMain);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }
}
