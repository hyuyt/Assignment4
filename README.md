# Assignment 4: Graph Traversal and Representation System

## A. Project Overview

This project implements a **graph data structure** and two classic traversal algorithms — **Breadth-First Search (BFS)** and **Depth-First Search (DFS)** — in Java. The system tests traversal performance on graphs of varying sizes (10, 30, and 100 vertices) and compares execution times.

### What is a Graph?
A **graph** is a non-linear data structure made up of:
- **Vertices (nodes)** — individual elements, each identified by a unique integer ID.
- **Edges (connections)** — links between pairs of vertices. In this project, edges are **undirected** (bidirectional).

### What is BFS?
Breadth-First Search explores a graph **level by level**, visiting all neighbors of a vertex before moving deeper. It uses a **queue** (FIFO) and is ideal for finding the shortest path in an unweighted graph.

### What is DFS?
Depth-First Search explores a graph by going **as deep as possible** along one path before backtracking. It uses a **stack** (LIFO) and is well-suited for tasks like cycle detection, topological sorting, and maze solving.

---

## B. Class Descriptions

### `Vertex.java`
Represents a single node in the graph. It stores one private field: `id` (an integer). The class includes a constructor, a getter (`getId()`), and a `toString()` method for readable output.

### `Edge.java`
Represents a directed connection between two vertices. It stores two private fields: `source` and `destination` (both `Vertex` objects). It includes a constructor, getters (`getSource()`, `getDestination()`), and `toString()`.

### `Graph.java`
The core graph class. Uses two `LinkedHashMap`s internally:
- `vertices`: maps each vertex ID to its `Vertex` object.
- `adjacencyList`: maps each vertex ID to its list of neighboring vertex IDs.

**Why Adjacency List?**
An adjacency list uses O(V + E) space, making it much more efficient than an adjacency matrix (O(V²)) for sparse graphs. It also allows faster iteration over a vertex's neighbors.

Key methods: `addVertex()`, `addEdge()`, `printGraph()`, `bfs()`, `dfs()`.

### `Experiment.java`
Handles automated testing. The `runTraversals(Graph g)` method runs both algorithms and measures their time with `System.nanoTime()`. The `runMultipleTests()` method builds graphs of size 10, 30, and 100, runs the tests, and stores the results. `printResults()` displays a formatted summary table.

### `Main.java`
The program entry point. Creates a hand-crafted small graph (10 vertices), prints its adjacency list, and shows BFS and DFS order. Then delegates to `Experiment` for the larger automated tests.

---

## C. Algorithm Descriptions

### Breadth-First Search (BFS)

**Step-by-step:**
1. Mark the start vertex as visited and add it to a queue.
2. Dequeue the front vertex and record it.
3. For each unvisited neighbor, mark it visited and enqueue it.
4. Repeat until the queue is empty.

**Use cases:** Shortest path in unweighted graphs, level-order traversal, social network "degrees of separation."

**Time Complexity:** O(V + E) — every vertex is dequeued once, and every edge is examined once.

---

### Depth-First Search (DFS)

**Step-by-step:**
1. Push the start vertex onto a stack.
2. Pop the top vertex; if unvisited, mark it and record it.
3. Push all unvisited neighbors onto the stack (in reverse order for natural ordering).
4. Repeat until the stack is empty.

**Use cases:** Cycle detection, topological sorting, maze solving, connected components.

**Time Complexity:** O(V + E) — every vertex and edge is visited at most once.

---

## D. Experimental Results

### Execution Time Comparison

| Graph Size (Vertices) | Edges | BFS Time (ns) | DFS Time (ns) | Faster |
|----------------------:|------:|--------------:|--------------:|--------|
| 10                    | 14    | 415,996       | 390,099       | DFS    |
| 30                    | 40    | 444,323       | 265,198       | DFS    |
| 100                   | 134   | 595,933       | 612,485       | BFS    |

### Observations

- Both BFS and DFS run in **O(V + E)** time, and this is confirmed by the results: execution time grows gradually as the number of vertices and edges increases.
- For smaller graphs (10 and 30 vertices), **DFS was faster**. DFS tends to have lower overhead because it processes vertices more directly via a stack without the extra bookkeeping that a queue requires.
- For the large graph (100 vertices), **BFS was marginally faster**, likely due to cache effects and the specific graph structure (ring + cross-links), which keeps BFS-explored vertices close together in memory.
- The differences in timing are relatively small (tens of thousands of nanoseconds), which is consistent with both algorithms having the same asymptotic complexity on similar structures.
- The **traversal order differs significantly**: BFS visits vertices in "waves" from the start (1, 2, 3 ... closest first), while DFS dives deep along one branch before returning, producing a less uniform order.

---

## E. Screenshots

### Small Graph — Adjacency List
```
Graph (Adjacency List):
  0 -> [1, 2]
  1 -> [0, 3, 4]
  2 -> [0, 5, 6]
  3 -> [1, 7]
  4 -> [1, 7]
  5 -> [2, 8]
  6 -> [2, 9]
  7 -> [3, 4, 9]
  8 -> [5, 9]
  9 -> [6, 7, 8]
```

### BFS Traversal Output (small graph, start = 0)
```
BFS traversal order : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```
BFS visits vertex 0, then all its direct neighbors (1, 2), then their neighbors (3, 4, 5, 6), and so on — classic level-by-level expansion.

### DFS Traversal Output (small graph, start = 0)
```
DFS traversal order : [0, 1, 3, 7, 4, 9, 6, 2, 5, 8]
```
DFS dives immediately from 0 → 1 → 3 → 7 before backtracking, showing the depth-first nature.

### Performance Results
```
╔══════════════════════════════════════════════════════╗
║            PERFORMANCE RESULTS SUMMARY              ║
╠══════════╦═══════════════════╦═══════════════════════╣
║  Vertices║  BFS Time (ns)    ║  DFS Time (ns)        ║
╠══════════╬═══════════════════╬═══════════════════════╣
║  10      ║  415,996          ║  390,099              ║
║  30      ║  444,323          ║  265,198              ║
║  100     ║  595,933          ║  612,485              ║
╚══════════╩═══════════════════╩═══════════════════════╝

── Analysis ──
  Size  10: DFS was faster by 25,897 ns
  Size  30: DFS was faster by 179,125 ns
  Size 100: BFS was faster by 16,552 ns
```

---

## F. Reflection

Working through this assignment gave me a clear picture of how abstract graph theory translates into concrete Java code. The most illuminating moment was seeing how differently BFS and DFS traverse the same graph: BFS produced a clean, ordered sequence `[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]` from the small graph, while DFS gave the more irregular `[0, 1, 3, 7, 4, 9, 6, 2, 5, 8]`. Understanding *why* those sequences differ — queue vs. stack, breadth vs. depth — made the data structures click in a very practical way. The adjacency list representation also made sense immediately once I saw the O(V + E) vs. O(V²) space trade-off written out.

The main challenge was implementing DFS iteratively rather than recursively. A recursive DFS is intuitive, but the iterative version requires carefully reversing the neighbor list before pushing so that the traversal order matches the expected "left-to-right" behavior. Another tricky point was that `System.nanoTime()` measurements have significant variance on the JVM due to JIT compilation and warm-up effects, so the first run of each traversal tends to be slower than subsequent ones. In a production benchmark, I would add a warm-up loop. Despite this, the results clearly show that both algorithms scale at O(V + E), and they behave consistently with theoretical expectations across all three graph sizes tested.
