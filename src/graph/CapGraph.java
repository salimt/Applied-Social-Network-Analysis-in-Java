/**
 * @author salimt
 */

package graph;
import java.util.*;

public class CapGraph implements Graph {
    private HashMap <Integer, HashSet<Integer>> nodeMap = new HashMap <>();

    /* (non-Javadoc)
     * @see graph.Graph#addVertex(int)
     */
    @Override
    public void addVertex(int num) {
        if (!contains(num)) {
            nodeMap.put(num, new HashSet <Integer>());
        }
    }

    /* (non-Javadoc)
     * @see graph.Graph#addEdge(int, int)
     */
    @Override
    public void addEdge(int from, int to) {
        if (contains(from) && contains(to)) {
            nodeMap.get(from).add(to);
        }
    }

    /**
     * @param map
     * @return new Graph with reversed edges
     */
    public Graph reverseEdges(HashMap <Integer, HashSet <Integer>> map) {
        Graph tempGraph = new CapGraph();
        for (int n : map.keySet()) {
            tempGraph.addVertex(n);
            for (int neighbor : map.get(n)) {
                tempGraph.addVertex(neighbor);
                tempGraph.exportGraph().get(neighbor).add(n);
                tempGraph.addEdge(neighbor, n);
            }
        }
        return tempGraph;
    }

    /* (non-Javadoc)
     * @see graph.Graph#getEgonet(int)
     */
    @Override
    public Graph getEgonet(int center) {
        Graph egoNet = new CapGraph();
        egoNet.addVertex(center);

        for (int n : nodeMap.get(center)) {
            egoNet.addVertex(n);
            egoNet.addEdge(center, n);

            List <Integer> common = new ArrayList <>(nodeMap.get(n));
            common.retainAll(nodeMap.get(center));

            for (int num : common) {
                egoNet.addVertex(num);
                egoNet.addEdge(num, n);
            }

            if (nodeMap.get(n).contains(center)) {
                egoNet.addEdge(n, center);
            }

        }
        return egoNet;
    }

    /**
     * suggests unconnected potential friends by commonly-shared friend given as parameter
     *
     * @param user is the bridge between others
     * @return HashMap, key as one friend and its value as potential friends
     */
    public HashMap <Integer, HashSet <Integer>> suggestFriendsOfFriends(Integer user) {
        HashMap <Integer, HashSet <Integer>> suggestions = new HashMap <>();
        HashSet <Integer> allFriends = nodeMap.get(user);
        for (Integer friend : allFriends) {
            for (Integer pFriend : allFriends) {
                if (!friend.equals(pFriend) && !nodeMap.get(friend).contains(pFriend)) {
                    if (!suggestions.containsKey(friend)) {
                        suggestions.put(friend, new HashSet <Integer>(pFriend));
                    }
                    suggestions.get(friend).add(pFriend);

                    if (!suggestions.containsKey(pFriend)) {
                        suggestions.put(pFriend, new HashSet <Integer>(friend));
                    }
                    suggestions.get(pFriend).add(friend);
                }
            }
        }
        return suggestions;
    }

    /* (non-Javadoc)
     * @see graph.Graph#getSCCs()
     */
    @Override
    public List <Graph> getSCCs() {
        Stack <Integer> nodeStack = new Stack <>();
        nodeStack.addAll(nodeMap.keySet());

        Stack <Integer> normalStack = DFS.DFS(this, nodeStack);
        Graph reverse = reverseEdges(nodeMap);

        return DFS.constructSCC(reverse, normalStack);
    }

    /* (non-Javadoc)
     * @see graph.Graph#exportGraph()
     */
    @Override
    public HashMap <Integer, HashSet <Integer>> exportGraph() {
        return new HashMap <>(nodeMap);
    }

    //checks if the object included in the nodeMap
    private boolean contains(Object o) {
        return nodeMap.containsKey(o);
    }

}
