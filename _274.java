import java.util.Arrays;

/**
 * 274. H-Index
 * Transform the question goal: find max(x) such that at least x number of elems >= x
 */
public class _274 {
    class Solution1_Sorting {
        // [100, 100] -> 2
        // [0, 0, 2] -> 1
        // Sort + linear scan
        public int hIndex(int[] citations) {
            int n = citations.length, hIndex = 0;
            Arrays.sort(citations);
            for (int i = 0; i < n; i++) {
                int papers = n - i;
                if (citations[i] >= papers) {
                    hIndex = Math.max(hIndex, papers);
                }
            }
            return hIndex;
        }
    }

    class Solution2_Bucket_Sort_Suffix_Sum {
        // Counting sort (bucket sort)
        public int hIndex(int[] citations) {
            int n = citations.length;
            int[] papers = new int[n + 1]; // papers[i] is paper count for citation i
            for (int citation : citations) {
                if (citation >= n) {
                    papers[n]++;
                } else {
                    papers[citation]++;
                }
            }
    
            int paperCount = 0;
            for (int i = n; i >= 0; i--) { // index i is the citation
                paperCount += papers[i];
                if (paperCount >= i) return i;
            }
            return 0;
        }
    }

    class Solution3_Brute_Force {
        // Time: O(nmin(max(nums), n)) = O(n^2)
        public int hIndex(int[] citations) {
            int n = citations.length;
            for (int h = n; h >= 0; h--) {
                int count = 0;
                // for each h, count how many papers have at least h citations
                for (int c : citations) {
                    if (c >= h) {
                        count++;
                    }
                }
                // if there are at least h such papers, then h is a valid h-index
                if (count >= h) {
                    return h;
                }
            }
            return 0;
        }
    }
}
