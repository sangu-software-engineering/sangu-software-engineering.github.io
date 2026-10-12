/** Example code for the requirements chapter: one rule, written down as a contract. */
public class RetakeEligibility {
    static final int PASS_MARK = 50;
    static final int MAX_RETAKES = 1;
    static final int MAX_SCORE = 100;

    /**
     * Decides whether a student may register for a retake of an exam.
     *
     * <p>Preconditions: the score is between 0 and 100, and the number of retakes
     * already used is not negative. Postcondition: the answer is true exactly when
     * the student failed the last attempt and has a retake left.
     *
     * @param score the result of the last attempt, from 0 to 100
     * @param retakesUsed how many retakes the student has already used
     * @return true when the student may register, false otherwise
     * @throws IllegalArgumentException if an argument is outside its allowed range
     */
    static boolean mayRegister(int score, int retakesUsed) {
        if (score < 0 || score > MAX_SCORE) {
            throw new IllegalArgumentException("Score must be between 0 and 100: " + score);
        }
        if (retakesUsed < 0) {
            throw new IllegalArgumentException("Retakes used cannot be negative: " + retakesUsed);
        }
        return score < PASS_MARK && retakesUsed < MAX_RETAKES;
    }

    static String describe(int score, int retakesUsed) {
        String answer = mayRegister(score, retakesUsed) ? "may register" : "may not register";
        return "Score " + score + ", retakes used " + retakesUsed + ": " + answer;
    }

    public static void main(String[] args) {
        System.out.println(describe(41, 0));
        System.out.println(describe(49, 0));
        System.out.println(describe(50, 0));
        System.out.println(describe(41, 1));

        try {
            describe(101, 0);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Rejected: " + rejected.getMessage());
        }
    }
}
