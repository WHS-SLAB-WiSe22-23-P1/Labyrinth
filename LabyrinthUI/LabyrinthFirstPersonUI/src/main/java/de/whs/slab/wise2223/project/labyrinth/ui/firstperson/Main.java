package de.whs.slab.wise2223.project.labyrinth.ui.firstperson;

import de.whs.slab.wise2223.project.labyrinth.model.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.StreamLevelProvider;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze3D;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Mode3D;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Player3D;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

import javax.swing.*;
import java.io.Console;
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
    private Mode3D mode3D = Mode3D.birdView;

    private float heightCam = 10f;

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
        newMain.player = new Player3D(newMain, newMain.maze);

        PApplet.runSketch(new String[]{"Top down UI"}, newMain);
    }

    public void settings() {
        size(800, 600, P3D);
        //fullScreen();
        currentlyPressed = new ArrayList<Integer>();
        PImage floor = loadImage("./Textures/SandFloor.png",  "png");
        PImage stoneWall = loadImage("./Textures/StoneWall.png", "png");

        PImage skyBox = loadImage("./Textures/SkyBox.png", "png");

        maze.setImages(floor, stoneWall);
        maze.fillMaze(levelProvider.getLevel());
        player.SetImages(skyBox);
        player.setPosition(maze.getStart());
    }

    public void draw() {
        background(204);

        stroke(250, 0, 0);
        strokeWeight(10);
        line(-100, 0, -100, 100, 0, -100);

        stroke(0, 250, 0);
        line(-100, 0, -100, -100, 100, -100);

        stroke(0, 0, 250);
        line(-100, 0, -100, -100, 0, 100);

        //pointLight(255, 255, 255, player.getPositionX(), -1000f, player.getPositionZ());

        strokeWeight(4);

        switch (mode3D) {
            case firstPerson:
                float centerPosX = (player.getPositionX() - player.ballSize) * 20f + 1f * (float)Math.cos(player.rotationY);
                float centerPosY = heightCam;//player.getPositionX() + 1f * (float)Math.cos(player.rotationX);
                float centerPosZ = (player.getPositionZ() - player.ballSize) * 20f + 1f * (float)Math.sin(player.rotationY);
                //spotLight(255, 255, 255, (player.getPositionX() - player.ballSize) * 20f, -100f, (player.getPositionZ() - player.ballSize) * 20f, (player.getPositionX() - player.ballSize) * 20f, 0f, (player.getPositionZ() - player.ballSize) * 20f, PI/2, 2);

                ambientLight(50, 50, 50);
                pointLight(255, 255, 255, (player.getPositionX() - player.ballSize) * 20f, -20.0f * 20f, (player.getPositionZ() - player.ballSize) * 20f);

                camera((player.getPositionX() - player.ballSize) * 20f, heightCam, (player.getPositionZ() - player.ballSize) * 20f, centerPosX, centerPosY, centerPosZ, 0.0f, 1.0f, 0.0f);
                break;
            case thirdPerson:
                break;
            case birdView:
                ambientLight(50, 50, 50);
                stroke(0, 250, 0);
                //spotLight(255, 255, 255, 250f, -250f, 250f, 0, 1, 0, PI/2, 2);
                pointLight(255, 255, 255, (player.getPositionX() - player.ballSize), -20.0f, (player.getPositionZ() - player.ballSize));
                camera((player.getPositionX() - player.ballSize), -200.0f, (player.getPositionZ() - player.ballSize) + 50, (player.getPositionX() - player.ballSize), 0.0f, (player.getPositionZ() - player.ballSize), 0.0f, 1.0f, 0.0f);
                break;
        }

        maze.drawMaze(mode3D);
        player.movePlayer(currentlyPressed, mode3D);
        player.drawCharacter(mode3D);

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

        if (event.getKey() == 't' || event.getKey() == 'T') {
            if (mode3D == Mode3D.birdView) {
                mode3D = Mode3D.firstPerson;
            }
            else {
                mode3D = Mode3D.birdView;
            }
        }
    }

    @Override
    public void mouseMoved() {
        float cameraRotateY = (mouseX - pmouseX) * TWO_PI / width;
        float cameraRotateX = (pmouseY - mouseY) * TWO_PI / height;
        player.rotatePlayer(cameraRotateX, cameraRotateY);
    }
}
