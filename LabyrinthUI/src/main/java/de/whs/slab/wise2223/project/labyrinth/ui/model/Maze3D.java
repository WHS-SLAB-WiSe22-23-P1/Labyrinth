package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import processing.core.PApplet;
import processing.core.PImage;

public class Maze3D extends Maze2D {
    private PApplet parentProcessing;
    private PImage pimageStone;
    private PImage pimageStoneFloor;
    private PImage pimageStoneWall;
    private PImage pimageFinishFlag;

    public Maze3D(PApplet parentProcessing) {
        super(null);
        this.parentProcessing = parentProcessing;
        this.cellSize = 25f;
    }

    public void setImages(PImage pimageStoneFloor, PImage pimageStoneWall, PImage finishFlag) {
        this.pimageStoneFloor = pimageStoneFloor;
        this.pimageStoneWall = pimageStoneWall;
        this.pimageFinishFlag = finishFlag;
    }

    public void drawMaze(Mode3D mode3D, Coordinate playerCords) {
        float scale = 1f;
        int viewDistanceX = 12;
        int viewDistanceY = 6;

        if (mode3D == Mode3D.firstPerson) {
            scale = 20f;
            viewDistanceX = 12;
            viewDistanceY = 12;
        }

        Coordinate startCorner = new Coordinate(Math.max(playerCords.getX() - viewDistanceX, 0), Math.max(playerCords.getY() - viewDistanceY, 0));
        Coordinate endCorner = new Coordinate(Math.min(playerCords.getX() + viewDistanceX + 1, sizeWidth), Math.min(playerCords.getY() + viewDistanceY + 1, sizeHeight));

        parentProcessing.noStroke();
        for (int y = startCorner.getY(); y < endCorner.getY(); y++) {
            for (int x = startCorner.getX(); x < endCorner.getX(); x++) {
                if(grid.get(y).get(x) == 0) {
                    drawBox(new Coordinate(x, y), cellSize * scale / 2);
                }
                else {
                    drawFloor(x * cellSize * scale,-(cellSize * scale), y * cellSize * scale, cellSize * scale / 2);
                }
            }
        }

        if (start != null) {
            //parentProcessing.fill(253, 102, 0);
            //parentProcessing.rect(start.getX() * cellSize, start.getY() * cellSize, cellSize, cellSize);
        }

        if (end != null) {
            //parentProcessing.fill(0, 120, 255);
            //parentProcessing.rect(end.getX() * cellSize, end.getY() * cellSize, cellSize, cellSize);
            parentProcessing.noStroke();
            drawFinish(end.getX() * cellSize * scale,-(cellSize * scale), end.getY() * cellSize * scale, cellSize * scale / 2);
        }
    }

