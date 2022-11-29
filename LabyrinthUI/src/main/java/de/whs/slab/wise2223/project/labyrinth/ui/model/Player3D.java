package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.ui.model.Player2D;
import processing.core.PApplet;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static processing.core.PApplet.constrain;

public class Player3D extends Player2D{
    private PApplet parentProcessing;
    private Maze3D maze;

    protected float positionZ;
    public float rotationX = 0;
    public float rotationY = 0;

    public float getPositionX() { return positionX;}
    public float getPositionY() { return positionZ;}
    public float getPositionZ() { return positionY;}
    public Player3D(PApplet parentProcessing, Maze3D maze) {
        super(parentProcessing, maze);
        this.maze = maze;
        this.parentProcessing = parentProcessing;
    }

    public void drawCharacter(Mode3D mode3D) {
        switch (mode3D) {
            case firstPerson:
            case thirdPerson:
                break;
            case birdView:
                parentProcessing.pushMatrix();
                parentProcessing.fill(100, 0, 100);
                parentProcessing.noStroke();
                parentProcessing.translate(getPositionX() - ballSize, getPositionY(), getPositionZ() - ballSize);
                parentProcessing.sphere(ballSize / 2f);
                parentProcessing.popMatrix();
                break;
        }
    }

    public void rotatePlayer(float newRotationX, float newRotationY) {
        rotationX += newRotationX;
        rotationY += newRotationY;
        rotationY = constrain(rotationY, (float)-PI/2, 0f);
    }

    public void movePlayer(ArrayList<Integer> currentlyPressed, Mode3D mode3D) {
        switch (mode3D) {
            case firstPerson:
                movePlayerFirstPerson(currentlyPressed);
                break;
            case thirdPerson:
                break;
            case birdView:
                movePlayerBird(currentlyPressed);
                break;
        }
    }

    private void movePlayerFirstPerson(ArrayList<Integer> currentlyPressed) {
        float speed = 1f;
        float cellSize = maze.getCellSize();
        Coordinate playerCords = new Coordinate((int)(positionX / maze.cellSize), (int)(positionY / maze.cellSize));
        for (int i = 0; i < currentlyPressed.size(); i++) {
            switch (currentlyPressed.get(i)) {
                case 87:
                    // W
                    Coordinate boxCordsTopLeft = playerCords.top().left();
                    Coordinate boxCordsTop = playerCords.top();
                    Coordinate boxCordsTopRight = playerCords.top().right();

                    if (!((maze.getCell(boxCordsTopLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsTopRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY -= speed;
                    }
                    break;
                case 83:
                    // S
                    Coordinate boxCordsBottomLeft = playerCords.bottom().left();
                    Coordinate boxCordsBottom = playerCords.bottom();
                    Coordinate boxCordsBottomRight = playerCords.bottom().right();

                    if (!((maze.getCell(boxCordsBottomLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsBottomRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY += speed;
                    }
                    break;
                case 65:
                    // A
                    Coordinate boxCordsLeftTop = playerCords.left().top();
                    Coordinate boxCordsLeft = playerCords.left();
                    Coordinate boxCordsLeftBottom = playerCords.left().bottom();

                    if (!((maze.getCell(boxCordsLeftTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsLeftBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX -= speed;
                    }
                    break;
                case 68:
                    // D
                    Coordinate boxCordsRightTop = playerCords.right().top();
                    Coordinate boxCordsRight = playerCords.right();
                    Coordinate boxCordsRightBottom = playerCords.right().bottom();

                    if (!((maze.getCell(boxCordsRightTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsRight, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsRightBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX += speed;
                    }
                    break;
                default:
            }
        }
    }
    private void movePlayerBird(ArrayList<Integer> currentlyPressed) {
        float speed = 1f;
        float cellSize = maze.getCellSize();
        Coordinate playerCords = new Coordinate((int)(positionX / 25f), (int)(positionY / 25f));
        for (int i = 0; i < currentlyPressed.size(); i++) {
            switch (currentlyPressed.get(i)) {
                case 87:
                    // W
                    Coordinate boxCordsTopLeft = playerCords.top().left();
                    Coordinate boxCordsTop = playerCords.top();
                    Coordinate boxCordsTopRight = playerCords.top().right();

                    if (!((maze.getCell(boxCordsTopLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsTopRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY -= speed;
                    }
                    break;
                case 83:
                    // S
                    Coordinate boxCordsBottomLeft = playerCords.bottom().left();
                    Coordinate boxCordsBottom = playerCords.bottom();
                    Coordinate boxCordsBottomRight = playerCords.bottom().right();

                    if (!((maze.getCell(boxCordsBottomLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsBottomRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY += speed;
                    }
                    break;
                case 65:
                    // A
                    Coordinate boxCordsLeftTop = playerCords.left().top();
                    Coordinate boxCordsLeft = playerCords.left();
                    Coordinate boxCordsLeftBottom = playerCords.left().bottom();

                    if (!((maze.getCell(boxCordsLeftTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsLeftBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX -= speed;
                    }
                    break;
                case 68:
                    // D
                    Coordinate boxCordsRightTop = playerCords.right().top();
                    Coordinate boxCordsRight = playerCords.right();
                    Coordinate boxCordsRightBottom = playerCords.right().bottom();

                    if (!((maze.getCell(boxCordsRightTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsRight, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (maze.getCell(boxCordsRightBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX += speed;
                    }
                    break;
                default:
            }
        }
    }
}
