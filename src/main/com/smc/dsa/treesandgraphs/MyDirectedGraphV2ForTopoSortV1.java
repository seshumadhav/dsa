package com.smc.dsa.treesandgraphs;

import java.util.*;

public class MyDirectedGraphV2ForTopoSortV1 {

    private final List<Edge> edges = new ArrayList<>();

    private static Set<String> getSourceVertices(List<Edge> edges) {
        Set<String> sources = new HashSet<>();

        for (Edge edge : edges) {
            sources.add(edge.source);
        }

        return sources;
    }


    private static Set<String> getTargetVertices(List<Edge> edges) {
        Set<String> targets = new HashSet<>();

        for (Edge edge : edges) {
            targets.add(edge.target);
        }

        return targets;
    }

    public void addEdge(String source, String target) {
        edges.add(new Edge(source, target));
    }

    public static void main(String[] args) {
        MyDirectedGraphV2ForTopoSortV1 graph = new MyDirectedGraphV2ForTopoSortV1();

        graph.addEdge("0", "1");
        graph.addEdge("0", "4");
        graph.addEdge("0", "5");

        graph.addEdge("1", "3");
        graph.addEdge("1", "4");
        graph.addEdge("1", "6");

        graph.addEdge("2", "5");

        graph.addEdge("3", "2");
        graph.addEdge("3", "4");

        graph.addEdge("6", "5");
        graph.addEdge("6", "2");

        List<String> topoOrdering = graph.topoSortv2();
        System.out.println(topoOrdering);
    }

    public List<String> topoSortv2() {
        List<Edge> edgesBefore = new LinkedList<>(edges);

        Set<String> sources;
        Set<String> targets;
        List<Edge> edgesAfter;

        List<String> topoOrdering = new ArrayList<>();

        while (!edgesBefore.isEmpty()) {

            sources = getSourceVertices(edgesBefore);
            targets = getTargetVertices(edgesBefore);
            edgesAfter = new ArrayList<>();

            for (String src : sources) {
                if (!targets.contains(src)) {
                    topoOrdering.add(src);
                }
            }

            for (Edge edge : edgesBefore) {
                if (!topoOrdering.contains(edge.source)) {
                    edgesAfter.add(edge);
                }
            }

            edgesBefore = edgesAfter;
        }

        for (Edge edge : edges) {
            if (!topoOrdering.contains(edge.source)) {
                topoOrdering.add(edge.source);
            }

            if (!topoOrdering.contains(edge.target)) {
                topoOrdering.add(edge.target);
            }
        }

        return topoOrdering;
    }


    class Edge {
        String source;
        String target;

        public Edge(String source, String target) {
            this.source = source;
            this.target = target;
        }
    }

    // Don't use
    // Grungy boring long complex way of implementing topological sort
    /*
    public List<String> topoSortv1() {
        Queue<String> queue = new LinkedList<>();
        List<String> topoOrdering = new ArrayList<>();

        List<Edge> remainingEdges = new LinkedList<>();
        remainingEdges.addAll(edges);

        List<String> sources = getSourceVertices(remainingEdges);
        List<String> targets = getTargetVertices(remainingEdges);

        do {

            // Find vertices in source that are not in target vertices. Add them to final vertices list
            // When adding a vertex to final list, add all its target vertices to a queue; Remove edges that were used
            for (String source : sources) {
                if (!targets.contains(source)) {
                    // Add source to ordering
                    topoOrdering.add(source);

                    // Remove edges that start with source
                    for (Edge edge : remainingEdges) {
                        if (edge.source.equals(source)) {
                            queue.add(edge.target); // Add the target to queue

                            remainingEdges.remove(edge);
                            sources = getSourceVertices(remainingEdges);
                            targets = getTargetVertices(remainingEdges);
                        }
                    }
                }
            }

            if (queue.isEmpty()) {
                return topoOrdering;
            }

            // Pick first item in the queue
            String vertex = queue.remove();

            // If item is already in target vertices list, move on to next
            if (topoOrdering.contains(vertex)) {
                continue;
            }

            // If vertex is in remaining edges list, enqueue it at the end
            if (targets.contains(vertex)) {
                queue.add(vertex);
                continue;
            }

            // Add it to the target vertices and remove its edges
            topoOrdering.add(vertex);

            // Remove edges that start with source
            for (Edge edge : remainingEdges) {
                if (edge.source.equals(vertex)) {
                    queue.add(edge.target); // Add the target to queue

                    remainingEdges.remove(edge);
                    sources = getSourceVertices(remainingEdges);
                    targets = getTargetVertices(remainingEdges);
                }
            }
        } while (!queue.isEmpty());

        return topoOrdering;
    } */

}
