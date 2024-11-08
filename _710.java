import java.util.*;

/**
 * 710. Random Pick with Blacklist.
 */
public class _710 {

    /**
     * The reason this solution doesn't work is that the saved up backup valid number could be a blacklist number.
     */
    class Solution1_Wrong_Solution {

        private Map<Integer, Integer> blToValidMap;
        private int valid;
        private Random random;

        public Solution1_Wrong_Solution(int n, int[] blacklist) {
            // valid = n - blacklist.length;
            random = new Random();
            valid = n - 1;
            blToValidMap = new HashMap<>();
            for (int invalid : blacklist) {
                blToValidMap.put(invalid, valid--); // here is the problem
            }
        }

        public int pick() {
            int pickup = random.nextInt(valid + 1);
            return blToValidMap.getOrDefault(pickup, pickup);
        }
    }

    /**
     * Swap blacklist elements with the numbers at the end of the imaginary array so that all blacklist numbers are stored in range [n - m, n),
     * where m is the size of the blacklist.
     * Numbers within range [0, n - m) will be picked up for randomly access.
     * Time: O(m)
     * Space: O(m)
     */
    class Solution {
        private Map<Integer, Integer> map;
        private int length;
        private Random random;

        public Solution(int n, int[] blacklist) {
            random = new Random();
            length = n - blacklist.length; // length = n = m, this is the size of the valid numbers
            map = new HashMap<>();

            // mark all blacklist elems
            for (int invalid : blacklist) {
                map.put(invalid, -1);
            }

            // swap the number in blacklist if it's in range [0, length), use map to save the swapped num
            int index = n - 1;
            for (int invalid : blacklist) {
                // this index is a blacklist number, we need to skip it
                while (map.containsKey(index)) index--;
                // do nothing if black number is already in its right place [length, n)
                if (invalid >= length) continue;
                // make sure invalid is in range [0, length) && index points to a valid number
                map.put(invalid, index--);
            }
        }

        public int pick() {
            int pickup = random.nextInt(length);
            return map.getOrDefault(pickup, pickup);
        }
    }

    class Solution1 {
        List<Integer> list;
        Random random;
        public Solution1(int n, int[] blacklist) {
            Set<Integer> whitelist = new HashSet<>();
            list = new ArrayList<>();
            random = new Random();

            for (int i = 0; i < n; i++) {
                whitelist.add(i);
            }
            for (int num : blacklist) {
                whitelist.remove(num);
            }
            for (int num : whitelist) {
                list.add(list.size(), num);
            }
        }

        public int pick() {
            return list.get(random.nextInt(list.size()));
        }
    }
}