    private void drawBox(Coordinate coordinate, float size) {
        float xSizeOfWallTexture = 1024;
        float ySizeOfWallTexture = 1024;

        float x = coordinate.getX() * size * 2;
        float y = 0;
        float z = coordinate.getY() * size * 2;

        parentProcessing.beginShape(parentProcessing.QUADS);
        parentProcessing.texture(pimageStoneWall);

        if (getCell(coordinate.bottom()) == 1) {
            // +Z "front" face
            parentProcessing.vertex((x) - size, (-y) - size, (z) + size, 0, 0);
            parentProcessing.vertex((x) + size, (-y) - size, (z) + size, xSizeOfWallTexture, 0);
            parentProcessing.vertex((x) + size, (-y) + size, (z) + size, xSizeOfWallTexture, ySizeOfWallTexture);
            parentProcessing.vertex((x) - size, (-y) + size, (z) + size, 0, ySizeOfWallTexture);
        }

        if (getCell(coordinate.top()) == 1) {
            // -Z "back" face
            parentProcessing.vertex((x) + size, (-y) - size, (z) - size, 0, 0);
            parentProcessing.vertex((x) - size, (-y) - size, (z) - size, xSizeOfWallTexture, 0);
            parentProcessing.vertex((x) - size, (-y) + size, (z) - size, xSizeOfWallTexture, ySizeOfWallTexture);
            parentProcessing.vertex((x) + size, (-y) + size, (z) - size, 0, ySizeOfWallTexture);
        }

        // +Y "bottom" face
        //parentProcessing.vertex((x)-size, (-y)+size, (z)+size, 0, 0);
        //parentProcessing.vertex((x)+size, (-y)+size, (z)+size, xSizeOfWallTexture, 0);
        //parentProcessing.vertex((x)+size, (-y)+size, (z)-size, xSizeOfWallTexture, ySizeOfWallTexture);
        //parentProcessing.vertex((x)-size, (-y)+size, (z)-size, 0, ySizeOfWallTexture);

        // -Y "top" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, 0, ySizeOfWallTexture);

        if (getCell(coordinate.right()) == 1) {
            // +X "right" face
            parentProcessing.vertex((x) + size, (-y) - size, (z) + size, 0, 0);
            parentProcessing.vertex((x) + size, (-y) - size, (z) - size, xSizeOfWallTexture, 0);
            parentProcessing.vertex((x) + size, (-y) + size, (z) - size, xSizeOfWallTexture, ySizeOfWallTexture);
            parentProcessing.vertex((x) + size, (-y) + size, (z) + size, 0, ySizeOfWallTexture);
        }

        if (getCell(coordinate.left()) == 1) {
            // -X "left" face
            parentProcessing.vertex((x) - size, (-y) - size, (z) - size, 0, 0);
            parentProcessing.vertex((x) - size, (-y) - size, (z) + size, xSizeOfWallTexture, 0);
            parentProcessing.vertex((x) - size, (-y) + size, (z) + size, xSizeOfWallTexture, ySizeOfWallTexture);
            parentProcessing.vertex((x) - size, (-y) + size, (z) - size, 0, ySizeOfWallTexture);
        }

        parentProcessing.endShape();
    }

    private void drawFloor(float x, float y, float z, float size) {
        float xSizeOfTexture = 1024;
        float ySizeOfTexture = 1024;

        parentProcessing.beginShape(parentProcessing.QUADS);
        parentProcessing.texture(pimageStoneFloor);

        // -Y "top" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, xSizeOfTexture, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, xSizeOfTexture, ySizeOfTexture);
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, 0, ySizeOfTexture);

        parentProcessing.endShape();
    }

    private void drawFinish(float x, float y, float z, float size) {
        float xSizeOfTexture = 1024;
        float ySizeOfTexture = 1024;

        parentProcessing.beginShape(parentProcessing.QUADS);
        parentProcessing.texture(pimageFinishFlag);

        // -Y "top" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, xSizeOfTexture, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, xSizeOfTexture, ySizeOfTexture);
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, 0, ySizeOfTexture);

        parentProcessing.endShape();
    }

    public void drawOutsideBox(float x, float y, float z) {
        float size = cellSize / 2;

        float xSizeOfWallTexture = 1024;
        float ySizeOfWallTexture = 1024;

        parentProcessing.beginShape(parentProcessing.QUADS);
        parentProcessing.texture(pimageStoneWall);

        // -Y "top" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, 0, ySizeOfWallTexture);

        parentProcessing.endShape();
    }

    private void drawOutsideBoxes(Coordinate start, Coordinate end) {
        for (int y = start.getY(); y < end.getY(); y++) {
            for (int x = start.getX(); x < end.getX(); x++) {
                if ((x < 0 || x > sizeWidth) || (y < 0 || y > sizeHeight)) {
                    drawOutsideBox(x * cellSize,0 , y * cellSize);
                }
            }
        }
    }

    public void drawOutside(Coordinate playerCords) {
        int viewDistanceX = 12;
        int viewDistanceY = 6;

        if (playerCords.getX() - viewDistanceX < 0 ||
            playerCords.getX() + viewDistanceX + 1 > sizeWidth ||
            playerCords.getY() - viewDistanceY < 0 ||
            playerCords.getY() + viewDistanceY + 1 > sizeHeight) {

            Coordinate startCorner = new Coordinate(playerCords.getX() - viewDistanceX, playerCords.getY() - viewDistanceY);
            Coordinate endCorner = new Coordinate(playerCords.getX() + viewDistanceX + 1, playerCords.getY() + viewDistanceY + 1);

            parentProcessing.noStroke();
            drawOutsideBoxes(startCorner, endCorner);
        }
    }
}
