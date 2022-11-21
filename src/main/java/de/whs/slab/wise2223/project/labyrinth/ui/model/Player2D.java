package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;

import java.util.ArrayList;

public class Player2D {
    private Main parentProcessing;
    private float positionX;
    private float positionY;

    private  float ballSize;

    public Player2D(Main parentProcessing, float posX, float posY) {
        this.parentProcessing = parentProcessing;
        this.positionX = posX;
        this.positionY = posY;
        this.ballSize = 12.5f;
    }

    public Player2D(Main parentProcessing) {

        this.parentProcessing = parentProcessing;
        this.ballSize = 12.5f;
    }

    public void drawCharacter() {
        this.parentProcessing.fill(100, 0, 100);
        this.parentProcessing.circle(positionX, positionY, ballSize);
    }

    public void setPosition(Coordinate cords) {
        this.positionX = cords.getX() * 25f + 12.5f;
        this.positionY = cords.getY() * 25f + 12.5f;
    }

    public void movePlayer(ArrayList<Integer> currentlyPressed) {
        float speed = 1f;
        float cellSize = parentProcessing.maze.getCellSize();

        Coordinate playerCords = new Coordinate((int)(positionX / 25f), (int)(positionY / 25f));
        for (int i = 0; i < currentlyPressed.size(); i++) {
            switch (currentlyPressed.get(i)) {
                case 87:
                    // W
                    Coordinate boxCordsTopLeft = new Coordinate(playerCords.getX() - 1, playerCords.getY() - 1);
                    Coordinate boxCordsTop = new Coordinate(playerCords.getX(), playerCords.getY() - 1);
                    Coordinate boxCordsTopRight = new Coordinate(playerCords.getX() + 1, playerCords.getY() - 1);

                    if (!((parentProcessing.maze.getCell(boxCordsTopLeft) == 1 && CollisionHelper.collideWithWall(boxCordsTopLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                         (parentProcessing.maze.getCell(boxCordsTop) == 1 && CollisionHelper.collideWithWall(boxCordsTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                         (parentProcessing.maze.getCell(boxCordsTopRight) == 1 && CollisionHelper.collideWithWall(boxCordsTopRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY -= speed;
                    }
                    break;
                case 83:
                    // S
                    Coordinate boxCordsBottomLeft = new Coordinate(playerCords.getX() - 1, playerCords.getY() + 1);
                    Coordinate boxCordsBottom = new Coordinate(playerCords.getX(), playerCords.getY() + 1);
                    Coordinate boxCordsBottomRight = new Coordinate(playerCords.getX() + 1, playerCords.getY() + 1);

                    if (!((parentProcessing.maze.getCell(boxCordsBottomLeft) == 1 && CollisionHelper.collideWithWall(boxCordsBottomLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsBottom) == 1 && CollisionHelper.collideWithWall(boxCordsBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsBottomRight) == 1 && CollisionHelper.collideWithWall(boxCordsBottomRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY += speed;
                    }
                    break;
                case 65:
                    // A
                    Coordinate boxCordsLeftTop = new Coordinate(playerCords.getX() - 1, playerCords.getY() - 1);
                    Coordinate boxCordsLeft = new Coordinate(playerCords.getX() - 1, playerCords.getY());
                    Coordinate boxCordsLeftBottom = new Coordinate(playerCords.getX() - 1, playerCords.getY() + 1);

                    if (!((parentProcessing.maze.getCell(boxCordsLeftTop) == 1 && CollisionHelper.collideWithWall(boxCordsLeftTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsLeft) == 1 && CollisionHelper.collideWithWall(boxCordsLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsLeftBottom) == 1 && CollisionHelper.collideWithWall(boxCordsLeftBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX -= speed;
                    }
                    break;
                case 68:
                    // D
                    Coordinate boxCordsRightTop = new Coordinate(playerCords.getX() + 1, playerCords.getY() - 1);
                    Coordinate boxCordsRight = new Coordinate(playerCords.getX() + 1, playerCords.getY());
                    Coordinate boxCordsRightBottom = new Coordinate(playerCords.getX() + 1, playerCords.getY() + 1);

                    if (!((parentProcessing.maze.getCell(boxCordsRightTop) == 1 && CollisionHelper.collideWithWall(boxCordsRightTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsRight) == 1 && CollisionHelper.collideWithWall(boxCordsRight, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsRightBottom) == 1 && CollisionHelper.collideWithWall(boxCordsRightBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX += speed;
                    }
                    break;
                default:
            }
        }
    }
}
