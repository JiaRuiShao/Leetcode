/**
 * 273. Integer to English Words
 * 1234567 -> One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven
 */
public class _273 {
    class Solution1 {
        private final String[] belowTwenty = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
                "Nineteen" };
        private final String[] tens = { "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
                "Ninety" };

        public String numberToWords(int num) {
            if (num == 0)
                return "Zero";
            return helper(num);
        }

        private String helper(int num) {
            StringBuilder result = new StringBuilder();
            if (num < 20) {
                result.append(belowTwenty[num]);
            } else if (num < 100) {
                result.append(tens[num / 10]).append(" ").append(belowTwenty[num % 10]);
            } else if (num < 1000) {
                result.append(helper(num / 100)).append(" Hundred ").append(helper(num % 100));
            } else if (num < 1000000) {
                result.append(helper(num / 1000)).append(" Thousand ").append(helper(num % 1000));
            } else if (num < 1000000000) {
                result.append(helper(num / 1000000)).append(" Million ").append(helper(num % 1000000));
            } else {
                result.append(helper(num / 1000000000)).append(" Billion ").append(helper(num % 1000000000));
            }
            return result.toString().trim();
        }
    }

    class Solution2 {
        private static final String[] BELOW_20 = {
            "", "One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen",
            "Seventeen", "Eighteen", "Nineteen"
        };

        private static final String[] TENS = {
            "", "", "Twenty", "Thirty", "Forty", "Fifty",
            "Sixty", "Seventy", "Eighty", "Ninety"
        };

        private static final String[] THOUSANDS = {
            "", "Thousand", "Million", "Billion"
        };

        public String numberToWords(int num) {
            if (num == 0) return "Zero";

            StringBuilder res = new StringBuilder();
            int idx = 0;

            while (num > 0) {
                int chunk = num % 1000;
                if (chunk != 0) {
                    String part = helper(chunk);
                    res.insert(0, part + THOUSANDS[idx] + " ");
                }
                num /= 1000;
                idx++;
            }

            return res.toString().trim();
        }

        private String helper(int num) {
            if (num == 0) return "";
            if (num < 20) return BELOW_20[num] + " ";
            if (num < 100) {
                return TENS[num / 10] + " " + helper(num % 10);
            }
            return BELOW_20[num / 100] + " Hundred " + helper(num % 100);
        }
    }
}
