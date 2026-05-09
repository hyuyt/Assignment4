import java.util.List;
public class Experiment {

    private long[] bfsTimes;
    private long[] dfsTimes;
    private int[] graphSizes;
    private int runCount;

    public Experiment() {
        bfsTimes  = new long[10];
        dfsTimes  = new long[10];
        graphSizes = new int[10];
        runCount  = 0;
    }

    public long[] runTraversals(Graph g) {
        int startId = g.getVertexIds().iterator().next(); // first vertex
        boolean isSmall = g.vertexCount() <= 10;

        long bfsStart = System.nanoTime();
        List<Integer> bfsOrder = g.bfs(startId);
        long bfsEnd   = System.nanoTime();
        long bfsElapsed = bfsEnd - bfsStart;

        if (isSmall) {
            System.out.println("  BFS order : " + bfsOrder);
        }

        long dfsStart = System.nanoTime();
        List<Integer> dfsOrder = g.dfs(startId);
        long dfsEnd   = System.nanoTime();
        long dfsElapsed = dfsEnd - dfsStart;

        if (isSmall) {
            System.out.println("  DFS order : " + dfsOrder);
        }

        return new long[]{ bfsElapsed, dfsElapsed };
    }

    public void runMultipleTests() {
        int[] sizes = { 10, 30, 100 };

        for (int size : sizes) {
            Graph g = buildGraph(size);

            System.out.println("\n=== Graph with " + size + " vertices ===");
            System.out.println("  Vertices: " + g.vertexCount()
                    + "  |  Edges: " + g.edgeCount());

            if (size <= 10) {
                g.printGraph();
            }

            long[] times = runTraversals(g);

            bfsTimes[runCount]  = times[0];
            dfsTimes[runCount]  = times[1];
            graphSizes[runCount] = size;
            runCount++;

            System.out.printf("  BFS time: %,d ns%n", times[0]);
            System.out.printf("  DFS time: %,d ns%n", times[1]);
        }
    }

    public void printResults() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║            PERFORMANCE RESULTS SUMMARY              ║");
        System.out.println("╠══════════╦═══════════════════╦═══════════════════════╣");
        System.out.println("║  Vertices║  BFS Time (ns)    ║  DFS Time (ns)        ║");
        System.out.println("╠══════════╬═══════════════════╬═══════════════════════╣");

        for (int i = 0; i < runCount; i++) {
            System.out.printf("║  %-8d║  %-17s║  %-21s║%n",
                    graphSizes[i],
                    String.format("%,d", bfsTimes[i]),
                    String.format("%,d", dfsTimes[i]));
        }

        System.out.println("╚══════════╩═══════════════════╩═══════════════════════╝");

        // Simple analysis
        System.out.println("\n── Analysis ──");
        for (int i = 0; i < runCount; i++) {
            String faster = bfsTimes[i] < dfsTimes[i] ? "BFS" : "DFS";
            long diff = Math.abs(bfsTimes[i] - dfsTimes[i]);
            System.out.printf("  Size %3d: %s was faster by %,d ns%n",
                    graphSizes[i], faster, diff);
        }
    }

    private Graph buildGraph(int size) {
        Graph g = new Graph();

        for (int i = 0; i < size; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < size; i++) {
            g.addEdge(i, (i + 1) % size);
        }

        for (int i = 0; i < size; i += 3) {
            g.addEdge(i, (i + 4) % size);
        }

        return g;
    }
}