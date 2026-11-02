/** Walks one submission through its states, and shows two refusals. */
public class SubmissionDemo {

    public static void main(String[] args) {
        Submission nino = new Submission("Nino");
        System.out.println(nino.student() + ": " + nino.state());

        nino.submit();
        System.out.println(nino.student() + ": " + nino.state());

        nino.grade(87);
        System.out.println(nino.student() + ": " + nino.state() + " with " + nino.score());

        Submission luka = new Submission("Luka");
        luka.withdraw();
        System.out.println(luka.student() + ": " + luka.state());

        Submission ana = new Submission("Ana");
        try {
            ana.grade(70);
        } catch (IllegalStateException refused) {
            System.out.println("Refused: " + refused.getMessage());
        }

        Submission giorgi = new Submission("Giorgi");
        giorgi.submit();
        try {
            giorgi.grade(101);
        } catch (IllegalArgumentException refused) {
            System.out.println("Refused: " + refused.getMessage());
        }
    }
}
