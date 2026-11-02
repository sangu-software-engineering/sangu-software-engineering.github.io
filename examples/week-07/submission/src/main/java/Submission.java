/**
 * One student's assignment submission.
 *
 * <p>The object is a small state machine: every method either moves it to a new
 * state or refuses, so a submission can never be graded twice or graded before
 * it was handed in.
 */
public class Submission {
    private final String student;
    private SubmissionState state = SubmissionState.DRAFT;
    private int score;

    public Submission(String student) {
        if (student == null || student.isBlank()) {
            throw new IllegalArgumentException("A submission needs a student name");
        }
        this.student = student;
    }

    public String student() {
        return student;
    }

    public SubmissionState state() {
        return state;
    }

    /** @throws IllegalStateException unless the submission is still a draft */
    public void submit() {
        requireState(SubmissionState.DRAFT, "submit");
        state = SubmissionState.SUBMITTED;
    }

    /**
     * @param score the result, from 0 to 100
     * @throws IllegalStateException if the submission has not been handed in
     * @throws IllegalArgumentException if the score is outside 0 to 100
     */
    public void grade(int score) {
        requireState(SubmissionState.SUBMITTED, "grade");
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100: " + score);
        }
        this.score = score;
        state = SubmissionState.GRADED;
    }

    /** @throws IllegalStateException if the submission is already graded or withdrawn */
    public void withdraw() {
        if (state == SubmissionState.GRADED || state == SubmissionState.WITHDRAWN) {
            throw new IllegalStateException("Cannot withdraw a submission that is " + state);
        }
        state = SubmissionState.WITHDRAWN;
    }

    /** @throws IllegalStateException unless the submission has been graded */
    public int score() {
        requireState(SubmissionState.GRADED, "read the score of");
        return score;
    }

    private void requireState(SubmissionState required, String action) {
        if (state != required) {
            throw new IllegalStateException("Cannot " + action + " a submission that is " + state);
        }
    }
}
