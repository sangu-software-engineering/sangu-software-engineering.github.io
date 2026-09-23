public class ShippingCost {
    // this method calculates the cost
    static int cost(int w, boolean e, boolean m) {
        int c;
        if (w <= 1000) {
            c = 5;
        } else {
            if (w <= 5000) {
                c = 10;
            } else {
                c = 20;
            }
        }
        if (e == true) {
            c = c * 2;
        }
        // if (m) { c = c - 1; }
        return c;
    }

    public static void main(String[] args) {
        System.out.println("500 g, standard: " + cost(500, false, false));
        System.out.println("500 g, express: " + cost(500, true, false));
        System.out.println("3000 g, standard: " + cost(3000, false, false));
        System.out.println("9000 g, express: " + cost(9000, true, false));
    }
}
