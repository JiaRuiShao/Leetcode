import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 187. Repeated DNA Sequences.
 * Sliding Window + Rabin-Karp Algorithm
 */
public class _187 {
    /**
     * Brute force solution with HashSet.
     * Notice it's actually sliding window with a fixed window length as L -- 10
     * Time: O(NL) where N is the number of chars in given string, and L is the DNA sequence length 10
     * Space: O(NL)
     */
    class Solution1_HashSet_Brute_Force {
        public List<String> findRepeatedDnaSequences(String s) {
            int dnaSeqLen = 10;
            Set<String> seen = new HashSet<>();
            Set<String> res = new HashSet<>();

            for (int i = 0; i + dnaSeqLen <= s.length(); i++) {
                String str = s.substring(i, i + dnaSeqLen);
                if (seen.contains(str)) {
                    res.add(str);
                } else {
                    seen.add(str);
                }
            }

            return new ArrayList<>(res);
        }
    }

    /**
     * Sliding Window Solution.
     * Time: O(NL) where N is the number of chars in given string, and L is the DNA sequence length 10
     * Space: O(NL)
     */
    class Solution2_Sliding_Window {
        public List<String> findRepeatedDnaSequences(String s) {
            int windowLen = 10;
            Set<String> seen = new HashSet<>();
            Set<String> seqDna = new HashSet<>(); // why we need a Set? to de-duplicate
            // if we don't want to use a Set, we can use a Map for seen and count the freq to only add to res list when freq == 2
            List<String> repeatDnaSeq = new ArrayList<>();
    
            int l  = 0, r = 0;
            while (r < s.length()) {
                r++;
                if (r - l == windowLen) {
                    String winStr = s.substring(l, r);
                    if (seen.contains(winStr)) {
                        seqDna.add(winStr);
                    } else {
                        seen.add(winStr);
                    }
                    l++;
                }
            }
            repeatDnaSeq.addAll(seqDna);
            return repeatDnaSeq;
        }
    }

    /**
     * Sliding Window with window substring converted to base-4 number.
     * Time: average O(N) worst O(NL) when there're too many results
     * Space: O(N + NL)
     */
    class Solution {
        int decimal = 4, numDigits = 10;
    
        public List<String> findRepeatedDnaSequences(String s) {
            int windowLen = numDigits, len = s.length();
            // convert the search String to a num array
            int[] nums = new int[len];
            for (int i = 0; i < len; i++) {
                int num = 0;
                switch (s.charAt(i)){
                    case 'C':
                        num = 1;
                        break;
                    case 'G':
                        num = 2;
                        break;
                    case 'T':
                        num = 3;
                        break;
                    default:
                }
                nums[i] = num;
            }
    
            // sliding window
            int l = 0, r = 0, currWinNum = 0;
            Set<Integer> seen = new HashSet<>();
            Set<String> dnaSeq = new HashSet<>();
            while (r < len) {
                currWinNum = addLast(currWinNum, nums[r]);
                r++;
    
                while (r - l == windowLen) {
                    if (seen.contains(currWinNum)) {
                        dnaSeq.add(s.substring(l, r));
                    } else {
                        seen.add(currWinNum);
                    }
                    currWinNum = removeFirst(currWinNum, nums[l]);
                    l++;
                }
            }
            return new ArrayList<>(dnaSeq);
        }
    
        private int addLast(int num, int digitToAdd) {
            // numDigits++;
            return Math.multiplyExact(decimal, num) + digitToAdd;
        }
    
        private int removeFirst(int num, int digitToRem) {
            int sub = Math.multiplyExact(digitToRem, (int) Math.pow(decimal, numDigits - 1)); // here the pow is fixed b/c this problem is a fixed window length issue; if not fixed, we need to maintain the numDigits
            // numDigits--;
            return num - sub;
        }
    }
}
