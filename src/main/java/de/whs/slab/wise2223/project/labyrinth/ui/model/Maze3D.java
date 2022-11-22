package de.whs.slab.wise2223.project.labyrinth.ui.model;

import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.ui.firstperson.Main;

public class Maze3D extends Maze2D {
    private Main parentProcessing;
    public Maze3D(Main parentProcessing) {
        super(null);
        this.parentProcessing = parentProcessing;
    }

    @Override
    public void drawMaze() {
        int offSetX = 0;
        int offSetY = 0;

        parentProcessing.pushMatrix();
        parentProcessing.translate(sizeWidth * 12.5f, -1.0f, sizeHeight * 12.5f);
        parentProcessing.fill(128,128,128);
        parentProcessing.box(sizeWidth * 25f, 1.0f, sizeHeight * 25f);
        parentProcessing.popMatrix();

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                parentProcessing.stroke(0);
                if(grid.get(y).get(x) == 0) {
                    parentProcessing.fill(153);
                    parentProcessing.pushMatrix();
                    //parentProcessing.rect(offSetX + x * cellSize, offSetY + y * cellSize, cellSize, cellSize);
                    parentProcessing.translate(offSetX + x * cellSize,0 , offSetY + y * cellSize);
                    parentProcessing.box(cellSize);
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
