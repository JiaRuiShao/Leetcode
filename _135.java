import java.util.Arrays;

/**
 * 135. Candy
 */
public class _135 {
    class Solution1_Greedy_Two_Arrays {
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] left = new int[n];
            int[] right = new int[n];
            Arrays.fill(left, 1);
            Arrays.fill(right, 1);
    
            for (int i = 0; i < n - 1; i++) {
                if (ratings[i] < ratings[i + 1]) {
                    left[i + 1] = left[i] + 1;
                }
            }
    
            for (int i = n - 1; i > 0; i--) {
                if (ratings[i] < ratings[i - 1]) {
                    right[i - 1] = right[i] + 1;
                }
            }
            int total = 0;
            for (int i = 0; i < n; i++) {
                total += Math.max(left[i], right[i]);
            }
            return total;
        }
    }

    class Solution2_Greedy_One_Array {
        // [1,3,2,2,1] 7
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] candies = new int[n];
            Arrays.fill(candies, 1);
            
            for (int i = 0; i < n - 1; i++) {
                if (ratings[i] < ratings[i + 1]) {
                    candies[i + 1] = ratings[i] + 1;
                }
            }

            for (int i = n - 1; i > 0; i--) {
                if (ratings[i] < ratings[i - 1]) {
                    candies[i - 1] = Math.max(candies[i - 1], candies[i] + 1);
                }
            }

            int total = 0;
            for (int candy : candies) {
                total += candy;
            }
            return total;
        }
    }

    class Solution3_Math {
        public int candy(int[] ratings) {
            if (ratings == null || ratings.length == 0) return 0;
            int total = 1, prev = 1, decreasing = 0; // prev here is the prev candy in increasing slope; decreasing is the count of elems in decreasing slope
            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i] >= ratings[i-1]) { // increasing slope
                    if (decreasing > 0) { // calc decreasing slope candies when decreasing slope ends
                        total += calcDownSlope(decreasing, prev);
                        // reset decreasing & prev
                        decreasing = 0;
                        prev = 1;
                    }
                    int curr = ratings[i] == ratings[i-1] ? 1 : prev + 1; // if increasing slope, curr = prev + 1; else keep as 1
                    total += curr;
                    prev = curr;
                } else decreasing++;
            }
            // if we were descending at the end
            total += calcDownSlope(decreasing, prev);
            return total;
        }

        private int calcDownSlope(int dec, int lastPeak) {
            if (dec <= 0) return 0; 
            int candies = (1 + dec) * dec / 2; // AP sum
            // if peak of decreasing >= peak of increasing, we need to update the candy for local peak by (decreasing peak - increasing peak + 1)
            if (dec >= lastPeak) {
                candies += dec - lastPeak + 1;
            }
            return candies;
        }
    }
}
