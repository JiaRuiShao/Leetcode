import java.util.ArrayList;
import java.util.List;

/**
 * 3507. Minimum Pair Removal to Sort Array I
 */
public class _3507 {
    class Solution1 {
        // newSum may < prevNum, need to check from prev pos
        public int minimumPairRemoval(int[] nums) {
            List<Integer> list = new ArrayList<>();
            for (int num : nums) {
                list.add(num);
            }

            int removal = 0;
            for (int i = 0; i < list.size() - 1; ) {
                if (list.get(i) > list.get(i + 1)) {
                    removePair(list);
                    removal++;
                    i = 0;
                } else {
                    i++;
                }
            }
            return removal;
        }
        
        private void removePair(List<Integer> list) {
            int minSum = Integer.MAX_VALUE, idx = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) + list.get(i + 1) < minSum) {
                    minSum = list.get(i) + list.get(i + 1);
                    idx = i;
                }
            }
            list.set(idx, minSum);
            list.remove(idx + 1);
        }
    }
}
