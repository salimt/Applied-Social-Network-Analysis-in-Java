/**
 * @author: salimt
 */

package graph.grader;

import graph.CapGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.GraphLoader;

import java.util.HashMap;
import java.util.HashSet;

public class suggestFriendGrader {

    private CapGraph graph;
    private HashMap <Integer, HashSet <Integer>> potentialFriends;
    private HashMap <Integer, HashSet <Integer>> connections = new HashMap <>();

    @BeforeEach
    public void initialize() {
        this.graph = new CapGraph();
        GraphLoader.loadGraph(graph, "data/small_test_graph.txt");
    }

    // all friends know each other
    @Test
    public void test01() {
        potentialFriends = graph.suggestFriendsOfFriends(2);
        Assertions.assertEquals(connections, potentialFriends);
    }

    // 2 possible friendships
    @Test
    public void test02() {
        potentialFriends = graph.suggestFriendsOfFriends(3);
        connections = new HashMap <>();
        connections.put(1, new HashSet <Integer>());
        connections.get(1).add(7);
        connections.put(2, new HashSet <Integer>());
        connections.get(2).add(7);
        connections.put(7, new HashSet <Integer>());
        connections.get(7).add(1); connections.get(7).add(2);
        Assertions.assertEquals(connections, potentialFriends);
    }
}
