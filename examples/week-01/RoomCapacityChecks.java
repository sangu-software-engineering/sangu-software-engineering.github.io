public class RoomCapacityChecks {
    // Checks the real rule in RoomCapacityDemo rather than a copy,
    // so a change to the rule is always checked.
    static void check(int capacity, int attendees, boolean expected) {
        boolean actual = RoomCapacityDemo.canSeat(capacity, attendees);
        String status = "FAIL";
        if (actual == expected) {
            status = "PASS";
        }
        System.out.println(status + ": canSeat(" + capacity + ", " + attendees
                + ") returned " + actual + ", expected " + expected);
    }

    public static void main(String[] args) {
        check(24, 18, true);
        check(24, 24, true);
        // Add the remaining rows of the expected-results table here.
    }
}
