public class Test {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5 };
        int[] b = { 1, 2, 3, 4, 5 };

        if (a == b) {
            System.out.println("a == b is true");
        } else {
            System.out.println("a == b is false");
        }
        
        if (a.equals(b)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

    }
}
