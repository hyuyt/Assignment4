public static void main(String[] args) {
    System.out.println("╔════════════════════════════════════════════╗");
    System.out.println("║   Graph Traversal and Representation System ║");
    System.out.println("╚════════════════════════════════════════════╝\n");

    System.out.println("─── Small Graph Demo (10 vertices) ───");

    Graph smallGraph = new Graph();
    for (int i = 0; i < 10; i++) {
        smallGraph.addVertex(new Vertex(i));
    }
     int[][] edges = {
            {0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,7},{5,8},{6,9},{7,9},{8,9}
     };
     for (int[] e : edges) {
         smallGraph.addEdge(e[0], e[1]);
     }

     smallGraph.printGraph();

     System.out.println("\nRunning BFS and DFS from vertex 0:");

     long t1 = System.nanoTime();
     var bfsResult = smallGraph.bfs(0);
     long t2 = System.nanoTime();

     long t3 = System.nanoTime();
     var dfsResult = smallGraph.dfs(0);
     long t4 = System.nanoTime();

     System.out.println("BFS traversal order : " + bfsResult);
     System.out.println("DFS traversal order : " + dfsResult);
     System.out.printf("BFS time: %,d ns%n", (t2 - t1));
     System.out.printf("DFS time: %,d ns%n", (t4 - t3));

     System.out.println("\n─── Automated Experiment (sizes 10, 30, 100) ───");
     Experiment exp = new Experiment();
     exp.runMultipleTests();

     exp.printResults();

     System.out.println("\nDone.");
}