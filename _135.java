import java.util.Arrays;

/**
 * 135. Candy
 * 
 * Clarification:
 * - If ratings[i] > ratings[i-1] but < ratings[i+1], do we still need to give i more candy than i-1? Yes
 * - If all children have the same rating, do we give them one candy? Yes all get one candy
 */
public class _135 {
    class Solution1_GreedyTwoPass {
        // [1,3,2,2,1] 7
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] candy = new int[n];
            Arrays.fill(candy, 1);
            for (int i = 1; i < n; i++) { // compare to the left
                if (ratings[i] > ratings[i-1]) {
                    candy[i] = candy[i-1] + 1;
                }
            }

            for (int i = n - 2; i >= 0; i--) { // compare to the right
                if (ratings[i] > ratings[i+1]) {
                    // take max to preserve left-to-right constraint
                    candy[i] = Math.max(candy[i], candy[i+1] + 1);
                }
            }
            int total = 0;
            for (int c : candy) {
                total += c;
            }
            return total;
        }
    }

    class Solution2_Math {
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

    class Solution2_PeakValleyDetection {
        public int candy(int[] ratings) {
            int n = ratings.length, total = 1;
            int up = 0, down = 0, peak = 0;
            
            for (int i = 1; i < n; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    // Going up
                    up++;
                    down = 0;
                    peak = up;
                    total += 1 + up;
                } else if (ratings[i] == ratings[i - 1]) {
                    // Flat - reset
                    up = 0;
                    down = 0;
                    peak = 0;
                    total += 1;
                } else {
                    // Going down
                    up = 0;
                    down++;
                    total += 1 + down + (peak >= down ? -1 : 0);
                }
            }
            
            return total;
        }
    }
}
