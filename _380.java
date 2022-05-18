import java.util.*;

public class _380 {
    /**
     * Why use HashMap + ArrayList?
     *
     * Array List has indexes and could provide Insert and GetRandom
     * in average constant time, though has problems with Delete.
     * For the delete operation, it has to find the number (which takes linear time) first,
     * and then swap that number with the last element, then delete it.
     *
     * In order to solve that, we will use a HashMap to store the numbers as the keys,
     * and the indices as the values.
     */
    static class RandomizedSet {

        Map<Integer, Integer> map; // store the numbers & the indices
        List<Integer> nums; // store the numbers
        Random random;

        public RandomizedSet() {
            map = new HashMap<>();
            nums = new ArrayList<>();
            random = new Random();
        }

        /**
         * Inserts an item val into the set if not present.
         *
         * @param val the val to be inserted
         * @return true if the item was not present, false otherwise.
         */
        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            map.put(val, nums.size());
            nums.add(nums.size(), val);
            return true;
        }

        private int swap(int idx) {
            int last = nums.get(nums.size() - 1);
            nums.set(idx, last);
            return last;
        }

        /**
         * Removes an item val from the set if present.
         *
         * @param val the val to be removed
         * @return true if the item was present, false otherwise.
         */
        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int idx = map.get(val);
            int last = swap(idx);
            map.put(last, idx);
            nums.remove(nums.size() - 1);
            map.remove(val);
            return true;
        }

        /**
         * Returns a random element from the current set of elements
         * (it's guaranteed that at least one element exists when this method is called).
         *
         * @return a random element from the current set of elements
         */
        public int getRandom() {
            int idx = random.nextInt(nums.size()); // [0, n)
            return nums.get(idx);
        }
    }

    public static void main(String[] args) {
        RandomizedSet sol = new RandomizedSet();
        System.out.println(sol.insert(0));
        System.out.println(sol.getRandom());
        System.out.println(sol.remove(0));
        System.out.println(sol.insert(0));
    }
}
