/**
 * @author salimt
 * 
 * Grader for the egonet assignment. Runs implementation against
 * ten nodes from the Facebook dataset. 
 *
 */

package graph.grader;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class EgoGrader extends Grader {
    private static final int TESTS = 10;

    public static void main(String[] args) {
        Grader grader = new EgoGrader();
        Thread thread = new Thread(grader);
        thread.start();

        // Safeguard against infinite loops
        long endTime = System.currentTimeMillis() + 30000;
        boolean infinite = false;
        while (thread.isAlive()) {
            if (System.currentTimeMillis() > endTime) {
                thread.stop();
                infinite = true;
                break;
            }
        }
        if (grader.correct < TESTS) {
        	grader.feedback = "Some tests failed. Please check the following and try again:\nFailed tests will display the first mismatched lines of the output.\n" + grader.feedback;
        } else {
        	grader.feedback = "All tests passed. Congrats!\n" + grader.feedback;
        }
        if (infinite) {
            grader.feedback += "Your program entered an infinite loop or took longer than 30 seconds to finish.";
        }
        System.out.println(makeOutput((double)grader.correct / TESTS, grader.feedback));
    }

    /* Main grading method */
    public void run() {
        try {
            Graph graph = new CapGraph();
            GraphLoader.loadGraph(graph, "data/facebook_ucsd.txt");
            feedback += "\nGRAPH: facebook_ucsd.txt";
            for (int i = 0; i < 10; i++) {
                feedback += appendFeedback(i + 1, "Starting from node " + i);
                // Run user's implementation and make the output readable
                HashMap<Integer, HashSet<Integer>> res = graph.getEgonet(i).exportGraph();
                BufferedReader br = new BufferedReader(new FileReader("data/ego_answers/ego_" + i + ".txt"));
                String next;
                boolean failed = false;
                // Scan though the file
                while ((next = br.readLine()) != null) {
                    // Punctuation is for readability and doesn't help us
                    next = next.replaceAll("[:,]", " ");
                    Scanner sc = new Scanner(next);
                    int vertex = sc.nextInt();
                    HashSet<Integer> others = res.get(vertex);

                    // If vertex is present, should return a list
                    if (others == null) {
                        feedback += "FAILED. Egonet does not include vertex " + vertex + ".";
                        failed = true;
                        break;
                    }

                    HashSet<Integer> check = new HashSet<Integer>();
                    while(sc.hasNextInt()) {
                        check.add(sc.nextInt());
                    }
                    
                    // Sets should be equal
                    if (!check.equals(others)) {
                        check.add(i);
                        if(!check.equals(others)) {
                            feedback += "FAILED. Expected \"" + check + "\" for vertex #" + vertex + ", got \"" + others + "\".";
                            failed = true;
                            break;
                        }
                    }
                }
                if (!failed) {
                    feedback += "PASSED.";
                    correct += 1;
                }
            }
        } catch (Exception e) {
            feedback = "An error occurred during runtime.\n" + feedback + "\nError during runtime: " + e;
            e.printStackTrace();
        }
    }
}
