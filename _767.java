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
                // if (!reorg.isEmpty() && reorg.charAt(reorg.length() - 1) == cf.c || cf.freq > 1) {
                //     return "";
                // }
                reorg.append(cf.c);
            }
            return reorg.toString();
        }
    }

    // Count frequencies
    // Find most frequent character
    // Place it at even positions (0, 2, 4, ...)
    // Fill remaining characters in remaining positions
    class Solution2_MostFreqFirst {
        public String reorganizeString(String s) {
            int n = s.length();
            int[] count = new int[26];
            int maxCount = 0;
            char maxChar = 'a';
            
            // Count and find max frequency character
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
                if (count[c - 'a'] > maxCount) {
                    maxCount = count[c - 'a'];
                    maxChar = c;
                }
            }
            
            // Check if possible
            if (maxCount > (n + 1) / 2) {
                return "";
            }
            
            char[] result = new char[n];
            int index = 0;
            
            // Place most frequent character at even positions
            while (count[maxChar - 'a'] > 0) {
                result[index] = maxChar;
                index += 2;
                count[maxChar - 'a']--;
            }
            
            // Place remaining characters
            for (int i = 0; i < 26; i++) {
                char ch = (char) ('a' + i);
                while (count[i] > 0) {
                    if (index >= n) {
                        index = 1;  // Switch to odd positions
                    }
                    result[index] = ch;
                    index += 2;
                    count[i]--;
                }
            }
            
            return new String(result);
        }
    }
}
