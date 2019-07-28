/**
 * @author: salimt
 */

package graph.grader;

import graph.CapGraph;
import graph.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static graph.MDS.MDS;



public class MDSGrader {

    private HashSet<Integer> finalSet;

    // biggest node is not included for the sake of minimum dominating set
    @Test
    public void test01() {
        Graph G = new CapGraph();
        for (int i=0; i<8; i++) { G.addVertex(i); }
        G.addEdge(0,4); G.addEdge(4,0);
        G.addEdge(0,2); G.addEdge(2,0);
        G.addEdge(0,5); G.addEdge(5,0);
        G.addEdge(0,1); G.addEdge(1,0);

        G.addEdge(1,4); G.addEdge(4,1);
        G.addEdge(1,2); G.addEdge(2,1);
        G.addEdge(1,6); G.addEdge(6,1);

        G.addEdge(3,6); G.addEdge(6,3);
        G.addEdge(3,7); G.addEdge(7,3);
        G.addEdge(3,1); G.addEdge(1,3);

        finalSet = MDS(G);
        HashSet<Integer> answer = new HashSet <>();
        answer.add(0); answer.add(3);

        Assertions.assertEquals(answer, finalSet);
    }

    // the first two biggest nodes
    @Test
    public void test02() {
        Graph G = new CapGraph();
        for (int i=1; i<14; i++) { G.addVertex(i); }

        G.addEdge(3,1); G.addEdge(1,3);
        G.addEdge(3,2); G.addEdge(2,3);
        G.addEdge(3,4); G.addEdge(4,3);
        G.addEdge(3,5); G.addEdge(5,3);
        G.addEdge(3,6); G.addEdge(6,3);
        G.addEdge(3,7); G.addEdge(7,3);
        G.addEdge(3,8); G.addEdge(8,3);

        G.addEdge(7,8); G.addEdge(8,7);
        G.addEdge(7,1); G.addEdge(1,7);

        G.addEdge(6,5); G.addEdge(5,6);
        G.addEdge(6,10); G.addEdge(10,6);

        G.addEdge(4,9); G.addEdge(9,4);

        G.addEdge(11,9); G.addEdge(9,11);
        G.addEdge(11,12); G.addEdge(12,11);
        G.addEdge(12,10); G.addEdge(10,12);

        G.addEdge(13,10); G.addEdge(10,13);
        G.addEdge(13,11); G.addEdge(11,13);
        G.addEdge(13,12); G.addEdge(12,13);
        G.addEdge(13,9); G.addEdge(9,13);

        finalSet = MDS(G);
        HashSet<Integer> answer = new HashSet <>();
        answer.add(3); answer.add(13);

        Assertions.assertEquals(answer, finalSet);
    }

    @Test
    public void test03() {
        Graph G = new CapGraph();
        for (int i=1; i<6; i++) { G.addVertex(i); }

        G.addEdge(2,1); G.addEdge(1,2);
        G.addEdge(2,3); G.addEdge(3,2);
        G.addEdge(2,5); G.addEdge(5,2);
        G.addEdge(4,1); G.addEdge(1,4);

        finalSet = MDS(G);
        HashSet<Integer> answer = new HashSet <>();
        answer.add(2); answer.add(4);

        Assertions.assertEquals(answer.size(), finalSet.size());
    }

    @Test
    public void test04() {
        Graph G = new CapGraph();
        for (int i=1; i<9; i++) { G.addVertex(i); }

        G.addEdge(4,1); G.addEdge(1,4);
        G.addEdge(4,2); G.addEdge(2,4);
        G.addEdge(4,3); G.addEdge(3,4);

        G.addEdge(2,1); G.addEdge(1,2);
        G.addEdge(3,7); G.addEdge(7,3);
        G.addEdge(5,6); G.addEdge(6,5);

        G.addEdge(8,5); G.addEdge(5,8);
        G.addEdge(8,6); G.addEdge(6,8);
        G.addEdge(8,7); G.addEdge(7,8);


        finalSet = MDS(G);
        HashSet<Integer> answer = new HashSet <>();
        answer.add(4); answer.add(8);

        Assertions.assertEquals(answer, finalSet);
    }

    @Test
    public void test05() {
        Graph G = new CapGraph();
        for (int i=1; i<5; i++) { G.addVertex(i); }

        G.addEdge(2,1); G.addEdge(1,2);
        G.addEdge(2,3); G.addEdge(3,2);
        G.addEdge(2,4); G.addEdge(4,2);

        finalSet = MDS(G);
        HashSet<Integer> answer = new HashSet <>();
        answer.add(2);

        Assertions.assertEquals(answer, finalSet);
    }

    //facebook_1000 -> 214
    //facebook_2000 -> 223
    //facebook_ucsd -> 357 (18K Nodes)
    /*@Test
    public void test06() {
        Graph G = new CapGraph();
        GraphLoader.loadGraph(G, "data/facebook_ucsd.txt");

        System.out.println(MDS(G).size());

    }*/

}
