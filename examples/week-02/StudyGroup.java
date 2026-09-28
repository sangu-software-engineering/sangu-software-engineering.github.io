/** Example code for the version-control chapter: a small program with a history. */
public class StudyGroup {
    static final int MAX_MEMBERS = 6;

    static int freePlaces(int memberCount) {
        return MAX_MEMBERS - memberCount;
    }

    public static void main(String[] args) {
        String[] members = {"Nino", "Luka", "Ana"};

        System.out.println("Study group: Software Engineering");
        System.out.println("Members: " + members.length);
        System.out.println("Free places: " + freePlaces(members.length));
    }
}
