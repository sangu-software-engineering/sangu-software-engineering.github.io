/** One student's result. A grade cannot exist with an impossible score. */
public record Grade(String student, int score) {

    public Grade {
        if (student == null || student.isBlank()) {
            throw new IllegalArgumentException("A grade needs a student name");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100: " + score);
        }
    }
}
