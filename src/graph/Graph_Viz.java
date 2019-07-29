/**
 * @author: salimt
 */

package graph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import org.apache.commons.collections15.Transformer;
import util.GraphLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedList;


public class Graph_Viz {
    static int edgeCount_Directed = 0;   // This works with the inner MyEdge class

    public static void main(String[] args) {
        Graph_Viz GA1 = new Graph_Viz();

        LinkedList <Integer> Distinct_Vertex = new LinkedList <Integer>();//used to enter vertexes
        LinkedList <Integer> Source_Vertex = new LinkedList <Integer>();
        LinkedList <Integer> Target_Vertex = new LinkedList <Integer>();
        LinkedList <Double> Edge_Weight = new LinkedList <Double>();//used to enter edge weight
        LinkedList <String> Edge_Label = new LinkedList <String>(); //used to enter edge levels

        graph.Graph G = new CapGraph();
        GraphLoader.loadGraph(G, "data/small_test_graph.txt");
        Distinct_Vertex.addAll(G.exportGraph().keySet());
        for (int v : G.exportGraph().keySet()) {

            for (int neighbor : G.exportGraph().get(v)) {
                Source_Vertex.add(v);
                Target_Vertex.add(neighbor);
                Edge_Weight.add(Double.parseDouble(String.valueOf(v + neighbor)));
                Edge_Label.add(v + " - " + neighbor);
            }
        }
        GA1.Visualize_Directed_Graph(Distinct_Vertex, Source_Vertex, Target_Vertex, Edge_Weight, Edge_Label);
    }

    //used to construct graph and call graph algorithm used in JUNG
    public void Visualize_Directed_Graph(LinkedList <Integer> Distinct_nodes, LinkedList <Integer> source_vertex,
                                         LinkedList <Integer> target_vertex, LinkedList <Double> Edge_Weight,
                                         LinkedList <String> Edge_Label) {
        //CREATING weighted directed graph
        Graph <MyNode, MyLink> g = new DirectedSparseGraph <Graph_Viz.MyNode, Graph_Viz.MyLink>();
        //create node objects
        Hashtable <Integer, MyNode> Graph_Nodes = new Hashtable <>();
        LinkedList <MyNode> Source_Node = new LinkedList <Graph_Viz.MyNode>();
        LinkedList <MyNode> Target_Node = new LinkedList <Graph_Viz.MyNode>();
        LinkedList <MyNode> Graph_Nodes_Only = new LinkedList <Graph_Viz.MyNode>();
        //LinkedList<MyLink> Graph_Links = new LinkedList<Graph_Algos.MyLink>();
        //create graph nodes
        for (int i = 0; i < Distinct_nodes.size(); i++) {
            Integer node_name = Distinct_nodes.get(i);
            MyNode data = new MyNode(node_name);
            Graph_Nodes.put(node_name, data);
            Graph_Nodes_Only.add(data);
        }
        //Now convert all source and target nodes into objects
        for (int t = 0; t < source_vertex.size(); t++) {
            Source_Node.add(Graph_Nodes.get(source_vertex.get(t)));
            Target_Node.add(Graph_Nodes.get(target_vertex.get(t)));
        }
        //Now add nodes and edges to the graph
        for (int i = 0; i < Edge_Weight.size(); i++) {
            g.addEdge(new MyLink(Edge_Weight.get(i), Edge_Label.get(i)), Source_Node.get(i), Target_Node.get(i), EdgeType.DIRECTED);
        }

        //-------------

        CircleLayout <MyNode, MyLink> layout1 = new CircleLayout <MyNode, MyLink>(g);
        layout1.setSize(new Dimension(600, 600));
        BasicVisualizationServer <MyNode, MyLink> viz = new BasicVisualizationServer <MyNode, MyLink>(layout1);
        viz.setPreferredSize(new Dimension(600, 600));

        Transformer <MyNode, String> vertexLabelTransformer = new Transformer <MyNode, String>() {
            public String transform(MyNode vertex) {
                return vertex.Node_Property().toString();
            }
        };

        Transformer <MyLink, String> edgeLabelTransformer = new Transformer <MyLink, String>() {
            public String transform(MyLink edge) {
                return "[ " + edge.Link_Property() + " ]: Wt = " + edge.Link_Property_wt();
            }
        };

        viz.getRenderContext().setEdgeLabelTransformer(edgeLabelTransformer);
        viz.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer);

        JFrame frame = new JFrame("Graph of " + Distinct_nodes.size() + " Nodes and " + Edge_Label.size() + " Edges");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viz);
        frame.pack();
        frame.setVisible(true);

    }

    class MyNode {
        //static int edgeCount = 0;   // This works with the inner MyEdge class
        Integer id;

        public MyNode(Integer id) {
            this.id = id;
        }

        public String toString() {
            return "V" + id;
        }

        public Integer Node_Property() {
            Integer node_prop = id;
            return (node_prop);
        }
    }

    class MyLink {
        double weight;
        String Label;
        int id;

        public MyLink(double weight, String Label) {
            this.id = edgeCount_Directed++;
            this.weight = weight;
            this.Label = Label;
        }

        public String toString() {
            return "E" + id;
        }

        public String Link_Property() {
            String Link_prop = Label;
            return (Link_prop);
        }

        public String Link_Property_wt() {
            String Link_prop_wt = "" + weight;
            return (Link_prop_wt);
        }
    }
}