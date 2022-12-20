package de.whs.slab.wise2223.project.labyrinth.ui.topdown;

import de.whs.slab.wise2223.project.labyrinth.model.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.LevelUI;
import de.whs.slab.wise2223.project.labyrinth.ui.StreamLevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Player2D;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import processing.event.KeyEvent;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends LevelUI {
    public Maze2D maze;
    public Player2D player;
    private ArrayList<Integer> currentlyPressed;
    private Boolean finished = false;

    public Main(LevelProvider levelProvider) {
        super(levelProvider);
    }

    public static void main(String[] args) {
        LevelProvider level;
        try {
            if (args.length != 1) throw new FileNotFoundException();

            level = new StreamLevelProvider(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            level = new StreamLevelProvider(System.in);
        }

        new Main(level).startLevel();
    }

    @Override
    public void startLevel() {

        //Give the maze the reference to this, so it can draw
        maze = new Maze2D(this);
        player = new Player2D(this, maze);

        PApplet.runSketch(new String[]{"Top down UI"}, this);
    }

    @Override
    public void settings() {
        //size(600, 480);
        fullScreen();
        currentlyPressed = new ArrayList<>();

        maze.fillMaze(level);
        player.setPosition(maze.getStart());
    }

    @Override
    public void draw() {
        maze.drawMaze();
        player.movePlayer(currentlyPressed);
        player.drawCharacter();

        checkForWin();
    }

    public void checkForWin() {
        if (!finished && player.getCords().getX() == maze.getEnd().getX() && player.getCords().getY() == maze.getEnd().getY()) {
            finished = true;
            JOptionPane.showConfirmDialog(null, "Yes you did!", "You win!", JOptionPane.CLOSED_OPTION);
        }
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
