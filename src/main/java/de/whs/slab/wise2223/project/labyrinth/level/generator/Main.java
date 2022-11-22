package de.whs.slab.wise2223.project.labyrinth.level.generator;

import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Dimensions;

public class Main {
    public static void main(String[] args) {
        new RandomisedDepthFirstSearchLevelProvider(new Dimensions(Integer.parseUnsignedInt(args[0]), Integer.parseUnsignedInt(args[1]))).printLevel();
    }
}
