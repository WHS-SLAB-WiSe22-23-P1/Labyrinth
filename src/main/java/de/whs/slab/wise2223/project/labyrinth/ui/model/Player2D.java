package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.ui.topdown.Main;

import java.util.ArrayList;

public class Player2D {
    private Main parentProcessing;
    private float positionX;
    private float positionY;

    public Player2D(Main parentProcessing, float posX, float posY) {
        this.parentProcessing = parentProcessing;
        this.positionX = posX;
        this.positionY = posY;
    }

    public Player2D(Main parentProcessing) {
        this.parentProcessing = parentProcessing;
    }

    public void drawCharacter() {
        this.parentProcessing.fill(100, 0, 100);
        this.parentProcessing.circle(positionX, positionY, 10);
    }

    public void setPosition(Coordinate cords) {
        this.positionX = cords.getX() * 25f + 12.5f;
        this.positionY = cords.getY() * 25f + 12.5f;
    }

    public void movePlayer(ArrayList<Integer> currentlyPressed) {
        float speed = 1f;
        for (int i = 0; i < currentlyPressed.size(); i++) {
            switch (currentlyPressed.get(i)) {
                case 87:
                    // W
                    positionY -= speed;
                    break;
                case 83:
                    // S
                    positionY += speed;
                    break;
                case 65:
                    // A
                    positionX -= speed;
                    break;
                case 68:
                    // D
                    positionX += speed;
                    break;
                default:
            }
        }
    }
}
