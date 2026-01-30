import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1200. Minimum Absolute Difference
 */
public class _1200 {
    class Solution1_Sort {
        public List<List<Integer>> minimumAbsDifference(int[] arr) {
            Arrays.sort(arr);
            List<List<Integer>> minDiffPairs = new ArrayList<>();
            int n = arr.length;
            int minDiff = arr[n - 1] - arr[0];
            for (int i = 1; i < n; i++) {
                if (arr[i] - arr[i - 1] > minDiff) {
                    continue;
                }

                if (arr[i] - arr[i - 1] < minDiff) {
                    minDiff = arr[i] - arr[i - 1];
                    minDiffPairs.clear();
                }
                minDiffPairs.add(List.of(arr[i - 1], arr[i]));
            }
            return minDiffPairs;
        }
    }
}
