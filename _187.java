import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 187. Repeated DNA Sequences
 */
public class _187 {
    /**
     * Brute force solution with HashSet.
     * Sliding window with a fixed window length as L -- 10
     * Time: O(NL) where N is the number of chars in given string, and L is the DNA
     * sequence length 10
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
     * Time: O(NL) where N is the number of chars in given string, and L is the DNA
     * sequence length 10
     * Space: O(NL)
     */
    class Solution2_Sliding_Window {
        public List<String> findRepeatedDnaSequences(String s) {
            Map<String, Integer> dnaSeqToFreq = new HashMap<>();
            List<String> repeatedDnaSeq = new ArrayList<>();
            int left = 0, right = 0, winLen = 10;
            while (right < s.length()) {
                right++;
                if (right - left == winLen) {
                    String winStr = s.substring(left, right);
                    dnaSeqToFreq.put(winStr, dnaSeqToFreq.getOrDefault(winStr, 0) + 1);
                    if (dnaSeqToFreq.get(winStr) == 2) repeatedDnaSeq.add(winStr);
                    left++;
                }
            }
            return repeatedDnaSeq;
        }
    }

    /**
     * Sliding Window with the window substring converted to base-10 number.
     * Time: average O(N) worst O(NL) when there are too many results
     * Space: O(N + NL)
     */
    class Solution3_RabinKarp_Base10 {
        int decimal = 10, numDigits = 10;

        public List<String> findRepeatedDnaSequences(String s) {
            int windowLen = 10, len = s.length();
            // convert the search String to a num array
            int[] nums = new int[len];
            for (int i = 0; i < len; i++) {
                int num = 0;
                switch (s.charAt(i)) {
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
            int l = 0, r = 0;
            long currWinNum = 0;
            Set<Long> seen = new HashSet<>();
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

        private long addLast(long num, int digitToAdd) {
            return Math.multiplyExact(decimal, num) + (long) digitToAdd;
        }

        private long removeFirst(long num, int digitToRem) {
            long sub = Math.multiplyExact((long) digitToRem, (long) Math.pow(decimal, numDigits - 1));
            return num - sub;
        }
    }

    /**
     * This solution is a optimized version of previous solution with just base-4
     * system.
     * Notice here max num is maxCharNum * BASE^windowLen which is 3*4^10 = 3*2^20 < Integer.MAX_VALUE = 2^31 - 1
     * Time: average O(N) worst O(NL) when there're too many results
     * Space: O(N + NL)
     */
    class Solution3_RabinKarp_Base4 {
        public List<String> findRepeatedDnaSequences(String s) {
            Map<Integer, Integer> dnaToFreq = new HashMap<>();
            List<String> repeated = new ArrayList<>();
            int base = 4, winLen = 10, basePowLenMinusOne = (int) Math.pow(base, winLen - 1);
            int left = 0, right = 0, windowHash = 0;
            while (right < s.length()) {
                int addToRight = decode(s.charAt(right++));
                windowHash = base * windowHash + addToRight;
                if (right - left == winLen) {
                    dnaToFreq.put(windowHash, dnaToFreq.getOrDefault(windowHash, 0) + 1);
                    if (dnaToFreq.get(windowHash) == 2) repeated.add(s.substring(left, right));
                    int remFromLeft = decode(s.charAt(left++));
                    windowHash -= remFromLeft * basePowLenMinusOne;
                }
            }
            return repeated;
        }
    
        private int decode(char c) {
            switch (c) {
                case 'A':
                    return 0;
                case 'C':
                    return 1;
                case 'G':
                    return 2;
                default:
                    return 3;
            }
        }
    }

    class Solution4_BitManipulation {
        // we could use int bit mask here since window bit len is 2 * 10 = 20, which is < 31
        // Time: O(n)
        // Space: O(n)
        public List<String> findRepeatedDnaSequences(String s) {
            Set<String> repeated = new HashSet<>();
            Set<Integer> hashSet = new HashSet<>();
            Map<Character, Integer> charToBit = Map.of('A', 0, 'C', 1, 'G', 2, 'T', 3);
            int mask = (1 << 20) - 1; // keep rightmost 20 bits only
            int left = 0, right = 0, windowLen = 10, windowHash = 0;
            while (right < s.length()) {
                char add = s.charAt(right++);
                windowHash = ((windowHash << 2) | charToBit.get(add)) & mask;
                if (right - left == windowLen) {
                    if (hashSet.contains(windowHash)) {
                        repeated.add(s.substring(left, right));
                    } else {
                        hashSet.add(windowHash);
                    }
                    left++;
                }
            }
            return new ArrayList<>(repeated);
        }
    }
}
