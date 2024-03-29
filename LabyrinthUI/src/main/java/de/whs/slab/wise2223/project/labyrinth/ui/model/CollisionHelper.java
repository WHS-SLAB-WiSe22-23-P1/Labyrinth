package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;

public class CollisionHelper {
    static public boolean collideBallWithWall(Coordinate boxCord, float boxSize, float ballX, float ballY, float ballRadius) {
        float boxX = (boxCord.getX() * boxSize) + (boxSize / 2);
        float boxY = (boxCord.getY() * boxSize) + (boxSize / 2);
        return collideBallWithBox(boxX, boxY, boxSize, ballX, ballY, ballRadius);
    }

    static public boolean collideBallWithBox(float boxX, float boxY, float boxSize, float ballX, float ballY, float ballRadius) {
        float boxTop = boxY - boxSize / 2;
        float boxBottom = boxY + boxSize / 2;
        float boxLeft = boxX - boxSize / 2;
        float boxRight = boxX + boxSize / 2;

        if (ballY < boxTop) { //Top of box
            if (ballX < boxLeft) { //Top Left of box
                return calcDistance(boxLeft, boxTop, ballX, ballY) <= ballRadius - 1;
            }
            else if (ballX < boxRight) { //Top Middle of box
                return boxTop <= (ballY + ballRadius);
            }
            else { //Top Right of box
                return calcDistance(boxRight, boxTop, ballX, ballY) <= ballRadius - 1;
            }
        }
        else if(ballY < boxBottom) { //Middle of box
            if (ballX < boxLeft) { //Middle Left of box
                return boxLeft <= (ballX + ballRadius);
            }
            else if (ballX <= boxRight) { //Inside of box so it's stuck
                return true;
            }
            else { //Middle Right of box
                return boxRight >= (ballX - ballRadius);
            }
        }
        else { //Bottom of box
            if (ballX < boxLeft) { //Bottom Left of box
                return calcDistance(boxLeft, boxBottom, ballX, ballY) <= ballRadius - 1;
            }
            else if (ballX < boxRight) { //Bottom Middle of box
                return boxBottom >= (ballY - ballRadius);
            }
            else { //Bottom Right of box
                return calcDistance(boxRight, boxBottom, ballX, ballY) <= ballRadius - 1;
            }
        }
    }

    private static float calcDistance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }
}
