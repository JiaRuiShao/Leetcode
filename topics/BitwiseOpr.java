package topics;

public class BitwiseOpr {
    public static void main(String[] args) {
        System.out.println(
                "-----------------------------------------------------------------------------------------");
        System.out.println(
                "i << n is two to the power of n");
        System.out.println("1 << 2: " + (1 << 2));
        System.out.println("1 << 3: " + (1 << 3));
        System.out.println("1 << 4: " + (1 << 4));
        System.out.println("1 << 5: " + (1 << 5));
        System.out.println("1 << 6: " + (1 << 6));
        System.out.println(
                "-----------------------------------------------------------------------------------------");
        System.out.println(
                "i >> 1 is to divide i by two");
        System.out.println("0 >> 1: " + (0 >> 1));
        System.out.println("1 >> 1: " + (1 >> 1));
        System.out.println("2 >> 1: " + (2 >> 1));
        System.out.println("3 >> 1: " + (3 >> 1));
        System.out.println("4 >> 1: " + (4 >> 1));
        System.out.println("5 >> 1: " + (5 >> 1));
        System.out.println("6 >> 1: " + (6 >> 1));
    }
}
