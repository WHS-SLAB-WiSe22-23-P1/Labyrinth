package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.ui.model.Maze2D;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Maze3D extends Maze2D {
    private PApplet parentProcessing;

    public Maze3D(PApplet parentProcessing) {
        super(null);
        this.parentProcessing = parentProcessing;
        this.cellSize = 25f;
    }

    public void drawMaze(Mode3D mode3D) {
        float scale = 1f;

        if (mode3D == Mode3D.firstPerson) {
            scale = 20f;
        }

        parentProcessing.noStroke();
        parentProcessing.pushMatrix();
        parentProcessing.translate(-50f, 0 ,-50f);
        PImage pimage = parentProcessing.loadImage("./Textures/Stone.png",  "png");
        parentProcessing.texture(pimage);
        parentProcessing.box(100);
        parentProcessing.popMatrix();


        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.stroke(0);
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.fill(153);
                    parentProcessing.pushMatrix();
                    //parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                    parentProcessing.translate(x * cellSize * scale,0 , y * cellSize * scale);
                    parentProcessing.box(cellSize * scale);
                    parentProcessing.popMatrix();
                }
                else {
                    parentProcessing.noStroke();
                    parentProcessing.fill(153);
                    parentProcessing.pushMatrix();
                    //parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                    parentProcessing.translate(x * cellSize * scale,cellSize * scale, y * cellSize * scale);
                    parentProcessing.box(cellSize * scale);
                    parentProcessing.popMatrix();
                }
            }
        }

        if (start != null) {
            parentProcessing.fill(253, 102, 0);
            parentProcessing.rect(start.getX() * cellSize, start.getY() * cellSize, cellSize, cellSize);
        }

        if (end != null) {
            parentProcessing.fill(0, 120, 255);
            parentProcessing.rect(end.getX() * cellSize, end.getY() * cellSize, cellSize, cellSize);
        }
    }
}
