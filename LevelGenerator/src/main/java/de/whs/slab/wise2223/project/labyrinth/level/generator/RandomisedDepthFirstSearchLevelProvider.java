package de.whs.slab.wise2223.project.labyrinth.level.generator;

import de.whs.slab.wise2223.project.labyrinth.model.Dimensions;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Graph;
import de.whs.slab.wise2223.project.labyrinth.level.generator.model.Node;
import de.whs.slab.wise2223.project.labyrinth.model.LevelProvider;
import de.whs.slab.wise2223.project.labyrinth.model.Coordinate;
import de.whs.slab.wise2223.project.labyrinth.model.Directions;
import de.whs.slab.wise2223.project.labyrinth.model.Level;
import de.whs.slab.wise2223.project.labyrinth.model.LevelImpl;

import java.util.Arrays;
import java.util.Random;

public class RandomisedDepthFirstSearchLevelProvider extends LevelProvider {

    final Dimensions size;
    private final Coordinate start;
    private final Coordinate end;
    private final Graph graph;
    private final Random random;

    public RandomisedDepthFirstSearchLevelProvider(Dimensions size) {

        this.size = size;
        random = new Random();
        start = new Coordinate(0, 0);
        end = new Coordinate(size.width-1, size.height-1);

        graph = new Graph(size);
    }

    @Override
    protected Level createNewLevel() {
        recursivePathGeneration();

        final boolean[][] blocks = graph.getBlocks();

        return new LevelImpl(blocks[0].length, blocks.length, new Coordinate(start.getX() * 2 + 1, start.getY() * 2 + 1), new Coordinate(end.getX() * 2 + 1, end.getY() * 2 + 1), blocks);
    }

    private void recursivePathGeneration() {
        recursivePathGeneration(start);
    }

    private void recursivePathGeneration(Coordinate current) {
        for (Coordinate[] adjacent = generateCandidates(current); adjacent.length != 0; adjacent = generateCandidates(current)) {
            Coordinate next = adjacent[random.nextInt(adjacent.length)];

            graph.addEdge(current, next);

            recursivePathGeneration(next);
        }
    }

    private Coordinate[] generateCandidates(Coordinate current) {
        Coordinate[] adjacent = new Coordinate[4];

        byte i = 0;

        for (Directions value : Directions.values()) {
            Coordinate next = current.getOn(value);

            if (!size.contains(next)) {
                continue;
            }

            Node node = graph.nodeAt(next);

            if (!node.hasAny()) {
                adjacent[i++] = next;
            }
        }

        return Arrays.copyOf(adjacent, i);
    }
}
