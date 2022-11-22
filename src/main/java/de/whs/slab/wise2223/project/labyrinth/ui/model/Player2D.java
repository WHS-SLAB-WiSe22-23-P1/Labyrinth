package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;

import java.util.ArrayList;

public class Player2D {
    private Main parentProcessing;
    protected float positionX;
    protected float positionY;
    protected float ballSize;

    public Coordinate getCords() {
        return new Coordinate((int)positionX / 25, (int)positionY / 25);
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
                    Coordinate boxCordsTopLeft = playerCords.top().left();
                    Coordinate boxCordsTop = playerCords.top();
                    Coordinate boxCordsTopRight = playerCords.top().right();

                    if (!((parentProcessing.maze.getCell(boxCordsTopLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                         (parentProcessing.maze.getCell(boxCordsTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                         (parentProcessing.maze.getCell(boxCordsTopRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsTopRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY -= speed;
                    }
                    break;
                case 83:
                    // S
                    Coordinate boxCordsBottomLeft = playerCords.bottom().left();
                    Coordinate boxCordsBottom = playerCords.bottom();
                    Coordinate boxCordsBottomRight = playerCords.bottom().right();

                    if (!((parentProcessing.maze.getCell(boxCordsBottomLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsBottomRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsBottomRight, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionY += speed;
                    }
                    break;
                case 65:
                    // A
                    Coordinate boxCordsLeftTop = playerCords.left().top();
                    Coordinate boxCordsLeft = playerCords.left();
                    Coordinate boxCordsLeftBottom = playerCords.left().bottom();

                    if (!((parentProcessing.maze.getCell(boxCordsLeftTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsLeft) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeft, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsLeftBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsLeftBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX -= speed;
                    }
                    break;
                case 68:
                    // D
                    Coordinate boxCordsRightTop = playerCords.right().top();
                    Coordinate boxCordsRight = playerCords.right();
                    Coordinate boxCordsRightBottom = playerCords.right().bottom();

                    if (!((parentProcessing.maze.getCell(boxCordsRightTop) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightTop, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsRight) == 0 && CollisionHelper.collideBallWithWall(boxCordsRight, cellSize, positionX, positionY, (ballSize / 2) + speed)) ||
                            (parentProcessing.maze.getCell(boxCordsRightBottom) == 0 && CollisionHelper.collideBallWithWall(boxCordsRightBottom, cellSize, positionX, positionY, (ballSize / 2) + speed)))) {
                        positionX += speed;
                    }
                    break;
                default:
            }
        }
    }
}
