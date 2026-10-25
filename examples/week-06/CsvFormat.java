/** The same data for a spreadsheet. */
public class CsvFormat implements GradeFormat {

    @Override
    public String format(GradeBook book) {
        StringBuilder text = new StringBuilder("course,student,score,passing");
        for (Grade grade : book.grades()) {
            text.append(System.lineSeparator())
                    .append(book.courseName()).append(',')
                    .append(grade.student()).append(',')
                    .append(grade.score()).append(',')
                    .append(book.isPassing(grade));
        }
        return text.toString();
    }
}
