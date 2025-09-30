import java.util.*;

/**
 * 519. Random Flip Matrix
 */
public class _519 {
    /**
     * This key of this solution is to have a map to store {key: flipped index, value: last available flippable index for given size}.
     * It guarantees evenly probability to be flipped for flippable indices and O(1) time complexity for each flip().
     */
    class Solution1 {
        private int row;
        private int col;
        private int flippable;
        private Map<Integer, Integer> flipped; // flipped indices map
        private Random random;

        public Solution1(int m, int n) {
            row = m;
            col = n;
            flippable = m * n;
            flipped = new HashMap<>();
            random = new Random();
        }

        /**
         * Randomly select an index, swap this index with the last available index that hasn't been flipped if this index has been flipped.
         * Save the last available flippable index as an extra life for this index, for next round of selection, the search range would be: [0, size - 1].
         * Notice that the size index is saved as an "extra life" for the picked up index;
         * If this selected index is the last flippable index, then there's no extra life.
         *
         * @return mapping to a 2D matrix
         */
        public int[] flip() {
            int idx = random.nextInt(flippable--);
            // if this index hasn't been flipped, flipped it and set the last flippable index as its backup index
            // if this index has been flipped, the prev backup index will be returned and flipped, new backup index(current size - 1) will be set for this index
            int flipIdx = flipped.getOrDefault(idx, idx);
            // update the backup index
            flipped.put(idx, flipped.getOrDefault(flippable, flippable));
            return new int[]{flipIdx / col, flipIdx % col};
        }

        public void reset() {
            flippable = row * col;
            flipped.clear();
        }
    }

    /**
     * Use array to store the swapped indices.
     * Got Memory limit exceeded error due to large m & n.
     * This solution is useful when m & n are small and flip # is large.
     * Compared to this solution using array, HashMap solution takes less memory when m & n are large or flip # is small.
     */
    class Solution2 {
        int[] indexMap;
        int rows;
        int cols;
        int remainingCells;
        Random random;

        public Solution2(int m, int n) {
            this.rows = m;
            this.cols = n;
            remainingCells = rows * cols;
            indexMap = new int[remainingCells];
            Arrays.fill(indexMap, -1); // Initialize with -1
            random = new Random();
        }

        public int[] flip() {
            int randIndex = random.nextInt(remainingCells);
            remainingCells--;

            // Get the actual index to flip
            int flipIndex = indexMap[randIndex] != -1 ? indexMap[randIndex] : randIndex;
            // Map randIndex to the value at remainingCells
            indexMap[randIndex] = indexMap[remainingCells] != -1 ? indexMap[remainingCells] : remainingCells;

            return new int[]{ flipIndex / cols, flipIndex % cols };
        }

        public void reset() {
            Arrays.fill(indexMap, -1); // Reset all mappings
            remainingCells = rows * cols;
        }
    }

    /**
     * This solution also guarantee universe random access for all flippable indices; however, flip() time complexity is not O(1);
     * in worst case, the flippable indices will never be picked up, and the while loop will never be terminated unless the flippable indices are luckily selected.
     */
    class Solution3 {

        private int m;
        private int n;
        private Set<Integer> flipped;
        private Random random;

        public Solution3(int m, int n) {
            this.m = m;
            this.n = n;
            this.random = new Random();
            this.flipped = new HashSet<>();
        }

        public int[] flip() {
            int i = random.nextInt(m);
            int j = random.nextInt(n);
            // randomly select 2D indices and map to a 1D array: [i, j] -> i * m + j
            // if already selected, choose another indices
            // this DOES NOT guarantee O(1) time
            while (!flipped.add(i * n + j)) {
                i = random.nextInt(m);
                j = random.nextInt(n);
            }
            return new int[] {i, j};
        }

        public void reset() {
            this.flipped = new HashSet<>();
        }
    }
}
