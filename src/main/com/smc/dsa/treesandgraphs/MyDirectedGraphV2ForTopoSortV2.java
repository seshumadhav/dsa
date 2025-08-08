package com.smc.dsa.treesandgraphs;

import java.util.*;

// Implement Kahn's algorithm
public class MyDirectedGraphV2ForTopoSortV2 {

    private Map<String, List<String>> adjList = new HashMap<>();

    public void add(String source, Collection<String> targets) {
        adjList.put(source, new ArrayList<>(targets));
    }

    public static void main(String[] args) {
        MyDirectedGraphV2ForTopoSortV2 graph = new MyDirectedGraphV2ForTopoSortV2();

        graph.add("0", Arrays.asList("1", "4", "5"));
        graph.add("1", Arrays.asList("3", "4", "6"));
        graph.add("2", Arrays.asList("5"));
        graph.add("3", Arrays.asList("2", "4"));
        graph.add("6", Arrays.asList("2", "5"));

        System.out.println(graph.topoSort());
    }

    private List<String> topoSort() {
        List<String> ordering = new ArrayList<>();

        Map<String, Integer> indegree = computeIndegree();
        Queue<String> queue = getIndegree0(indegree);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            ordering.add(node);

            // Update indegrees of targets starting from this node
            for (String target : adjList.get(node)) {
                int newIndegree = indegree.get(target) - 1;
                indegree.put(target, newIndegree);
                if (newIndegree == 0) {
                    queue.add(target);
                }
            }

        }

        if (ordering.size() != indegree.size()) {
            throw new RuntimeException("Graph contains a cycle. Topological sort not possible.");
        }

        return ordering;
    }

    private Queue<String> getIndegree0(Map<String, Integer> indegree) {
        Queue<String> queue = new LinkedList<>();

        for (Map.Entry<String, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        return queue;
    }

    private Map<String, Integer> computeIndegree() {
        Map<String, Integer> indegree = new HashMap<>();

        for(Map.Entry<String, List<String>> entry : adjList.entrySet()) {
            indegree.putIfAbsent(entry.getKey(), 0);

            for (String target : entry.getValue()) {
                indegree.putIfAbsent(target, 0);
                indegree.put(target, indegree.get(target) + 1);
            }
        }

        return indegree;
    }

}
