import java.util.*;

public class _710 {
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

    class Solution {
        int length;
        HashMap<Integer, Integer> map; // used to store the blacklist num & the swapped number
        Random random;

        public Solution(int n, int[] blacklist) {
            random = new Random();
            // 因为黑名单都在n中，所以，随机数在[0,length)中取
            length = n - blacklist.length;
            map = new HashMap<>();

            // 标记黑名单
            for (int b : blacklist) {
                map.put(b, -1);
            }

            int index = length;

            // swap the number in blacklist if in range [length, n), use map to save the num
            for (int black : blacklist) {
                // do nothing if black number is not in range [0,length)
                if (black >= 0 && black < length) {
                    while (index < n) { // clean numbers in range [length, n)
                        if (!map.containsKey(index)) { // fastest way to check
                            map.put(black, index);
                            index++;
                            break;
                        } else {
                            index++;
                        }
                    }
                }
            }
        }

        public int pick() {
            int i = random.nextInt(length);
            if (map.containsKey(i)) { // if the num chose is in blacklist, use the swapped clean number saved in the value
                return map.get(i);
            }
            return i;
        }
    }

}
