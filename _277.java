
/**
 * 277. Find the Celebrity
 */
public class _277 {
    boolean knows(int a, int b) {
        return false;
    }

    public class Solution0_BF {
        public int findCelebrity(int n) {
            for (int i = 0; i < n; i++) {
                if (isCelebrity(i, n)) {
                    return i;
                }
            }
            return -1;
        }
        
        private boolean isCelebrity(int candidate, int n) {
            for (int j = 0; j < n; j++) {
                if (j == candidate) continue;
                
                // Candidate knows someone else → not celebrity
                if (knows(candidate, j)) {
                    return false;
                }
                
                // Someone doesn't know candidate → not celebrity
                if (!knows(j, candidate)) {
                    return false;
                }
            }
            return true;
        }
    }

    public class Solution1_EliminationAndValidation {
        public int findCelebrity(int n) {
            int candidate = 0;
            for (int i = 0; i < n; i++) {
                if (candidate == i) continue;
                if (knows(candidate, i)) {
                    candidate = i;
                }
                // else: i cannot be celebrity
            }

            // validate this candidate
            for (int i = 0; i < n; i++) {
                if (candidate == i) continue;
                if (!knows(i, candidate) || knows(candidate, i)) {
                    return -1;
                }
            }
            
            return candidate;
        }

        public int findCelebrityOptimized(int n) {
            int candidate = 0;
            for (int i = 1; i < n; i++) {
                if (knows(candidate, i)) {
                    candidate = i;
                }
            }

            for (int i = 0; i < n; i++) {
                if (candidate == i) continue;
                // for i > candidate, knows(candidate, i) == false
                if (i < candidate) {
                    if (knows(candidate, i) || !knows(i, candidate)) return -1;
                } else {
                    if (!knows(i, candidate)) return -1;
                }
            }

            return candidate;
        }
    }
}
