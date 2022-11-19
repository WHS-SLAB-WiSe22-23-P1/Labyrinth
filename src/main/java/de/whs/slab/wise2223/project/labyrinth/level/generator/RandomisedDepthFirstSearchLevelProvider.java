package de.whs.slab.wise2223.project.labyrinth.level.generator;

import de.whs.slab.wise2223.project.labyrinth.level.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Dimensions;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Graph;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Node;
import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Directions;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;

import java.util.Arrays;
import java.util.Random;

public class RandomisedDepthFirstSearchLevelProvider extends LevelProvider {

    private final Coordinate start;
    private final Coordinate end;
    private final Graph graph;
    private final Random random;

    final Dimensions size;

    public RandomisedDepthFirstSearchLevelProvider(Dimensions size) {

        this.size = size;
        random = new Random();
        start = new Coordinate(random.nextInt(size.width), random.nextInt(size.height));
        end = new Coordinate(random.nextInt(size.width), random.nextInt(size.height));

        graph = new Graph(size);
    }

    @Override
    protected Level createNewLevel() {

        recursivePathGeneration();

        final boolean[][] blocks = graph.getBlocks();

        return new LevelImpl(blocks[0].length, blocks.length, start, end, blocks);
    }

    private void recursivePathGeneration() {
        recursivePathGeneration(start);
    }

    private void recursivePathGeneration(Coordinate current) {
        for(Coordinate[] adjacent = generateCandidates(current); adjacent.length != 0; adjacent = generateCandidates(current)){
            Coordinate next = adjacent[random.nextInt(adjacent.length)];

            graph.addEdge(current, next);

            recursivePathGeneration(next);
        }

    }

    private Coordinate[] generateCandidates(Coordinate current){
        Coordinate[] adjacent = new Coordinate[4];

        byte i = 0;

        for (Directions value : Directions.values()) {
            Coordinate next = current.nextTo(value);

            if(!size.contains(next)){
                continue;
            }

            Node node = graph.nodeAt(next);

            if(!node.hasAny()){
                adjacent[i++] = next;
            }
        }

        return Arrays.copyOf(adjacent, i);


    }

}
