/** Builds a grade book and prints it in two formats. */
public class GradeReport {

    public static void main(String[] args) {
        GradeBook book = new GradeBook("Software Engineering");
        book.add(new Grade("Nino", 87));
        book.add(new Grade("Luka", 41));
        book.add(new Grade("Ana", 63));
        book.add(new Grade("Giorgi", 50));

        GradeFormat plain = new PlainFormat();
        GradeFormat csv = new CsvFormat();

        System.out.println(plain.format(book));
        System.out.println();
        System.out.println(csv.format(book));

        try {
            book.add(new Grade("Nino", 70));
        } catch (IllegalArgumentException refused) {
            System.out.println();
            System.out.println("Refused: " + refused.getMessage());
        }
    }
}
