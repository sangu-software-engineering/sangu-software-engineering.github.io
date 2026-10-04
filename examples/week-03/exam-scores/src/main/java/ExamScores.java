/** The week 1 program, now inside a Maven project. */
public class ExamScores {
    static final int PASS_MARK = 50;

    static boolean isPassing(int score) {
        return score >= PASS_MARK;
    }

    static int countPassing(int[] scores) {
        int passingCount = 0;
        for (int score : scores) {
            if (isPassing(score)) {
                passingCount++;
            }
        }
        return passingCount;
    }

    public static void main(String[] args) {
        int[] scores = {41, 50, 87, 12, 63};

        System.out.println("Passing scores: " + countPassing(scores));
    }
}
