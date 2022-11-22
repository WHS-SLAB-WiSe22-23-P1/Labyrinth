package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.ui.StreamLevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends PApplet {
    private final LevelProvider levelProvider;
    private Maze2D maze;

    public Main(LevelProvider levelProvider) {
        this.levelProvider = levelProvider;
    }

    public static void main(String[] args) {
        LevelProvider level = null;
        try {
            if (args.length != 1) throw new FileNotFoundException();

            level = new StreamLevelProvider(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            level = new StreamLevelProvider(System.in);
        }
        Main newMain = new Main(level);

        //Give the maze the reference to this, so it can draw
        newMain.maze = new Maze2D(newMain);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }

    public void settings() {
        size(600, 480);

        maze.fillMaze(levelProvider.getLevel());
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
