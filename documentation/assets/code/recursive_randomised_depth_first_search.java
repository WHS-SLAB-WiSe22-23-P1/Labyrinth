private void recursivePathGeneration(Coordinate current) {
    for (Coordinate[] adjacent = generateCandidates(current); adjacent.length != 0; adjacent = generateCandidates(current)) {
        Coordinate next = adjacent[random.nextInt(adjacent.length)];

        graph.addEdge(current, next);

        recursivePathGeneration(next);
    }
}
