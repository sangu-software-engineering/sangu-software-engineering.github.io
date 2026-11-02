import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SubmissionTest {

    private Submission submission;

    @BeforeEach
    void createDraft() {
        submission = new Submission("Nino");
    }

    @Test
    @DisplayName("a new submission starts as a draft")
    void startsAsDraft() {
        assertEquals(SubmissionState.DRAFT, submission.state());
    }

    @Test
    @DisplayName("draft -> submitted -> graded is the normal path")
    void theNormalPath() {
        submission.submit();
        assertEquals(SubmissionState.SUBMITTED, submission.state());

        submission.grade(87);
        assertAll(
                () -> assertEquals(SubmissionState.GRADED, submission.state()),
                () -> assertEquals(87, submission.score()));
    }

    @Test
    @DisplayName("a draft can be withdrawn")
    void aDraftCanBeWithdrawn() {
        submission.withdraw();
        assertEquals(SubmissionState.WITHDRAWN, submission.state());
    }

    @Test
    @DisplayName("grading a draft is refused")
    void gradingADraftIsRefused() {
        IllegalStateException refused =
                assertThrows(IllegalStateException.class, () -> submission.grade(70));
        assertEquals("Cannot grade a submission that is DRAFT", refused.getMessage());
    }

    @Test
    @DisplayName("a submission cannot be handed in twice")
    void submittingTwiceIsRefused() {
        submission.submit();
        assertThrows(IllegalStateException.class, () -> submission.submit());
    }

    @Test
    @DisplayName("a graded submission cannot be withdrawn")
    void withdrawingAfterGradingIsRefused() {
        submission.submit();
        submission.grade(55);
        assertThrows(IllegalStateException.class, () -> submission.withdraw());
    }

    @Test
    @DisplayName("there is no score before grading")
    void noScoreBeforeGrading() {
        submission.submit();
        assertThrows(IllegalStateException.class, () -> submission.score());
    }

    @ParameterizedTest(name = "a score of {0} is accepted")
    @ValueSource(ints = {0, 50, 100})
    void acceptsScoresInRange(int score) {
        submission.submit();
        submission.grade(score);
        assertEquals(score, submission.score());
    }

    @ParameterizedTest(name = "a score of {0} is refused")
    @ValueSource(ints = {-1, 101})
    void refusesScoresOutOfRange(int score) {
        submission.submit();
        assertThrows(IllegalArgumentException.class, () -> submission.grade(score));
    }
}
