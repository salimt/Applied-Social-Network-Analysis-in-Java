/**
 * @author salimt
 *
 * Grader for the SCC assignment.
 *
 */

package graph.grader;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class SCCGrader extends Grader {
    public int totalTests;
    public int testsPassed;

    public SCCGrader() {
        totalTests = 0;
        testsPassed = 0;
    }
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        SCCGrader grader = new SCCGrader();
        Thread thread = new Thread(grader);
        thread.start();
        long endTime = System.currentTimeMillis() + 30000;
        boolean infinite = false;
        while (thread.isAlive()) {
            if (System.currentTimeMillis() > endTime) {
                thread.stop();
                infinite = true;
                break;
            }
        }
        if (grader.testsPassed < grader.totalTests) {
        	grader.feedback = "Some tests failed. Please check the following and try again:\n" + grader.feedback;
        } else {
        	grader.feedback = "All tests passed. Congrats!\n" + grader.feedback;
        }
        if (infinite) {
            grader.testsPassed = 0;
            grader.totalTests = 1;
            grader.feedback += "Your program entered an infinite loop or took longer than 30 seconds to finish.";
        }
        System.out.println(makeOutput((double)grader.testsPassed/grader.totalTests, grader.feedback));
        System.out.close();
    }

    public void run() {

        try {

            for(int i = 0; i < 10; i++) {
                Graph g = new CapGraph();
                Set<Integer> vertices;

                String answerFile = "data/scc_answers/scc_" + (i + 1) + ".txt";
                GraphLoader.loadGraph(g, "data/scc/test_" + (i +1)+ ".txt");
                BufferedReader br = new BufferedReader(new FileReader(answerFile));
                feedback += appendFeedback(i + 1, "\nGRAPH: T" + (i + 1));

                // build list from answer
                List<Set<Integer>> answer = new ArrayList<Set<Integer>>();
                String line;

                while((line = br.readLine()) != null) {
                    Scanner sc = new Scanner(line);
                    vertices = new TreeSet<Integer>();
                    while(sc.hasNextInt()) {
                        vertices.add(sc.nextInt());
                    }
                    answer.add(vertices);


                    sc.close();
                }



                // get student SCC result
                List<Graph> graphSCCs = g.getSCCs();

                List<Set<Integer>> sccs = new ArrayList<Set<Integer>>();

                for(Graph graph : graphSCCs) {
                    HashMap<Integer, HashSet<Integer>> curr = graph.exportGraph();
                    TreeSet<Integer> scc = new TreeSet<Integer>();
                    for (Map.Entry<Integer, HashSet<Integer>> entry : curr.entrySet()) {
                        scc.add(entry.getKey());
                    }
                    sccs.add(scc);
                }


                boolean testFailed = false;
                testsPassed += answer.size() + sccs.size();
                totalTests += answer.size() + sccs.size();

                Set<Integer> answerSCC = null;
                Set<Integer> scc = null;

                // loop over SCCs
                int j = 0;
                for(; j < answer.size(); j++) {

                    answerSCC = answer.get(j);
                    scc = null;

                    if(j < sccs.size()) {
                        scc = sccs.get(j);
                    }


                    // check if learner result contains SCC from answer file
                    if(!sccs.contains(answerSCC)) {
                        if(!testFailed) {
                            testFailed = true;
                            feedback += "FAILED. ";
                        }
                        feedback += "Your result did not contain the scc on line "
                                     + (j+1) + " in \"" + answerFile + "\"";
                        feedback += "\n";
                        testsPassed--;
                    }

                    // check if answer contains learners scc
                    if(scc != null && !answer.contains(scc)) {
                        if(!testFailed) {
                            testFailed = true;
                            feedback += "FAILED. ";
                        }
                        feedback += "Your result contained an extra SCC: ";
                        for(Integer id : scc) {
                            feedback += id + " ";
                        }
                        feedback += "\n";
                        testsPassed--;
                    }


                }

                while(j < sccs.size()) {
                    // check if answer contains learners scc
                    if(scc != null && !answer.contains(scc)) {
                        if(!testFailed) {
                            testFailed = true;
                            feedback += "FAILED. ";
                        }
                        feedback += "Your result contained an extra SCC : ";
                        for(Integer id : scc) {
                            feedback += id + " ";
                        }
                        feedback += "\n";
                        testsPassed--;
                    }

                    j++;
                }

                if(!testFailed) {
                    feedback += "PASSED.";
                }

                br.close();
            }
        } catch (Exception e) {
            feedback = "An error occurred during runtime.\n" + feedback + "\nError during runtime: " + e;
            testsPassed = 0;
            totalTests = 1;
        }
    }
}
