import java.util.Locale;

/** A short report for a human to read. */
public class PlainFormat implements GradeFormat {

    @Override
    public String format(GradeBook book) {
        StringBuilder text = new StringBuilder();
        text.append(book.courseName()).append(": ")
                .append(book.grades().size()).append(" grades, ")
                .append(book.countPassing()).append(" passing, average ")
                .append(String.format(Locale.ROOT, "%.1f", book.average()))
                .append(System.lineSeparator());
        for (Grade grade : book.grades()) {
            text.append(String.format(Locale.ROOT, "  %-8s %3d  %s%n",
                    grade.student(), grade.score(), book.isPassing(grade) ? "passed" : "failed"));
        }
        return text.toString().stripTrailing();
    }
}
