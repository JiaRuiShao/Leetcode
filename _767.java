import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 767. Reorganize String
 */
public class _767 {
    // Time: O(n)
    // Space: O(1)
    class Solution1_PriorityQueue {
        class CharFreq {
            char c;
            int freq;
            public CharFreq(char c, int freq) {
                this.c = c;
                this.freq = freq;
            }
        }

        public String reorganizeString(String s) {
            int n = s.length();
            int maxFreq = 0;
            int[] freq = new int[26];
            for (int i = 0; i < n; i++) {
                maxFreq = Math.max(maxFreq, ++freq[s.charAt(i) - 'a']);
            }
            if (maxFreq > n - maxFreq + 1) {
                return "";
            }

            Queue<CharFreq> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b.freq, a.freq));
            for (int i = 0; i < 26; i++) {
                if (freq[i] > 0) {
                    maxHeap.offer(new CharFreq((char) (i + 'a'), freq[i]));
                }
            }

            StringBuilder reorg = new StringBuilder();
            while (maxHeap.size() >= 2) {
                CharFreq cf1 = maxHeap.poll();
                CharFreq cf2 = maxHeap.poll();
                reorg.append(cf1.c);
                reorg.append(cf2.c);
                if (--cf1.freq > 0) {
                    maxHeap.offer(cf1);
                }
                if (--cf2.freq > 0) {
                    maxHeap.offer(cf2);
                }
            }
            if (!maxHeap.isEmpty()) {
                CharFreq cf = maxHeap.poll();
                if (!reorg.isEmpty() && reorg.charAt(reorg.length() - 1) == cf.c || cf.freq > 1) {
                    return "";
                }
                reorg.append(cf.c);
            }
            return reorg.toString();
        }
    }
}
