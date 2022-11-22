package de.whs.slab.wise2223.project.labyrinth.ui.firstperson;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.StreamLevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze3D;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Player3D;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends PApplet {
    public Maze3D maze;
    public Player3D player;
    private ArrayList<Integer> currentlyPressed;;
    private final LevelProvider levelProvider;
    private Boolean finished = false;

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
        Main newMain = new de.whs.slab.wise2223.project.labyrinth.ui.firstperson.Main(level);

        //Give the maze the reference to this, so it can draw
        newMain.maze = new Maze3D(newMain);
        newMain.player = new Player3D(newMain);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }

    public void settings() {
        size(800, 600, P3D);
        //fullScreen();
        currentlyPressed = new ArrayList<Integer>();

        maze.fillMaze(levelProvider.getLevel());
        player.setPosition(maze.getStart());
    }

    public void draw() {
        background(204);
        camera(player.getPositionX(), -200.0f, player.getPositionZ() + 50, player.getPositionX(), 0.0f, player.getPositionZ(), 0.0f, 1.0f, 0.0f);

        maze.drawMaze();
        player.movePlayer(currentlyPressed);
        player.drawCharacter();

        checkForWin();
    }

    public void checkForWin() {
        if (!finished && player.getCords().getX() == maze.getEnd().getX() && player.getCords().getY() == maze.getEnd().getY()) {
            finished = true;
            JOptionPane.showConfirmDialog(null,
                    "Yes you did!", "You win!", JOptionPane.CLOSED_OPTION);
        }
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
