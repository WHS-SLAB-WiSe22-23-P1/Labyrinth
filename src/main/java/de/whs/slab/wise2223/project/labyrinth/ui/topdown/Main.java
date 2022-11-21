package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Player2D;
import org.json.simple.JSONObject;
import processing.core.PApplet;
import java.io.FileReader;
import org.json.simple.parser.*;
import processing.event.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
    public Maze2D maze;
    public Player2D player;
    private ArrayList<Integer> currentlyPressed;

    public static void main(String[] args) {
        Main newMain = new Main();

        //Give the maze the reference to this, so it can draw
        newMain.maze = new Maze2D(newMain);
        newMain.player = new Player2D(newMain);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }

    public void settings() {
        size(600, 480);
        currentlyPressed = new ArrayList<Integer>();

        //Nik has to implement this function fully
        Level level = null;//new LevelImpl(getExampleJson());
        maze.fillMaze(level);
        player.setPosition(maze.getStart());
    }

    public void draw() {
        maze.drawMaze();
        player.movePlayer(currentlyPressed);
        player.drawCharacter();
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

    @Override
    public void keyReleased(KeyEvent event) {
        super.keyReleased(event);

        if (currentlyPressed.contains(event.getKeyCode())) {
            currentlyPressed.removeAll(Arrays.asList(event.getKeyCode()));
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);

        if (!currentlyPressed.contains(event.getKeyCode())) {
            currentlyPressed.add(event.getKeyCode());
        }
    }
}
