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
            int windowLen = 10;
            Set<String> seen = new HashSet<>();
            Set<String> seqDna = new HashSet<>(); // why we need a Set? to de-duplicate
            // if we don't want to use a Set, we can use a Map for seen and count the freq
            // to only add to res list when freq == 2
            List<String> repeatDnaSeq = new ArrayList<>();

            int l = 0, r = 0;
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
     * Sliding Window with the window substring converted to base-10 number.
     * Time: average O(N) worst O(NL) when there're too many results
     * Space: O(N + NL)
     */
    class Solution3_Sliding_Hash_Technique_Base10 {
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
     * Time: average O(N) worst O(NL) when there're too many results
     * Space: O(N + NL)
     */
    class Solution4_Sliding_Hash_Technique_Base4 {
        private static final int BASE = 4;
        private static final int DNA_LENGTH = 10;

        public List<String> findRepeatedDnaSequences(String s) {
            int[] hash = new int[s.length()];

            for (int i = 0; i < s.length(); i++) {
                hash[i] = getHashNum(s.charAt(i));
            }

            Set<Integer> uniqueDNAs = new HashSet<>();
            Set<String> repeatedDNAs = new HashSet<>();
            int left = 0, right = 0, hashing = 0; // hash val for the sliding window
            // window: [left, right)
            while (right < hash.length) {
                hashing = addToRight(hashing, hash[right++]); // hashing val update -- add num from the right
                if (right - left == DNA_LENGTH) {
                    if (uniqueDNAs.contains(hashing)) {
                        repeatedDNAs.add(s.substring(left, right));
                    } else {
                        uniqueDNAs.add(hashing);
                    }
                    hashing = subtractFromLeft(hashing, hash[left++]); // hashing val update -- remove num from the left
                }
            }

            return new ArrayList<>(repeatedDNAs);
        }

        private int subtractFromLeft(int base, int rem) {
            return base - rem * (int) Math.pow(BASE, DNA_LENGTH - 1);
        }

        private int addToRight(int base, int add) {
            return base * BASE + add;
        }

        private int getHashNum(char c) {
            int hashedNum = -1;
            switch (c) {
                case 'A':
                    hashedNum = 0;
                    break;
                case 'C':
                    hashedNum = 1;
                    break;
                case 'G':
                    hashedNum = 2;
                    break;
                case 'T':
                    hashedNum = 3;
                    break;
                default:
            }
            return hashedNum;
        }
    }
}
