public class Dessert {
    int flavor;
    int price;
    static int numDesserts = 0;

    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        numDesserts++;
    }

    public void printDessert() {
        String S = String.format("%d %d %d",this.flavor, this.price, numDesserts);
        System.out.println(S);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}
