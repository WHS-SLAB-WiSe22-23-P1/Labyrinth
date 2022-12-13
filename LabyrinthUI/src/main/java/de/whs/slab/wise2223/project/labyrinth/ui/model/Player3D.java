package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static processing.core.PApplet.*;

public class Player3D extends Player2D{
    private PApplet parentProcessing;
    private PImage skyBox;
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

    public void SetImages(PImage skyBox) {
        this.skyBox = skyBox;
        initializeSphere(30, 30);
    }

    public void drawCharacter(Mode3D mode3D) {
        switch (mode3D) {
            case firstPerson:
                parentProcessing.pushMatrix();
                parentProcessing.fill(100, 0, 100);
                parentProcessing.noStroke();
                parentProcessing.translate((getPositionX() - ballSize) * 20f, getPositionY(), (getPositionZ() - ballSize) * 20f);
                drawCircle(5200, 5200, 5200);
                parentProcessing.popMatrix();
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
        float speed = 0.5f;
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

    int numPointsW;
    int numPointsH_2pi;
    int numPointsH;

    float[] coorX;
    float[] coorY;
    float[] coorZ;
    float[] multXZ;

    private void initializeSphere(int numPtsW, int numPtsH_2pi) {

        // The number of points around the width and height
        numPointsW=numPtsW+1;
        numPointsH_2pi=numPtsH_2pi;  // How many actual pts around the sphere (not just from top to bottom)
        numPointsH=ceil((float)numPointsH_2pi/2)+1;  // How many pts from top to bottom (abs(....) b/c of the possibility of an odd numPointsH_2pi)

        coorX=new float[numPointsW];   // All the x-coor in a horizontal circle radius 1
        coorY=new float[numPointsH];   // All the y-coor in a vertical circle radius 1
        coorZ=new float[numPointsW];   // All the z-coor in a horizontal circle radius 1
        multXZ=new float[numPointsH];  // The radius of each horizontal circle (that you will multiply with coorX and coorZ)

        for (int i=0; i<numPointsW ;i++) {  // For all the points around the width
            float thetaW= (float) (i*2*PI/(numPointsW-1));
            coorX[i]=sin(thetaW);
            coorZ[i]=cos(thetaW);
        }

        for (int i=0; i<numPointsH; i++) {  // For all points from top to bottom
            if ((numPointsH_2pi/2) != (float)numPointsH_2pi/2 && i==numPointsH-1) {  // If the numPointsH_2pi is odd and it is at the last pt
                float thetaH= (float) ((i-1)*2*PI/(numPointsH_2pi));
                coorY[i]=cos((float) (PI+thetaH));
                multXZ[i]=0;
            }
            else {
                //The numPointsH_2pi and 2 below allows there to be a flat bottom if the numPointsH is odd
                float thetaH= (float) (i*2*PI/(numPointsH_2pi));

                //PI+ below makes the top always the point instead of the bottom.
                coorY[i]=cos((float) (PI+thetaH));
                multXZ[i]=sin(thetaH);
            }
        }
    }

    private void drawCircle(float rx, float ry, float rz) {
        // These are so we can map certain parts of the image on to the shape
        float changeU=skyBox.width/(float)(numPointsW-1);
        float changeV=skyBox.height/(float)(numPointsH-1);
        float u=0;  // Width variable for the texture
        float v=0;  // Height variable for the texture

        parentProcessing.beginShape(parentProcessing.TRIANGLE_STRIP);
        parentProcessing.texture(skyBox);
        for (int i=0; i<(numPointsH-1); i++) {  // For all the rings but top and bottom
            // Goes into the array here instead of loop to save time
            float coory=coorY[i];
            float cooryPlus=coorY[i+1];

            float multxz=multXZ[i];
            float multxzPlus=multXZ[i+1];

            for (int j=0; j<numPointsW; j++) { // For all the pts in the ring
                parentProcessing.normal(-coorX[j]*multxz, -coory, -coorZ[j]*multxz);
                parentProcessing.vertex(coorX[j]*multxz*rx, coory*ry, coorZ[j]*multxz*rz, u, v);
                parentProcessing.normal(-coorX[j]*multxzPlus, -cooryPlus, -coorZ[j]*multxzPlus);
                parentProcessing.vertex(coorX[j]*multxzPlus*rx, cooryPlus*ry, coorZ[j]*multxzPlus*rz, u, v+changeV);
                u+=changeU;
            }
            v+=changeV;
            u=0;
        }
        parentProcessing.endShape();
    }
}
