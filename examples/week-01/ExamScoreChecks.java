public class ExamScoreChecks {
    // Calls the real rule in ExamScores instead of a copy,
    // so every change to the rule is checked here.
    static void check(int score, boolean expected) {
        boolean actual = ExamScores.isPassing(score);
        String status = "FAIL";
        if (actual == expected) {
            status = "PASS";
        }
        System.out.println(status + ": isPassing(" + score + ") returned "
                + actual + ", expected " + expected);
    }

    public static void main(String[] args) {
        check(49, false);
        check(50, true);
        // Add the remaining rows of the expected-results table here.
    }
}
