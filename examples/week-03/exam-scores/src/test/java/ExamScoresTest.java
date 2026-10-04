import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** The expected-results table from week 1, as tests Maven can run. */
class ExamScoresTest {

    @Test
    @DisplayName("a score below the pass mark fails")
    void belowThePassMark() {
        assertFalse(ExamScores.isPassing(49));
    }

    @Test
    @DisplayName("exactly the pass mark passes")
    void exactlyThePassMark() {
        assertTrue(ExamScores.isPassing(50));
    }

    @Test
    @DisplayName("a score above the pass mark passes")
    void aboveThePassMark() {
        assertTrue(ExamScores.isPassing(51));
    }

    @Test
    @DisplayName("the whole group is counted")
    void countsPassingScores() {
        assertEquals(3, ExamScores.countPassing(new int[] {41, 50, 87, 12, 63}));
    }

    @Test
    @DisplayName("an empty group has no passing scores")
    void countsNothingInAnEmptyGroup() {
        assertEquals(0, ExamScores.countPassing(new int[] {}));
    }
}
