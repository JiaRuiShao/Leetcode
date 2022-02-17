public class _370 {
    class Solution1 {
        /**
         * Use difference array to return the arr with length after applying the updates.
         *
         * @param length result array length
         * @param updates updates[i] = [startIdx, endIdx, increaseVal]
         * @return the result array
         */
        public int[] getModifiedArray(int length, int[][] updates) {
            int[] arr = new int[length];
            if (updates == null || updates.length == 0) return arr;
            // build the difference array
            int[] diff = new int[length];
            for (int[] update : updates) {
                int i = update[0], j = update[1], val = update[2];
                diff[i] += val;
                if (j + 1 < length) diff[j + 1] -= val;
            }
            // build the result array based on the difference array
            arr[0] = diff[0];
            for (int i = 1; i < length; i++) {
                arr[i] = arr[i - 1] + diff[i];
            }
            return arr;
        }
    }

    public static class Solution2 {
        /**
         * The brute force way to update the array.
         *
         * @param length result array length
         * @param updates updates[i] = [startIdx, endIdx, increaseVal]
         * @return the result array
         */
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
