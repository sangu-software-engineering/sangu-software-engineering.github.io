public class RoomCapacityDemo {
    // This example checks capacity only, not room availability or permissions.
    static boolean canSeat(int capacity, int attendees) {
        return capacity > 0 && attendees > 0 && attendees <= capacity;
    }

    static int countSuitableRooms(int[] capacities, int attendees) {
        int suitableCount = 0;

        for (int capacity : capacities) {
            if (canSeat(capacity, attendees)) {
                suitableCount++;
            }
        }

        return suitableCount;
    }

    public static void main(String[] args) {
        int[] capacities = {12, 24, 40};
        int attendees = 24;

        for (int capacity : capacities) {
            String decision = "too small or invalid";
            if (canSeat(capacity, attendees)) {
                decision = "suitable";
            }
            System.out.println("Capacity " + capacity + ": " + decision);
        }

        System.out.println("Suitable rooms: "
                + countSuitableRooms(capacities, attendees));
        System.out.println("Zero attendees accepted: " + canSeat(24, 0));
    }
}
