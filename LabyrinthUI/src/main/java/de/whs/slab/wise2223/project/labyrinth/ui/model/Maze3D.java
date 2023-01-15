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

    public void drawMaze(Mode3D mode3D) {
        float scale = 1f;

        if (mode3D == Mode3D.firstPerson) {
            scale = 20f;
        }

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.stroke(0);
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.noStroke();
                    //parentProcessing.fill(153);
                    //parentProcessing.pushMatrix();
                    //parentProcessing.translate(x * cellSize * scale,0 , y * cellSize * scale);
                    //parentProcessing.box(cellSize * scale);
                    //parentProcessing.popMatrix();
                    drawBox(x * cellSize * scale,0 , y * cellSize * scale, cellSize * scale / 2);
                }
                else {
                    parentProcessing.noStroke();
                    //parentProcessing.fill(153);
                    //parentProcessing.pushMatrix();
                    //parentProcessing.translate(x * cellSize * scale,cellSize * scale, y * cellSize * scale);
                    //parentProcessing.box(cellSize * scale);
                    //parentProcessing.popMatrix();
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
                drawOutsideBox(x * cellSize,0 , y * cellSize);
            }
        }
    }

    public void drawOutside(Coordinate playerCords) {
        int layers = 5;

        parentProcessing.noStroke();
        if (sizeWidth > 10 && sizeHeight > 10) {
            if (playerCords.getX() < sizeWidth / 2) {
                drawOutsideBoxes(new Coordinate(-layers, 0), new Coordinate(0, sizeHeight));
                if (playerCords.getY() < sizeHeight / 2) {
                    drawOutsideBoxes(new Coordinate(0, -layers), new Coordinate(sizeWidth, 0));
                    drawOutsideBoxes(new Coordinate(-layers, -layers), new Coordinate(0, 0));
                } else {
                    drawOutsideBoxes(new Coordinate(0, sizeHeight), new Coordinate(sizeWidth, layers + sizeHeight));
                    drawOutsideBoxes(new Coordinate(-layers, sizeHeight), new Coordinate(0, layers + sizeHeight));
                }
            } else {
                drawOutsideBoxes(new Coordinate(sizeWidth, 0), new Coordinate(layers + sizeWidth, sizeHeight));
                if (playerCords.getY() < sizeHeight / 2) {
                    drawOutsideBoxes(new Coordinate(0, -layers), new Coordinate(sizeWidth, 0));
                    drawOutsideBoxes(new Coordinate(sizeWidth, -layers), new Coordinate(sizeWidth + layers, 0));
                } else {
                    drawOutsideBoxes(new Coordinate(0, sizeHeight), new Coordinate(sizeWidth, layers + sizeHeight));
                    drawOutsideBoxes(new Coordinate(sizeWidth, sizeHeight), new Coordinate(sizeWidth + layers, layers + sizeHeight));
                }
            }
        }
        else {
            drawOutsideBoxes(new Coordinate(0, -layers), new Coordinate(sizeWidth + layers, 0));
            drawOutsideBoxes(new Coordinate(-layers, -layers), new Coordinate(0, sizeHeight + layers));
            drawOutsideBoxes(new Coordinate(0, sizeHeight), new Coordinate(sizeWidth + layers, sizeHeight + layers));
            drawOutsideBoxes(new Coordinate(sizeWidth, 0), new Coordinate(sizeWidth + layers, sizeHeight));
        }
    }

    private void drawBox(float x, float y, float z, float size) {
        float xSizeOfWallTexture = 1024;
        float ySizeOfWallTexture = 1024;

        parentProcessing.beginShape(parentProcessing.QUADS);
        parentProcessing.texture(pimageStoneWall);

        // +Z "front" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)+size, (-y)+size, (z)+size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)-size, (-y)+size, (z)+size, 0, ySizeOfWallTexture);

        // -Z "back" face
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)-size, (-y)+size, (z)-size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)+size, (-y)+size, (z)-size, 0, ySizeOfWallTexture);

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

        // +X "right" face
        parentProcessing.vertex((x)+size, (-y)-size, (z)+size, 0, 0);
        parentProcessing.vertex((x)+size, (-y)-size, (z)-size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)+size, (-y)+size, (z)-size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)+size, (-y)+size, (z)+size, 0, ySizeOfWallTexture);

        // -X "left" face
        parentProcessing.vertex((x)-size, (-y)-size, (z)-size, 0, 0);
        parentProcessing.vertex((x)-size, (-y)-size, (z)+size, xSizeOfWallTexture, 0);
        parentProcessing.vertex((x)-size, (-y)+size, (z)+size, xSizeOfWallTexture, ySizeOfWallTexture);
        parentProcessing.vertex((x)-size, (-y)+size, (z)-size, 0, ySizeOfWallTexture);

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
}
