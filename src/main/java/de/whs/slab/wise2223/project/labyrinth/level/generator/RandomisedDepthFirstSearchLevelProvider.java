package de.whs.slab.wise2223.project.labyrinth.level.generator;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Dimensions;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Graph;
import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;

import java.util.Random;

public class RandomisedDepthFirstSearchLevelProvider extends LevelProvider {
    final Random random = new Random();

    final Dimensions size;

    public RandomisedDepthFirstSearchLevelProvider(Dimensions size) {
        this.size = size;
    }

    @Override
    protected Level createNewLevel() {
        Coordinate start = new Coordinate(random.nextInt(size.width), random.nextInt(size.height));
        Coordinate end = new Coordinate(random.nextInt(size.width), random.nextInt(size.height));

        Graph graph = new Graph(size);

        graph.addEdge(new Coordinate(1, 1), new Coordinate(1, 2));
        graph.addEdge(new Coordinate(1, 2), new Coordinate(1, 3));

        return new LevelImpl(size.width, size.height, start, end, graph.getBlocks());
    }
}
