import java.util.ArrayList;
import java.util.List;

/**
 * The grades of one course. The book owns its grades: nobody outside can add a
 * second grade for the same student, and nobody can change the list behind its back.
 */
public class GradeBook {
    public static final int PASS_MARK = 50;

    private final String courseName;
    private final List<Grade> grades = new ArrayList<>();

    public GradeBook(String courseName) {
        if (courseName == null || courseName.isBlank()) {
            throw new IllegalArgumentException("A grade book needs a course name");
        }
        this.courseName = courseName;
    }

    /**
     * Adds one grade.
     *
     * @throws IllegalArgumentException if the student already has a grade in this course
     */
    public void add(Grade grade) {
        if (hasGradeFor(grade.student())) {
            throw new IllegalArgumentException("Already graded: " + grade.student());
        }
        grades.add(grade);
    }

    public boolean hasGradeFor(String student) {
        for (Grade grade : grades) {
            if (grade.student().equals(student)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPassing(Grade grade) {
        return grade.score() >= PASS_MARK;
    }

    public int countPassing() {
        int passing = 0;
        for (Grade grade : grades) {
            if (isPassing(grade)) {
                passing++;
            }
        }
        return passing;
    }

    /**
     * The mean score.
     *
     * @throws IllegalStateException if the book is empty, because there is no average of nothing
     */
    public double average() {
        if (grades.isEmpty()) {
            throw new IllegalStateException("An empty grade book has no average");
        }
        int total = 0;
        for (Grade grade : grades) {
            total += grade.score();
        }
        return (double) total / grades.size();
    }

    public String courseName() {
        return courseName;
    }

    /** A copy, so that a caller cannot add or remove grades without going through add. */
    public List<Grade> grades() {
        return List.copyOf(grades);
    }
}
