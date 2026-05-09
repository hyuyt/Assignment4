import java.util.*;

public class Graph {

    private Map<Integer, Vertex> vertices;

    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        vertices = new LinkedHashMap<>();
        adjacencyList = new LinkedHashMap<>();
    }

    public void addVertex(Vertex v) {
        int id = v.getId();
        if (!vertices.containsKey(id)) {
            vertices.put(id, v);
            adjacencyList.put(id, new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
            System.out.println("Warning: one or both vertices not found for edge " + from + " -> " + to);
            return;
        }
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public void printGraph() {
        System.out.println("Graph (Adjacency List):");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            System.out.println("BFS: start vertex " + start + " not found.");
            return order;
        }

        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return order;
    }

    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();

        if (!vertices.containsKey(start)) {
            System.out.println("DFS: start vertex " + start + " not found.");
            return order;
        }

        Set<Integer> visited = new LinkedHashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                order.add(current);

                List<Integer> neighbors = adjacencyList.get(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return order;
    }

    public Set<Integer> getVertexIds() {
        return vertices.keySet();
    }

    public int vertexCount() {
        return vertices.size();
    }

    public int edgeCount() {
        int total = 0;
        for (List<Integer> neighbors : adjacencyList.values()) {
            total += neighbors.size();
        }
        return total / 2;
    }
}