/** Plans how many whole pizzas a party needs. It knows about slices only. */
public class PizzaPartyPlanner {
    static final int SLICES_PER_PIZZA = 8;

    static int sumSlices(int[] appetites) {
        int totalSlices = 0;
        for (int slices : appetites) {
            totalSlices += slices;
        }
        return totalSlices;
    }

    static int countPizzasNeeded(int slicesWanted) {
        if (slicesWanted < 0) {
            throw new IllegalArgumentException("Slices cannot be negative: " + slicesWanted);
        }
        int pizzas = slicesWanted / SLICES_PER_PIZZA;
        if (slicesWanted % SLICES_PER_PIZZA != 0) {
            pizzas++; // the pizzeria sells whole pizzas only
        }
        return pizzas;
    }

    static int countLeftoverSlices(int slicesWanted) {
        return countPizzasNeeded(slicesWanted) * SLICES_PER_PIZZA - slicesWanted;
    }

    public static void main(String[] args) {
        int[] appetites = {3, 2, 5, 1}; // slices per guest
        int slicesWanted = sumSlices(appetites);

        System.out.println("Guests: " + appetites.length);
        System.out.println("Slices wanted: " + slicesWanted);
        System.out.println("Pizzas to order: " + countPizzasNeeded(slicesWanted));
        System.out.println("Leftover slices: " + countLeftoverSlices(slicesWanted));
        System.out.println("Pizzas for an empty party: " + countPizzasNeeded(0));
    }
}
