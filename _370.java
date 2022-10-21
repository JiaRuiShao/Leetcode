/**
 * 370. Range Addition.
 */
public class _370 {
    class Solution1_Differential_Array {
        public int[] getModifiedArray(int length, int[][] updates) {
            int[] diff = new int[length];
            for (int[] update : updates) {
                updateDiff(diff, update[0], update[1], update[2]);
            }
            return updatedArr(diff);
        }
        
        private void updateDiff(int[] diff, int start, int end, int val) {
            diff[start] += val;
            if (end + 1 < diff.length) {
                diff[end + 1] -= val;
            }
        }
        
        private int[] updatedArr(int[] diff) {
            int[] arr = new int[diff.length];
            arr[0] = diff[0];
            for (int i = 1; i < diff.length; i++) {
                arr[i] = arr[i - 1] + diff[i];
            }
            return arr;
        }
    }

    class Solution2_Brute_Force {
        public int[] getModifiedArray(int length, int[][] updates) {
            int[] result = new int[length];
            for (int[] update : updates) {
                for (int i = update[0]; i <= update[1]; i++) {
                    result[i] += update[2];
                }
            }
            return result;
        }
    }
}
