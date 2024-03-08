package topics;

public class NumeralSystem {
    private static class QuaternaryNumeralSystem {

        private static final int BASE = 4;
        long number = 0;
        int numberLen = 0;

        void addLast(int digit) {
            numberLen++;
            number = number * (long) BASE + (long) digit;
        }

        void removeFirst(int digit) {
            numberLen--;
            long numberToDeduct = digit * (long) Math.pow(BASE, numberLen);
            number -= numberToDeduct;
        }

        public long getNumber() {
            return number;
        }

    }

    private static class DecimalNumeralSystem {

        private static final int BASE = 10;
        long number = 0;
        int numberLen = 0;

        void addLast(int digit) {
            numberLen++;
            number = number * (long) BASE + digit;
        }

        void removeFirst(int digit) {
            numberLen--;
            long numberToDeduct = digit * (long) Math.pow(BASE, numberLen);
            number -= numberToDeduct;
        }

        public long getNumber() {
            return number;
        }

    }

    public static void main(String[] args) {
        QuaternaryNumeralSystem base4System = new QuaternaryNumeralSystem();
        DecimalNumeralSystem base10System = new DecimalNumeralSystem();

        // For Base-4 system, the results are [0, 1, 6, 27]
        // 0 = 0 * 4^0
        // 1 = 0 * 4^1 + 1 * 4^0
        // 6 = 0 * 4^2 + 1 * 4^1 + 2 * 4^0
        // 27 = 0 * 4^3 + 1 * 4^2 + 2 * 4^1 + 3 * 4^0
        long num = 0;
        
        int[] digitsToAdd = new int[]{0, 1, 2, 3};
        for (int digit : digitsToAdd) {
            base4System.addLast(digit);
            num = base4System.getNumber();
            System.out.printf("Base4-System: %d added, number now is %d%n", digit, num);
            base10System.addLast(digit);
            num = base10System.getNumber();
            System.out.printf("Base10-System: %d added, number now is %d%n", digit, num);
        }

        // For Base-4 system, the results are [27, 11, 3, 0]
        // 27 = 1 * 4^2 + 2 * 4^1 + 3 * 4^0
        // 11 = 2 * 4^1 + 3 * 4^0
        // 3 = 3 * 4^0
        // 0 = 0 * 4^0
        int[] digitsToRem = new int[]{0, 1, 2, 3};
        for (int digit : digitsToRem) {
            base4System.removeFirst(digit);
            num = base4System.getNumber();
            System.out.printf("Base4-System: %d removed from first position, number now is %d%n", digit, num);
            base10System.removeFirst(digit);
            num = base10System.getNumber();
            System.out.printf("Base10-System: %d removed from first position, number now is %d%n", digit, num);
        }
    }
}
