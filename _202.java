import java.util.HashSet;
import java.util.Set;

/**
 * 202. Happy Number
 */
public class _202 {
    // use a hash set to prevent cycle
    class Solution1_Set {
        public boolean isHappy(int n) {
            if (n == 1) {
                return true;
            }
    
            Set<Integer> seen = new HashSet<>();
            int curr = n;
            while (!seen.contains(curr)) {
                seen.add(curr);
                curr = getNext(curr);
                if (curr == 1) return true;
            }
            return false;
        }
    
        private int getNext(int num) {
            int next = 0;
            while (num != 0) {
                int digit = num % 10;
                next += digit * digit;
                num /= 10;
            }
            return next;
        }
    }

    class Solution2_Fast_Slow_Pointers {
        public boolean isHappy(int n) {
            int slow = n, fast = n;
            do {
                slow = getNext(slow);
                fast = getNext(fast);
                fast = getNext(fast);
            } while (slow != fast);
            return fast == 1;
        }
    
        private int getNext(int num) {
            int next = 0;
            while (num != 0) {
                int digit = num % 10;
                next += digit * digit;
                num /= 10;
            }
            return next;
        }
    }
}
