import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1209. Remove All Adjacent Duplicates in String II
 */
public class _1209 {
    class Solution1_Deque {
        class CharCount {
            char c;
            int count;
            CharCount(char c) {
                this.c = c;
                this.count = 1;
            }
        }

        public String removeDuplicates(String s, int k) {
            int n = s.length();
            Deque<CharCount> deque = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                if (!deque.isEmpty() && deque.peekLast().c == c) {
                    if (++deque.peekLast().count == k) {
                        deque.pollLast();
                    }
                } else {
                    deque.offerLast(new CharCount(c));
                }
            }

            StringBuilder dupRem = new StringBuilder();
            while (!deque.isEmpty()) {
                CharCount cc = deque.pollFirst();
                for (int i = 0; i < cc.count; i++) {
                    dupRem.append(cc.c);
                }
            }
            return dupRem.toString();
        }
    }
}
