package suams.result;

/**
 * Decides what a student sees on the result page.
 * This is the function analysed with basis-path testing: 3 decisions, V(G) = 4.
 */
public class ResultStatusService {

    public static final double PASS_MARK = 40.0;

    public String getResultStatus(boolean published, boolean duesCleared, double totalMarks) {
        if (!published) {                    // node 1 (decision 1)
            return "Result Not Published";   // node 2
        }
        if (!duesCleared) {                  // node 3 (decision 2)
            return "Result Withheld";        // node 4
        }
        if (totalMarks >= PASS_MARK) {       // node 5 (decision 3)
            return "Passed";                 // node 6
        }
        return "Failed";                     // node 7
    }                                        // node 8 (exit)
}
