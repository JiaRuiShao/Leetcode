import java.util.ArrayList;
import java.util.List;

/**
 * 229. Majority Element II
 */
public class _229 {
    class Solution1_Boyer_Moore {
        public List<Integer> majorityElement(int[] nums) {
            int candidate1 = -1, candidate2 = 1, count1 = 0, count2 = 0;
            for (int num : nums) {
                if (num == candidate1) {
                    count1++;
                } else if (num == candidate2) {
                    count2++;
                } else if (count1 == 0) {
                    candidate1 = num;
                    count1 = 1;
                } else if (count2 == 0) {
                    candidate2 = num;
                    count2 = 1;
                } else {
                    count1--;
                    count2--;
                }
            }
            int threshold = nums.length / 3;
            count1 = 0;
            count2 = 0;
            for (int num : nums) {
                if (num == candidate1) count1++;
                else if (num == candidate2) count2++;
            }
            List<Integer> res = new ArrayList<>();
            if (count1 > threshold) res.add(candidate1);
            if (count2 > threshold) res.add(candidate2);
            return res;
        }
    }
}
