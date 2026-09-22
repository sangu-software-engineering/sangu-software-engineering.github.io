public class CatMood {
    // This method returns the mood
    static String mood(int h, int n, boolean s) {
        String r = "";
        if (h > 5) {
            r = "hangry";
        } else {
            if (s == true) {
                r = "sunbathing";
            } else {
                if (n < 3) {
                    r = "zoomies";
                } else {
                    r = "purring";
                }
            }
        }
        // if (h > 12) { r = "plotting revenge"; }
        return r;
    }

    public static void main(String[] args) {
        System.out.println("Murka: " + mood(6, 4, true));
        System.out.println("Khachapuri: " + mood(5, 0, true));
        System.out.println("Professor Whiskers: " + mood(2, 1, false));
        System.out.println("Tiko: " + mood(1, 3, false));
    }
}
