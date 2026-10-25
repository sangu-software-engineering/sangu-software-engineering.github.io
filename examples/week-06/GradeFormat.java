/** Turns a grade book into text. Each implementation decides the shape. */
public interface GradeFormat {

    String format(GradeBook book);
}
