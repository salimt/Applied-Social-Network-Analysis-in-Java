/**
 * @author salimt
 * 
 * Abstract grader class that includes methods common to concrete graders.
 *
 */

package graph.grader;

public abstract class Grader implements Runnable {
    public String feedback = "";
    public int correct = 0;
    protected static final int TESTS = 10;

    /* Formats output to look nice */
    public static String makeOutput(double score, String feedback) {
        return "Score: " + score + "\nFeedback: " + feedback;
    }

    /* Print test descriptions neatly */
    public static String appendFeedback(int num, String test) {
        return "\n**Test #" + num + ": " + test + "...";
    }

}
