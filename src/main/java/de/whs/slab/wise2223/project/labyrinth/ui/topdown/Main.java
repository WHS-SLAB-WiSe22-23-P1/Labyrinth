package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import org.json.simple.JSONObject;
import processing.core.PApplet;
import java.io.FileReader;
import org.json.simple.parser.*;
import java.io.IOException;

public class Main extends PApplet {
    private Maze2D maze;

    public static void main(String[] args) {
        Main newMain = new Main();

        //Give the maze the reference to this, so it can draw
        newMain.maze = new Maze2D(newMain);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }

    public void settings() {
        size(600, 480);

        //Nik has to implement this function fully
        Level level = null;//new LevelImpl(getExampleJson());
        maze.fillMaze(level);
    }

    public void draw() {
        maze.drawMaze();
    }

    private JSONObject getExampleJson() {
        JSONObject json;
        try {
            json = (JSONObject) new JSONParser().parse(new FileReader("./src/main/java/de/whs/slab/wise2223/project/labyrinth/ui/model/Example.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
