import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 392. Is Subsequence
 */
public class _392 {
    public boolean isSubsequence(String s, String t) {
        if (t.length() < s.length()) {
            return false;
        }

        int ps = 0, pt = 0;
        while (ps < s.length() && pt < t.length()) {
            if (s.charAt(ps) == t.charAt(pt)) ps++;
            pt++;
        }
        return ps == s.length();
    }

    public boolean[] isSubsequenceFollowUp(String[] strs, String t) {
        int n = strs.length;
        boolean[] isSubsequence = new boolean[n];
        int[] pos = new int[n];
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            for (int j = 0; j < n; j++) {
                String str = strs[j];
                if (pos[j] < str.length() && str.charAt(pos[j]) == c) {
                    pos[j]++;
                }
            }
        }
        for (int j = 0; j < n; j++) {
            if (pos[j] == strs[j].length()) isSubsequence[j] = true;
        }
        return isSubsequence;
    }

    public boolean[] isSubsequenceFollowUp_Binary_Search(String[] strs, String t) {
        Map<Character, List<Integer>> indexMap = new HashMap<>();

        // Step 1: Preprocess `t`
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            indexMap.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }

        boolean[] result = new boolean[strs.length];

        // Step 2: Check each string in `strs`
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            int prevIdx = -1; // Last matched index in `t`
            boolean isSubseq = true;

            for (char c : s.toCharArray()) {
                if (!indexMap.containsKey(c)) {
                    isSubseq = false;
                    break;
                }

                List<Integer> indices = indexMap.get(c);
                int nextIdx = findNextIndex(indices, prevIdx);

                if (nextIdx == -1) {
                    isSubseq = false;
                    break;
                }

                prevIdx = nextIdx;
            }

            result[i] = isSubseq;
        }

        return result;
    }

    // Binary search: find smallest index in list > prevIdx
    private int findNextIndex(List<Integer> indices, int prevIdx) {
        int left = 0, right = indices.size() - 1, ans = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (indices.get(mid) > prevIdx) {
                ans = indices.get(mid);
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
