package com.smc.dsa.treesandgraphs;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyDirectedGraph {

    private final HashMap<String, List<String>> adjMatrix = new HashMap<>();

    public void insert(String startNodeName, String endNodeName) {
        if (!adjMatrix.containsKey(startNodeName)) {
            adjMatrix.put(startNodeName, new ArrayList<>());
        }

        if (endNodeName != null) {
            adjMatrix.get(startNodeName).add(endNodeName);
        }
    }
    public String traverse(String startNode) {
        if (startNode == null) {
            return "";
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(startNode);

        Set<String> visited = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        do {

            // This was a bug. Since in disconnected graph, we do not want to traverse the other disconnected part
            // String node = queue.isEmpty() ? findAnUnvisitedNode(adjMatrix, visited) : queue.remove();

            String node = queue.poll();
            if (visited.contains(node) || node == null) {
                continue;
            }

            sb.append(node).append(" ");
            visited.add(node);
            queue.addAll(adjMatrix.get(node));

            // This exit condition was a bug; There would be no way to get out the loop.
            // } while (!queue.isEmpty() || visited.size() < adjMatrix.size());
        } while (!queue.isEmpty());

        return sb.toString();
    }

    private static String findAnUnvisitedNode(HashMap<String, List<String>> adjMatrix, Set<String> visited) {

        for (String node : adjMatrix.keySet()) {
            if (!visited.contains(node)) {
                return node;
            }
        }

        return null;
    }

    public static void main(String[] args) {


        MyDirectedGraph mdg = new MyDirectedGraph();
        mdg.insert("0", "1");
        mdg.insert("0", "4");
        mdg.insert("0", "5");

        mdg.insert("1", "3");
        mdg.insert("1", "4");


        mdg.insert("2", "1");

        mdg.insert("3", "2");
        mdg.insert("3", "4");

        mdg.insert("4", null);
        mdg.insert("5", null);

        String traversalResult = mdg.traverse("0").trim();
        System.out.println("Graph traversal starting '0': " + traversalResult);
        assertEquals("0 1 4 5 3 2", traversalResult);

        traversalResult = mdg.traverse("2").trim();
        System.out.println("Graph traversal starting '2': " + traversalResult);
        assertTrue(traversalResult.equals("2 1 3 4") || traversalResult.equals("2 1 4 3"));

        traversalResult = mdg.traverse("3").trim();
        System.out.println("Graph traversal starting '3': " + traversalResult);
        assertTrue(traversalResult.equals("3 4") || traversalResult.equals("3 2 4 1"));

        traversalResult = mdg.traverse("4").trim();
        System.out.println("Graph traversal starting '4': " + traversalResult);
        assertEquals("4", traversalResult);

        traversalResult = mdg.traverse("5").trim();
        System.out.println("Graph traversal starting '5': " + traversalResult);
        assertEquals("5", traversalResult);
    }

}
