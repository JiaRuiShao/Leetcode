import java.util.ArrayList;
import java.util.List;

/**
 * 68. Text Justification
 */
public class _68 {
    class Solution1 {
        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> res = new ArrayList<>();
            List<String> curWords = new ArrayList<>();
            int curLen = 0;

            for (String word : words) {
                if (curLen + word.length() + curWords.size() > maxWidth) {
                    int totalSpaces = maxWidth - curLen;
                    int gaps = curWords.size() - 1;
                    if (gaps == 0) {
                        res.add(curWords.get(0) + " ".repeat(totalSpaces));
                    } else {
                        int spacePerGap = totalSpaces / gaps;
                        int extraSpaces = totalSpaces % gaps;
                        StringBuilder line = new StringBuilder();
                        for (int i = 0; i < curWords.size(); i++) {
                            line.append(curWords.get(i));
                            if (i < gaps) { // not the last word
                                line.append(" ".repeat(spacePerGap));
                                if (i < extraSpaces) { // i == 0 && extraSpaces > 0
                                    line.append(' ');
                                }
                            }
                        }
                        res.add(line.toString());
                    }
                    curWords.clear();
                    curLen = 0;
                }
                curWords.add(word);
                curLen += word.length();
            }

            StringBuilder lastLine = new StringBuilder(String.join(" ", curWords));
            while (lastLine.length() < maxWidth) {
                lastLine.append(' ');
            }
            res.add(lastLine.toString());

            return res;
        }
    }

    class Solution2 {
        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> padded = new ArrayList<>();
            List<String> currWords = new ArrayList<>();
            int currLen = 0;
            final String EMPTY = " ";
    
            for (String word: words) {
                if (currLen + word.length() + currWords.size() > maxWidth) {
                    StringBuilder sb = new StringBuilder();
                    int gaps = currWords.size() - 1;
                    if (gaps == 0) {
                        sb.append(currWords.get(0));
                        while (sb.length() < maxWidth) {
                            sb.append(EMPTY);
                        }
                    } else {
                        int totalSpaces = maxWidth - currLen;
                        int spacesPerGap = totalSpaces / gaps, extraSpaces = totalSpaces % gaps;
                        
                        for (int i = 0; i < currWords.size(); i++) {
                            String curr = currWords.get(i);
                            sb.append(curr);
                            if (i < gaps) { // not last word
                                int padding = spacesPerGap;
                                // if (i == 0 && extraSpaces > 0) padding += extraSpaces;
                                if (extraSpaces > 0) {
                                    padding++;
                                    extraSpaces--;
                                }
                                for (int j = 0; j < padding; j++) {
                                    sb.append(EMPTY);
                                }
                            }
                        }
                    }
                    padded.add(sb.toString());
                    currWords = new ArrayList<>();
                    currLen = 0;
                }
                currWords.add(word);
                currLen += word.length();
            }
    
            // last line
            if (currWords.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (String word : currWords) {
                    if (sb.length() > 0) sb.append(EMPTY);
                    sb.append(word);
                }
                while (sb.length() < maxWidth) {
                    sb.append(EMPTY);
                }
                padded.add(sb.toString());
            }
            return padded;
        }
    }
}
